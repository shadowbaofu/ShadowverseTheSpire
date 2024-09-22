 package shadowverse.cards.Witch.Default;
 
 import basemod.abstracts.CustomCard;
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.actions.animations.VFXAction;
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
 import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;
 import shadowverse.characters.Witchcraft;
 
 
 public class StaffOfWhirlwinds
   extends CustomCard
 {
   public static final String ID = "shadowverse:StaffOfWhirlwinds";
   public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:StaffOfWhirlwinds");
   public static final String NAME = cardStrings.NAME;
   public static final String DESCRIPTION = cardStrings.DESCRIPTION;
   public static final String IMG_PATH = "img/cards/StaffOfWhirlwinds.png";
   
   public StaffOfWhirlwinds() {
     super("shadowverse:StaffOfWhirlwinds", NAME, "img/cards/StaffOfWhirlwinds.png", 2, DESCRIPTION, CardType.SKILL, Witchcraft.Enums.COLOR_BLUE, CardRarity.COMMON, CardTarget.ENEMY);
     this.baseDamage = 12;
     this.baseMagicNumber = this.baseDamage / 3;
     this.magicNumber = this.baseMagicNumber;
   }
 
   
   public void upgrade() {
     if (!this.upgraded) {
       upgradeName();
       upgradeDamage(3);
     } 
   }
 
   
   public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
     addToBot(new SFXAction("ATTACK_WHIRLWIND"));
     addToBot(new VFXAction(new WhirlwindEffect(), 0.0F));
     addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
     addToBot(new DamageAllEnemiesAction(abstractPlayer, DamageInfo.createDamageMatrix(this.magicNumber, true), DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE, true));
   }
 
   
   public AbstractCard makeCopy() {
     return (AbstractCard)new StaffOfWhirlwinds();
   }
 }

