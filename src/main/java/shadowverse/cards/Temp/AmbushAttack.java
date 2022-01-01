package shadowverse.cards.Temp;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.characters.Royal;

public class AmbushAttack extends CustomCard {
    public static final String ID = "shadowverse:AmbushAttack";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:AmbushAttack");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/AmbushAttack.png";

    public AmbushAttack() {
        super(ID, NAME, IMG_PATH, -2, DESCRIPTION, CardType.SKILL, Royal.Enums.COLOR_YELLOW, CardRarity.SPECIAL, CardTarget.NONE);
    }


    public void upgrade() {
    }



    public void use(AbstractPlayer p, AbstractMonster m) {
        onChoseThisOption();
    }

    public void onChoseThisOption() {
    }

    public AbstractCard makeCopy() {
        return new AmbushAttack();
    }
}
