package shadowverse.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostForTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import shadowverse.characters.AbstractShadowversePlayer;

import java.util.ArrayList;

public class OzSetCostAction
        extends AbstractGameAction {
    private ArrayList<AbstractCard> group;

    public OzSetCostAction(ArrayList<AbstractCard> group) {
        this.group = group;
    }


    public void update() {
        for (AbstractCard c : this.group) {
            if (!c.hasTag(AbstractShadowversePlayer.Enums.SPELL_BOOST) && !c.hasTag(AbstractShadowversePlayer.Enums.SPELL_BOOST_ATTACK) && CardLibrary.getCard(c.cardID) != null && CardLibrary.getCard(c.cardID).type == AbstractCard.CardType.SKILL) {
                addToBot(new ReduceCostForTurnAction(c, 9));
            }
            this.isDone = true;
        }
    }
}

