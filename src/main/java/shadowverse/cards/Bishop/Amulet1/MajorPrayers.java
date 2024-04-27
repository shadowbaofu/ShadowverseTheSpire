package shadowverse.cards.Bishop.Amulet1;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import rs.lazymankits.interfaces.cards.BranchableUpgradeCard;
import rs.lazymankits.interfaces.cards.UpgradeBranch;
import shadowverse.action.EvokeAmuletAction;
import shadowverse.cards.AbstractAmuletCard;
import shadowverse.cards.AbstractCrystalizeCard;
import shadowverse.cards.AbstractNoCountDownAmulet;
import shadowverse.cards.Neutral.Temp.*;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Bishop;
import shadowverse.orbs.AmuletOrb;

import java.util.ArrayList;
import java.util.List;


public class MajorPrayers
        extends CustomCard implements BranchableUpgradeCard {
    public static final String ID = "shadowverse:MajorPrayers";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:MajorPrayers");
    public static CardStrings cardStrings2 = CardCrawlGame.languagePack.getCardStrings("shadowverse:MajorPrayers2");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/MajorPrayers.png";

    public MajorPrayers() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.SKILL, Bishop.Enums.COLOR_WHITE, CardRarity.UNCOMMON, CardTarget.NONE);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.cardsToPreview = (AbstractCard)new Munyaru();
    }


    public void upgrade() {
        ((UpgradeBranch) ((BranchableUpgradeCard) this).possibleBranches().get(chosenBranch())).upgrade();
    }

    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (chosenBranch()!=0){
            if (c instanceof AbstractAmuletCard || (c instanceof AbstractCrystalizeCard && c.type==CardType.POWER) || c instanceof AbstractNoCountDownAmulet) {
                flash();
                addToBot(new SFXAction("spell_boost"));
                addToBot(new ReduceCostAction(this));
            }
            if (c instanceof NaterranGreatTree){
                flash();
                addToBot(new SFXAction("spell_boost"));
                addToBot(new ReduceCostAction(this));
            }
        }
    }

    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        switch (chosenBranch()){
            case 0:
                int amt = 0;
                for (AbstractOrb o:abstractPlayer.orbs){
                    if (o instanceof AmuletOrb && ((AmuletOrb) o).amulet.type!=CardType.CURSE && !((AmuletOrb) o).amulet.hasTag(AbstractShadowversePlayer.Enums.MINION)){
                        amt++;
                    }
                }
                if (amt>=2){
                    addToBot(new SFXAction("MajorPrayers"));
                    addToBot(new DrawCardAction(this.magicNumber));
                    addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy()));
                }else{
                    ArrayList<AbstractCard> stanceChoices = new ArrayList<>();
                    AbstractCard m = new MunyaruRaid();
                    AbstractCard e = new ErisPrayer();
                    if (this.upgraded){
                        m.upgrade();
                        e.upgrade();
                    }
                    stanceChoices.add(m);
                    stanceChoices.add(e);
                    addToBot( new ChooseOneAction(stanceChoices));
                }
                break;
            case 1:
                addToBot(new SFXAction("MajorPrayers2"));
                addToBot(new EvokeAmuletAction());
                AbstractMonster mo = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster) null, true, AbstractDungeon.cardRandomRng);
                addToBot(new ApplyPowerAction(mo, AbstractDungeon.player, new WeakPower(mo, 2, false), 2, true, AbstractGameAction.AttackEffect.NONE));
                addToBot(new ApplyPowerAction(mo, AbstractDungeon.player, new VulnerablePower(mo, 2, false), 2, true, AbstractGameAction.AttackEffect.NONE));
                this.cost = 4;
                break;
        }
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new MajorPrayers();
    }

    @Override
    public List<UpgradeBranch> possibleBranches() {
        ArrayList<UpgradeBranch> list = new ArrayList<UpgradeBranch>();
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++MajorPrayers.this.timesUpgraded;
                MajorPrayers.this.upgraded = true;
                MajorPrayers.this.name = cardStrings.NAME + "+";
                MajorPrayers.this.initializeTitle();
                MajorPrayers.this.baseMagicNumber = 3;
                MajorPrayers.this.magicNumber = MajorPrayers.this.baseMagicNumber;
                MajorPrayers.this.upgradedMagicNumber = true;
                MajorPrayers.this.cardsToPreview.upgrade();
                MajorPrayers.this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
                MajorPrayers.this.initializeDescription();
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++MajorPrayers.this.timesUpgraded;
                MajorPrayers.this.upgraded = true;
                MajorPrayers.this.name = cardStrings2.NAME;
                MajorPrayers.this.initializeTitle();
                MajorPrayers.this.upgradeBaseCost(4);
                MajorPrayers.this.rawDescription = cardStrings2.DESCRIPTION;
                MajorPrayers.this.initializeDescription();
                MajorPrayers.this.cardsToPreview = null;
            }
        });
        return list;
    }
}

