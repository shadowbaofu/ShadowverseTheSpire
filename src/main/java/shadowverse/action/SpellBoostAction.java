 package shadowverse.action;
 
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import shadowverse.characters.AbstractShadowversePlayer;

import java.util.ArrayList;







 
 public class SpellBoostAction
   extends AbstractGameAction {
   private AbstractPlayer abstractPlayer;
   private AbstractCard card;
   private ArrayList<AbstractCard> group;
   
   public SpellBoostAction(AbstractPlayer abstractPlayer, AbstractCard card, ArrayList<AbstractCard> group) {
     this.abstractPlayer = abstractPlayer;
     this.card = card;
     this.group = group;
   }
 
   
   public void update() {
     for (AbstractCard c : this.group) {
       if (c.hasTag(AbstractShadowversePlayer.Enums.SPELL_BOOST)) {
         for (int i = 0; i < this.card.magicNumber; i++) {
           c.flash();
           addToBot(new SFXAction("spell_boost"));
           addToBot(new ReduceCostAction(c));
         }  continue;
       } 
       if (c.hasTag(AbstractShadowversePlayer.Enums.SPELL_BOOST_ATTACK)) {
         for (int i = 0; i < this.card.magicNumber; i++) {
           c.flash();
           
           c.magicNumber = ++c.baseMagicNumber;
           addToBot(new SFXAction("spell_boost"));
         } 
       }
     } 
     this.isDone = true;
   }
 }


