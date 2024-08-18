package shadowverse.powers;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnApplyPowerPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class TheStrengthPower extends AbstractPower implements BetterOnApplyPowerPower {
    public static final String POWER_ID = "shadowverse:TheStrengthPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("shadowverse:TheStrengthPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private int dmgCut = AbstractDungeon.player.getPower(StrengthPower.POWER_ID) == null ? 0 : AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount;

    public TheStrengthPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.type = PowerType.BUFF;
        updateDescription();
        this.img = new Texture("img/powers/TheStrengthPower.png");
    }

    public float atDamageFinalReceive(float damage, DamageInfo.DamageType type) {
            damage -= dmgCut;
        return damage;
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (damageAmount > 0) {
            addToBot(new ApplyPowerAction(this.owner, this.owner,new StrengthPower(this.owner, -1), -1));
        }
        return damageAmount;
    }

    public void updateDescription() {
        if (dmgCut >= 0)
            this.description = DESCRIPTIONS[0] + this.dmgCut + DESCRIPTIONS[1];
        else
            this.description = DESCRIPTIONS[2] + -this.dmgCut + DESCRIPTIONS[1];
    }

    @Override
    public boolean betterOnApplyPower(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        return true;
    }

    @Override
    public int betterOnApplyPowerStacks(AbstractPower power, AbstractCreature target, AbstractCreature source, int stackAmount) {
        if ((power instanceof StrengthPower)) {
            this.dmgCut += stackAmount;
            updateDescription();
            AbstractDungeon.onModifyPower();
        }
        return stackAmount;
    }
}
