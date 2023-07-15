 package shadowverse.cards.Nemesis.Resonance;
 


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.stances.NeutralStance;
import shadowverse.cards.Neutral.Temp.Shin_Token;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Nemesis;
import shadowverse.stance.Resonance;


 public class ChaosAura extends CustomCard {
   public static final String ID = "shadowverse:ChaosAura";
   public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:ChaosAura");
   public static final String NAME = cardStrings.NAME;
   public static final String DESCRIPTION = cardStrings.DESCRIPTION;
   public static final String IMG_PATH = "img/cards/ChaosAura.png";

   public ChaosAura() {
     super(ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.SKILL, Nemesis.Enums.COLOR_SKY, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
     this.baseDamage = 2;
     this.cardsToPreview = new Shin_Token();
     this.exhaust = true;
   }
 
   
   public void upgrade() {
     if (!this.upgraded) {
       upgradeName();
       upgradeDamage(1);
       this.cardsToPreview.upgrade();
       this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
       initializeDescription();
     } 
   }


   public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
     addToBot(new SFXAction("ChaosAura"));
     int amount = 0;
     for (AbstractCard c : p.exhaustPile.group){
       if (c instanceof ChaosAura){
         amount++;
       }
     }
     if (amount < 5){
       addToBot(new DamageRandomEnemyAction(new DamageInfo(p,this.damage * amount,this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
       addToBot(new DrawCardAction(1));
       if ((p.drawPile.size()+1)%2==0&&!p.stance.ID.equals(Resonance.STANCE_ID)){
         addToBot(new ChangeStanceAction(new Resonance()));
         if (p instanceof AbstractShadowversePlayer)
           ((AbstractShadowversePlayer) p).resonanceCount++;
       }else if ((p.drawPile.size()+1)%2!=0&& p.stance.ID.equals(Resonance.STANCE_ID)){
         addToBot(new ChangeStanceAction(new NeutralStance()));
         if (p instanceof AbstractShadowversePlayer)
           ((AbstractShadowversePlayer) p).resonanceCount--;
       }
       addToBot(new MakeTempCardInDrawPileAction(this.makeStatEquivalentCopy(),3,true,true));
     }else {
       addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy()));
     }
   }
 
   
   public AbstractCard makeCopy() {
     return (AbstractCard)new ChaosAura();
   }
 }

