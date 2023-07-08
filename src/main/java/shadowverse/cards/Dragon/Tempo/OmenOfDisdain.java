package shadowverse.cards.Dragon.Tempo;


import basemod.abstracts.CustomCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.ClawEffect;
import rs.lazymankits.interfaces.cards.BranchableUpgradeCard;
import rs.lazymankits.interfaces.cards.UpgradeBranch;
import shadowverse.Shadowverse;
import shadowverse.cards.Neutral.Temp.FangsOfArdentDestruction;
import shadowverse.characters.Dragon;

import java.util.ArrayList;
import java.util.List;

public class OmenOfDisdain extends CustomCard implements BranchableUpgradeCard {
    public static final String ID = "shadowverse:OmenOfDisdain";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:OmenOfDisdain");
    public static CardStrings cardStrings2 = CardCrawlGame.languagePack.getCardStrings("shadowverse:OmenOfDisdain2");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/OmenOfDisdain.png";
    public static final String IMG_PATH2 = "img/cards/OmenOfDisdain2.png";
    private static final String TEXT = CardCrawlGame.languagePack.getUIString("shadowverse:Exhaust").TEXT[0];
    private boolean branch2 = false;
    public int enhanceCost;
    public int baseCost;

    public boolean exFreeOnce;

    public int exCost;

    public int exCostForTurn;

    public int ex;

    private float rotationTimer;
    private int previewIndex;

    public static ArrayList<AbstractCard> returnChoice() {
        ArrayList<AbstractCard> list = new ArrayList<>();
        list.add(new FangsOfArdentDestruction());
        list.add(new DisdainfulRending());
        list.add(new Burn());
        return list;
    }

    public OmenOfDisdain() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, Dragon.Enums.COLOR_BROWN, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage = 20;
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        this.baseCost = cost;
        this.enhanceCost = 3;
        this.exCost = cost;
        this.exCostForTurn = cost;
        this.exFreeOnce = false;
        this.ex = 0;
    }


    public void update() {
        super.update();
        if (!branch2) {
            if (AbstractDungeon.currMapNode != null && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
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
                        setCostForTurn(enhanceCost);
                        this.cost = this.exCost;
                        this.costForTurn = this.exCostForTurn;
                        this.freeToPlayOnce = this.exFreeOnce;
                    }
                    this.ex = 0;
                }
            }
        }else {
            if (this.hb.hovered)
                if (this.rotationTimer <= 0.0F) {
                    this.rotationTimer = 2.0F;
                    this.cardsToPreview = returnChoice().get(previewIndex).makeCopy();
                    if (this.previewIndex == returnChoice().size() - 1) {
                        this.previewIndex = 0;
                    } else {
                        this.previewIndex++;
                    }
                } else {
                    this.rotationTimer -= Gdx.graphics.getDeltaTime();
                }
        }
    }

    public void upgrade() {
        ((UpgradeBranch) ((BranchableUpgradeCard) this).possibleBranches().get(chosenBranch())).upgrade();
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        switch (chosenBranch()) {
            case 0:
                if (this.costForTurn == 3) {
                    addToBot(new SFXAction("OmenOfDisdain_Eh"));
                    if (abstractMonster != null)
                        addToBot(new VFXAction(new ClawEffect(abstractMonster.hb.cX, abstractMonster.hb.cY, Color.SCARLET, Color.ORANGE), 0.1F));
                    addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
                    addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(this.magicNumber, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
                    addToBot(new MakeTempCardInHandAction(new Burn()));
                    for (AbstractCard c : abstractPlayer.hand.group) {
                        if (c instanceof Burn) {
                            addToBot(new ExhaustSpecificCardAction(c, abstractPlayer.hand));
                            addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(this.magicNumber, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
                        }
                    }
                } else {
                    addToBot(new SFXAction("OmenOfDisdain"));
                    if (abstractMonster != null)
                        addToBot(new VFXAction(new ClawEffect(abstractMonster.hb.cX, abstractMonster.hb.cY, Color.SCARLET, Color.ORANGE), 0.1F));
                    addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
                    for (AbstractCard c : abstractPlayer.hand.group) {
                        if (c instanceof Burn) {
                            addToBot(new ExhaustSpecificCardAction(c, abstractPlayer.hand));
                            addToBot(new DamageRandomEnemyAction(new DamageInfo(abstractPlayer, this.magicNumber, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
                        }
                    }
                }

                break;
            case 1:
                addToBot(new SFXAction("OmenOfDisdain2"));
                if (abstractMonster != null)
                    addToBot(new VFXAction(new ClawEffect(abstractMonster.hb.cX, abstractMonster.hb.cY, Color.SCARLET, Color.ORANGE), 0.1F));
                addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
                addToBot(new MakeTempCardInHandAction(new Burn()));
                addToBot(new MakeTempCardInHandAction(new FangsOfArdentDestruction()));
                AbstractCard tmp = new DisdainfulRending();
                tmp.exhaustOnUseOnce = true;
                tmp.exhaust = true;
                tmp.rawDescription += " NL " + TEXT + " 。";
                tmp.initializeDescription();
                tmp.applyPowers();
                AbstractDungeon.player.hand.addToTop(tmp);
                for (AbstractCard c : abstractPlayer.hand.group) {
                    if (c instanceof Burn) {
                        addToBot(new ExhaustSpecificCardAction(c, abstractPlayer.hand));
                        addToBot(new MakeTempCardInHandAction(new FangsOfArdentDestruction()));
                    }
                }
                break;
        }
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new OmenOfDisdain();
    }

    @Override
    public List<UpgradeBranch> possibleBranches() {
        ArrayList<UpgradeBranch> list = new ArrayList<UpgradeBranch>();
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++OmenOfDisdain.this.timesUpgraded;
                OmenOfDisdain.this.upgraded = true;
                OmenOfDisdain.this.name = NAME + "+";
                OmenOfDisdain.this.initializeTitle();
                OmenOfDisdain.this.baseDamage = 25;
                OmenOfDisdain.this.upgradedDamage = true;
                OmenOfDisdain.this.baseMagicNumber = 5;
                OmenOfDisdain.this.magicNumber = OmenOfDisdain.this.baseMagicNumber;
                OmenOfDisdain.this.upgradedMagicNumber = true;
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++OmenOfDisdain.this.timesUpgraded;
                OmenOfDisdain.this.textureImg = IMG_PATH2;
                OmenOfDisdain.this.loadCardImage(IMG_PATH2);
                OmenOfDisdain.this.name = cardStrings2.NAME;
                OmenOfDisdain.this.initializeTitle();
                OmenOfDisdain.this.rawDescription = cardStrings2.DESCRIPTION;
                OmenOfDisdain.this.initializeDescription();
                OmenOfDisdain.this.branch2 = true;
                OmenOfDisdain.this.cardsToPreview = new FangsOfArdentDestruction();
                OmenOfDisdain.this.upgraded = true;
            }
        });
        return list;
    }
}

