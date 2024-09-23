package shadowverse.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class GabrielAction extends AbstractGameAction {
    private boolean freeToPlayOnce = false;

    private boolean upgraded = false;

    private AbstractCreature p;

    private int energyOnUse = -1;

    public GabrielAction(AbstractCreature p, boolean upgraded, boolean freeToPlayOnce, int energyOnUse) {
        this.p = p;
        this.freeToPlayOnce = freeToPlayOnce;
        this.upgraded = upgraded;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = AbstractGameAction.ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
    }


    @Override
    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1)
            effect = this.energyOnUse;
        if (AbstractDungeon.player.hasRelic("Chemical X")) {
            effect += 2;
            AbstractDungeon.player.getRelic("Chemical X").flash();
        }
        if (this.upgraded)
            effect++;
        if (this.p instanceof AbstractMonster)
            effect *= -1;
        addToBot(new ApplyPowerAction(this.p, AbstractDungeon.player, new StrengthPower(this.p, effect), effect));
        addToBot(new ApplyPowerAction(this.p, AbstractDungeon.player, new DexterityPower(this.p, effect), effect));
        if (!this.freeToPlayOnce)
            AbstractDungeon.player.energy.use(EnergyPanel.totalCount);
        this.isDone = true;
    }
}
