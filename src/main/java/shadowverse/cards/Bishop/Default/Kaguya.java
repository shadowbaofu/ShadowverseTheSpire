 package shadowverse.cards.Bishop.Default;
 


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.action.PlaceAmulet;
import shadowverse.action.ReduceAllCountDownAction;
import shadowverse.cards.Neutral.Temp.EphemeralMoon;
import shadowverse.characters.Bishop;


 public class Kaguya extends CustomCard {
   public static final String ID = "shadowverse:Kaguya";
   public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Kaguya");
   public static final String NAME = cardStrings.NAME;
   public static final String DESCRIPTION = cardStrings.DESCRIPTION;
   public static final String IMG_PATH = "img/cards/Kaguya.png";

   public Kaguya() {
     super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, Bishop.Enums.COLOR_WHITE, CardRarity.UNCOMMON, CardTarget.NONE);
     this.baseMagicNumber = 2;
     this.magicNumber = this.baseMagicNumber;
     this.cardsToPreview = new EphemeralMoon();
   }
 
   
   public void upgrade() {
     if (!this.upgraded) {
       upgradeName();
       upgradeMagicNumber(1);
     } 
   }
 
   
   public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
     addToBot(new SFXAction("Kaguya"));
     addToBot(new ReduceAllCountDownAction(this.magicNumber));
     addToBot(new PlaceAmulet(this.cardsToPreview.makeStatEquivalentCopy(), null));
   }
 
   
   public AbstractCard makeCopy() {
     return (AbstractCard)new Kaguya();
   }
 }

