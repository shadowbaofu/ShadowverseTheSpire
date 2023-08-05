 package shadowverse.relics;

 import basemod.abstracts.CustomRelic;
 import com.megacrit.cardcrawl.actions.common.DrawCardAction;
 import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
 import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
 import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
 import com.megacrit.cardcrawl.actions.utility.UseCardAction;
 import com.megacrit.cardcrawl.cards.AbstractCard;
 import com.megacrit.cardcrawl.cards.status.Burn;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.helpers.ImageMaster;
 import com.megacrit.cardcrawl.relics.AbstractRelic;
 import com.megacrit.cardcrawl.rooms.AbstractRoom;


 public class MaoAndFengtian
   extends CustomRelic
 {
   public static final String ID = "shadowverse:MaoAndFengtian";
   public static final String IMG = "img/relics/MaoAndFengtian.png";
   public static final String OUTLINE_IMG = "img/relics/outline/MaoAndFengtian_Outline.png";

   public MaoAndFengtian() {
     super("shadowverse:MaoAndFengtian", ImageMaster.loadImage("img/relics/MaoAndFengtian.png"), RelicTier.UNCOMMON, LandingSound.SOLID);
   }

   public String getUpdatedDescription() {
     return this.DESCRIPTIONS[0];
   }


     @Override
     public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
         if (targetCard.type == AbstractCard.CardType.ATTACK && targetCard.rarity == AbstractCard.CardRarity.SPECIAL){
             this.counter++;
             beginLongPulse();
         }
         if (this.counter == 2 && !this.grayscale){
             addToBot(new DrawCardAction(2));
             addToBot(new GainEnergyAction(1));
             stopPulse();
             this.grayscale = true;
             this.counter = -1;
         }
     }

     public void justEnteredRoom(AbstractRoom room) {
         this.grayscale = false;
         this.counter = 0;
     }

     public AbstractRelic makeCopy() {
     return new MaoAndFengtian();
   }
 }
 