package shadowverse.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;

public class JervaPower extends AbstractPower implements HealthBarRenderPower {
    public static final String POWER_ID = "shadowverse:JervaPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("shadowverse:JervaPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private int half;
    private int healthToReturn;

    public JervaPower(AbstractCreature owner, int half, int currentHp) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.half = half;
        this.amount = -1;
        if (currentHp>half){
            this.healthToReturn = currentHp-half;
        }else {
            this.healthToReturn = 0;
        }
        this.type = NeutralPowertypePatch.NEUTRAL;
        updateDescription();
        this.img = new Texture("img/powers/JervaPower.png");
    }

    @Override
    public int onHeal(int healAmount){
        if (this.owner.currentHealth+healAmount>half)
            return half-this.owner.currentHealth;
        return healAmount;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public int getHealthBarAmount() {
        return this.half;
    }

    @Override
    public void onVictory(){
        AbstractPlayer p = AbstractDungeon.player;
        if (p.currentHealth > 0){
            p.currentHealth+=this.healthToReturn;
            p.update();
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer){
            AbstractCreature m = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
            if (m != null){
                addToBot(new VFXAction(new WeightyImpactEffect(m.hb.cX, m.hb.cY)));
                int dmg = m.maxHealth / 4;
                if (dmg < 25){
                    dmg = 25;
                }
                addToBot(new DamageAction(m,new DamageInfo(this.owner,dmg, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.NONE));
            }
        }
    }

    @Override
    public Color getColor() {
        return Color.SKY;
    }
}
