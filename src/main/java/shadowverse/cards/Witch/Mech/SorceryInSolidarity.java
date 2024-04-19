package shadowverse.cards.Witch.Mech;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.actions.unique.AttackFromDeckToHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rs.lazymankits.interfaces.cards.BranchableUpgradeCard;
import rs.lazymankits.interfaces.cards.UpgradeBranch;
import shadowverse.cards.Neutral.Temp.IsabellesConjuration;
import shadowverse.cards.Neutral.Temp.RepairMode;
import shadowverse.cards.Neutral.Temp.TetrasMettle;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Witchcraft;
import shadowverse.powers.MachineAttackPower;
import shadowverse.powers.MachineDrawPower;

import java.util.ArrayList;
import java.util.List;


public class SorceryInSolidarity
        extends CustomCard implements BranchableUpgradeCard {
    public static final String ID = "shadowverse:SorceryInSolidarity";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:SorceryInSolidarity");
    public static CardStrings cardStrings2 = CardCrawlGame.languagePack.getCardStrings("shadowverse:SorceryInSolidarity2");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/SorceryInSolidarity.png";

    public SorceryInSolidarity() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.SKILL, Witchcraft.Enums.COLOR_BLUE, CardRarity.UNCOMMON, CardTarget.NONE);
        this.baseMagicNumber = 0;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(AbstractShadowversePlayer.Enums.SPELL_BOOST_ATTACK);
        this.exhaust = true;
    }


    public void upgrade() {
        ((UpgradeBranch)((BranchableUpgradeCard)this).possibleBranches().get(chosenBranch())).upgrade();
    }

    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (chosenBranch() == 0){
            if (c.type == CardType.SKILL) {
                flash();
                this.magicNumber = ++this.baseMagicNumber;
                addToBot(new SFXAction("spell_boost"));
            }
        }else {
            if (c.hasTag(AbstractShadowversePlayer.Enums.MACHINE)) {
                flash();
                addToBot(new SFXAction("spell_boost"));
                addToBot(new ReduceCostAction(this));
            }
        }
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        switch (chosenBranch()){
            case 0:
                if (this.magicNumber >= 3) {
                    addToBot(new SFXAction("SorceryInSolidarity"));
                    addToBot(new AttackFromDeckToHandAction(1));
                    addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new MachineDrawPower(abstractPlayer, 1), 1));
                } else {
                    ArrayList<AbstractCard> stanceChoices = new ArrayList<>();
                    stanceChoices.add(new IsabellesConjuration());
                    stanceChoices.add(new TetrasMettle());
                    addToBot(new ChooseOneAction(stanceChoices));
                }
                this.baseMagicNumber = 0;
                this.magicNumber = this.baseMagicNumber;
                break;
            case 1:
                addToBot(new MakeTempCardInHandAction(new RepairMode(),2));
                addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new MachineAttackPower(abstractPlayer, 5), 5));
                this.cost = 3;
                break;
        }
    }


    public void applyPowers() {
        super.applyPowers();
        if (chosenBranch() == 0){
            int count = this.magicNumber;
            this.rawDescription = cardStrings.DESCRIPTION;
            this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + count;
            this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[1];
            initializeDescription();
        }
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new SorceryInSolidarity();
    }

    @Override
    public List<UpgradeBranch> possibleBranches() {
        ArrayList<UpgradeBranch> list = new ArrayList<UpgradeBranch>();
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++SorceryInSolidarity.this.timesUpgraded;
                SorceryInSolidarity.this.upgraded = true;
                SorceryInSolidarity.this.name = cardStrings.NAME + "+";
                SorceryInSolidarity.this.initializeTitle();
                SorceryInSolidarity.this.upgradeBaseCost(0);
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++SorceryInSolidarity.this.timesUpgraded;
                SorceryInSolidarity.this.upgraded = true;
                SorceryInSolidarity.this.name = cardStrings2.NAME;
                SorceryInSolidarity.this.initializeTitle();
                SorceryInSolidarity.this.rawDescription = cardStrings2.DESCRIPTION;
                SorceryInSolidarity.this.initializeDescription();
                SorceryInSolidarity.this.upgradeBaseCost(3);
                SorceryInSolidarity.this.exhaust = false;
                SorceryInSolidarity.this.tags.remove(AbstractShadowversePlayer.Enums.SPELL_BOOST_ATTACK);
                SorceryInSolidarity.this.cardsToPreview = new RepairMode();
            }
        });
        return list;
    }
}
 