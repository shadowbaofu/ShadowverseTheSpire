package shadowverse.cards.Bishop.Amulet2;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import rs.lazymankits.interfaces.cards.BranchableUpgradeCard;
import rs.lazymankits.interfaces.cards.UpgradeBranch;
import shadowverse.action.ReduceAllCountDownAction;
import shadowverse.cards.Neutral.Status.EvolutionPoint;
import shadowverse.cards.Neutral.Temp.FieryReproach;
import shadowverse.cards.Neutral.Temp.HeavenFire;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Bishop;
import shadowverse.orbs.AmuletOrb;

import java.util.ArrayList;
import java.util.List;

public class Selena
        extends CustomCard implements BranchableUpgradeCard {
    public static final String ID = "shadowverse:Selena";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Selena");
    public static CardStrings cardStrings2 = CardCrawlGame.languagePack.getCardStrings("shadowverse:Selena2");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Selena.png";

    private static AbstractCard heavenFire = new HeavenFire();
    private static AbstractCard upgradedHeavenFire(){
        AbstractCard c = new HeavenFire();
        c.upgrade();
        return c;
    }

    private static AbstractCard fieryReproach = new FieryReproach();

    public Selena() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, Bishop.Enums.COLOR_WHITE, CardRarity.RARE, CardTarget.ALL);
        this.cardsToPreview = heavenFire;
        this.baseBlock = 12;
        this.baseDamage = 15;
        this.isMultiDamage = true;
    }


    public void upgrade() {
        ((UpgradeBranch) ((BranchableUpgradeCard) this).possibleBranches().get(chosenBranch())).upgrade();
    }


    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        switch (chosenBranch()){
            case 0:
                addToBot(new SFXAction("Selena"));
                addToBot(new VFXAction(new InflameEffect(p),0.3F));
                addToBot(new GainBlockAction(p,this.block));
                int amt = 0;
                for (AbstractOrb o:p.orbs){
                    if (o instanceof AmuletOrb && !((AmuletOrb) o).amulet.hasTag(AbstractShadowversePlayer.Enums.MINION)){
                        amt++;
                    }
                }
                if (amt>0)
                    addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy()));
                if (amt>1)
                    addToBot(new GainEnergyAction(1));
                if (amt>2)
                    addToBot(new DamageAllEnemiesAction(p,this.damage,this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
                if (amt>3)
                    addToBot(new ReduceAllCountDownAction(3));
                break;
            case 1:
                addToBot(new SFXAction("Selena2"));
                addToBot(new VFXAction(new InflameEffect(p),0.3F));
                addToBot(new GainBlockAction(p,this.block));
                for (AbstractCard card : p.hand.group){
                    if (card instanceof EvolutionPoint){
                        addToBot(new GainEnergyAction(1));
                        break;
                    }
                }
                addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy(),2));
                break;
        }
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new Selena();
    }

    @Override
    public List<UpgradeBranch> possibleBranches() {
        ArrayList<UpgradeBranch> list = new ArrayList<UpgradeBranch>();
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Selena.this.timesUpgraded;
                Selena.this.upgraded = true;
                Selena.this.name = cardStrings.NAME + "+";
                Selena.this.initializeTitle();
                Selena.this.cardsToPreview = upgradedHeavenFire();
                Selena.this.baseBlock = 16;
                Selena.this.upgradedBlock = true;
                Selena.this.baseDamage = 18;
                Selena.this.upgradedDamage = true;
                Selena.this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
                Selena.this.initializeDescription();
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Selena.this.timesUpgraded;
                Selena.this.upgraded = true;
                Selena.this.name = cardStrings2.NAME;
                Selena.this.initializeTitle();
                Selena.this.rawDescription = cardStrings2.DESCRIPTION;
                Selena.this.initializeDescription();
                Selena.this.cardsToPreview = fieryReproach;
            }
        });
        return list;
    }
}


