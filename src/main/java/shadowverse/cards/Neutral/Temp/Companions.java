 package shadowverse.cards.Neutral.Temp;
 


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.BlurPower;
import com.megacrit.cardcrawl.powers.FlameBarrierPower;
import shadowverse.characters.Dragon;
import shadowverse.characters.Vampire;
import shadowverse.powers.CompanionsPower;
import shadowverse.powers.DisableEffectDamagePower;


 public class Companions extends CustomCard {
   public static final String ID = "shadowverse:Companions";
   public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Companions");
   public static final String NAME = cardStrings.NAME;
   public static final String DESCRIPTION = cardStrings.DESCRIPTION;
   public static final String IMG_PATH = "img/cards/Companions.png";
   
   public Companions() {
     super(ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.ATTACK, Vampire.Enums.COLOR_SCARLET, CardRarity.SPECIAL, CardTarget.SELF);
     this.baseBlock = 25;
   }
 
   
   public void upgrade() {
     if (!this.upgraded) {
       upgradeName();
       upgradeBlock(5);
     } 
   }


   public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
     addToBot(new SFXAction("Companions"));
     addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new FlameBarrierPower(abstractPlayer,3),3));
     addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new CompanionsPower(abstractPlayer,this.magicNumber)));
     addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new ArtifactPower(abstractPlayer,1),1));
     addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new BlurPower(abstractPlayer,1),1));
     addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new DisableEffectDamagePower(abstractPlayer,1),1));
   }
 
   
   public AbstractCard makeCopy() {
     return (AbstractCard)new Companions();
   }
 }

