package shadowverse.cards.Neutral.Temp;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.cards.AbstractNeutralCard;
import shadowverse.cards.Neutral.Neutral.WingedInversion;
import shadowverse.characters.AbstractShadowversePlayer;

public class CorruptionGuardian extends AbstractNeutralCard {
    public static final String ID = "shadowverse:CorruptionGuardian";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:CorruptionGuardian");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/CorruptionGuardian.png";

    public CorruptionGuardian() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.SELF);
        this.baseBlock = 16;
        this.cardsToPreview = new PurifyGuardian();
        this.tags.add(AbstractShadowversePlayer.Enums.DARK_ANGEL);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded){
            upgradeName();
            upgradeBlock(3);
            this.cardsToPreview.upgrade();
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new GainBlockAction(abstractPlayer,this.block));
        addToBot(new MakeTempCardInHandAction(new WingedInversion()));
        addToBot(new SelectCardsInHandAction(1, TEXT[0], false, true, card -> card.type == CardType.ATTACK
                && card.color == CardColor.COLORLESS, abstractCards -> {
            for (AbstractCard c : abstractCards) {
                addToBot(new DiscardSpecificCardAction(c));
                addToBot(new DrawCardAction(1));
                addToBot(new GainEnergyAction(1));
            }
        }));
    }
}
