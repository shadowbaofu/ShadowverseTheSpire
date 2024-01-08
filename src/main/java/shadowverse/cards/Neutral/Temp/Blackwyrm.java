 package shadowverse.cards.Neutral.Temp;
 


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
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
import shadowverse.powers.NextTurnAmaterasu;
import shadowverse.relics.AnneBOSS;


 public class Blackwyrm
   extends CustomCard {
   public static final String ID = "shadowverse:Blackwyrm";
   public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Blackwyrm");
   public static final String NAME = cardStrings.NAME;
   public static final String DESCRIPTION = cardStrings.DESCRIPTION;
   public static final String IMG_PATH = "img/cards/Blackwyrm.png";
   
   public Blackwyrm() {
     super(ID, NAME,IMG_PATH, 4, DESCRIPTION, CardType.ATTACK, Witchcraft.Enums.COLOR_BLUE, CardRarity.SPECIAL, CardTarget.ENEMY);
     this.baseDamage = 12;
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
       upgradeDamage(3);
     } 
   }

   public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
     addToBot(new SFXAction("Blackwyrm"));
     if (abstractPlayer instanceof AbstractShadowversePlayer){
       ((AbstractShadowversePlayer)abstractPlayer).mysteriaCount++;
     }
     addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,this.damage,this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
     addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(6, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE, true));
   }
 
 
   
   public AbstractCard makeCopy() {
     return (AbstractCard)new Blackwyrm();
   }
 }

