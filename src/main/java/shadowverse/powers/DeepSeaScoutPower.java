package shadowverse.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DeepSeaScoutPower extends AbstractPower {
    public static final String POWER_ID = "shadowverse:DeepSeaScoutPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("shadowverse:DeepSeaScoutPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private int num;

    public DeepSeaScoutPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.num = 0;
        updateDescription();
        this.img = new Texture("img/powers/DeepSeaScoutPower.png");
    }

    @Override
    public void stackPower(int stackAmount) {
        this.amount += stackAmount;
        if (this.amount >= 999) {
            this.amount = 999;
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void onExhaust(AbstractCard card) {
        if (card.cardID == "shadowverse:DreadPirateFlag") {
            flash();
            addToBot(new SFXAction("DeepSeaScoutPower"));
            addToBot(new DrawCardAction(this.amount));
        } else if (card.type == AbstractCard.CardType.SKILL) {
            num++;
            if (num % 2 == 0) {
                flash();
                addToBot(new SFXAction("DeepSeaScoutPower"));
                addToBot(new DrawCardAction(this.amount));
                num = 0;
            }
        }
    }
}