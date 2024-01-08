package shadowverse.powers;


import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import shadowverse.cards.Neutral.Temp.Amaterasu;


public class NextTurnAmaterasu
        extends AbstractPower {
    public static final String POWER_ID = "shadowverse:NextTurnAmaterasu";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("shadowverse:NextTurnAmaterasu");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean upgraded;

    public NextTurnAmaterasu(AbstractCreature owner, int amount, boolean upgraded) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.upgraded = upgraded;
        updateDescription();
        this.img = new Texture("img/powers/AmaterasuPower.png");
    }

    public void stackPower(int stackAmount) {
        this.amount += stackAmount;
        if (this.amount >= 999) {
            this.amount = 999;
        }
    }

    public void atStartOfTurn() {
        flash();
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        AbstractCard c = new Amaterasu();
        if (upgraded){
          c.upgrade();
        }
        addToBot(new MakeTempCardInHandAction(c, this.amount));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}

