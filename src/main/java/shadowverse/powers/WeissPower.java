package shadowverse.powers;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnApplyPowerPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class WeissPower
        extends AbstractPower implements BetterOnApplyPowerPower {
    public static final String POWER_ID = "shadowverse:WeissPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("shadowverse:WeissPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public WeissPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.type = NeutralPowertypePatch.NEUTRAL;
        updateDescription();
        this.img = new Texture("img/powers/WeissPower.png");
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
    public void atStartOfTurn() {
        if (this.owner == AbstractDungeon.player){
            addToBot(new RemoveSpecificPowerAction(this.owner,this.owner,this));
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (!isPlayer){
            addToBot(new RemoveSpecificPowerAction(this.owner,this.owner,this));
        }
    }

    @Override
    public boolean betterOnApplyPower(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        if (abstractCreature == AbstractDungeon.player){
            if (abstractPower.type == PowerType.DEBUFF && abstractCreature1 != abstractCreature)
                return  false;
        }
        if (abstractCreature instanceof AbstractMonster){
            if (abstractPower.type == PowerType.BUFF)
                return  false;
        }
        return true;
    }

}

