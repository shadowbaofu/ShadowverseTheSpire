package shadowverse.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.RetainCardPower;
import shadowverse.characters.AbstractShadowversePlayer;

import java.util.Iterator;

public class MinaAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("shadowverse:applyEffect");
    public static final String[] TEXT = uiStrings.TEXT;
    private AbstractPlayer p;

    public MinaAction(int amount) {
        this.p = AbstractDungeon.player;
        this.setValues(this.p, AbstractDungeon.player, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
    }

    @Override
    public void update() {
        AbstractCard card;
        if (this.duration == Settings.ACTION_DUR_MED) {
            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            Iterator var5 = this.p.drawPile.group.iterator();

            while (var5.hasNext()) {
                AbstractCard c = (AbstractCard) var5.next();
                if (c.hasTag(AbstractShadowversePlayer.Enums.LEVIN)) {
                    tmp.addToRandomSpot(c);
                }
            }

            if (tmp.size() == 0) {
                this.isDone = true;
            } else if (tmp.size() == 1) {
                card = tmp.getTopCard();
                if (this.p.hand.size() == 10) {
                    this.p.drawPile.moveToDiscardPile(card);
                    this.p.createHandIsFullDialog();
                } else {
                    if (!card.isEthereal) {
                        card.retain = true;
                    }
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
            } else {
                AbstractDungeon.gridSelectScreen.open(tmp, this.amount, TEXT[0], false);
                this.tickDuration();
            }
        } else {
            if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
                Iterator var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

                while (var1.hasNext()) {
                    card = (AbstractCard) var1.next();
                    card.unhover();
                    if (this.p.hand.size() == 10) {
                        this.p.drawPile.moveToDiscardPile(card);
                        this.p.createHandIsFullDialog();
                    } else {
                        this.p.drawPile.removeCard(card);
                        if (!card.isEthereal) {
                            card.retain = true;
                        }
                        this.p.hand.addToTop(card);
                    }

                    this.p.hand.refreshHandLayout();
                    this.p.hand.applyPowers();
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                this.p.hand.refreshHandLayout();
            }

            this.tickDuration();
        }
    }

}
