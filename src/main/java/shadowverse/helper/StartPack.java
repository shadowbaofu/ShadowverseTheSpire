package shadowverse.helper;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class StartPack {
    public ArrayList<AbstractCard> cards;
    public AbstractCard lastCard;
    public AbstractCard rareCard;

    public StartPack(ArrayList<AbstractCard> cards, AbstractCard lastCard, AbstractCard rareCard) {
        this.cards = cards;
        this.lastCard = lastCard;
        this.rareCard = rareCard;
    }

    public StartPack(AbstractCard lastCard, AbstractCard rareCard) {
        this.cards = new ArrayList<>();
        this.lastCard = lastCard;
        this.rareCard = rareCard;
    }

    public ArrayList<AbstractCard> getPack() {
        ArrayList<AbstractCard> pack = new ArrayList<>(cards);
        int roll = AbstractDungeon.cardRandomRng.random(99);
        if (roll < 95) {
            pack.add(lastCard);
        } else {
            pack.add(rareCard);
        }
        return pack;
    }

    public void addCard(AbstractCard card) {
        this.cards.add(card);
    }

    public void addCard(AbstractCard card, int count) {
        for (int i = 0; i < count; i++) {
            this.cards.add(card.makeCopy());
        }
    }
}
