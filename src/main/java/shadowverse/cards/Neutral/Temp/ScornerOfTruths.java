package shadowverse.cards.Neutral.Temp;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.action.ImaginationAction;
import shadowverse.characters.Witchcraft;

public class ScornerOfTruths extends CustomCard {
    public static final String ID = "shadowverse:ScornerOfTruths";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:ScornerOfTruths");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/ScornerOfTruths.png";

    public ScornerOfTruths() {
        super(ID, NAME, IMG_PATH, 3, DESCRIPTION, CardType.ATTACK, Witchcraft.Enums.COLOR_BLUE, CardRarity.SPECIAL, CardTarget.SELF);
        this.baseBlock = 21;
        this.exhaust = true;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(7);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("ScornerOfTruths"));
        addToBot(new GainBlockAction(abstractPlayer, this.block));
        addToBot(new DrawCardAction(5, new ImaginationAction(this.upgraded)));
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new ScornerOfTruths();
    }
}

