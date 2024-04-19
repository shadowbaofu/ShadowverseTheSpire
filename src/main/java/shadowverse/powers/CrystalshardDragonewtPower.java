 package shadowverse.powers;
 


import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.*;
import shadowverse.cards.Neutral.Temp.NaterranGreatTree;


 public class CrystalshardDragonewtPower
   extends AbstractPower
 {
   public static final String POWER_ID = "shadowverse:CrystalshardDragonewtPower";
   private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("shadowverse:CrystalshardDragonewtPower");
   public static final String NAME = powerStrings.NAME;
   public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
   private int useCount;

   public CrystalshardDragonewtPower(AbstractCreature owner, int amount) {
     this.name = NAME;
     this.ID = POWER_ID;
     this.owner = owner;
     this.amount = amount;
     this.type = PowerType.BUFF;
     updateDescription();
     this.img = new Texture("img/powers/CrystalshardDragonewtPower.png");
   }

   public void stackPower(int stackAmount) {
     this.amount += stackAmount;
     if (this.amount >= 999)
       this.amount = 999;
   }
   
   public void updateDescription() {
     this.description = DESCRIPTIONS[0]+this.amount*2 +DESCRIPTIONS[1] + this.amount* 10 +
     DESCRIPTIONS[2] + this.amount * 2 + DESCRIPTIONS[3] + this.amount*3 +DESCRIPTIONS[4] +
     this.amount + DESCRIPTIONS[5];
   }

   @Override
   public void atStartOfTurn() {
     addToBot(new RemoveSpecificPowerAction(this.owner,this.owner,this));
   }

   @Override
   public void onUseCard(AbstractCard card, UseCardAction action) {
     if (card instanceof NaterranGreatTree){
       if (useCount < 3){
         addToBot(new SFXAction("CrystalshardDragonewtPower"));
         useCount++;
         switch (useCount){
           case 1:
             AbstractCreature m = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
             if (m != null) {
               addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new VulnerablePower(m, this.amount * 2, false)));
               addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new WeakPower(m, this.amount * 2, false)));
             }
             break;
           case 2:
             addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(10*this.amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
             addToBot(new HealAction(this.owner,this.owner,2*this.amount));
             break;
           case 3:
             addToBot(new ApplyPowerAction(this.owner,this.owner,new StrengthPower(this.owner,3*this.amount),3*this.amount));
             addToBot(new ApplyPowerAction(this.owner,this.owner,new DexterityPower(this.owner,3*this.amount),3*this.amount));
             addToBot(new GainEnergyAction(this.amount));
             break;
         }
       }
     }
   }

 }


