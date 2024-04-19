 package shadowverse.cards.Dragon.Natura;
 


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import shadowverse.cards.Neutral.Temp.NaterranGreatTree;
import shadowverse.characters.Dragon;
import shadowverse.powers.CrystalshardDragonewtPower;

 public class CrystalshardDragonewt extends CustomCard {
   public static final String ID = "shadowverse:CrystalshardDragonewt";
   public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:CrystalshardDragonewt");
   public static final String NAME = cardStrings.NAME;
   public static final String DESCRIPTION = cardStrings.DESCRIPTION;
   public static final String IMG_PATH = "img/cards/CrystalshardDragonewt.png";

   public CrystalshardDragonewt() {
     super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, Dragon.Enums.COLOR_BROWN, CardRarity.RARE, CardTarget.SELF);
     this.baseBlock = 6;
     this.cardsToPreview = new NaterranGreatTree();
   }
 
   
   public void upgrade() {
     if (!this.upgraded) {
       upgradeName();
       upgradeBlock(3);
     } 
   }
 
   
   public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
     addToBot(new GainBlockAction(abstractPlayer,abstractPlayer,this.block));
     addToBot(new SFXAction("CrystalshardDragonewt"));
     addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new ArtifactPower(abstractPlayer,1),1));
     addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new CrystalshardDragonewtPower(abstractPlayer,1),1));
   }
 
   
   public AbstractCard makeCopy() {
     return (AbstractCard)new CrystalshardDragonewt();
   }
 }

