package shadowverse.powers;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnApplyPowerPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class TanzaniteConvictorPower
        extends AbstractPower implements BetterOnApplyPowerPower, NonStackablePower {
    public static final String POWER_ID = "shadowverse:TanzaniteConvictorPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("shadowverse:TanzaniteConvictorPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean triggered;

    public TanzaniteConvictorPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.type = PowerType.BUFF;
        updateDescription();
        this.img = new Texture("img/powers/TanzaniteConvictorPower.png");
    }

    public void stackPower(int stackAmount) {
        this.amount += stackAmount;
        if (this.amount >= 999)
            this.amount = 999;
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
        if ((power instanceof StrengthPower || power instanceof DexterityPower) && stackAmount > 0 && !triggered) {
            flash();
            triggered = true;
            addToBot(new SFXAction("TanzaniteConvictorPower2"));
            addToBot(new ApplyPowerAction(this.owner,this.owner,new ArtifactPower(this.owner,1),1));
        }
        return stackAmount;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            addToBot(new SFXAction("TanzaniteConvictorPower1"));
            addToBot(new HealAction(this.owner, this.owner, 1));
            for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                if (mo != null && !mo.isDeadOrEscaped()) {
                    addToBot(new HealAction(mo, this.owner, 1));
                }
            }
        }
    }

    @Override
    public void atStartOfTurn() {
        addToBot(new RemoveSpecificPowerAction(this.owner,this.owner,this));
    }
}

