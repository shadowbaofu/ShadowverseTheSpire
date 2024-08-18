package shadowverse.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.NeutralStance;
import shadowverse.stance.Vengeance;

public class ExitVengeancePower extends AbstractPower {
    public static final String POWER_ID = "shadowverse:ExitVengeancePower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("shadowverse:ExitVengeancePower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ExitVengeancePower(AbstractCreature owner,int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        updateDescription();
        loadRegion("phantasmal");
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + (this.amount-1) + DESCRIPTIONS[1];
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer){
            this.amount -= 1;
            if (this.amount == 0){
                flash();
                addToBot(new RemoveSpecificPowerAction(this.owner,this.owner,this));
                if(this.owner.currentHealth > this.owner.maxHealth / 2 && !this.owner.hasPower(VengeanceHealthPower.POWER_ID)){
                    if (this.owner instanceof AbstractPlayer){
                        if (((AbstractPlayer) this.owner).stance.ID.equals(Vengeance.STANCE_ID)){
                            addToBot(new ChangeStanceAction(new NeutralStance()));
                        }
                    }
                }
            }
            updateDescription();
        }
    }
}
