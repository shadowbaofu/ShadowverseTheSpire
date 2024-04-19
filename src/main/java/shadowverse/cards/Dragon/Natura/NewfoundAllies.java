 package shadowverse.cards.Dragon.Natura;
 


import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rs.lazymankits.interfaces.cards.BranchableUpgradeCard;
import rs.lazymankits.interfaces.cards.UpgradeBranch;
import shadowverse.action.DrawPileToHandAction_Tag_NOREPEAT;
import shadowverse.cards.AbstractRightClickCard2;
import shadowverse.cards.Neutral.Temp.NaterranGreatTree;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Dragon;
import shadowverse.powers.NaterranTree;

import java.util.ArrayList;
import java.util.List;


 public class NewfoundAllies
   extends AbstractRightClickCard2 implements BranchableUpgradeCard {
   public static final String ID = "shadowverse:NewfoundAllies";
   public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:NewfoundAllies");
   public static CardStrings cardStrings2 = CardCrawlGame.languagePack.getCardStrings("shadowverse:NewfoundAllies2");
   public static final String NAME = cardStrings.NAME;
   public static final String DESCRIPTION = cardStrings.DESCRIPTION;
   public static final String IMG_PATH = "img/cards/NewfoundAllies.png";
   private boolean hasFusion = false;

   public NewfoundAllies() {
     super(ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.SKILL, Dragon.Enums.COLOR_BROWN, CardRarity.COMMON, CardTarget.NONE);
     this.baseMagicNumber = 1;
     this.magicNumber = this.baseMagicNumber;
     this.tags.add(AbstractShadowversePlayer.Enums.NATURAL);
   }
 
   
   public void upgrade() {
     ((UpgradeBranch) ((BranchableUpgradeCard) this).possibleBranches().get(chosenBranch())).upgrade();
   }
 
   
   public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
     switch (chosenBranch()){
       case 0:
         addToBot(new SFXAction("NewfoundAllies"));
         addToBot(new DrawPileToHandAction_Tag_NOREPEAT(this.magicNumber, AbstractShadowversePlayer.Enums.NATURAL,null,this));
         if (abstractPlayer.hasPower(NaterranTree.POWER_ID)){
           addToBot(new DrawPileToHandAction_Tag_NOREPEAT(1, AbstractShadowversePlayer.Enums.NATURAL,null,this));
         }
         break;
       case 1:
         addToBot(new SFXAction("NewfoundAllies2"));
         addToBot(new GainBlockAction(abstractPlayer,this.block));
         addToBot(new MakeTempCardInHandAction(new NaterranGreatTree()));
         break;
     }
   }
   
   public AbstractCard makeCopy() {
     return (AbstractCard)new NewfoundAllies();
   }

   @Override
   protected void onRightClick() {
     if (this.chosenBranch() == 1){
       if (!this.hasFusion && AbstractDungeon.player != null) {
         addToBot(new SelectCardsInHandAction(9, TEXT[0], true, true, card -> {
           return CardLibrary.getCard(card.cardID) != null && card.color== Dragon.Enums.COLOR_BROWN && card != this
                   && !card.hasTag(CardTags.STARTER_STRIKE) && !card.hasTag(CardTags.STARTER_DEFEND);
         }, abstractCards -> {
           if (abstractCards.size() > 0) {
             this.hasFusion = true;
             addToBot(new MakeTempCardInHandAction(new NaterranGreatTree()));
           }
           for (AbstractCard c : abstractCards) {
             addToBot(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
           }
         }));
       }
     }
   }

   @Override
   public List<UpgradeBranch> possibleBranches() {
     ArrayList<UpgradeBranch> list = new ArrayList<UpgradeBranch>();
     list.add(new UpgradeBranch() {
       @Override
       public void upgrade() {
         ++NewfoundAllies.this.timesUpgraded;
         NewfoundAllies.this.upgraded = true;
         NewfoundAllies.this.name = cardStrings.NAME + "+";
         NewfoundAllies.this.initializeTitle();
         NewfoundAllies.this.upgradeName();
         NewfoundAllies.this.baseMagicNumber = 2;
         NewfoundAllies.this.magicNumber = NewfoundAllies.this.baseMagicNumber;
         NewfoundAllies.this.upgradedMagicNumber = true;
       }
     });
     list.add(new UpgradeBranch() {
       @Override
       public void upgrade() {
         ++NewfoundAllies.this.timesUpgraded;
         NewfoundAllies.this.upgraded = true;
         NewfoundAllies.this.name = cardStrings2.NAME;
         NewfoundAllies.this.initializeTitle();
         NewfoundAllies.this.rawDescription = cardStrings2.DESCRIPTION;
         NewfoundAllies.this.initializeDescription();
         NewfoundAllies.this.baseBlock = 11;
         NewfoundAllies.this.upgradedBlock = true;
         NewfoundAllies.this.cardsToPreview = new NaterranGreatTree();
         NewfoundAllies.this.upgradeBaseCost(1);
       }
     });
     return list;
   }
 }

