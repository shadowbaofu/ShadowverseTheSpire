 package shadowverse.cards.Dragon.Buff;
 


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import shadowverse.cards.AbstractEnhanceCard;
import shadowverse.characters.Dragon;


 public class DragonAwakening
   extends AbstractEnhanceCard {
   public static final String ID = "shadowverse:DragonAwakening";
   public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:DragonAwakening");
   public static final String NAME = cardStrings.NAME;
   public static final String DESCRIPTION = cardStrings.DESCRIPTION;
   public static final String IMG_PATH = "img/cards/DragonAwakening.png";
   private boolean trigger;
   
   public DragonAwakening() {
     super(ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.SKILL, Dragon.Enums.COLOR_BROWN, CardRarity.COMMON, CardTarget.SELF,1);
     this.baseMagicNumber = 2;
     this.magicNumber = this.baseMagicNumber;
   }
 
   
   public void upgrade() {
     if (!this.upgraded) {
       upgradeName();
       upgradeMagicNumber(1);
     } 
   }

   @Override
   public void exEnhanceUse(AbstractPlayer p, AbstractMonster m) {

   }

   @Override
   public void enhanceUse(AbstractPlayer p, AbstractMonster m) {
     if (!trigger){
       trigger = true;
       addToBot(new ApplyPowerAction(p,p,new DexterityPower(p,1),1));
     }
     addToBot(new DrawCardAction(this.magicNumber));
   }

   @Override
   public void baseUse(AbstractPlayer p, AbstractMonster m) {
     if (!trigger){
       trigger = true;
       addToBot(new ApplyPowerAction(p,p,new DexterityPower(p,1),1));
     }
   }


   public AbstractCard makeCopy() {
     return (AbstractCard)new DragonAwakening();
   }
 }

