package shadowverse.cards.Neutral.Temp;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.cards.Neutral.Status.EvolutionPoint;
import shadowverse.characters.Vampire;
import shadowverse.powers.DreadAuraPower;

public class HowToTrainYourWolf extends CustomCard {
    public static final String ID = "shadowverse:HowToTrainYourWolf";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:HowToTrainYourWolf");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/HowToTrainYourWolf.png";

    public HowToTrainYourWolf() {
        super(ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.SKILL, Vampire.Enums.COLOR_SCARLET, CardRarity.SPECIAL, CardTarget.SELF);
        this.exhaust = true;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.exhaust = false;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new DreadAuraPower(p)));
        addToBot(new MakeTempCardInHandAction(new EvolutionPoint()));
        addToBot(new MakeTempCardInHandAction(new Miracle()));
    }


    public AbstractCard makeCopy() {
        return new HowToTrainYourWolf();
    }
}
