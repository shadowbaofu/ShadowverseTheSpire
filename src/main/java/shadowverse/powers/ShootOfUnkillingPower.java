package shadowverse.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.CollectorCurseEffect;
import com.megacrit.cardcrawl.vfx.combat.DaggerSprayEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import com.megacrit.cardcrawl.vfx.combat.MiracleEffect;
import shadowverse.characters.AbstractShadowversePlayer;


public class ShootOfUnkillingPower
        extends AbstractPower {
    public static final String POWER_ID = "shadowverse:ShootOfUnkillingPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("shadowverse:ShootOfUnkillingPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ShootOfUnkillingPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        updateDescription();
        this.img = new Texture("img/powers/ShootOfUnkillingPower.png");
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
        addToBot(new VFXAction(new MiracleEffect(Color.FOREST.cpy(),Color.WHITE.cpy(),"HEAL_3")));
        addToBot(new VFXAction(new DaggerSprayEffect(AbstractDungeon.getMonsters().shouldFlipVfx()), 1.5F));
        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
            if (!mo.isDeadOrEscaped()&&!mo.halfDead) {
                if ((mo.intent == AbstractMonster.Intent.ATTACK||
                        mo.intent == AbstractMonster.Intent.ATTACK_BUFF||
                        mo.intent == AbstractMonster.Intent.ATTACK_DEBUFF||
                        mo.intent == AbstractMonster.Intent.ATTACK_DEFEND)&&
                mo.getIntentDmg()<=0){
                    addToBot( new DamageAction(mo, new DamageInfo(this.owner, mo.maxHealth * this.amount, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
                }
            }
        }
        addToBot( new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

}

