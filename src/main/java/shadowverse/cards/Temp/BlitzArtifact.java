 package shadowverse.cards.Temp;


 import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Nemesis;


 public class BlitzArtifact
   extends CustomCard
 {
   public static final String ID = "shadowverse:BlitzArtifact";
   public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:BlitzArtifact");
   public static final String NAME = cardStrings.NAME;
   public static final String DESCRIPTION = cardStrings.DESCRIPTION;
   public static final String IMG_PATH = "img/cards/BlitzArtifact.png";

   public BlitzArtifact() {
     super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, Nemesis.Enums.COLOR_SKY, CardRarity.SPECIAL, CardTarget.ENEMY);
     this.baseDamage = 14;
     this.baseMagicNumber = 14;
     this.magicNumber = this.baseMagicNumber;
     this.exhaust = true;
     this.tags.add(AbstractShadowversePlayer.Enums.ARTIFACT);
     this.tags.add(AbstractShadowversePlayer.Enums.LASTWORD);
   }
 
   
   public void upgrade() {
     if (!this.upgraded) {
       upgradeName();
       upgradeDamage(2);
       upgradeMagicNumber(2);
     } 
   }
 
   @Override
   public void triggerOnExhaust(){
       addToBot((AbstractGameAction)new DamageAllEnemiesAction((AbstractCreature) AbstractDungeon.player, DamageInfo.createDamageMatrix(this.magicNumber, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE, true));
   }

   public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
     addToBot((AbstractGameAction)new DamageAction((AbstractCreature)abstractMonster, new DamageInfo((AbstractCreature)abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
   }
 
   
   public AbstractCard makeCopy() {
     return (AbstractCard)new BlitzArtifact();
   }
 }

