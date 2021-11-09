 package charbosses.actions.utility;
 
 import charbosses.bosses.AbstractCharBoss;
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.cards.AbstractCard;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 
 public class EnemyShowCardAction extends AbstractGameAction {
   private static final float PURGE_DURATION = 0.2F;
   
   public EnemyShowCardAction(AbstractCard card) {
     this.card = null;
     setValues((AbstractCreature)AbstractCharBoss.boss, null, 1);
     this.card = card;
     this.duration = 0.2F;
     this.actionType = ActionType.SPECIAL;
   }
   private AbstractCard card;
   
   public void update() {
     if (this.duration == 0.2F) {
       if (AbstractCharBoss.boss.limbo.contains(this.card)) {
         AbstractCharBoss.boss.limbo.removeCard(this.card);
       }
       AbstractCharBoss.boss.cardInUse = null;
     } 
     tickDuration();
   }
 }
