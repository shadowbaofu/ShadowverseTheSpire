package shadowverse.powers;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnApplyPowerPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

public class Forte4Power
        extends TwoAmountPower implements BetterOnApplyPowerPower {
    public static final String POWER_ID = "shadowverse:Forte4Power";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("shadowverse:Forte4Power");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public Forte4Power(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.amount2 = 0;
        this.type = PowerType.BUFF;
        updateDescription();
        this.img = new Texture("img/powers/Forte4Power.png");
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
        if ((power instanceof StrengthPower || power instanceof DexterityPower) && stackAmount > 0) {
            flash();
            addToBot(new SFXAction("Forte4Power"));
            this.amount2++;
        }
        return stackAmount;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer){
            if (amount2 > 0){
                addToBot(new ApplyPowerAction(this.owner,this.owner,new EnergizedPower(this.owner,1)));
            }
            if (amount2 > 1){
                AbstractMonster m = AbstractDungeon.getRandomMonster();
                addToBot(new ApplyPowerAction(m,this.owner,new VulnerablePower(m,2,false),2));
                addToBot(new ApplyPowerAction(m,this.owner,new WeakPower(m,2,false),2));
            }
            if (amount2 > 2){
                addToBot(new ApplyPowerAction(this.owner,this.owner,new BufferPower(this.owner,1),1));
                addToBot(new RemoveSpecificPowerAction(this.owner,this.owner,this));
            }
            addToBot(new RemoveSpecificPowerAction(this.owner,this.owner,this));
        }
    }
}

