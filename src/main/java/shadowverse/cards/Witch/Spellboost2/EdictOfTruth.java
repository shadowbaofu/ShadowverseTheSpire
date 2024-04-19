package shadowverse.cards.Witch.Spellboost2;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.unique.ExpertiseAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Witchcraft;


public class EdictOfTruth
        extends CustomCard {
    public static final String ID = "shadowverse:EdictOfTruth";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:EdictOfTruth");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/EdictOfTruth.png";

    public EdictOfTruth() {
        super("shadowverse:EdictOfTruth", NAME, "img/cards/EdictOfTruth.png", 3, DESCRIPTION, CardType.SKILL, Witchcraft.Enums.COLOR_BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
        this.tags.add(AbstractShadowversePlayer.Enums.SPELL_BOOST_ATTACK);
        this.baseMagicNumber = 0;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
        this.selfRetain = true;
    }


    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (c.type == CardType.SKILL) {
            flash();
            addToBot((AbstractGameAction) new SFXAction("spell_boost"));

            this.magicNumber = ++this.baseMagicNumber;
        }
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(2);
        }
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        CardCrawlGame.sound.playA("EdictOfTruth", 0.0F);
        addToBot((AbstractGameAction) new ExpertiseAction((AbstractCreature) abstractPlayer, 10));
        if (this.magicNumber >= 6) {
            addToBot((AbstractGameAction) new GainEnergyAction(2));
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
        return (AbstractCard) new EdictOfTruth();
    }
}

