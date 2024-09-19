package shadowverse.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class RaziaPower extends AbstractPower {
    public static final String POWER_ID = "shadowverse:RaziaPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("shadowverse:RaziaPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public RaziaPower(AbstractCreature owner, int thornsDamage) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = thornsDamage;
        this.type = PowerType.BUFF;
        this.updateDescription();
        this.img = new Texture("img/powers/RaziaPower.png");
    }

    public void stackPower(int stackAmount) {
        this.amount += stackAmount;
        if (this.amount >= 999)
            this.amount = 999;
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != this.owner) {
            this.flash();
            int damage = this.amount;
            if (owner.currentBlock > 0) {
                addToBot(new SFXAction("RaziaPower1"));
                this.addToTop(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, 1), 1));
                if (this.owner.hasPower(StrengthPower.POWER_ID) && this.owner.getPower(StrengthPower.POWER_ID).amount > 0) {
                    damage += 3 * this.owner.getPower(StrengthPower.POWER_ID).amount;
                }
                this.addToTop(new DamageAction(info.owner, new DamageInfo(this.owner, damage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
            } else {
                addToBot(new SFXAction("RaziaPower2"));
                if (this.owner.hasPower(StrengthPower.POWER_ID) && this.owner.getPower(StrengthPower.POWER_ID).amount > 0) {
                    damage += 3 * this.owner.getPower(StrengthPower.POWER_ID).amount;
                }
                this.addToTop(new DamageAction(info.owner, new DamageInfo(this.owner, damage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
                this.addToTop(new DamageAction(info.owner, new DamageInfo(this.owner, damage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
            }
        }

        return damageAmount;
    }

    public void atStartOfTurn() {
        this.addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, POWER_ID));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    }

}
