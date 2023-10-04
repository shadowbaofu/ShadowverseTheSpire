package shadowverse.cards.Witch.Chess;


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.cards.Neutral.Temp.MagicalPawn;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Witchcraft;


public class Checkmate extends CustomCard {
    public static final String ID = "shadowverse:Checkmate";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Checkmate");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Checkmate.png";

    public Checkmate() {
        super(ID, NAME, IMG_PATH, 3, DESCRIPTION, CardType.SKILL, Witchcraft.Enums.COLOR_BLUE, CardRarity.COMMON, CardTarget.SELF);
        this.cardsToPreview = new MagicalPawn();
        this.tags.add(AbstractShadowversePlayer.Enums.CHESS);
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DiscardAction(abstractPlayer,abstractPlayer,abstractPlayer.hand.size()-1,false));
        int amount = abstractPlayer.hand.size()-1;
        if (this.upgraded)
            amount++;
        addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy(),amount));
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new Checkmate();
    }
}

