 package shadowverse.cards.Neutral.Temp;
 


import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;


 public class BayleonPower
   extends AbstractPower
 {
   public static final String POWER_ID = "shadowverse:BayleonPower";
   private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("shadowverse:BayleonPower");
   public static final String NAME = powerStrings.NAME;
   public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
   private boolean triggered;
   public BayleonPower(AbstractCreature owner) {
     this.name = NAME;
     this.ID = POWER_ID;
     this.owner = owner;
     this.amount = -1;
     this.type = PowerType.BUFF;
     updateDescription();
     this.img = new Texture("img/powers/BayleonPower.png");
   }

   public void updateDescription() {
     this.description = DESCRIPTIONS[0];
   }

   @Override
   public void onUseCard(AbstractCard card, UseCardAction action) {
     if (card instanceof NaterranGreatTree && !triggered){
       triggered = true;
       addToBot(new MakeTempCardInHandAction(new LuminousBlade()));
     }
   }

   @Override
   public void atStartOfTurn() {
     triggered = false;
   }
 }


