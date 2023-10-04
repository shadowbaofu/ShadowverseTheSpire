 package shadowverse.cards.Neutral.Temp;
 


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;
import shadowverse.characters.Nemesis;


 public class BlazingBarrage extends CustomCard {
   public static final String ID = "shadowverse:BlazingBarrage";
   public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:BlazingBarrage");
   public static final String NAME = cardStrings.NAME;
   public static final String DESCRIPTION = cardStrings.DESCRIPTION;
   public static final String IMG_PATH = "img/cards/BlazingBarrage.png";

   public BlazingBarrage() {
     super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.SKILL, Nemesis.Enums.COLOR_SKY, CardRarity.SPECIAL, CardTarget.ENEMY);
     this.baseDamage = 5;
     this.exhaust = true;
   }
 
   
   public void upgrade() {
     if (!this.upgraded) {
       upgradeName();
       upgradeDamage(3);
     } 
   }
 
   
   public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
     addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
     addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new BufferPower(abstractPlayer,1),1));
   }
 
   
   public AbstractCard makeCopy() {
     return (AbstractCard)new BlazingBarrage();
   }
 }

