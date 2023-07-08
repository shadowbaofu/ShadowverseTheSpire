 package shadowverse.relics;


 import basemod.abstracts.CustomRelic;
 import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
 import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
 import com.megacrit.cardcrawl.actions.common.DrawCardAction;
 import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.helpers.ImageMaster;
 import com.megacrit.cardcrawl.powers.WraithFormPower;
 import com.megacrit.cardcrawl.relics.AbstractRelic;
 import shadowverse.cards.Neutral.Temp.Ember;
 import shadowverse.patch.CharacterSelectScreenPatches;
 import shadowverse.powers.OverflowPower;


 public class GreaBOSS
   extends CustomRelic
 {
   public static final String ID = "shadowverse:GreaBOSS";
   public static final String IMG = "img/relics/GreaBOSS.png";
   public static final String OUTLINE_IMG = "img/relics/outline/GreaBOSS_Outline.png";

   public GreaBOSS() {
     super(ID, ImageMaster.loadImage(IMG), RelicTier.BOSS, LandingSound.CLINK);
   }
   
   public String getUpdatedDescription() {
     return this.DESCRIPTIONS[0];
   }

     @Override
     public void obtain() {
         if (AbstractDungeon.player.hasRelic(Offensive8.ID)) {
             instantObtain(AbstractDungeon.player, 0, false);
         } else {
             super.obtain();
         }
     }


     @Override
     public boolean canSpawn(){
         return AbstractDungeon.player.hasRelic(Offensive8.ID)
                 &&(CharacterSelectScreenPatches.characters[7]).reskinCount == 1;
     }

     @Override
     public void atBattleStart() {
         addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new OverflowPower(AbstractDungeon.player,1)));
     }

     @Override
     public void atTurnStart() {
         if (AbstractDungeon.player.hasPower(OverflowPower.POWER_ID)){
             TwoAmountPower p = (TwoAmountPower) AbstractDungeon.player.getPower(OverflowPower.POWER_ID);
             if (p.amount2 > 0){
                 addToBot(new MakeTempCardInHandAction(new Ember()));
             }
         }
     }

     public AbstractRelic makeCopy() {
     return (AbstractRelic)new GreaBOSS();
   }
 }


