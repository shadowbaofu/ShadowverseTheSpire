package shadowverse.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shadowverse.cards.Neutral.Temp.ForestBat;
import shadowverse.cards.Neutral.Temp.ProductMachine;
import shadowverse.characters.AbstractShadowversePlayer;

import java.util.Iterator;

public class NightmareAction extends AbstractGameAction {
    private AbstractPlayer p;
    private AbstractCard.CardType typeToCheck;

    public NightmareAction(int amount, AbstractCard.CardType type) {
        this.p = AbstractDungeon.player;
        this.setValues(this.p, this.p, amount);
        this.actionType = ActionType.DRAW;
        this.duration = Settings.ACTION_DUR_MED;
        this.typeToCheck = type;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            if (this.p.drawPile.isEmpty()) {
                this.isDone = true;
                return;
            }

            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            Iterator var2 = this.p.drawPile.group.iterator();

            AbstractCard card;
            while(var2.hasNext()) {
                card = (AbstractCard)var2.next();
                if (card.type == this.typeToCheck) {
                    tmp.addToRandomSpot(card);
                }
            }

            if (tmp.size() == 0) {
                this.isDone = true;
                return;
            }

            for(int i = 0; i < this.amount; ++i) {
                if (!tmp.isEmpty()) {
                    tmp.shuffle();
                    card = tmp.getBottomCard();
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
                        if (card.hasTag(AbstractShadowversePlayer.Enums.MACHINE)){
                            addToBot((AbstractGameAction)new MakeTempCardInHandAction((AbstractCard)new ProductMachine()));
                        }else {
                            addToBot((AbstractGameAction)new MakeTempCardInHandAction((AbstractCard)new ForestBat()));
                        }
                        AbstractDungeon.player.hand.refreshHandLayout();
                        AbstractDungeon.player.hand.applyPowers();
                    }
                }
            }
            this.isDone = true;
        }

        this.tickDuration();
    }
}
