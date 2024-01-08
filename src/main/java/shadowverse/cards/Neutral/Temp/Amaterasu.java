 package shadowverse.cards.Neutral.Temp;
 


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
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.powers.NextTurnAmaterasu;
import shadowverse.powers.NextTurnTsukuyomi;


 public class Amaterasu
   extends CustomCard {
   public static final String ID = "shadowverse:Amaterasu";
   public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Amaterasu");
   public static final String NAME = cardStrings.NAME;
   public static final String DESCRIPTION = cardStrings.DESCRIPTION;
   public static final String IMG_PATH = "img/cards/Amaterasu.png";
   
   public Amaterasu() {
     super(ID, NAME,IMG_PATH, 0, DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.SELF);
     this.baseBlock = 8;
     this.exhaust = true;
     this.tags.add(AbstractShadowversePlayer.Enums.LASTWORD);
   }
 
   
   public void upgrade() {
     if (!this.upgraded) {
       upgradeName();
       upgradeBlock(3);
       this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
       initializeDescription();
     } 
   }

   @Override
   public void triggerOnExhaust() {
     addToBot(new HealAction(AbstractDungeon.player,AbstractDungeon.player,1));
     addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new NextTurnTsukuyomi(AbstractDungeon.player,1,this.upgraded)));
   }

   public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
     addToBot(new SFXAction("Amaterasu"));
     addToBot(new GainBlockAction(abstractPlayer,this.block));
   }
 
 
   
   public AbstractCard makeCopy() {
     return (AbstractCard)new Amaterasu();
   }
 }

