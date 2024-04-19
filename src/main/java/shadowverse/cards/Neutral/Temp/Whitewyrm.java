 package shadowverse.cards.Neutral.Temp;
 


import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
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
import shadowverse.characters.Witchcraft;
import shadowverse.relics.AnneBOSS;


 public class Whitewyrm
   extends CustomCard {
   public static final String ID = "shadowverse:Whitewyrm";
   public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Whitewyrm");
   public static final String NAME = cardStrings.NAME;
   public static final String DESCRIPTION = cardStrings.DESCRIPTION;
   public static final String IMG_PATH = "img/cards/Whitewyrm.png";
   
   public Whitewyrm() {
     super(ID, NAME,IMG_PATH, 4, DESCRIPTION, CardType.ATTACK, Witchcraft.Enums.COLOR_BLUE, CardRarity.SPECIAL, CardTarget.SELF);
     this.baseBlock = 8;
     this.exhaust = true;
     this.selfRetain = true;
     this.tags.add(AbstractShadowversePlayer.Enums.MYSTERIA);
   }

   public void triggerOnOtherCardPlayed(AbstractCard c) {
     if (c.hasTag(AbstractShadowversePlayer.Enums.MYSTERIA)||(AbstractDungeon.player.hasRelic(AnneBOSS.ID)&&c.type==CardType.SKILL)) {
       flash();
       addToBot(new SFXAction("spell_boost"));
       addToBot(new ReduceCostAction(this));
     }
   }
   
   public void upgrade() {
     if (!this.upgraded) {
       upgradeName();
       upgradeBlock(3);
     } 
   }
   
   public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
     addToBot(new SFXAction("Whitewyrm"));
     if (abstractPlayer instanceof AbstractShadowversePlayer){
       ((AbstractShadowversePlayer)abstractPlayer).mysteriaCount++;
     }
     addToBot(new GainBlockAction(abstractPlayer,this.block));
     addToBot(new AddTemporaryHPAction(abstractPlayer,abstractPlayer,3));
     addToBot(new DrawCardAction(1));
   }
 
 
   
   public AbstractCard makeCopy() {
     return (AbstractCard)new Whitewyrm();
   }
 }

