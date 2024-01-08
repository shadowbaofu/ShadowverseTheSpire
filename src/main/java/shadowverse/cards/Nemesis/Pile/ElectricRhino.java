 package shadowverse.cards.Nemesis.Pile;
 


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostForTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.cards.Neutral.Temp.MechamagicalRhino;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Nemesis;


 public class ElectricRhino extends CustomCard {
   public static final String ID = "shadowverse:ElectricRhino";
   public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:ElectricRhino");
   public static final String NAME = cardStrings.NAME;
   public static final String DESCRIPTION = cardStrings.DESCRIPTION;
   public static final String IMG_PATH = "img/cards/ElectricRhino.png";

   public ElectricRhino() {
     super(ID, NAME, IMG_PATH, 7, DESCRIPTION, CardType.ATTACK, Nemesis.Enums.COLOR_SKY, CardRarity.RARE, CardTarget.ENEMY);
     this.baseDamage = 16;
     this.cardsToPreview = new MechamagicalRhino();
     this.tags.add(AbstractShadowversePlayer.Enums.ARTIFACT);
   }
 
   
   public void upgrade() {
     if (!this.upgraded) {
       upgradeName();
       upgradeDamage(3);
       this.cardsToPreview.upgrade();
       this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
       initializeDescription();
     } 
   }

   @Override
   public void triggerOnOtherCardPlayed(AbstractCard c) {
     if (c.exhaust && c.hasTag(AbstractShadowversePlayer.Enums.ARTIFACT)) {
       flash();
       addToBot(new ReduceCostAction(this));
     }
   }

   @Override
   public void triggerWhenDrawn() {
     int amount = 0;
     for (AbstractCard c : AbstractDungeon.player.exhaustPile.group){
       if (c.hasTag(AbstractShadowversePlayer.Enums.ARTIFACT))
         amount++;
     }
     addToBot(new ReduceCostForTurnAction(this,amount));
   }

   public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
     addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
     addToBot(new MakeTempCardInDrawPileAction(this.cardsToPreview.makeStatEquivalentCopy(),3,true,true));
   }
 
   
   public AbstractCard makeCopy() {
     return (AbstractCard)new ElectricRhino();
   }
 }

