package shadowverse.cards.Royal.Evolve;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.cards.Neutral.Status.EvolutionPoint;
import shadowverse.characters.Royal;
import shadowverse.powers.RadiantLuminousMagePower;

public class RadiantLuminousMage extends CustomCard {
    public static final String ID = "shadowverse:RadiantLuminousMage";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:RadiantLuminousMage");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/RadiantLuminousMage.png";

    public RadiantLuminousMage() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, AbstractCard.CardType.POWER, Royal.Enums.COLOR_YELLOW, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.cardsToPreview = new EvolutionPoint();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new SFXAction(ID.replace("shadowverse:", "")));
        addToBot(new ApplyPowerAction(p, p, new RadiantLuminousMagePower(p, this.magicNumber)));
    }

    @Override
    public void triggerWhenDrawn() {
        if (this.upgraded) {
            this.addToTop(new MakeTempCardInHandAction(new EvolutionPoint(), 1));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new RadiantLuminousMage();
    }
}