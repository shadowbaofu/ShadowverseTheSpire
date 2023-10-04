package shadowverse.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ImaginationAction extends AbstractGameAction {
    private boolean upgraded;

    public ImaginationAction(boolean upgraded) {
        this.upgraded = upgraded;
    }

    @Override
    public void update() {
        AbstractDungeon.actionManager.addToTop((AbstractGameAction) new WaitAction(0.4F));
        tickDuration();
        if (this.isDone)
            for (AbstractCard c : DrawCardAction.drawnCards) {
                if (c.costForTurn > 0) {
                    c.costForTurn = 0;
                    c.isCostModifiedForTurn = true;
                }
                if (upgraded && c.cost > 0) {
                    c.cost = 0;
                    c.isCostModified = true;
                }
            }
    }
}
