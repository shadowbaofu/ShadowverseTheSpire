package shadowverse.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import shadowverse.characters.AbstractShadowversePlayer;

public class ShadowRejectionPower extends AbstractPower implements HealthBarRenderPower {
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("shadowverse:ShadowRejectionPower");
    public static final String POWER_ID = "shadowverse:ShadowRejectionPower";
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public int maxHealth;

    public ShadowRejectionPower(AbstractCreature owner,int maxHealth) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.maxHealth = maxHealth;
        this.type = PowerType.DEBUFF;
        updateDescription();
        this.img = new Texture("img/powers/ShadowCorrosionPower.png");
    }


    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void atStartOfTurn() {
        flash();
        double rate = 0.05;
        if (AbstractDungeon.player instanceof AbstractShadowversePlayer){
            if (((AbstractShadowversePlayer) AbstractDungeon.player).naterranCount>4)
                rate *= 3.0f;
        }
        this.owner.decreaseMaxHealth((int)(this.maxHealth*rate));
    }

    @Override
    public int getHealthBarAmount() {
        return this.maxHealth;
    }

    @Override
    public Color getColor() {
        return Color.TEAL;
    }
}


