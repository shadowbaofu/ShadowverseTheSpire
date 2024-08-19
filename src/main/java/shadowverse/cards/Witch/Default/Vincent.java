 package shadowverse.cards.Witch.Default;
 


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.TimeWarpTurnEndEffect;
import rs.lazymankits.interfaces.cards.BranchableUpgradeCard;
import rs.lazymankits.interfaces.cards.UpgradeBranch;
import shadowverse.cards.Neutral.Temp.JudgmentWord;
import shadowverse.cards.Neutral.Temp.WordsOfSanction;
import shadowverse.characters.Witchcraft;
import shadowverse.powers.VincentPower;
import shadowverse.powers.VincentPower2;

import java.util.ArrayList;
import java.util.List;


 public class Vincent
   extends CustomCard  implements BranchableUpgradeCard
 {
   public static final String ID = "shadowverse:Vincent";
   public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Vincent");
   public static CardStrings cardStrings2 = CardCrawlGame.languagePack.getCardStrings("shadowverse:Vincent2");
   public static final String NAME = cardStrings.NAME;
   public static final String DESCRIPTION = cardStrings.DESCRIPTION;
   public static final String IMG_PATH = "img/cards/Vincent.png";

   private int previewBranch;

   private static AbstractCard judgmentWord = new JudgmentWord();
   private static AbstractCard wordsOfSanction = new WordsOfSanction();

   public Vincent() {
     super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.POWER, Witchcraft.Enums.COLOR_BLUE, CardRarity.RARE, CardTarget.SELF);
     this.baseMagicNumber = 1;
     this.magicNumber = this.baseMagicNumber;
   }
 
   
   public void upgrade() {
     ((UpgradeBranch)((BranchableUpgradeCard)this).possibleBranches().get(chosenBranch())).upgrade();
   }

   public void update() {
     super.update();
     if (this.previewBranch == 0) {
         this.cardsToPreview = judgmentWord;
     }else if (this.previewBranch == 1){
       this.cardsToPreview = wordsOfSanction;
     }
   }
   
   
   public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
     switch (chosenBranch()){
       case 0:
         addToBot(new SFXAction("Vincent"));
         addToBot(new VFXAction(new TimeWarpTurnEndEffect(), 0.0F));
         addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new VincentPower( abstractPlayer)));
         break;
       case 1:
         addToBot(new SFXAction("Vincent2"));
         addToBot(new VFXAction(new TimeWarpTurnEndEffect(), 0.0F));
         addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new VincentPower2( abstractPlayer)));
         addToBot(new MakeTempCardInHandAction(new WordsOfSanction()));
         break;
     }
   }

   @Override
   public List<UpgradeBranch> possibleBranches() {
     ArrayList<UpgradeBranch> list = new ArrayList<UpgradeBranch>();
     list.add(new UpgradeBranch() {
       @Override
       public void upgrade() {
         ++Vincent.this.timesUpgraded;
         Vincent.this.upgraded = true;
         Vincent.this.name = cardStrings.NAME + "+";
         Vincent.this.isInnate = true;
         Vincent.this.initializeTitle();
         Vincent.this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
         Vincent.this.initializeDescription();
         Vincent.this.previewBranch = 0;
       }
     });
     list.add(new UpgradeBranch() {
       @Override
       public void upgrade() {
         ++Vincent.this.timesUpgraded;
         Vincent.this.upgraded = true;
         Vincent.this.name = cardStrings2.NAME;
         Vincent.this.initializeTitle();
         Vincent.this.rawDescription = cardStrings2.DESCRIPTION;
         Vincent.this.initializeDescription();
         Vincent.this.previewBranch = 1;
       }
     });
     return list;
   }
   
   public AbstractCard makeCopy() {
     return (AbstractCard)new Vincent();
   }
 }


