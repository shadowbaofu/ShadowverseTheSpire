 package shadowverse.cards.Temp;


 import basemod.abstracts.CustomCard;
 import com.badlogic.gdx.graphics.Color;
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.actions.animations.VFXAction;
 import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
 import com.megacrit.cardcrawl.actions.common.DamageAction;
 import com.megacrit.cardcrawl.actions.common.GainBlockAction;
 import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
 import com.megacrit.cardcrawl.actions.utility.SFXAction;
 import com.megacrit.cardcrawl.cards.AbstractCard;
 import com.megacrit.cardcrawl.cards.DamageInfo;
 import com.megacrit.cardcrawl.characters.AbstractPlayer;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.core.Settings;
 import com.megacrit.cardcrawl.localization.CardStrings;
 import com.megacrit.cardcrawl.monsters.AbstractMonster;
 import com.megacrit.cardcrawl.powers.AbstractPower;
 import com.megacrit.cardcrawl.powers.PoisonPower;
 import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
 import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
 import shadowverse.characters.Vampire;


 public class Vanpi1
   extends CustomCard
 {
   public static final String ID = "shadowverse:Vanpi1";
   public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Vanpi1");
   public static final String NAME = cardStrings.NAME;
   public static final String DESCRIPTION = cardStrings.DESCRIPTION;
   public static final String IMG_PATH = "img/cards/Vanpi1.png";
   
   public Vanpi1() {
     super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, Vampire.Enums.COLOR_SCARLET, CardRarity.SPECIAL, CardTarget.ENEMY);
     this.baseDamage = 18;
       this.baseBlock = 18;
     this.exhaust = true;
     this.cardsToPreview = (AbstractCard)new ForestBat();
   }
 
   
   public void upgrade() {
     if (!this.upgraded) {
       upgradeName();
       upgradeDamage(3);
       upgradeBlock(3);
     } 
   }
 
   
   public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
       addToBot((AbstractGameAction)new SFXAction("Vanpi1"));
       addToBot((AbstractGameAction)new VFXAction((AbstractGameEffect)new BiteEffect(abstractMonster.hb.cX, abstractMonster.hb.cY - 40.0F * Settings.scale, Color.SCARLET.cpy()), 0.2F));
     addToBot((AbstractGameAction)new DamageAction((AbstractCreature)abstractMonster, new DamageInfo((AbstractCreature)abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
     addToBot((AbstractGameAction)new GainBlockAction(abstractPlayer,this.block));
     addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)abstractMonster, (AbstractCreature)abstractPlayer, (AbstractPower)new PoisonPower((AbstractCreature)abstractMonster, (AbstractCreature)abstractPlayer, 3), 3));
     addToBot((AbstractGameAction)new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy()));
   }
 
   
   public AbstractCard makeCopy() {
     return (AbstractCard)new Vanpi1();
   }
 }

