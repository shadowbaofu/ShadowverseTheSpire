package shadowverse.powers;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnPlayerDeathPower;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import shadowverse.cards.Dragon.Armed.LavateinnDragon;

public class DualModeAPower extends AbstractPower implements OnPlayerDeathPower {
    public static final String POWER_ID = "shadowverse:DualModeAPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("shadowverse:DualModeAPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean upgraded;

    public DualModeAPower(AbstractCreature owner, int amount, boolean upgraded) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.upgraded = upgraded;
        updateDescription();
        this.img = new Texture("img/powers/DualModeAPower.png");
        this.priority = 75;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer){
            addToBot(new HealAction(this.owner,this.owner,3));
        }
    }

    @Override
    public void atStartOfTurn() {
        addToBot(new RemoveSpecificPowerAction(this.owner,this.owner,this));
        addToBot(new MakeTempCardInHandAction(new LavateinnDragon()));
    }

    public float atDamageFinalReceive(float damage, DamageInfo.DamageType type) {
        damage -= amount;
        return damage;
    }

    @Override
    public boolean onPlayerDeath(AbstractPlayer abstractPlayer, DamageInfo damageInfo) {
        addToBot(new RemoveSpecificPowerAction(this.owner,this.owner,this));
        this.owner.heal(1, true);
        addToBot(new RemoveDebuffsAction(this.owner));
        addToBot(new MakeTempCardInHandAction(new LavateinnDragon()));
        return false;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
