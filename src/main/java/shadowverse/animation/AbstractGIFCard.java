package shadowverse.animation;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.evacipated.cardcrawl.modthespire.lib.SpireSuper;
import com.megacrit.cardcrawl.core.Settings;

/**
 * Created by Keeper on 2019/3/16.
 *
 * 和CustomCard一样 用法完全一样，只要把img换成gif就行了
 */
public abstract class AbstractGIFCard extends CustomCard {

    public GifAnimation gifAnimation;

    public AbstractGIFCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
        if (img.endsWith("gif")) {
            this.gifAnimation = new GifAnimation(img);
        }
    }

    @SpireOverride
    protected void renderPortrait(SpriteBatch sb) {
        if (gifAnimation != null) {
            float drawX = this.current_x - 125.0F;
            float drawY = this.current_y - 23.0F;//this.current_y - 95.0F + 72.0F;
            gifAnimation.render(sb, drawX, drawY, 125.0F, 23.0F, 250.0F, 190.0F, this.drawScale * Settings.scale, this.drawScale * Settings.scale, this.angle, 0, 0, 250, 190, false, false);
        } else {
            SpireSuper.call(sb);
        }

    }

}
