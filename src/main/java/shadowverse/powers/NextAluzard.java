 package shadowverse.powers;
 


import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import shadowverse.cards.Vampire.Default.Aluzard;
import shadowverse.cards.Neutral.Status.EvolutionPoint;
import shadowverse.cards.Neutral.Temp.BloodArts;


 public class NextAluzard
   extends AbstractPower {
   public static final String POWER_ID = "shadowverse:NextAluzard";
   private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("shadowverse:NextAluzard");
   public static final String NAME = powerStrings.NAME;
   public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
   private AbstractCard c;
   private boolean upgraded;

   public NextAluzard(AbstractCreature owner,AbstractCard c,boolean upgraded) {
     this.name = NAME;
     this.ID = POWER_ID;
     this.owner = owner;
     this.amount = -1;
     this.type = PowerType.BUFF;
     this.upgraded = upgraded;
     this.c = c.makeSameInstanceOf();
     updateDescription();
     this.img = new Texture("img/powers/AluzardPower.png");
   }
   
   public void atStartOfTurn() {
     flash();
     addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
     c.baseDamage += c.magicNumber;
     addToBot(new MakeTempCardInHandAction(c));
     int count = 0;
     for (AbstractCard c: AbstractDungeon.actionManager.cardsPlayedThisCombat){
       if (c instanceof Aluzard){
         count++;
       }
     }
     if (count>=2){
       AbstractCard card = new BloodArts();
       if (this.upgraded)
         card.upgrade();
       addToBot(new MakeTempCardInHandAction(card.makeStatEquivalentCopy()));
       addToBot(new MakeTempCardInHandAction(new EvolutionPoint()));
     }
   }
   
   public void updateDescription() {
     this.description = DESCRIPTIONS[0] + 1 + DESCRIPTIONS[1] + 1 +DESCRIPTIONS[2];
   }
 }

