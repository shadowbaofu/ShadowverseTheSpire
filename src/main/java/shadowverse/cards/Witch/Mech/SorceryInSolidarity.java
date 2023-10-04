package shadowverse.cards.Witch.Mech;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.AttackFromDeckToHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.cards.Neutral.Temp.IsabellesConjuration;
import shadowverse.cards.Neutral.Temp.TetrasMettle;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Witchcraft;
import shadowverse.powers.MachineDrawPower;

import java.util.ArrayList;


public class SorceryInSolidarity
        extends CustomCard {
    public static final String ID = "shadowverse:SorceryInSolidarity";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:SorceryInSolidarity");
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
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (c.type == CardType.SKILL) {
            flash();

            this.magicNumber = ++this.baseMagicNumber;
            addToBot(new SFXAction("spell_boost"));
        }
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
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
    }


    public void applyPowers() {
        super.applyPowers();
        int count = this.magicNumber;
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + count;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[1];
        initializeDescription();
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new SorceryInSolidarity();
    }
}
 