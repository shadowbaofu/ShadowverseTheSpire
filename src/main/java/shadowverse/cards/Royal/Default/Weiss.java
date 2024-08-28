 package shadowverse.cards.Royal.Default;

 import basemod.abstracts.CustomCard;
 import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
 import com.megacrit.cardcrawl.actions.utility.SFXAction;
 import com.megacrit.cardcrawl.cards.AbstractCard;
 import com.megacrit.cardcrawl.characters.AbstractPlayer;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.localization.CardStrings;
 import com.megacrit.cardcrawl.monsters.AbstractMonster;
 import shadowverse.characters.AbstractShadowversePlayer;
 import shadowverse.characters.Royal;
 import shadowverse.powers.WeissPower;


 public class Weiss
   extends CustomCard
 {
   public static final String ID = "shadowverse:Weiss";
   public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Weiss");
   public static final String NAME = cardStrings.NAME;
   public static final String DESCRIPTION = cardStrings.DESCRIPTION;
   public static final String IMG_PATH = "img/cards/Weiss.png";

   public Weiss() {
     super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, Royal.Enums.COLOR_YELLOW, CardRarity.UNCOMMON, CardTarget.ALL);
     this.tags.add(AbstractShadowversePlayer.Enums.ACADEMIC);
     this.exhaust = true;
   }


   public void upgrade() {
     if (!this.upgraded) {
       upgradeName();
       this.exhaust = false;
       this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
       this.initializeDescription();
     }
   }


   public void use(AbstractPlayer p, AbstractMonster m) {
       if(this.upgraded){
           addToBot(new SFXAction("Weiss"));
           addToBot(new ApplyPowerAction(p,p,new WeissPower(p)));
           for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters){
               if (mo != null && !mo.isDeadOrEscaped()){
                   addToBot(new ApplyPowerAction(mo,p,new WeissPower(mo)));
               }
           }
       }
   }


   public AbstractCard makeCopy() {
     return (AbstractCard)new Weiss();
   }
 }

