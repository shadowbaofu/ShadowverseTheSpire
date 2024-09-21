package shadowverse.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import shadowverse.cards.Neutral.Neutral.WingedInversion;
import shadowverse.cards.Neutral.Temp.ReturnToHeaven;

public class DarkAngelPower extends AbstractPower {
    public static final String POWER_ID = "shadowverse:DarkAngelPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("shadowverse:DarkAngelPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public DarkAngelPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        updateDescription();
        this.img = new Texture("img/powers/DarkAngelPower.png");
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
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card instanceof WingedInversion || card instanceof ReturnToHeaven) {
            flash();
            addToBot(new DamageRandomEnemyAction(new DamageInfo(this.owner,this.amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }
    }
}