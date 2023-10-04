package shadowverse.powers;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class GraveAdjudicatorPower extends AbstractPower implements OnReceivePowerPower, NonStackablePower {
    public static final String POWER_ID = "shadowverse:GraveAdjudicatorPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("shadowverse:GraveAdjudicatorPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public GraveAdjudicatorPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.type = NeutralPowertypePatch.NEUTRAL;
        updateDescription();
        this.img = new Texture("img/powers/GraveAdjudicatorPower.png");
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }


    @Override
    public boolean onReceivePower(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        return abstractPower.type != PowerType.DEBUFF || abstractPower instanceof StrengthPower || abstractPower instanceof DexterityPower;
    }

    @Override
    public int onReceivePowerStacks(AbstractPower power, AbstractCreature target, AbstractCreature source, int stackAmount) {
        return OnReceivePowerPower.super.onReceivePowerStacks(power, target, source, stackAmount);
    }

    @Override
    public void onExhaust(AbstractCard card) {
        if (card.type == AbstractCard.CardType.ATTACK){
            addToBot(new SFXAction("GraveAdjudicatorPower"));
            addToBot(new ApplyPowerAction(this.owner,this.owner,new StrengthPower(this.owner,1),1));
            addToBot(new ApplyPowerAction(this.owner,this.owner,new DexterityPower(this.owner,1),1));
        }
    }
}
