package shadowverse.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class OzPower
        extends AbstractPower {
    public static final String POWER_ID = "shadowverse:OzPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("shadowverse:OzPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public OzPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = "shadowverse:OzPower";
        this.owner = owner;
        this.amount = -1;
        this.type = PowerType.DEBUFF;
        updateDescription();
        this.img = new Texture("img/powers/OzPower.png");
    }


    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }


    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        if (isPlayer) {
            addToBot(new SFXAction("OzPower"));
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c.type == AbstractCard.CardType.SKILL && !(CardLibrary.getCard(c.cardID) != null && CardLibrary.getCard(c.cardID).type == AbstractCard.CardType.ATTACK)) {
                    addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
                }
            }
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "shadowverse:OzPower"));
        }
    }
}

