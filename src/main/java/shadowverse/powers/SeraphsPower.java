package shadowverse.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import shadowverse.action.ReduceAllCountDownAction;

public class SeraphsPower extends AbstractPower {
    public static final String POWER_ID = "shadowverse:SeraphsPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("shadowverse:SeraphsPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public SeraphsPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.type = PowerType.BUFF;
        updateDescription();
        this.img = new Texture("img/powers/SeraphsPower.png");
    }

    @Override
    public void atStartOfTurn() {
        addToBot(new RemoveSpecificPowerAction(this.owner,this.owner,this));
        addToBot(new ReduceAllCountDownAction(10));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
