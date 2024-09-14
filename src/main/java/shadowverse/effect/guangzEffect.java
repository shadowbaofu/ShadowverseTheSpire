package shadowverse.effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;


public class guangzEffect
        extends AbstractGameEffect {
    private static TextureRegion GhostFireTexture = new TextureRegion(new Texture("img/Effect/beam/moryy_0.png"));

    private float startX;
    private float endX;
    private float x;
    private float y;
    private float timer;
    private float morTimer = 0.05F;

    public guangzEffect() {
        this.duration = 1.0F;
        this.startX = -GhostFireTexture.getRegionWidth();
        this.endX = Settings.WIDTH;
        this.x = this.startX;
        this.y = Settings.HEIGHT / 2.0F;
        this.color = Color.WHITE.cpy();
        this.color.a = 1.0F;
        this.timer = 0.0F;
    }


    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        this.timer += Gdx.graphics.getDeltaTime();
        if (this.timer >= this.morTimer) {
            this.timer -= this.morTimer;
            AbstractDungeon.effectsQueue.add(new morEffect(this.x, this.y));
        }

        this.x = Interpolation.linear.apply(this.startX, this.endX, 1.0F - this.duration);

        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }


    public void render(SpriteBatch sb) {
        sb.setColor(this.color);

        sb.draw(GhostFireTexture, this.x, 0.0F);

        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}
