 package shadowverse.cards.Dragon.Armed;
 


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.action.ArmedReanimateAction;
import shadowverse.cards.Neutral.Temp.DragonWeapon;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Dragon;


 public class GallantDragonewt extends CustomCard {
   public static final String ID = "shadowverse:GallantDragonewt";
   public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:GallantDragonewt");
   public static final String NAME = cardStrings.NAME;
   public static final String DESCRIPTION = cardStrings.DESCRIPTION;
   public static final String IMG_PATH = "img/cards/GallantDragonewt.png";



   public GallantDragonewt() {
     super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, Dragon.Enums.COLOR_BROWN, CardRarity.UNCOMMON, CardTarget.ENEMY);
     this.baseDamage = 10;
     this.tags.add(AbstractShadowversePlayer.Enums.ARMED);
     this.cardsToPreview = new DragonWeapon();
   }
 
   
   public void upgrade() {
     if (!this.upgraded) {
       upgradeName();
       upgradeDamage(3);
     } 
   }

   public void applyPowers() {
     super.applyPowers();
     int count = 0;
     for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat){
       if (c.type == CardType.ATTACK && c.hasTag(AbstractShadowversePlayer.Enums.ARMED)){
         count++;
       }
     }
     this.rawDescription = cardStrings.DESCRIPTION;
     this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + count;
     this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[1];
     initializeDescription();
   }
 
   
   public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
     addToBot(new SFXAction("GallantDragonewt"));
     addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy()));
     int count = 0;
     for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat){
       if (c.type == CardType.ATTACK && c.hasTag(AbstractShadowversePlayer.Enums.ARMED)){
         count++;
       }
     }
     if (count > 4){
       addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage*2, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
       addToBot(new ArmedReanimateAction());
     }else {
       addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
     }
   }
 
   
   public AbstractCard makeCopy() {
     return (AbstractCard)new GallantDragonewt();
   }
 }

