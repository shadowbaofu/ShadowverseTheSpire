package shadowverse.cards.Dragon.Armed;


import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import rs.lazymankits.interfaces.cards.BranchableUpgradeCard;
import rs.lazymankits.interfaces.cards.UpgradeBranch;
import shadowverse.Shadowverse;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Dragon;
import shadowverse.powers.CutthroatPower;
import shadowverse.powers.OverflowPower;

import java.util.ArrayList;
import java.util.List;


public class Rola extends CustomCard implements BranchableUpgradeCard {
    public static final String ID = "shadowverse:Rola";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Rola");
    public static CardStrings cardStrings2 = CardCrawlGame.languagePack.getCardStrings("shadowverse:Rola2");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Rola.png";
    private static final Texture LEADER_SKIN_VERSION = ImageMaster.loadImage("img/cards/Rola_L.png");

    public int enhanceCost;

    public int baseCost;

    public boolean exFreeOnce;

    public int exCost;

    public int exCostForTurn;

    public int ex;

    public Rola() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, Dragon.Enums.COLOR_BROWN, CardRarity.RARE, CardTarget.SELF);
        this.baseBlock = 6;
        this.baseDamage = 18;
        this.tags.add(AbstractShadowversePlayer.Enums.MACHINE);
        this.jokePortrait = new TextureAtlas.AtlasRegion(LEADER_SKIN_VERSION, 0, 0, LEADER_SKIN_VERSION.getWidth(), LEADER_SKIN_VERSION.getHeight());
        this.baseCost = cost;
        this.exCost = cost;
        this.exCostForTurn = cost;
        this.exFreeOnce = false;
        this.ex = 0;
    }


    public void upgrade() {
        ((UpgradeBranch) ((BranchableUpgradeCard) this).possibleBranches().get(chosenBranch())).upgrade();
    }

    @Override
    public void update() {
        super.update();
        if (chosenBranch() == 0){
            if (AbstractDungeon.currMapNode != null && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT && AbstractDungeon.player != null) {
                if (Shadowverse.Enhance(enhanceCost)) {
                    if (this.ex == 0) {
                        this.exCost = this.cost;
                        this.exCostForTurn = this.costForTurn;
                    }
                    this.ex = 1;
                    this.exFreeOnce = this.freeToPlayOnce;
                    setCostForTurn(enhanceCost);
                } else {
                    if (this.ex > 0) {
                        setCostForTurn(baseCost);
                        this.cost = this.exCost;
                        this.costForTurn = this.exCostForTurn;
                        this.freeToPlayOnce = this.exFreeOnce;
                    }
                    this.ex = 0;
                }
            }
        }
    }


    public void enhanceUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
        for (AbstractCard card : p.hand.group) {
            if (card.hasTag(AbstractShadowversePlayer.Enums.MACHINE) && card != this) {
                addToBot(new GainBlockAction(p, this.block));
                break;
            }
        }
        if ((UnlockTracker.betaCardPref.getBoolean(this.cardID, false)))
            addToBot(new SFXAction("Rola_L_EH"));
        else
            addToBot(new SFXAction("Rola_EH"));
        if (p.hasPower(CutthroatPower.POWER_ID)){
            addToBot(new GainEnergyAction(2));
        }
        int count = 0;
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!mo.isDeadOrEscaped())
                count++;
        }
        if (count == 1) {
            addToBot(new DamageAction(AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true), new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
            addToBot(new DamageAction(AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true), new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        } else {
            addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(this.damage, true), DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE, true));
        }
    }

    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
        for (AbstractCard card : p.hand.group) {
            if (card.hasTag(AbstractShadowversePlayer.Enums.MACHINE) && card != this) {
                addToBot(new GainBlockAction(p, this.block));
                break;
            }
        }
        if ((UnlockTracker.betaCardPref.getBoolean(this.cardID, false)))
            addToBot(new SFXAction("Rola_L"));
        else
            addToBot(new SFXAction("Rola"));
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (chosenBranch()){
            case 0:
                if (this.costForTurn == enhanceCost && Shadowverse.Enhance(enhanceCost)) {
                    enhanceUse(p, m);
                    if (p.hasPower(CutthroatPower.POWER_ID)) {
                        addToBot(new GainEnergyAction(enhanceCost - 1));
                    }
                } else {
                    baseUse(p, m);
                }
                break;
            case 1:
                addToBot(new SFXAction("Rola2"));
                addToBot(new GainBlockAction(p, this.block));
                addToBot(new ApplyPowerAction(p,p,new OverflowPower(p,1)));
                int count = 0;
                for (AbstractCard card : p.hand.group) {
                    int cost = 0;
                    if (CardLibrary.getCard(card.cardID) != null) {
                        cost = CardLibrary.getCard(card.cardID).cost;
                    } else {
                        cost = card.cost;
                    }
                    if (cost >= 3 && card != this){
                        count++;
                    }
                }
                if (count >= 3){
                    addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(this.damage + this.magicNumber, true), DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE, true));
                }else {
                    addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(this.damage, true), DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE, true));
                }
                break;
        }
    }

    public AbstractCard makeCopy() {
        return (AbstractCard) new Rola();
    }

    @Override
    public List<UpgradeBranch> possibleBranches() {
        ArrayList<UpgradeBranch> list = new ArrayList<UpgradeBranch>();
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Rola.this.timesUpgraded;
                Rola.this.upgraded = true;
                Rola.this.name = cardStrings.NAME + "+";
                Rola.this.initializeTitle();
                Rola.this.baseBlock = 9;
                Rola.this.upgradedBlock = true;
                Rola.this.baseDamage = 21;
                Rola.this.upgradedDamage = true;
                Rola.this.initializeDescription();
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Rola.this.timesUpgraded;
                Rola.this.upgraded = true;
                Rola.this.name = cardStrings2.NAME;
                Rola.this.initializeTitle();
                Rola.this.rawDescription = cardStrings2.DESCRIPTION;
                Rola.this.initializeDescription();
                Rola.this.upgradeBaseCost(3);
                Rola.this.baseBlock = 9;
                Rola.this.upgradedBlock = true;
                Rola.this.baseDamage = 21;
                Rola.this.upgradedDamage = true;
                Rola.this.baseMagicNumber = 11;
                Rola.this.magicNumber = Rola.this.baseMagicNumber;
                Rola.this.upgradedMagicNumber = true;
            }
        });
        return list;
    }
}
