package shadowverse.cards.Neutral.Temp;

import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import shadowverse.characters.AbstractShadowversePlayer;

public class ReturnToHeaven extends CustomCard {
    public static final String ID = "shadowverse:ReturnToHeaven";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:ReturnToHeaven");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/ReturnToHeaven.png";

    public ReturnToHeaven() {
        super(ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);
        this.exhaust = true;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardsInHandAction(1, TEXT[0], false, true, card -> (card.type == CardType.ATTACK
                && card.color == CardColor.COLORLESS) || card instanceof DarkGabriel, abstractCards -> {
            for (AbstractCard c : abstractCards) {
                addToBot(new ExhaustSpecificCardAction(c,p.hand));
                if (c.hasTag(AbstractShadowversePlayer.Enums.DARK_ANGEL) && c.cardsToPreview != null && c.cardsToPreview.type == CardType.ATTACK){
                    addToBot(new MakeTempCardInHandAction(c.cardsToPreview.makeStatEquivalentCopy()));
                }else {
                    addToBot(new MakeTempCardInHandAction(c.makeSameInstanceOf()));
                }
                addToBot(new GainEnergyAction(this.magicNumber));
            }
        }));
    }


    public AbstractCard makeCopy() {
        return new ReturnToHeaven();
    }
}
