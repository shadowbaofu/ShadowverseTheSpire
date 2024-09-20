package shadowverse.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static shadowverse.characters.AbstractShadowversePlayer.Enums.HERO;

public class ValiantFencerAction extends AbstractGameAction {
    public boolean upgraded;

    public ValiantFencerAction(boolean upgraded) {
        this.actionType = ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_FAST;
        this.upgraded = upgraded;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        for (AbstractCard c : p.hand.group) {
            if (c.hasTag(HERO)) {
                c.costForTurn = 0;
                c.isCostModified = true;
                if(this.upgraded){
                    c.upgrade();
                }
            }
        }
        this.tickDuration();
    }
}