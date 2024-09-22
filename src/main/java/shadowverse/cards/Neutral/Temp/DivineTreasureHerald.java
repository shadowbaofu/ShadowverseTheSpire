 package shadowverse.cards.Neutral.Temp;
 


import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Nemesis;


 public class DivineTreasureHerald
   extends CustomCard {
   public static final String ID = "shadowverse:DivineTreasureHerald";
   public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:DivineTreasureHerald");
   public static final String NAME = cardStrings.NAME;
   public static final String DESCRIPTION = cardStrings.DESCRIPTION;
   public static final String IMG_PATH = "img/cards/DivineTreasureHerald.png";

   public DivineTreasureHerald() {
     super(ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.ATTACK, Nemesis.Enums.COLOR_SKY, CardRarity.SPECIAL, CardTarget.ENEMY);
     this.baseDamage = 10;
     this.baseBlock = 10;
     this.tags.add(AbstractShadowversePlayer.Enums.LASTWORD);
     this.cardsToPreview = new SummonDivineTreasure_Copy();
     this.exhaust = true;
   }
 
   
   public void upgrade() {
     if (!this.upgraded) {
       upgradeName();
       upgradeDamage(3);
       upgradeBlock(3);
     } 
   }

   @Override
   public void triggerOnExhaust() {
     addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy()));
   }

   public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
     addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
     addToBot(new GainBlockAction(abstractPlayer,this.block));
   }
 
   
   public AbstractCard makeCopy() {
     return (AbstractCard)new shadowverse.cards.Neutral.Temp.DivineTreasureHerald();
   }
 }

