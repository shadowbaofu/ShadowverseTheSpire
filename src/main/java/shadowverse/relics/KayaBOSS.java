 package shadowverse.relics;


 import basemod.abstracts.CustomRelic;
 import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.helpers.ImageMaster;
 import com.megacrit.cardcrawl.powers.WraithFormPower;
 import com.megacrit.cardcrawl.relics.AbstractRelic;
 import shadowverse.patch.CharacterSelectScreenPatches;
 import shadowverse.powers.OverflowPower;


 public class KayaBOSS
   extends CustomRelic
 {
   public static final String ID = "shadowverse:KayaBOSS";
   public static final String IMG = "img/relics/KayaBOSS.png";
   public static final String OUTLINE_IMG = "img/relics/outline/KayaBOSS_Outline.png";

   public KayaBOSS() {
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
                 &&(CharacterSelectScreenPatches.characters[7]).reskinCount == 0;
     }

     @Override
     public void atBattleStart() {
         addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new OverflowPower(AbstractDungeon.player,3),3));
         addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new WraithFormPower(AbstractDungeon.player,-1),-1));
     }

     public AbstractRelic makeCopy() {
     return (AbstractRelic)new KayaBOSS();
   }
 }


