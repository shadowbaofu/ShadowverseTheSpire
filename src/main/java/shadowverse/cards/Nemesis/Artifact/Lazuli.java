 package shadowverse.cards.Nemesis.Artifact;
 


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.cards.Neutral.Status.EvolutionPoint;
import shadowverse.cards.Neutral.Temp.AnalyzeArtifact;
import shadowverse.cards.Neutral.Temp.RadiantArtifact;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Nemesis;

import java.util.ArrayList;


 public class Lazuli
   extends CustomCard {
   public static final String ID = "shadowverse:Lazuli";
   public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Lazuli");
   public static final String NAME = cardStrings.NAME;
   public static final String DESCRIPTION = cardStrings.DESCRIPTION;
   public static final String IMG_PATH = "img/cards/Lazuli.png";

   public Lazuli() {
     super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, Nemesis.Enums.COLOR_SKY, CardRarity.UNCOMMON, CardTarget.SELF);
     this.baseBlock = 6;
     this.baseMagicNumber = 1;
     this.magicNumber = this.baseMagicNumber;
     this.cardsToPreview = new RadiantArtifact();
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
     addToBot(new SFXAction("Lazuli"));
     addToBot(new GainBlockAction(abstractPlayer,this.block));
     addToBot(new MakeTempCardInDrawPileAction(this.cardsToPreview.makeStatEquivalentCopy(), this.magicNumber, true, true));
     addToBot(new DrawPileToHandAction(1,CardType.SKILL));
     ArrayList<AbstractCard> list = new ArrayList<>();
     ArrayList<String> dup = new ArrayList<>();
     for (AbstractCard c: abstractPlayer.exhaustPile.group){
       if (c.hasTag(AbstractShadowversePlayer.Enums.ARTIFACT)&&!dup.contains(c.cardID)){
         dup.add(c.cardID);
         AbstractCard card = c.makeCopy();
         list.add(card);
       }
     }
     if (list.size()>=6){
       addToBot(new MakeTempCardInHandAction(new EvolutionPoint()));
     }
   }
 
 
   
   public AbstractCard makeCopy() {
     return (AbstractCard)new Lazuli();
   }
 }

