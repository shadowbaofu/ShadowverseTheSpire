 package shadowverse.cards.Neutral.Temp;
 


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DoubleDamagePower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Dragon;


 public class DualModeC extends CustomCard {
   public static final String ID = "shadowverse:DualModeC";
   public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:DualModeC");
   public static final String NAME = cardStrings.NAME;
   public static final String DESCRIPTION = cardStrings.DESCRIPTION;
   public static final String IMG_PATH = "img/cards/DualModeC.png";

   public DualModeC() {
     super(ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.ATTACK, Dragon.Enums.COLOR_BROWN, CardRarity.SPECIAL, CardTarget.ALL);
     this.baseDamage = 0;
     this.baseMagicNumber = 14;
     this.tags.add(AbstractShadowversePlayer.Enums.ARMED);
     this.exhaust = true;
   }
 
   
   public void upgrade() {
     if (!this.upgraded) {
       upgradeName();
       upgradeMagicNumber(4);
     } 
   }

   public void applyPowers() {
     int amount = 0;
     for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat){
       if (c.hasTag(AbstractShadowversePlayer.Enums.ARMED) && c.type == CardType.ATTACK){
         amount++;
       }
     }
     int realBaseDamage = this.baseDamage;
     this.baseDamage += amount * this.magicNumber;
     super.applyPowers();
     this.baseDamage = realBaseDamage;
     this.isDamageModified = (this.damage != this.baseDamage);
   }

   public void calculateCardDamage(AbstractMonster mo) {
     int amount = -1;
     for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat){
       if (c.hasTag(AbstractShadowversePlayer.Enums.ARMED) && c.type == CardType.ATTACK){
         amount++;
       }
     }
     int realBaseDamage = this.baseDamage;
     this.baseDamage += amount * this.magicNumber;
     super.calculateCardDamage(mo);
     this.baseDamage = realBaseDamage;
     this.isDamageModified = (this.damage != this.baseDamage);
   }
   
   public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
     addToBot(new SFXAction("ATTACK_HEAVY"));
     addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new StrengthPower(abstractPlayer,2),2));
     addToBot(new DamageAllEnemiesAction(abstractPlayer, DamageInfo.createDamageMatrix(this.damage, true), this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
     addToBot(new VFXAction(abstractPlayer, new MindblastEffect(abstractPlayer.dialogX, abstractPlayer.dialogY, abstractPlayer.flipHorizontal), 0.1F));
     addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new DoubleDamagePower(abstractPlayer,1,false),1));
   }
 
   
   public AbstractCard makeCopy() {
     return (AbstractCard)new DualModeC();
   }
 }

