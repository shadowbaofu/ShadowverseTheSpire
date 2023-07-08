 package shadowverse.relics;


 import basemod.abstracts.CustomRelic;
 import com.megacrit.cardcrawl.helpers.ImageMaster;
 import com.megacrit.cardcrawl.relics.AbstractRelic;


 public class Enkia
   extends CustomRelic
 {
   public static final String ID = "shadowverse:Enkia";
   public static final String IMG = "img/relics/Enkia.png";
   public static final String OUTLINE_IMG = "img/relics/outline/Enkia_Outline.png";
   
   public Enkia() {
     super(ID, ImageMaster.loadImage(IMG), RelicTier.COMMON, LandingSound.CLINK);
   }
   
   public String getUpdatedDescription() {
     return this.DESCRIPTIONS[0];
   }


   public AbstractRelic makeCopy() {
     return (AbstractRelic)new Enkia();
   }
 }

