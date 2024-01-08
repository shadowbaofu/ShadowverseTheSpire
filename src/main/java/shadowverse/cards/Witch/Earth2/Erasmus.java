 package shadowverse.cards.Witch.Earth2;
 
 import basemod.abstracts.CustomCard;
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
 import com.megacrit.cardcrawl.actions.utility.SFXAction;
 import com.megacrit.cardcrawl.cards.AbstractCard;
 import com.megacrit.cardcrawl.characters.AbstractPlayer;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.localization.CardStrings;
 import com.megacrit.cardcrawl.monsters.AbstractMonster;
 import com.megacrit.cardcrawl.powers.AbstractPower;
 import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Witchcraft;
 import shadowverse.powers.ErasmusPower;
 
 public class Erasmus
   extends CustomCard {
   public static final String ID = "shadowverse:Erasmus";
   public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Erasmus");
   public static final String NAME = cardStrings.NAME;
   public static final String DESCRIPTION = cardStrings.DESCRIPTION;
   public static final String IMG_PATH = "img/cards/Erasmus.png";
   
   public Erasmus() {
     super("shadowverse:Erasmus", NAME, "img/cards/Erasmus.png", 2, DESCRIPTION, CardType.POWER, Witchcraft.Enums.COLOR_BLUE, CardRarity.RARE, CardTarget.SELF);
     this.baseMagicNumber = 7;
     this.magicNumber = this.baseMagicNumber;
     this.tags.add(AbstractShadowversePlayer.Enums.EARTH_RITE);
   }
 
   
   public void upgrade() {
     if (!this.upgraded) {
       upgradeName();
       upgradeMagicNumber(4);
     } 
   }
 
   
   public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
     addToBot((AbstractGameAction)new SFXAction("Erasmus"));
     addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)abstractPlayer, (AbstractCreature)abstractPlayer, (AbstractPower)new ErasmusPower((AbstractCreature)abstractPlayer, this.magicNumber), this.magicNumber));
   }
 
   
   public AbstractCard makeCopy() {
     return (AbstractCard)new Erasmus();
   }
 }