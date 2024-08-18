package shadowverse.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class RealWaitAction extends AbstractGameAction {
    public RealWaitAction(float setDur) {
        this.setValues(null, null, 0);
        this.duration = setDur;
        this.actionType = ActionType.WAIT;
    }

    public void update() {
        this.tickDuration();
    }
}