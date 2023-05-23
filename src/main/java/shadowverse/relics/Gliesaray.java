 package shadowverse.relics;

 import basemod.abstracts.CustomRelic;
 import com.megacrit.cardcrawl.actions.common.DrawCardAction;
 import com.megacrit.cardcrawl.actions.common.HealAction;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.helpers.ImageMaster;
 import com.megacrit.cardcrawl.relics.AbstractRelic;
 import com.megacrit.cardcrawl.rooms.AbstractRoom;

 public class Gliesaray
   extends CustomRelic {
   public static final String ID = "shadowverse:Gliesaray";
   public static final String IMG = "img/relics/Gliesaray.png";
   public static final String OUTLINE_IMG = "img/relics/outline/Gliesaray_Outline.png";

   public Gliesaray() {
     super(ID, ImageMaster.loadImage(IMG), RelicTier.RARE, LandingSound.MAGICAL);
   }
   
   public String getUpdatedDescription() {
     return this.DESCRIPTIONS[0];
   }
 
   
   public void atBattleStart() {
     this.counter = 0;
   }
   
   public void atTurnStart() {
     this.counter++;
     if (this.counter == 5){
         flash();
         addToBot(new DrawCardAction(5));
         addToBot(new HealAction(AbstractDungeon.player,AbstractDungeon.player,5));
         beginLongPulse();
         this.grayscale = true;
     }
   }

   public void justEnteredRoom(AbstractRoom room) {
     this.grayscale = false;
   }
   
   public void onVictory() {
     this.counter = -1;
     stopPulse();
   }
 
   
   public AbstractRelic makeCopy() {
     return (AbstractRelic)new Gliesaray();
   }
 }
