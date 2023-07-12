 package shadowverse.cards.Nemesis.Resonance;
 


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import shadowverse.characters.Nemesis;


 public class ChaosAura extends CustomCard {
   public static final String ID = "shadowverse:ChaosAura";
   public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:ChaosAura");
   public static final String NAME = cardStrings.NAME;
   public static final String DESCRIPTION = cardStrings.DESCRIPTION;
   public static final String IMG_PATH = "img/cards/ChaosAura.png";

   public ChaosAura() {
     super(ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.SKILL, Nemesis.Enums.COLOR_SKY, CardRarity.COMMON, CardTarget.ALL_ENEMY);
     this.baseBlock = 4;
     this.baseDamage = 2;
     this.baseMagicNumber = 12;
     this.magicNumber = this.baseMagicNumber;
     this.exhaust = true;
   }
 
   
   public void upgrade() {
     if (!this.upgraded) {
       upgradeName();
       upgradeDamage(1);
       upgradeBlock(2);
       upgradeMagicNumber(2);
     } 
   }


   public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
     addToBot(new SFXAction("ChaosAura"));
     int amount = -1;
     for (AbstractCard c : abstractPlayer.exhaustPile.group){
       if (c instanceof ChaosAura){
         amount++;
       }
     }
     if (amount < 5){
       addToBot(new DamageRandomEnemyAction(new DamageInfo(abstractPlayer,this.damage * amount,this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
       addToBot(new DrawCardAction(1));
       addToBot(new MakeTempCardInDrawPileAction(this.makeStatEquivalentCopy(),3,true,true));
     }else {
       AbstractMonster m = AbstractDungeon.getRandomMonster();
       if (AbstractDungeon.actionManager.turn > 4){
         addToBot(new DamageAction(m,new DamageInfo(abstractPlayer,this.magicNumber,this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
         addToBot(new GainBlockAction(abstractPlayer,this.block));
         addToBot(new ApplyPowerAction(m,abstractPlayer,new VulnerablePower(m,2,false),2));
         for (AbstractCard c : abstractPlayer.hand.group){
           addToBot(new ReduceCostForTurnAction(c,1));
         }
       }else {
         int rng = AbstractDungeon.cardRng.random(2);
         switch (rng){
           case 0:
             addToBot(new DamageAction(m,new DamageInfo(abstractPlayer,this.magicNumber,this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
             addToBot(new GainBlockAction(abstractPlayer,this.block));
             break;
           case 1:
             addToBot(new ApplyPowerAction(m,abstractPlayer,new VulnerablePower(m,2,false),2));
             break;
           case 2:
             for (AbstractCard c : abstractPlayer.hand.group){
               addToBot(new ReduceCostForTurnAction(c,1));
             }
             break;
         }
       }
     }
   }
 
   
   public AbstractCard makeCopy() {
     return (AbstractCard)new ChaosAura();
   }
 }

