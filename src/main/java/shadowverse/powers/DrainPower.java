package shadowverse.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DrainPower extends AbstractPower {
    public static final String POWER_ID = "shadowverse:DrainPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("shadowverse:DrainPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public DrainPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.type = PowerType.BUFF;
        updateDescription();
        this.img = new Texture("img/powers/DrainPower.png");
    }

    @Override
    public void atStartOfTurn(){
        addToBot(new RemoveSpecificPowerAction(this.owner,this.owner,this));
    }


    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (target instanceof AbstractMonster){
            int amt = damageAmount;
            addToBot(new HealAction(info.owner, info.owner, amt));
            addToBot(new RemoveSpecificPowerAction(this.owner,info.owner,this));
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
