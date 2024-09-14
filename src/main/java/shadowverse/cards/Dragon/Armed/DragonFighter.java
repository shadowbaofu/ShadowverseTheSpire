 package shadowverse.cards.Dragon.Armed;
 


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.watcher.WallopAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;
import shadowverse.cards.Neutral.Temp.DragonWeapon;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Dragon;


 public class DragonFighter extends CustomCard {
   public static final String ID = "shadowverse:DragonFighter";
   public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:DragonFighter");
   public static final String NAME = cardStrings.NAME;
   public static final String DESCRIPTION = cardStrings.DESCRIPTION;
   public static final String IMG_PATH = "img/cards/DragonFighter.png";

   public DragonFighter() {
     super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, Dragon.Enums.COLOR_BROWN, CardRarity.UNCOMMON, CardTarget.ENEMY);
     this.baseDamage = 0;
     this.baseMagicNumber = 3;
     this.magicNumber = this.baseMagicNumber;
     this.tags.add(AbstractShadowversePlayer.Enums.ARMED);
   }
 
   
   public void upgrade() {
     if (!this.upgraded) {
       upgradeName();
       upgradeMagicNumber(2);
     } 
   }

   public void applyPowers() {
     int amount = 0;
     for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat){
       if (c.hasTag(AbstractShadowversePlayer.Enums.ARMED) && c.type == CardType.ATTACK){
         amount++;
       }
     }
     if (amount>6)
       amount = 6;
     int realBaseDamage = this.baseDamage;
     this.baseDamage += amount * this.magicNumber;
     super.applyPowers();
     this.baseDamage = realBaseDamage;
     this.isDamageModified = (this.damage != this.baseDamage);
     this.rawDescription = cardStrings.DESCRIPTION;
     this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + amount;
     this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[1];
     initializeDescription();
   }

   public void calculateCardDamage(AbstractMonster mo) {
     int amount = -1;
     for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat){
       if (c.hasTag(AbstractShadowversePlayer.Enums.ARMED) && c.type == CardType.ATTACK){
         amount++;
       }
     }
     if (amount>6)
       amount = 6;
     int realBaseDamage = this.baseDamage;
     this.baseDamage += amount * this.magicNumber;
     super.calculateCardDamage(mo);
     this.baseDamage = realBaseDamage;
     this.isDamageModified = (this.damage != this.baseDamage);
   }

   
   public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
     addToBot(new SFXAction("ATTACK_HEAVY"));
     calculateCardDamage(abstractMonster);
     addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,this.damage,this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
     int count = 0;
     for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat){
       if (c.type == CardType.ATTACK && c.hasTag(AbstractShadowversePlayer.Enums.ARMED) && c!=this){
         count++;
       }
     }
     if (count>6){
       count=6;
     }
     addToBot(new DrawCardAction(count));
   }
 
   
   public AbstractCard makeCopy() {
     return (AbstractCard)new DragonFighter();
   }
 }

