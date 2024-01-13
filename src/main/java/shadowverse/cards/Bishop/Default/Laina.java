package shadowverse.cards.Bishop.Default;

import basemod.abstracts.CustomCard;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.cards.Neutral.Curse.DivineMinister;
import shadowverse.characters.Bishop;
import shadowverse.powers.LainaPower;
public class Laina
        extends CustomCard {
    public static final String ID = "shadowverse:Laina";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Laina");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Laina_E.png";
    public static final String IMG_PATH_EV = "img/cards/Laina.png";

    public Laina() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.POWER, Bishop.Enums.COLOR_WHITE, CardRarity.RARE, CardTarget.SELF);
        this.cardsToPreview = new DivineMinister();
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            textureImg = IMG_PATH_EV;
            loadCardImage(IMG_PATH_EV);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }


    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        if (upgraded){
            addToBot(new SFXAction("Laina"));
        }else {
            addToBot(new SFXAction("Laina_E"));
        }
        addToBot(new ApplyPowerAction(p,p,new LainaPower(p,this.upgraded)));
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new Laina();
    }
}


