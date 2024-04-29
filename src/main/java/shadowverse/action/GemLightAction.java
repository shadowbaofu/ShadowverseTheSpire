package shadowverse.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class GemLightAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");

    public static final String[] TEXT = uiStrings.TEXT;

    private boolean isCrystalBright;

    public GemLightAction(int amount, boolean isCrystalBright) {
        this.actionType = ActionType.EXHAUST;
        this.duration = 0.25F;
        this.amount = amount;
        this.isCrystalBright = isCrystalBright;
    }

    @Override
    public void update() {
        for (int i = 0; i < amount; i++){
            generateCard();
        }
        if (isCrystalBright){
            generateCard2();
        }
        this.isDone = true;
    }

    private void generateCard() {
        AbstractCard.CardRarity cardRarity;
        AbstractCard c = null;
        int roll = AbstractDungeon.cardRandomRng.random(99);
        if (roll < 55) {
            cardRarity = AbstractCard.CardRarity.COMMON;
        } else if (roll < 85) {
            cardRarity = AbstractCard.CardRarity.UNCOMMON;
        } else {
            cardRarity = AbstractCard.CardRarity.RARE;
        }
        c = AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy();
        while (c.rarity != cardRarity) {
            c = AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy();
        }
        addToBot(new MakeTempCardInHandAction(c));
    }

    private void generateCard2() {
        AbstractCard.CardRarity cardRarity;
        AbstractCard c = null;
        cardRarity = AbstractCard.CardRarity.RARE;
        c = AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy();
        while (c.rarity != cardRarity) {
            c = AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy();
        }
        addToBot(new MakeTempCardInHandAction(c));
    }

}
