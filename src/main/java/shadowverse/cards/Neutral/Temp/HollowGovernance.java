 package shadowverse.cards.Neutral.Temp;


 import basemod.abstracts.CustomCard;
 import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
 import com.megacrit.cardcrawl.actions.utility.SFXAction;
 import com.megacrit.cardcrawl.cards.AbstractCard;
 import com.megacrit.cardcrawl.characters.AbstractPlayer;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.localization.CardStrings;
 import com.megacrit.cardcrawl.monsters.AbstractMonster;
 import shadowverse.characters.Nemesis;


 public class HollowGovernance
   extends CustomCard
 {
   public static final String ID = "shadowverse:HollowGovernance";
   public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:HollowGovernance");
   public static final String NAME = cardStrings.NAME;
   public static final String DESCRIPTION = cardStrings.DESCRIPTION;
   public static final String IMG_PATH = "img/cards/VoidRealize.png";

   public HollowGovernance() {
     super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.SKILL, Nemesis.Enums.COLOR_SKY, CardRarity.SPECIAL, CardTarget.NONE);
     this.exhaust = true;
     this.selfRetain = true;
     this.cardsToPreview = (AbstractCard)new OvergrownWretch();
   }
 
   
   public void upgrade() {
     if (!this.upgraded) {
       upgradeName();
       upgradeBaseCost(1);
     } 
   }


   public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
       addToBot(new SFXAction("HollowGovernance"));
       addToBot(new MakeTempCardInHandAction(this.cardsToPreview,3));
   }
 
   
   public AbstractCard makeCopy() {
     return (AbstractCard)new HollowGovernance();
   }
 }

