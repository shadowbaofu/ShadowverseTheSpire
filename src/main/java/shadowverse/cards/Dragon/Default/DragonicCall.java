 package shadowverse.cards.Dragon.Default;
 


import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.powers.NoDrawPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import shadowverse.characters.Dragon;
import shadowverse.powers.OverflowPower;


 public class DragonicCall
   extends CustomCard {
   public static final String ID = "shadowverse:DragonicCall";
   public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:DragonicCall");
   public static final String NAME = cardStrings.NAME;
   public static final String DESCRIPTION = cardStrings.DESCRIPTION;
   public static final String IMG_PATH = "img/cards/DragonicCall.png";
   private static final Texture LEADER_SKIN_VERSION = ImageMaster.loadImage("img/cards/DragonicCall_L.png");

   public DragonicCall() {
     super(ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.SKILL, Dragon.Enums.COLOR_BROWN, CardRarity.UNCOMMON, CardTarget.NONE);
     this.baseMagicNumber = 2;
     this.magicNumber = this.baseMagicNumber;
     this.jokePortrait = new TextureAtlas.AtlasRegion(LEADER_SKIN_VERSION, 0, 0, LEADER_SKIN_VERSION.getWidth(), LEADER_SKIN_VERSION.getHeight());
   }
 
   
   public void upgrade() {
     if (!this.upgraded) {
       upgradeName();
       upgradeMagicNumber(1);
     } 
   }
 
   
   public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
     if ((UnlockTracker.betaCardPref.getBoolean(this.cardID, false))) {
       addToBot(new SFXAction("DragonicCall_L"));
     }
     addToBot(new DrawCardAction(this.magicNumber));
     if (abstractPlayer.hasPower(OverflowPower.POWER_ID)){
       TwoAmountPower p = (TwoAmountPower) abstractPlayer.getPower(OverflowPower.POWER_ID);
       if (p.amount2 > 0){
         addToBot(new DrawCardAction(1));
       }
     }
     addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new NoDrawPower(abstractPlayer)));
   }
 
 
   
   public AbstractCard makeCopy() {
     return (AbstractCard)new DragonicCall();
   }
 }

