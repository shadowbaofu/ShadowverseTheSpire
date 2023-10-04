 package shadowverse.cards.Neutral.Temp;


 import basemod.abstracts.CustomCard;
 import com.megacrit.cardcrawl.cards.AbstractCard;
 import com.megacrit.cardcrawl.characters.AbstractPlayer;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.core.Settings;
 import com.megacrit.cardcrawl.helpers.CardLibrary;
 import com.megacrit.cardcrawl.localization.CardStrings;
 import com.megacrit.cardcrawl.monsters.AbstractMonster;
 import shadowverse.action.BlockPerCardAction;
 import shadowverse.characters.Necromancer;

 import java.util.ArrayList;


 public class HeartRadiance extends CustomCard {
   public static final String ID = "shadowverse:HeartRadiance";
   public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:HeartRadiance");
   public static final String NAME = cardStrings.NAME;
   public static final String DESCRIPTION = cardStrings.DESCRIPTION;
   public static final String IMG_PATH = "img/cards/HeartRadiance.png";

   public HeartRadiance() {
     super(ID, NAME, IMG_PATH, 3, DESCRIPTION, CardType.SKILL, Necromancer.Enums.COLOR_PURPLE, CardRarity.SPECIAL, CardTarget.NONE);
     this.baseBlock = 2;
     this.exhaust = true;
   }
 
   
   public void upgrade() {
     if (!this.upgraded) {
       upgradeName();
       upgradeBlock(1);
     } 
   }

   public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
       int amount = 0;
       ArrayList<String> box = new ArrayList<>();
       ArrayList<AbstractCard> cardList = new ArrayList<AbstractCard>();
       for (AbstractCard c : abstractPlayer.exhaustPile.group){
           if (CardLibrary.getCard(c.cardID) != null && CardLibrary.getCard(c.cardID).type == AbstractCard.CardType.ATTACK
                   && c.color == Necromancer.Enums.COLOR_PURPLE && CardLibrary.getCard(cardID).cost >= 1){
               if (!box.contains(c.cardID)){
                   box.add(c.cardID);
                   cardList.add(c);
                   amount++;
               }
           }
           if (amount > 4)
               break;
       }
       for (AbstractCard tmp : cardList){
           tmp.setCostForTurn(0);
           tmp.costForTurn = 0;
           tmp.isCostModified = true;
           tmp.initializeDescription();
           tmp.applyPowers();
           tmp.lighten(true);
           tmp.setAngle(0.0F);
           tmp.drawScale = 0.12F;
           tmp.targetDrawScale = 0.75F;
           tmp.current_x = Settings.WIDTH / 2.0F;
           tmp.current_y = Settings.HEIGHT / 2.0F;
           abstractPlayer.hand.addToTop(tmp);
       }
       addToBot(new BlockPerCardAction(this.block));
   }
 
   
   public AbstractCard makeCopy() {
     return (AbstractCard)new HeartRadiance();
   }
 }
