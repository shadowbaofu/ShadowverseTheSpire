 package shadowverse.cards.Dragon.Ramp;
 


import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import shadowverse.action.ImaginationAction;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Dragon;


 public class AbsoluteDragon extends CustomCard {
   public static final String ID = "shadowverse:AbsoluteDragon";
   public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:AbsoluteDragon");
   public static final String NAME = cardStrings.NAME;
   public static final String DESCRIPTION = cardStrings.DESCRIPTION;
   public static final String IMG_PATH = "img/cards/AbsoluteDragon.png";

   public AbsoluteDragon() {
     super(ID, NAME, IMG_PATH, 3, DESCRIPTION, CardType.ATTACK, Dragon.Enums.COLOR_BROWN, CardRarity.RARE, CardTarget.ENEMY);
     this.baseDamage = 24;
     this.tags.add(AbstractShadowversePlayer.Enums.LASTWORD);
   }
 
   
   public void upgrade() {
     if (!this.upgraded) {
       upgradeName();
       upgradeDamage(6);
     } 
   }

   public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
     addToBot(new VFXAction(new BiteEffect(abstractMonster.hb.cX, abstractMonster.hb.cY - 40.0F * Settings.scale, Color.CYAN.cpy()), 0.1F));
     addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
     addToBot(new DrawCardAction(1, new AbstractGameAction() {
       @Override
       public void update() {
         tickDuration();
         if (this.isDone)
           for (AbstractCard c : DrawCardAction.drawnCards) {
             if (CardLibrary.getCard(c.cardID) != null) {
               if (CardLibrary.getCard(c.cardID).cost>2 && CardLibrary.getCard(c.cardID).type==CardType.ATTACK && c.color == Dragon.Enums.COLOR_BROWN){
                 if (c.costForTurn > 0) {
                   c.costForTurn = 0;
                   c.isCostModifiedForTurn = true;
                 }
               }
             }
           }
       }
     }));
     addToBot(new MakeTempCardInDrawPileAction(this.makeStatEquivalentCopy(),1,true,true));
     addToBot(new AddTemporaryHPAction(abstractPlayer,abstractPlayer,3));
   }
 
   
   public AbstractCard makeCopy() {
     return (AbstractCard)new AbsoluteDragon();
   }
 }

