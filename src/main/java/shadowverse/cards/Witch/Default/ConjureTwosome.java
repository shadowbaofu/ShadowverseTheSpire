 package shadowverse.cards.Witch.Default;
 
 import basemod.abstracts.CustomCard;
 import com.megacrit.cardcrawl.actions.common.GainBlockAction;
 import com.megacrit.cardcrawl.cards.AbstractCard;
 import com.megacrit.cardcrawl.characters.AbstractPlayer;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.localization.CardStrings;
 import com.megacrit.cardcrawl.monsters.AbstractMonster;
 import shadowverse.characters.Witchcraft;
 
 public class ConjureTwosome extends CustomCard {
   public static final String ID = "shadowverse:ConjureTwosome";
   public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:ConjureTwosome");
   public static final String NAME = cardStrings.NAME;
   public static final String DESCRIPTION = cardStrings.DESCRIPTION;
   public static final String IMG_PATH = "img/cards/ConjureTwosome.png";
   
   public ConjureTwosome() {
     super("shadowverse:ConjureTwosome", NAME, "img/cards/ConjureTwosome.png", 2, DESCRIPTION, CardType.SKILL, Witchcraft.Enums.COLOR_BLUE, CardRarity.COMMON, CardTarget.SELF);
     this.baseBlock = 6;
   }
 
   
   public void upgrade() {
     if (!this.upgraded) {
       upgradeName();
       upgradeBlock(2);
     } 
   }
 
   
   public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
     addToBot(new GainBlockAction(abstractPlayer, abstractPlayer, this.block));
     addToBot(new GainBlockAction(abstractPlayer, abstractPlayer, this.block));
   }
 
   
   public AbstractCard makeCopy() {
     return (AbstractCard)new ConjureTwosome();
   }
 }
 