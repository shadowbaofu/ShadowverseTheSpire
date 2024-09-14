package shadowverse.effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;


public class moryyEffect
        extends AbstractGameEffect {
    private float timer = 0.1F;

    public moryyEffect() {
        this.duration = 2.0F;
    }

    public void update() {
        if (this.duration == 2.0F) {
            AbstractDungeon.effectsQueue.add(new guangzEffect());
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        this.timer -= Gdx.graphics.getDeltaTime();

        if (this.timer < 0.0F) {
            this.timer += 0.1F;
            AbstractDungeon.effectsQueue.add(new piaolwEffect());
            AbstractDungeon.effectsQueue.add(new piaolwEffect());
        }

        if (this.duration < 0.0F)
            this.isDone = true;
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}

