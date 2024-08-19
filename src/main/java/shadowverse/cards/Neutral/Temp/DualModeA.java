 package shadowverse.cards.Neutral.Temp;
 


import basemod.abstracts.CustomCard;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import shadowverse.cardmods.ArmedMod;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Dragon;
import shadowverse.powers.DualModeAPower;


 public class DualModeA extends CustomCard {
   public static final String ID = "shadowverse:DualModeA";
   public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:DualModeA");
   public static final String NAME = cardStrings.NAME;
   public static final String DESCRIPTION = cardStrings.DESCRIPTION;
   public static final String IMG_PATH = "img/cards/DualModeA.png";

   public DualModeA() {
     super(ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.ATTACK, Dragon.Enums.COLOR_BROWN, CardRarity.SPECIAL, CardTarget.SELF_AND_ENEMY);
     this.baseDamage = 25;
     this.baseBlock = 18;
     this.tags.add(AbstractShadowversePlayer.Enums.ARMED);
     this.exhaust = true;
   }
 
   
   public void upgrade() {
     if (!this.upgraded) {
       upgradeName();
       upgradeDamage(5);
       upgradeBlock(4);
     } 
   }

   
   public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
     for (AbstractCard card : abstractPlayer.hand.group){
       if (card.type == CardType.ATTACK || (CardLibrary.getCard(card.cardID)!= null && CardLibrary.getCard(card.cardID).type == CardType.ATTACK)){
         CardModifierManager.addModifier(card, new ArmedMod());
         card.superFlash();
       }
     }
     addToBot(new GainBlockAction(abstractPlayer,this.block));
     addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,this.damage,this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
     addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,this.damage,this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
     addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new StrengthPower(abstractPlayer,1),1));
     addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new DexterityPower(abstractPlayer,2),2));
     addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new DualModeAPower(abstractPlayer,3,this.upgraded)));
   }
 
   
   public AbstractCard makeCopy() {
     return (AbstractCard)new DualModeA();
   }
 }

