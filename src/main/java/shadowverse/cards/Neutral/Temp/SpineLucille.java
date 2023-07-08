 package shadowverse.cards.Neutral.Temp;
 


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Necromancer;
import shadowverse.characters.Nemesis;

import java.util.ArrayList;


 public class SpineLucille
   extends CustomCard {
   public static final String ID = "shadowverse:SpineLucille";
   public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:SpineLucille");
   public static final String NAME = cardStrings.NAME;
   public static final String DESCRIPTION = cardStrings.DESCRIPTION;
   public static final String IMG_PATH = "img/cards/SpineLucille.png";

   public SpineLucille() {
     super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, Nemesis.Enums.COLOR_SKY, CardRarity.SPECIAL, CardTarget.ENEMY);
     this.baseDamage = 9;
     this.baseMagicNumber = 36;
     this.magicNumber = this.baseMagicNumber;
     this.tags.add(AbstractShadowversePlayer.Enums.ARTIFACT);
     this.exhaust = true;
   }
 
   
   public void upgrade() {
     if (!this.upgraded) {
       upgradeName();
       upgradeDamage(3);
     } 
   }

   public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
     addToBot(new SFXAction("SpineLucille"));
     addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
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
       addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(this.magicNumber, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
     }
   }
 
   
   public AbstractCard makeCopy() {
     return (AbstractCard)new SpineLucille();
   }
 }

