 package shadowverse.cards.Neutral.Temp;
 


import basemod.abstracts.CustomCard;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;
import shadowverse.cardmods.ArmedMod;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Dragon;
import shadowverse.powers.DualModeBPower;


 public class DualModeB extends CustomCard {
   public static final String ID = "shadowverse:DualModeB";
   public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:DualModeB");
   public static final String NAME = cardStrings.NAME;
   public static final String DESCRIPTION = cardStrings.DESCRIPTION;
   public static final String IMG_PATH = "img/cards/DualModeB.png";

   public DualModeB() {
     super(ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.ATTACK, Dragon.Enums.COLOR_BROWN, CardRarity.SPECIAL, CardTarget.SELF_AND_ENEMY);
     this.baseDamage = 0;
     this.baseMagicNumber = 7;
     this.magicNumber = this.baseMagicNumber;
     this.baseBlock = 18;
     this.tags.add(AbstractShadowversePlayer.Enums.ARMED);
     this.exhaust = true;
   }
 
   
   public void upgrade() {
     if (!this.upgraded) {
       upgradeName();
       upgradeMagicNumber(2);
       upgradeBlock(4);
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
     for (AbstractCard card : abstractPlayer.hand.group){
       if (card.type == CardType.ATTACK || (CardLibrary.getCard(card.cardID)!= null && CardLibrary.getCard(card.cardID).type == CardType.ATTACK)){
         CardModifierManager.addModifier(card, new ArmedMod());
         card.superFlash();
       }
     }
     addToBot(new GainBlockAction(abstractPlayer,this.block));
     addToBot(new DrawCardAction(2));
     addToBot(new SFXAction("ATTACK_HEAVY"));
     calculateCardDamage(abstractMonster);
     addToBot(new VFXAction(abstractPlayer, new MindblastEffect(abstractPlayer.dialogX, abstractPlayer.dialogY, abstractPlayer.flipHorizontal), 0.1F));
     addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,this.damage,this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
     addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new DualModeBPower(abstractPlayer,10)));
     addToBot(new MakeTempCardInHandAction(new DragonWeapon()));
   }
 
   
   public AbstractCard makeCopy() {
     return (AbstractCard)new DualModeB();
   }
 }

