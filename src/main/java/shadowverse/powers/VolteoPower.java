package shadowverse.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import shadowverse.action.BetterAutoPlayCardAction;
import shadowverse.stance.Vengeance;

import java.util.ArrayList;
import java.util.Collections;


public class VolteoPower
        extends AbstractPower {
    public static final String POWER_ID = "shadowverse:VolteoPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("shadowverse:VolteoPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public VolteoPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.type = PowerType.BUFF;
        updateDescription();
        this.img = new Texture("img/powers/VolteoPower.png");
    }


    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }


    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        if (isPlayer) {
            AbstractPlayer p = (AbstractPlayer) this.owner;
            addToBot(new ApplyPowerAction(this.owner, this.owner, new PlatedArmorPower(this.owner, 8), 8));
            ArrayList<AbstractCard> list = new ArrayList<>();
            for (AbstractCard card : p.drawPile.group) {
                if (card.type == AbstractCard.CardType.ATTACK) {
                    list.add(card);
                }
            }
            if (list.size() > 0) {
                Collections.shuffle(list);
                list.get(0).setCostForTurn(0);
                addToBot(new BetterAutoPlayCardAction(list.get(0), p.drawPile));
            }
            if (list.size() > 1) {
                list.get(1).setCostForTurn(0);
                addToBot(new BetterAutoPlayCardAction(list.get(1), p.drawPile));
            }
        }
    }
}


