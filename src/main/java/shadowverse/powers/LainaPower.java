package shadowverse.powers;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import shadowverse.cards.Neutral.Curse.DivineMinister;
import shadowverse.characters.AbstractShadowversePlayer;


public class LainaPower
        extends AbstractPower {
    public static final String POWER_ID = "shadowverse:LainaPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("shadowverse:LainaPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean upgraded;
    public LainaPower(AbstractCreature owner,boolean upgraded) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.upgraded = upgraded;
        this.type = PowerType.BUFF;
        updateDescription();
        this.img = new Texture("img/powers/LainaPower.png");
    }

    public void updateDescription() {
        int lainaCount = 0;
        if (AbstractDungeon.player instanceof AbstractShadowversePlayer) {
            lainaCount = ((AbstractShadowversePlayer) AbstractDungeon.player).lainaCount;
        }
        this.description = DESCRIPTIONS[0];
        if (upgraded)
            this.description += DESCRIPTIONS[1] + lainaCount + DESCRIPTIONS[2];
    }

    @Override
    public void atStartOfTurn() {
        this.updateDescription();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        this.updateDescription();
    }

    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        if (isPlayer) {
            this.updateDescription();
            addToBot(new MakeTempCardInDiscardAction(new DivineMinister(), 2));
            addToBot(new MakeTempCardInDrawPileAction(new DivineMinister(), 2, true, true));
            if (upgraded){
                if (AbstractDungeon.player instanceof AbstractShadowversePlayer) {
                    if (((AbstractShadowversePlayer) AbstractDungeon.player).lainaCount >= 20) {
                        addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(500, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_HEAVY));
                        addToBot(new HealAction(this.owner, this.owner, 1));
                    } else if (((AbstractShadowversePlayer) AbstractDungeon.player).lainaCount >= 10) {
                        addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(50, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                        addToBot(new HealAction(this.owner, this.owner, 1));
                    } else if (((AbstractShadowversePlayer) AbstractDungeon.player).lainaCount >= 5) {
                        addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(21, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                        addToBot(new AddTemporaryHPAction(this.owner, this.owner, 1));
                    } else {
                        addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(7, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                        addToBot(new GainBlockAction(this.owner, 1));
                    }
                }
            }
        }
    }
}

