 package shadowverse.cards.Dragon.Basic;
 


import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import shadowverse.characters.Dragon;
import shadowverse.powers.OverflowPower;


 public class Hiro
   extends CustomCard {
   public static final String ID = "shadowverse:Hiro";
   public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Hiro");
   public static final String NAME = cardStrings.NAME;
   public static final String DESCRIPTION = cardStrings.DESCRIPTION;
   public static final String IMG_PATH = "img/cards/Hiro.png";

   public Hiro() {
     super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.SKILL, Dragon.Enums.COLOR_BROWN, CardRarity.BASIC, CardTarget.SELF);
     this.baseMagicNumber = 1;
     this.magicNumber = this.baseMagicNumber;
     this.isInnate = true;
   }
 
   
   public void upgrade() {
     if (!this.upgraded) {
       upgradeName();
       upgradeMagicNumber(1);
       this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
       initializeDescription();
     } 
   }
 
   
   public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
     addToBot(new SFXAction("Hiro"));
     addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new EnergizedPower(abstractPlayer,this.magicNumber)));
     addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new OverflowPower(abstractPlayer,1)));
     if (abstractPlayer.hasPower(OverflowPower.POWER_ID)){
       TwoAmountPower p = (TwoAmountPower) abstractPlayer.getPower(OverflowPower.POWER_ID);
       if (p.amount2 > 0){
         addToBot(new DrawCardAction(this.magicNumber));
       }
     }
   }
 
 
   
   public AbstractCard makeCopy() {
     return (AbstractCard)new Hiro();
   }
 }

