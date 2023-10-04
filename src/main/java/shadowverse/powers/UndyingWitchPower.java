package shadowverse.powers;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnApplyPowerPower;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class UndyingWitchPower
        extends AbstractPower implements BetterOnApplyPowerPower {
    public static final String POWER_ID = "shadowverse:UndyingWitchPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("shadowverse:UndyingWitchPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean triggered;

    public UndyingWitchPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.type = PowerType.BUFF;
        updateDescription();
        this.img = new Texture("img/powers/UndyingWitchPower.png");
    }


    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }


    @Override
    public boolean betterOnApplyPower(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        return true;
    }

    @Override
    public int betterOnApplyPowerStacks(AbstractPower power, AbstractCreature target, AbstractCreature source, int stackAmount) {
        if (power instanceof EarthEssence && stackAmount < 0) {
            flash();
            addToBot(new GainEnergyAction(-stackAmount));
            this.triggered = true;
        }
        return stackAmount;
    }

    @Override
    public void atStartOfTurn() {
        triggered = false;
    }
}

