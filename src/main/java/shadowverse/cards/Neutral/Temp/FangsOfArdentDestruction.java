package shadowverse.cards.Neutral.Temp;


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import shadowverse.characters.Dragon;


public class FangsOfArdentDestruction extends CustomCard {
    public static final String ID = "shadowverse:FangsOfArdentDestruction";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:FangsOfArdentDestruction");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/FangsOfArdentDestruction.png";

    public FangsOfArdentDestruction() {
        super(ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.SKILL, Dragon.Enums.COLOR_BROWN, CardRarity.SPECIAL, CardTarget.SELF);
        this.cardsToPreview = new Burn();
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
        this.isEthereal = true;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.cardsToPreview.upgrade();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("FangsOfArdentDestruction"));
        if (!upgraded) {
            addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy()));
        } else {
            addToBot(new MakeTempCardInDiscardAction(this.cardsToPreview.makeStatEquivalentCopy(), 1));
        }
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new StrengthPower(abstractPlayer, this.magicNumber), this.magicNumber));
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new DexterityPower(abstractPlayer, this.magicNumber), this.magicNumber));
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new FangsOfArdentDestruction();
    }
}

