package shadowverse.cards.Nemesis.Tokens;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import rs.lazymankits.interfaces.cards.BranchableUpgradeCard;
import rs.lazymankits.interfaces.cards.UpgradeBranch;
import shadowverse.cards.Neutral.Temp.ForbiddenArt;
import shadowverse.cards.Neutral.Temp.ForbiddenPower;
import shadowverse.cards.Neutral.Temp.HollowGovernance;
import shadowverse.cards.Neutral.Temp.VoidRealize;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Nemesis;
import shadowverse.orbs.AmbushMinion;
import shadowverse.orbs.ErikaOrb;
import shadowverse.orbs.Minion;

import java.util.ArrayList;
import java.util.List;


public class Illganeau
        extends CustomCard implements BranchableUpgradeCard {
    public static final String ID = "shadowverse:Illganeau";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Illganeau");
    public static CardStrings cardStrings2 = CardCrawlGame.languagePack.getCardStrings("shadowverse:Illganeau2");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Illganeau.png";

    private static AbstractCard realize = new VoidRealize();
    private static AbstractCard hollow = new HollowGovernance();
    private int branchPreview;

    public Illganeau() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, Nemesis.Enums.COLOR_SKY, CardRarity.RARE, CardTarget.SELF);
        this.tags.add(AbstractShadowversePlayer.Enums.LASTWORD);
        this.baseBlock = 5;
        this.exhaust = true;
    }

    public void update(){
        super.update();
        if (branchPreview == 0){
            this.cardsToPreview = realize;
        }else if (branchPreview == 3){
            this.cardsToPreview = hollow;
        }
    }


    public void upgrade() {
        ((UpgradeBranch) ((BranchableUpgradeCard) this).possibleBranches().get(chosenBranch())).upgrade();
    }

    @Override
    public void triggerOnExhaust() {
        if (chosenBranch() == 0){
            int dex = 0;
            for (AbstractPower p : AbstractDungeon.player.powers) {
                if (p.ID.equals(DexterityPower.POWER_ID))
                    dex = p.amount;
            }
            if (dex >= 3)
                addToBot( new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy()));
            else{
                addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,(AbstractPower)new DexterityPower(AbstractDungeon.player,1),1));
                addToBot( new MakeTempCardInHandAction(this.makeStatEquivalentCopy()));
            }
        }
    }

    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        switch (chosenBranch()){
            case 0:
                addToBot( new SFXAction("Illganeau"));
                addToBot(new GainBlockAction(abstractPlayer,this.block));
                addToBot(new VFXAction(abstractPlayer, new VerticalAuraEffect(Color.BLACK, abstractPlayer.hb.cX,abstractPlayer.hb.cY), 0.33F));
                addToBot(new VFXAction(abstractPlayer, new VerticalAuraEffect(Color.PURPLE, abstractPlayer.hb.cX, abstractPlayer.hb.cY), 0.33F));
                break;
            case 1:
                addToBot( new SFXAction("Illganeau2"));
                addToBot(new VFXAction(abstractPlayer, new VerticalAuraEffect(Color.BLACK, abstractPlayer.hb.cX,abstractPlayer.hb.cY), 0.33F));
                addToBot(new VFXAction(abstractPlayer, new VerticalAuraEffect(Color.PURPLE, abstractPlayer.hb.cX, abstractPlayer.hb.cY), 0.33F));
                for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters){
                    if (mo!=null && !mo.isDeadOrEscaped() && !mo.isDying && !mo.halfDead){
                        addToBot(new ApplyPowerAction(mo, abstractPlayer, new StrengthPower(mo, -1), -1, true, AbstractGameAction.AttackEffect.NONE));
                        addToBot(new ApplyPowerAction(mo, abstractPlayer, new DexterityPower(mo, -1), -1, true, AbstractGameAction.AttackEffect.NONE));
                    }
                }
                if (rally() > 20){
                    addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy()));
                    this.exhaust = true;
                    abstractPlayer.hand.removeCard(this);
                }
                break;
        }
    }

    public int rally() {
        int rally = 0;

        for (AbstractOrb o : AbstractDungeon.actionManager.orbsChanneledThisCombat) {
            if (o instanceof Minion && !(o instanceof AmbushMinion) && !(o instanceof ErikaOrb)) {
                rally++;
            }
        }

        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (c.type == CardType.ATTACK && !(c.hasTag(CardTags.STRIKE))) {
                rally++;
            }
        }
        return rally;
    }

    @Override
    public void applyPowers() {
        if (chosenBranch() == 1){
            super.applyPowers();
            this.rawDescription = cardStrings2.DESCRIPTION;
            this.rawDescription += cardStrings2.EXTENDED_DESCRIPTION[0] + rally() + cardStrings.EXTENDED_DESCRIPTION[1];
            this.initializeDescription();
        }else {
            super.applyPowers();
        }
    }

    @Override
    public List<UpgradeBranch> possibleBranches() {
        ArrayList<UpgradeBranch> list = new ArrayList<UpgradeBranch>();
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Illganeau.this.timesUpgraded;
                Illganeau.this.upgraded = true;
                Illganeau.this.name = cardStrings.NAME + "+";
                Illganeau.this.initializeTitle();
                Illganeau.this.baseBlock = 8;
                Illganeau.this.upgradedBlock = true;
                Illganeau.this.initializeDescription();
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Illganeau.this.timesUpgraded;
                Illganeau.this.upgraded = true;
                Illganeau.this.name = cardStrings2.NAME;
                Illganeau.this.initializeTitle();
                Illganeau.this.upgradeBaseCost(0);
                Illganeau.this.baseBlock = 0;
                Illganeau.this.upgradedBlock = true;
                Illganeau.this.exhaust = false;
                Illganeau.this.rawDescription = cardStrings2.DESCRIPTION;
                Illganeau.this.initializeDescription();
            }
        });
        return list;
    }

    public AbstractCard makeCopy() {
        return (AbstractCard) new Illganeau();
    }
}


