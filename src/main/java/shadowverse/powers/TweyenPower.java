package shadowverse.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class TweyenPower extends AbstractPower {
    public static final String POWER_ID = "shadowverse:TweyenPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("shadowverse:TweyenPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public TweyenPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.type = PowerType.DEBUFF;
        updateDescription();
        this.img = new Texture("img/powers/TweyenPower.png");
    }

    public float atDamageReceive(float damage, DamageInfo.DamageType type) {
        return damage*1.2F;
    }

    public float modifyBlock(float blockAmount) {
        return blockAmount * 0.9F;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
