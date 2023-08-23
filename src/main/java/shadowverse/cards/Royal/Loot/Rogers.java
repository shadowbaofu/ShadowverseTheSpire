package shadowverse.cards.Royal.Loot;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.action.MinionSummonAction;
import shadowverse.cards.Neutral.Temp.GildedGoblet;
import shadowverse.characters.Royal;
import shadowverse.orbs.Cannoneer;
import shadowverse.powers.RogersPower;


public class Rogers extends CustomCard {
    public static final String ID = "shadowverse:Rogers";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Rogers");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Rogers.png";


    public Rogers() {
        super(ID, NAME, IMG_PATH, 3, DESCRIPTION, CardType.POWER, Royal.Enums.COLOR_YELLOW, CardRarity.RARE, CardTarget.SELF);
        this.isEthereal = true;
        this.cardsToPreview = new GildedGoblet();
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction(ID.replace("shadowverse:", "")));
        addToBot(new GainEnergyAction(1));
        addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy()));
        if (upgraded){
            addToBot(new ApplyPowerAction(p, p, new RogersPower(p)));
            addToBot(new MinionSummonAction(new Cannoneer()));
        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new Rogers();
    }
}

