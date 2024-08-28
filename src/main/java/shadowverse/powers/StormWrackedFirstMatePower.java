package shadowverse.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import shadowverse.action.MinionSummonAction;
import shadowverse.orbs.Cannoneer;

public class StormWrackedFirstMatePower extends AbstractPower {
    public static final String POWER_ID = "shadowverse:StormWrackedFirstMatePower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("shadowverse:StormWrackedFirstMatePower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private int num;

    public StormWrackedFirstMatePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.num = 0;
        updateDescription();
        this.img = new Texture("img/powers/StormWrackedFirstMatePower.png");
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
        AbstractPlayer p = AbstractDungeon.player;
        if (card.cardID == "shadowverse:DreadPirateFlag") {
            flash();
            addToBot(new SFXAction("StormWrackedFirstMatePower"));
            for (int i = 0; i < this.amount; i++) {
                if (p.hasEmptyOrb()) {
                    addToBot(new MinionSummonAction(new Cannoneer()));
                }
            }
        } else if (card.type == AbstractCard.CardType.SKILL) {
            num++;
            if (num % 2 == 0) {
                flash();
                addToBot(new SFXAction("StormWrackedFirstMatePower"));
                for (int i = 0; i < this.amount; i++) {
                    if (p.hasEmptyOrb()) {
                        addToBot(new MinionSummonAction(new Cannoneer()));
                    }
                }
                num = 0;
            }
        }
    }
}