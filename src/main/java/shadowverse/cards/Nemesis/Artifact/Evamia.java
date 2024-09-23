 package shadowverse.cards.Nemesis.Artifact;
 


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;
import shadowverse.cards.Neutral.Temp.MysticArtifact;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Nemesis;

import java.util.ArrayList;


 public class Evamia
   extends CustomCard {
   public static final String ID = "shadowverse:Evamia";
   public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Evamia");
   public static final String NAME = cardStrings.NAME;
   public static final String DESCRIPTION = cardStrings.DESCRIPTION;
   public static final String IMG_PATH = "img/cards/Evamia.png";
   boolean trigger;

   public Evamia() {
     super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, Nemesis.Enums.COLOR_SKY, CardRarity.COMMON, CardTarget.SELF);
     this.baseBlock = 4;
     this.baseMagicNumber = 1;
     this.magicNumber = this.baseMagicNumber;
     this.cardsToPreview = new MysticArtifact();
     this.tags.add(AbstractShadowversePlayer.Enums.ARTIFACT);
   }
 
   
   public void upgrade() {
     if (!this.upgraded) {
       upgradeName();
       upgradeBlock(2);
       upgradeMagicNumber(1);
     } 
   }
 
   
   public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
     addToBot(new GainBlockAction(abstractPlayer,this.block));
     AbstractCard temp = this.cardsToPreview.makeStatEquivalentCopy();
     temp.cost = 0;
     temp.costForTurn = 0;
     temp.isCostModified = true;
     addToBot(new MakeTempCardInDrawPileAction(temp, this.magicNumber, true, true));
     ArrayList<AbstractCard> list = new ArrayList<>();
     ArrayList<String> dup = new ArrayList<>();
     for (AbstractCard c: abstractPlayer.exhaustPile.group){
       if (c.hasTag(AbstractShadowversePlayer.Enums.ARTIFACT)&&!dup.contains(c.cardID)){
         dup.add(c.cardID);
         AbstractCard card = c.makeCopy();
         list.add(card);
       }
     }
     if (list.size()>=6 && !trigger){
       trigger = true;
       addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new BufferPower(abstractPlayer,1),1));
     }
   }
 
 
   
   public AbstractCard makeCopy() {
     return (AbstractCard)new Evamia();
   }
 }

