 package shadowverseCharbosses.actions.orb;
 
 import shadowverseCharbosses.bosses.AbstractCharBoss;
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.orbs.AbstractOrb;
 import com.megacrit.cardcrawl.powers.AbstractPower;


 public class EnemyTriggerEndOfTurnOrbActions
   extends AbstractGameAction
 {
   public void update() {
     if (!AbstractCharBoss.boss.orbs.isEmpty()) {
       for (AbstractOrb o : AbstractCharBoss.boss.orbs) {
         o.onEndOfTurn();
       }

     }
     
     for (AbstractPower p : AbstractCharBoss.boss.powers) {
       if (!AbstractCharBoss.boss.isPlayer) {
         p.atEndOfTurnPreEndTurnCards(false);
       }
       p.atEndOfTurn(AbstractCharBoss.boss.isPlayer);
     } 
 
     
     this.isDone = true;
   }
 }

