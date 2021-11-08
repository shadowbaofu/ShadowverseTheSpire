 package charbosses.actions.orb;
 
 import com.badlogic.gdx.graphics.Color;
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.actions.utility.WaitAction;
 import com.megacrit.cardcrawl.cards.DamageInfo;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.core.Settings;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.orbs.AbstractOrb;
 import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
 
 public class EnemyDarkOrbEvokeAction
   extends AbstractGameAction {
   private static final float DURATION = 0.1F;
   private static final float POST_ATTACK_WAIT_DUR = 0.1F;
   
   public EnemyDarkOrbEvokeAction(DamageInfo info, AttackEffect effect) {
     this.muteSfx = false;
     setValues((AbstractCreature)AbstractDungeon.player, this.info = info);
     this.actionType = ActionType.DAMAGE;
     this.attackEffect = effect;
     this.duration = 0.1F;
   }
   private DamageInfo info; private boolean muteSfx;
   
   public void update() {
     if ((shouldCancelAction() && this.info.type != DamageInfo.DamageType.THORNS) || this.target == null) {
       this.isDone = true;
       return;
     } 
     if (this.duration == 0.1F) {
       this.info.output = AbstractOrb.applyLockOn(this.target, this.info.base);
       if (this.info.type != DamageInfo.DamageType.THORNS && (this.info.owner.isDying || this.info.owner.halfDead)) {
         this.isDone = true;
         return;
       } 
       AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect, this.muteSfx));
     } 
     tickDuration();
     if (this.isDone) {
       if (this.attackEffect == AttackEffect.POISON) {
         this.target.tint.color = Color.CHARTREUSE.cpy();
         this.target.tint.changeColor(Color.WHITE.cpy());
       } else if (this.attackEffect == AttackEffect.FIRE) {
         this.target.tint.color = Color.RED.cpy();
         this.target.tint.changeColor(Color.WHITE.cpy());
       } 
       this.target.damage(this.info);
       if (!Settings.FAST_MODE)
         addToTop((AbstractGameAction)new WaitAction(0.1F)); 
     } 
   }
 }

