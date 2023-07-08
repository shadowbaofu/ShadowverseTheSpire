 package shadowverse.relics;
 
 import basemod.abstracts.CustomRelic;
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
 import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
 import com.megacrit.cardcrawl.cards.DamageInfo;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.helpers.ImageMaster;
 import com.megacrit.cardcrawl.relics.AbstractRelic;
 import com.megacrit.cardcrawl.rooms.AbstractRoom;
 
 public class Genius
   extends CustomRelic {
   public static final String ID = "shadowverse:Genius";
   public static final String IMG = "img/relics/Genius.png";
   public static final String OUTLINE_IMG = "img/relics/outline/Genius_Outline.png";
   
   public Genius() {
     super("shadowverse:Genius", ImageMaster.loadImage("img/relics/Genius.png"), RelicTier.RARE, LandingSound.FLAT);
   }
   
   public String getUpdatedDescription() {
     return this.DESCRIPTIONS[0];
   }
 
   
   public void atBattleStart() {
     this.counter = 0;
   }
   
   public void atTurnStart() {
     this.counter++;
     if (this.counter == 8)
       beginLongPulse(); 
   }
   
   public void onPlayerEndTurn() {
     if (this.counter == 8) {
       flash();
       addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, (AbstractRelic)this));
       addToBot(new DamageAllEnemiesAction(null, 
             
             DamageInfo.createDamageMatrix(61, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
       stopPulse();
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
     return (AbstractRelic)new Genius();
   }
 }
