package shadowverse.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;

public class DrawPileToHandAction_Tag_NOREPEAT
        extends AbstractGameAction {
    private AbstractPlayer p;
    private AbstractCard.CardTags tagToCheck;
    private AbstractCard.CardTags tagToCheck2;
    private AbstractCard noRepeat;

    public DrawPileToHandAction_Tag_NOREPEAT(int amount, AbstractCard.CardTags tag, AbstractCard.CardTags tag2, AbstractCard noRepeat) {
        this.p = AbstractDungeon.player;
        setValues((AbstractCreature) this.p, (AbstractCreature) this.p, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
        this.tagToCheck = tag;
        this.tagToCheck2 = tag2;
        this.noRepeat = noRepeat;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            if (this.p.drawPile.isEmpty()) {
                this.isDone = true;
                return;
            }
            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : this.p.drawPile.group) {
                if ((c.hasTag(this.tagToCheck) || c.hasTag(this.tagToCheck2)) && c.cardID != noRepeat.cardID && CardLibrary.getCard(c.cardID) != null && CardLibrary.getCard(c.cardID).type == AbstractCard.CardType.ATTACK)
                    tmp.addToRandomSpot(c);
            }
            if (tmp.size() == 0) {
                this.isDone = true;
                return;
            }
            for (int i = 0; i < this.amount; i++) {
                if (!tmp.isEmpty()) {
                    tmp.shuffle();
                    AbstractCard card = tmp.getBottomCard();
                    tmp.removeCard(card);
                    if (this.p.hand.size() == 10) {
                        this.p.drawPile.moveToDiscardPile(card);
                        this.p.createHandIsFullDialog();
                    } else {
                        card.unhover();
                        card.lighten(true);
                        card.setAngle(0.0F);
                        card.drawScale = 0.12F;
                        card.targetDrawScale = 0.75F;
                        card.current_x = CardGroup.DRAW_PILE_X;
                        card.current_y = CardGroup.DRAW_PILE_Y;
                        this.p.drawPile.removeCard(card);
                        AbstractDungeon.player.hand.addToTop(card);
                        AbstractDungeon.player.hand.refreshHandLayout();
                        AbstractDungeon.player.hand.applyPowers();
                    }
                    this.isDone = true;
                }
                tickDuration();
            }
        }
    }
}