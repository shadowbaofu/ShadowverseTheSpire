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
import shadowverse.cards.Neutral.Temp.ScorchingCurse;


public class GingerPower
        extends AbstractPower {
    public static final String POWER_ID = "shadowverse:GingerPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("shadowverse:GingerPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean upgraded;
    public GingerPower(AbstractCreature owner,boolean upgraded) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.upgraded = upgraded;
        this.type = PowerType.BUFF;
        updateDescription();
        this.img = new Texture("img/powers/GingerPower.png");
    }

    public void atStartOfTurn() {
        flash();
        AbstractCard c = new ScorchingCurse();
        if (upgraded)
            c.upgrade();
        addToBot(new MakeTempCardInHandAction(c));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}

