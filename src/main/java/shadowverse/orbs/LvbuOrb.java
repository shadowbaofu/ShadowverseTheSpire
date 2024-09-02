package shadowverse.orbs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import shadowverse.action.MinionAttackAction;
import shadowverse.action.MinionSummonAction;
import shadowverse.action.RemoveMinionAction;
import shadowverse.effect.AddCardToStasisEffect;

public class LvbuOrb extends Minion {
    public AbstractCard card;
    private AbstractGameEffect stasisStartEffect;


    public LvbuOrb(int atk, int def, AbstractCard card) {
        this.attack = this.baseAttack = atk;
        this.defense = this.baseDefense = def;
        this.card = card;
        this.stasisStartEffect = new AddCardToStasisEffect(card, this, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, false);
        AbstractDungeon.effectsQueue.add(this.stasisStartEffect);
    }

    @Override
    public void onEvoke() {
        AbstractDungeon.actionManager.addToBottom(new RemoveMinionAction());
        AbstractDungeon.actionManager.addToBottom(new MinionSummonAction(this.makeCopy()));
    }

    @Override
    public void onStartOfTurn() {
        super.onStartOfTurn();
    }




    @Override
    public void onEndOfTurn() {
        if (this.defense > 0) {
            this.effect();
            this.updateDescription();
        }
    }

    @Override
    public void playChannelSFX() {
    }

    @Override
    public void effect() {
        int damage = this.attack;
        int block = this.defense;
        if (AbstractDungeon.player.hasPower("Electro")) {
            AbstractDungeon.actionManager.addToBottom(new MinionAttackAction(new DamageInfo(AbstractDungeon.player, damage, DamageInfo.DamageType.NORMAL), true));
        } else {
            AbstractDungeon.actionManager.addToBottom(new MinionAttackAction(new DamageInfo(AbstractDungeon.player, damage, DamageInfo.DamageType.NORMAL), false));
        }
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player,block));
    }

    @Override
    public void update() {
        super.update();
        if (this.stasisStartEffect == null || this.stasisStartEffect.isDone) {
            this.card.target_x = this.tX;
            this.card.target_y = this.tY;
            this.card.applyPowers();
            if (this.hb.hovered) {
                this.card.targetDrawScale = 1.0F;
            } else {
                this.card.targetDrawScale = Float.valueOf(0.2F);
            }
        }
        this.card.update();
    }

    @Override
    public void updateDescription() {
    }

    @Override
    public void render(SpriteBatch sb) {
        this.card.render(sb);
        if (this.defense > this.baseDefense) {
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.defense), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET, new Color(0.2F, 1.0F, 0.2F, this.c.a), this.fontScale);
        } else if (this.defense < this.baseDefense) {
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.defense), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET, new Color(1.0F, 0.2F, 0.2F, this.c.a), this.fontScale);
        } else {
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.defense), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET, new Color(1.0F, 1.0F, 1.0F, this.c.a), this.fontScale);
        }
        if (this.attack > this.baseAttack) {
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.attack), this.cX - NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET, new Color(0.2F, 1.0F, 0.2F, this.c.a), this.fontScale);
        } else {
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.attack), this.cX - NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET, new Color(1.0F, 1.0F, 1.0F, this.c.a), this.fontScale);
        }
        hb.render(sb);
    }

    @Override
    public AbstractOrb makeCopy() {
        return new LvbuOrb(this.attack, this.defense, this.card);
    }
}
