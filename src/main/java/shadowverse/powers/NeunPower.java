package shadowverse.powers;


import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import shadowverse.characters.AbstractShadowversePlayer;


public class NeunPower
        extends AbstractPower {
    public static final String POWER_ID = "shadowverse:NeunPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("shadowverse:NeunPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public NeunPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.type = PowerType.BUFF;
        updateDescription();
        this.img = new Texture("img/powers/NeunPower.png");
    }

    public void stackPower(int stackAmount) {
        this.amount += stackAmount;
        if (this.amount >= 999) {
            this.amount = 999;
        }
    }


    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        int mCount = 0;
        for (AbstractCard c: AbstractDungeon.actionManager.cardsPlayedThisCombat){
            if (c.hasTag(AbstractShadowversePlayer.Enums.MACHINE)&&c.type!= AbstractCard.CardType.SKILL)
                mCount++;
        }
        if (card.hasTag(AbstractShadowversePlayer.Enums.MACHINE)&&mCount>=10){
            addToBot((AbstractGameAction)new SFXAction("NeunPower"));
            addToBot((AbstractGameAction)new ApplyPowerAction(this.owner,this.owner,(AbstractPower)new MetallicizePower(this.owner,2),2));
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}

