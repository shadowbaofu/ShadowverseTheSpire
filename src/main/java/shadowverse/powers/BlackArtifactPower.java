package shadowverse.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import shadowverse.effect.RedLaserBeamEffect;
import shadowverse.effect.moryyEffect;


public class BlackArtifactPower
        extends AbstractPower {
    public static final String POWER_ID = "shadowverse:BlackArtifactPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("shadowverse:BlackArtifactPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public BlackArtifactPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        updateDescription();
        this.img = new Texture("img/powers/BlackArtifactPower.png");
    }

    public void stackPower(int stackAmount) {
        this.amount += stackAmount;
        if (this.amount >= 999)
            this.amount = 999;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void atStartOfTurnPostDraw() {
        flash();
        if (Settings.FAST_MODE) {
            addToBot(new VFXAction(new moryyEffect(), 0.7F));
        } else {
            addToBot(new VFXAction(new moryyEffect(), 1.0F));
        }
        if (this.owner.hasPower(WhiteArtifactPower.POWER_ID)) {
            addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(this.amount * 2, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
        } else {
            addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(this.amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
        }
    }


}

