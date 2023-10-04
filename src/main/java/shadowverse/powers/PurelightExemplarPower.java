package shadowverse.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import shadowverse.characters.AbstractShadowversePlayer;


public class PurelightExemplarPower
        extends AbstractPower {
    public static final String POWER_ID = "shadowverse:PurelightExemplarPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("shadowverse:PurelightExemplarPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private int artifactCount;

    public PurelightExemplarPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.type = PowerType.BUFF;
        updateDescription();
        this.img = new Texture("img/powers/PurelightPrototypePower.png");
    }


    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }


    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.hasTag(AbstractShadowversePlayer.Enums.ARTIFACT)) {
            flash();
            artifactCount++;
            switch (artifactCount){
                case 1:
                case 3:
                case 5:
                    addToBot(new DrawCardAction(1));
                    break;
                case 2:
                case 4:
                case 6:
                    addToBot(new GainEnergyAction(1));
                    break;
            }
        }
    }

    @Override
    public void atStartOfTurn() {
        this.artifactCount = 0;
    }
}

