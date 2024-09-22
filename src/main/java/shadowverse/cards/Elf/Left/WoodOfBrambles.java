 package shadowverse.cards.Elf.Left;


 import basemod.abstracts.CustomCard;
 import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
 import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
 import com.megacrit.cardcrawl.cards.AbstractCard;
 import com.megacrit.cardcrawl.characters.AbstractPlayer;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.localization.CardStrings;
 import com.megacrit.cardcrawl.monsters.AbstractMonster;
 import com.megacrit.cardcrawl.powers.ThornsPower;
 import shadowverse.cards.Neutral.Temp.Fairy;
 import shadowverse.characters.Elf;


 public class WoodOfBrambles extends CustomCard {
   public static final String ID = "shadowverse:WoodOfBrambles";
   public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:WoodOfBrambles");
   public static final String NAME = cardStrings.NAME;
   public static final String DESCRIPTION = cardStrings.DESCRIPTION;
   public static final String IMG_PATH = "img/cards/WoodOfBrambles.png";

   public WoodOfBrambles() {
     super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.POWER, Elf.Enums.COLOR_GREEN, CardRarity.COMMON, CardTarget.SELF);
     this.baseMagicNumber = 2;
     this.magicNumber = this.baseMagicNumber;
     this.cardsToPreview = new Fairy();
   }
 
   
   public void upgrade() {
     if (!this.upgraded) {
       upgradeName();
       upgradeMagicNumber(2);
     } 
   }
 
   
   public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
     AbstractCard c = this.cardsToPreview.makeStatEquivalentCopy();
     addToBot(new MakeTempCardInHandAction(c,2));
     addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new ThornsPower(abstractPlayer, this.magicNumber), this.magicNumber));
   }
 
   
   public AbstractCard makeCopy() {
     return new WoodOfBrambles();
   }
 }

