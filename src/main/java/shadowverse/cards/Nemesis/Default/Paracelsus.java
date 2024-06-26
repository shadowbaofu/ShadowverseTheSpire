 package shadowverse.cards.Nemesis.Default;


 import basemod.abstracts.CustomCard;
 import com.badlogic.gdx.Gdx;
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.actions.common.GainBlockAction;
 import com.megacrit.cardcrawl.actions.utility.SFXAction;
 import com.megacrit.cardcrawl.cards.AbstractCard;
 import com.megacrit.cardcrawl.characters.AbstractPlayer;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.localization.CardStrings;
 import com.megacrit.cardcrawl.monsters.AbstractMonster;
 import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
 import shadowverse.action.ChoiceAction2;
 import shadowverse.cards.Neutral.Temp.*;
 import shadowverse.characters.Nemesis;

 import java.util.ArrayList;


 public class Paracelsus
   extends CustomCard {
   public static final String ID = "shadowverse:Paracelsus";
   public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Paracelsus");
   public static final String NAME = cardStrings.NAME;
   public static final String DESCRIPTION = cardStrings.DESCRIPTION;
   public static final String IMG_PATH = "img/cards/Paracelsus.png";
   private float rotationTimer;
   private int previewIndex;
   public static ArrayList<AbstractCard> returnChoice() {
     ArrayList<AbstractCard> list = new ArrayList<>();
     list.add(new StrikeFormGolem());
     list.add(new GuardFormGolem());
     return list;
   }

   public Paracelsus() {
     super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, Nemesis.Enums.COLOR_SKY, CardRarity.UNCOMMON, CardTarget.SELF);
     this.baseBlock = 5;
     this.cardsToPreview = (AbstractCard)new AncientArtifact();
   }
 
   
   public void upgrade() {
     if (!this.upgraded) {
       upgradeName();
       upgradeBlock(4);
     } 
   }

   public void update() {
     super.update();
     if (this.hb.hovered)
       if (this.rotationTimer <= 0.0F) {
         this.rotationTimer = 2.0F;
         this.cardsToPreview = (AbstractCard)returnChoice().get(previewIndex).makeCopy();
         if (this.previewIndex == returnChoice().size() - 1) {
           this.previewIndex = 0;
         } else {
           this.previewIndex++;
         }
       } else {
         this.rotationTimer -= Gdx.graphics.getDeltaTime();
       }
   }
 
   
   public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
     addToBot((AbstractGameAction)new SFXAction("Paracelsus"));
     addToBot((AbstractGameAction)new GainBlockAction(abstractPlayer,this.block));
     AbstractCard s = (AbstractCard)new StrikeFormGolem();
     AbstractCard g = (AbstractCard)new GuardFormGolem();
     if (EnergyPanel.getCurrentEnergy()>=this.costForTurn+2){
         s.setCostForTurn(2);
         g.setCostForTurn(2);
     }
     addToBot((AbstractGameAction)new ChoiceAction2(new AbstractCard[] { s,g  }));
   }
 
 
   
   public AbstractCard makeCopy() {
     return (AbstractCard)new Paracelsus();
   }
 }

