package shadowverse.effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;


public class morEffect
        extends AbstractGameEffect {
    private static TextureRegion[] MorTextures = new TextureRegion[]{new TextureRegion(
            new Texture("img/Effect/beam/moryy_6.png")), new TextureRegion(
            new Texture("img/Effect/beam/moryy_7.png")), new TextureRegion(
            new Texture("img/Effect/beam/moryy_8.png"))};

    private final TextureRegion morTexture;

    private float x;
    private float y;
    private float timer;
    private float alpha;

    public morEffect(float x, float y) {
        this.x = x;
        this.y = y;
        this.timer = 0.0F;
        this.alpha = 1.0F;
        this.duration = 0.3F;
        int randomIndex = MathUtils.random(MorTextures.length - 1);
        this.morTexture = MorTextures[randomIndex];
    }

    public void update() {
        this.timer += Gdx.graphics.getDeltaTime();

        if (this.timer < 0.1F) {
            this.alpha = 1.0F;
        } else {
            this.alpha = Interpolation.fade.apply(1.0F, 0.0F, (this.duration - 0.1F) / 0.2F);
        }

        this.duration -= Gdx.graphics.getDeltaTime();

        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
        Color color = Color.WHITE.cpy();
        color.a = this.alpha;
        sb.setColor(color);

        sb.draw(this.morTexture, this.x - (this.morTexture.getRegionWidth() / 2), this.y - (this.morTexture.getRegionHeight() / 2));
    }

    public void dispose() {
    }
}
