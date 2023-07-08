 package shadowverse.relics;

 import basemod.abstracts.CustomRelic;
 import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
 import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
 import com.megacrit.cardcrawl.cards.status.Burn;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.helpers.ImageMaster;
 import com.megacrit.cardcrawl.relics.AbstractRelic;


 public class TotalDomination
   extends CustomRelic
 {
   public static final String ID = "shadowverse:TotalDomination";
   public static final String IMG = "img/relics/TotalDomination.png";
   public static final String OUTLINE_IMG = "img/relics/outline/TotalDomination_Outline.png";

   public TotalDomination() {
     super("shadowverse:TotalDomination", ImageMaster.loadImage("img/relics/TotalDomination.png"), RelicTier.RARE, LandingSound.MAGICAL);
   }

   public String getUpdatedDescription() {
     return this.DESCRIPTIONS[0];
   }

   public void onEquip() {
     AbstractDungeon.player.energy.energyMaster++;
   }

   public void onUnequip() {
     AbstractDungeon.player.energy.energyMaster--;
   }

   public void atBattleStart() {
     flash();
     addToBot(new MakeTempCardInDrawPileAction(new Burn(),2,true,true));
     addToBot(new MakeTempCardInDiscardAction(new Burn(),2));
   }



   public AbstractRelic makeCopy() {
     return new TotalDomination();
   }
 }
 