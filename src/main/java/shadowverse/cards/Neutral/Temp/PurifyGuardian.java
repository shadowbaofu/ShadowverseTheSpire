package shadowverse.cards.Neutral.Temp;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;
import shadowverse.cards.AbstractNeutralCard;

public class PurifyGuardian extends AbstractNeutralCard {
    public static final String ID = "shadowverse:PurifyGuardian";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:PurifyGuardian");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/PurifyGuardian.png";

    public PurifyGuardian() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.SELF);
        this.baseBlock = 16;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded){
            upgradeName();
            upgradeBlock(3);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new GainBlockAction(abstractPlayer,this.block));
        addToBot(new SelectCardsInHandAction(1, TEXT[0], false, true, card -> card.type == CardType.ATTACK
                && card.color == CardColor.COLORLESS, abstractCards -> {
            for (AbstractCard c : abstractCards) {
                addToBot(new DiscardSpecificCardAction(c));
                addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new BufferPower(abstractPlayer,1)));
            }
        }));
    }
}
