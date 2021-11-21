package shadowverse.orbs;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import shadowverse.action.ReturnAmuletToDiscardAction;
import shadowverse.action.StasisEvokeIfRoomInHandAction;
import shadowverse.cards.AbstractAmuletCard;
import shadowverse.effect.AddCardToStasisEffect;

public class AmuletOrb extends AbstractOrb {

    public static final String ID = "shadowverse:Amulet";

    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ID);

    public static final String[] DESC = orbString.DESCRIPTION;

    public AbstractCard amulet;

    private AbstractGameEffect stasisStartEffect;

    public AmuletOrb(AbstractCard card) {
        this(card, (CardGroup) null);
    }

    public AmuletOrb(AbstractCard card, CardGroup source) {
        this(card, source, false);
    }

    public AmuletOrb(AbstractCard card, CardGroup source, boolean selfStasis) {
        card.targetAngle = 0.0F;
        this.amulet = card;
        this.amulet.beginGlowing();
        this.name = orbString.NAME + this.amulet.name;
        this.channelAnimTimer = 0.5F;
        if (card instanceof AbstractAmuletCard) {
            this.basePassiveAmount = ((AbstractAmuletCard) card).countDown;
        }else {
            this.basePassiveAmount = 3;
        }
        this.passiveAmount = this.basePassiveAmount;
        this.baseEvokeAmount = this.basePassiveAmount;
        this.evokeAmount = this.baseEvokeAmount;
        updateDescription();
        initialize(source, selfStasis);
    }

    public void applyFocus() {
    }

    public void updateDescription() {
        applyFocus();
        this.description = DESC[0] + this.passiveAmount + DESC[1] + this.amulet.name;
    }


    @Override
    public void onEndOfTurn() {
        if (this.amulet instanceof AbstractAmuletCard){
            ((AbstractAmuletCard) this.amulet).endOfTurn(this);
        }
    }

    public void onStartOfTurn() {
        super.onStartOfTurn();

        if (this.amulet instanceof AbstractAmuletCard) {
            this.amulet.applyPowers();
            ((AbstractAmuletCard) this.amulet).onStartOfTurn(this);
        }
        if (this.passiveAmount > 0) {
            this.passiveAmount--;
            this.evokeAmount--;
            updateDescription();
        }
        if (this.passiveAmount <= 0)
            AbstractDungeon.actionManager.addToTop((AbstractGameAction) new StasisEvokeIfRoomInHandAction(this));
    }

    public void onEvoke() {
        if (this.amulet instanceof AbstractAmuletCard)
            AbstractDungeon.actionManager.addToTop((AbstractGameAction)new ReturnAmuletToDiscardAction(this.amulet));
        if (this.passiveAmount <= 0) {
            if (this.amulet instanceof AbstractAmuletCard){
                ((AbstractAmuletCard) this.amulet).onEvoke(this);
                this.amulet.superFlash(Color.GOLDENROD);
            }
        }
    }

    public void triggerEvokeAnimation() {
    }

    private void initialize(CardGroup source, boolean selfStasis) {
        if (source != null) {
            source.removeCard(this.amulet);
            switch (source.type) {
                case HAND:
                    this.stasisStartEffect = (AbstractGameEffect)new AddCardToStasisEffect(this.amulet, this, this.amulet.current_x, this.amulet.current_y, !selfStasis);
                    break;
                case DRAW_PILE:
                    this.stasisStartEffect = (AbstractGameEffect)new AddCardToStasisEffect(this.amulet, this, AbstractDungeon.overlayMenu.combatDeckPanel.current_x + 100.0F * Settings.scale, AbstractDungeon.overlayMenu.combatDeckPanel.current_y + 100.0F * Settings.scale, !selfStasis);
                    break;
                case DISCARD_PILE:
                    this.stasisStartEffect = (AbstractGameEffect)new AddCardToStasisEffect(this.amulet, this, AbstractDungeon.overlayMenu.discardPilePanel.current_x - 100.0F * Settings.scale, AbstractDungeon.overlayMenu.discardPilePanel.current_y + 100.0F * Settings.scale, !selfStasis);
                    break;
                case EXHAUST_PILE:
                    this.amulet.unfadeOut();
                    this.stasisStartEffect = (AbstractGameEffect)new AddCardToStasisEffect(this.amulet, this, AbstractDungeon.overlayMenu.discardPilePanel.current_x - 100.0F * Settings.scale, AbstractDungeon.overlayMenu.exhaustPanel.current_y + 100.0F * Settings.scale, !selfStasis);
                    break;
                default:
                    this.stasisStartEffect = (AbstractGameEffect)new AddCardToStasisEffect(this.amulet, this, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, !selfStasis);
                    break;
            }
        } else {
            this.stasisStartEffect = (AbstractGameEffect)new AddCardToStasisEffect(this.amulet.makeStatEquivalentCopy(), this, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, !selfStasis);
        }
        AbstractDungeon.effectsQueue.add(this.stasisStartEffect);
        this.amulet.retain = false;
    }

    public void update() {
        super.update();
        if (this.stasisStartEffect == null || this.stasisStartEffect.isDone) {
            this.amulet.target_x = this.tX;
            this.amulet.target_y = this.tY;
            this.amulet.applyPowers();
            if (this.hb.hovered) {
                this.amulet.targetDrawScale = 1.0F;
            } else {
                this.amulet.targetDrawScale = Float.valueOf(0.2F);
            }
        }
        this.amulet.update();
    }

    public void render(SpriteBatch sb) {
        if (!this.hb.hovered && (this.stasisStartEffect == null || this.stasisStartEffect.isDone))
            renderActual(sb);
    }

    public void renderActual(SpriteBatch sb) {
        this.amulet.render(sb);
        if (!this.hb.hovered){
            renderText(sb);
        }
        this.hb.render(sb);
    }

    public void renderPreview(SpriteBatch sb) {
        if (this.hb.hovered && (this.stasisStartEffect == null || this.stasisStartEffect.isDone))
            renderActual(sb);
    }

    public void playChannelSFX() {}

    public AbstractOrb makeCopy() {
        AmuletOrb ao = new AmuletOrb(this.amulet);
        ao.passiveAmount = ao.basePassiveAmount = this.passiveAmount;
        ao.evokeAmount = ao.baseEvokeAmount = this.evokeAmount;
        return ao;
    }
}
