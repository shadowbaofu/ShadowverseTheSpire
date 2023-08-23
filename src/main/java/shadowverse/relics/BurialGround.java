 package shadowverse.relics;
 


import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import shadowverse.powers.Cemetery;

 
 
 
 public class BurialGround
   extends CustomRelic
 {
   public static final String ID = "shadowverse:BurialGround";
   public static final String IMG = "img/relics/BurialGround.png";
   public static final String OUTLINE_IMG = "img/relics/outline/BurialGround_Outline.png";
   
   public BurialGround() {
     super(ID, ImageMaster.loadImage(IMG), RelicTier.UNCOMMON, LandingSound.FLAT);
   }
   
   public String getUpdatedDescription() {
     return this.DESCRIPTIONS[0];
   }

   @Override
   public void onPlayerEndTurn() {
     flash();
     addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
     addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, (AbstractPower)new Cemetery(AbstractDungeon.player, 1), 1));
   }

   
   public AbstractRelic makeCopy() {
     return new BurialGround();
   }
 }
