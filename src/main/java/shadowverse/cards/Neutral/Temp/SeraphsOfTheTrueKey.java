 package shadowverse.cards.Neutral.Temp;
 


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.GrandFinalEffect;
import com.megacrit.cardcrawl.vfx.combat.MiracleEffect;
import shadowverse.action.SeraphsAction;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Bishop;
import shadowverse.powers.SeraphsPower;


 public class SeraphsOfTheTrueKey extends CustomCard {
   public static final String ID = "shadowverse:SeraphsOfTheTrueKey";
   public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:SeraphsOfTheTrueKey");
   public static final String NAME = cardStrings.NAME;
   public static final String DESCRIPTION = cardStrings.DESCRIPTION;
   public static final String IMG_PATH = "img/cards/SeraphsOfTheTrueKey.png";

   public SeraphsOfTheTrueKey() {
     super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, Bishop.Enums.COLOR_WHITE, CardRarity.SPECIAL, CardTarget.ENEMY);
     this.baseDamage = 30;
     this.baseBlock = 30;
     this.tags.add(AbstractShadowversePlayer.Enums.LASTWORD);
   }
 
   
   public void upgrade() {
     if (!this.upgraded) {
       upgradeName();
       upgradeDamage(5);
       upgradeBlock(5);
     } 
   }
 
   
   public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
     addToBot(new SFXAction("SeraphsOfTheTrueKey"));
     addToBot(new VFXAction(new MiracleEffect()));
     addToBot(new VFXAction(new GrandFinalEffect()));
     addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,this.damage,this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
     addToBot(new GainBlockAction(abstractPlayer,this.block));
     addToBot(new SeraphsAction());
     addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new SeraphsPower(abstractPlayer)));
   }
 
   
   public AbstractCard makeCopy() {
     return (AbstractCard)new SeraphsOfTheTrueKey();
   }
 }

