 package shadowverse.powers;

 import com.badlogic.gdx.graphics.Texture;
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.cards.AbstractCard;
 import com.megacrit.cardcrawl.characters.AbstractPlayer;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.core.Settings;
 import com.megacrit.cardcrawl.localization.PowerStrings;
 import com.megacrit.cardcrawl.powers.AbstractPower;
 import shadowverse.action.TheFoolAction;

 public class TheFoolPower2 extends AbstractPower {
   private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("shadowverse:TheFoolPower2");
   public static final String POWER_ID = "shadowverse:TheFoolPower2";
   public static final String NAME = powerStrings.NAME;
   public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
   public AbstractCard card;

   public TheFoolPower2(AbstractCreature owner, int amount) {
     this.name = NAME;
     this.ID = POWER_ID;
     this.owner = owner;
     this.amount = amount;
     this.type = PowerType.BUFF;
     updateDescription();
     this.img = new Texture("img/powers/TheFoolPower2.png");
   }
 
   
   public void stackPower(int stackAmount) {
     this.amount += stackAmount;
     if (this.amount >= 999)
       this.amount = 999; 
   }

   
   public void updateDescription() {
     this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
   }
 
   @Override
   public void atStartOfTurnPostDraw() {
       flash();
       addToBot((AbstractGameAction)new TheFoolAction((AbstractPlayer)this.owner,this.amount));
   }

 }


