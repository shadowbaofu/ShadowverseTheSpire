 package shadowverse.relics;


 import basemod.abstracts.CustomRelic;
 import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
 import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
 import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
 import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.helpers.ImageMaster;
 import com.megacrit.cardcrawl.monsters.AbstractMonster;
 import com.megacrit.cardcrawl.relics.AbstractRelic;
 import shadowverse.cards.Neutral.Temp.Ember;
 import shadowverse.patch.CharacterSelectScreenPatches;
 import shadowverse.powers.OverflowPower;


 public class InoriBOSS
   extends CustomRelic
 {
   public static final String ID = "shadowverse:InoriBOSS";
   public static final String IMG = "img/relics/InoriBOSS.png";
   public static final String OUTLINE_IMG = "img/relics/outline/InoriBOSS_Outline.png";

   public InoriBOSS() {
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
                 &&(CharacterSelectScreenPatches.characters[7]).reskinCount == 2;
     }

     @Override
     public void atBattleStart() {
         addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new OverflowPower(AbstractDungeon.player,1)));
         for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
             if (m != null && !m.isDeadOrEscaped()){
                 addToBot(new StunMonsterAction(m,AbstractDungeon.player));
             }
         }
     }


     public AbstractRelic makeCopy() {
     return (AbstractRelic)new InoriBOSS();
   }
 }


