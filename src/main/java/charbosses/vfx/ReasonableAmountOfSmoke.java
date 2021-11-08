 package charbosses.vfx;
 
 import com.badlogic.gdx.Gdx;
 import com.badlogic.gdx.graphics.Color;
 import com.badlogic.gdx.graphics.g2d.SpriteBatch;
 import com.badlogic.gdx.graphics.g2d.TextureAtlas;
 import com.badlogic.gdx.graphics.g2d.TextureRegion;
 import com.badlogic.gdx.math.Interpolation;
 import com.badlogic.gdx.math.MathUtils;
 import com.megacrit.cardcrawl.core.Settings;
 import com.megacrit.cardcrawl.helpers.ImageMaster;
 import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
 
 
 public class ReasonableAmountOfSmoke
   extends AbstractGameEffect
 {
   private float x;
   private float y;
   private float vY;
   
   public ReasonableAmountOfSmoke(float x, float y) {
     this.color = new Color(0.0F, 0.0F, 0.0F, 1.0F);
     this.color.r = MathUtils.random(0.5F, 0.6F);
     this.color.g = this.color.r + MathUtils.random(0.0F, 0.2F);
     this.color.b = 0.2F;
     if (MathUtils.randomBoolean()) {
       this.img = ImageMaster.EXHAUST_L;
     } else {
       this.img = ImageMaster.EXHAUST_S;
     } 
     this.duration = MathUtils.random(1.25F, 1.5F);
     this.targetScale = MathUtils.random(0.5F, 1.0F);
     
     this.startDur = this.duration;
     this.x = x + MathUtils.random(-180.0F * Settings.scale, 150.0F * Settings.scale) - this.img.packedWidth / 2.0F;
     this.y = y + MathUtils.random(-240.0F * Settings.scale, 150.0F * Settings.scale) - this.img.packedHeight / 2.0F;
     this.scale = 0.01F;
     this.rotation = MathUtils.random(360.0F);
     this.aV = MathUtils.random(-250.0F, 250.0F);
     this.vY = MathUtils.random(1.0F * Settings.scale, 5.0F * Settings.scale);
   }
   private float aV; private float startDur; private float targetScale; private TextureAtlas.AtlasRegion img;
   public void update() {
     this.duration -= Gdx.graphics.getDeltaTime();
     if (this.duration < 0.0F) {
       this.isDone = true;
     }
     
     this.x += MathUtils.random(-2.0F * Settings.scale, 2.0F * Settings.scale);
     this.x += this.vY;
     this.y += MathUtils.random(-2.0F * Settings.scale, 2.0F * Settings.scale);
     this.y += this.vY;
     this.rotation += this.aV * Gdx.graphics.getDeltaTime();
     this.scale = Interpolation.exp10Out.apply(0.01F, this.targetScale, 1.0F - this.duration / this.startDur);
     if (this.duration < 0.33F) {
       this.color.a = this.duration * 3.0F;
     }
   }
 
   
   public void render(SpriteBatch sb) {
     sb.setColor(this.color);
     sb.draw((TextureRegion)this.img, this.x, this.y, this.img.packedWidth / 2.0F, this.img.packedHeight / 2.0F, this.img.packedWidth, this.img.packedHeight, this.scale, this.scale, this.rotation);
   }
   
   public void dispose() {}
 }

