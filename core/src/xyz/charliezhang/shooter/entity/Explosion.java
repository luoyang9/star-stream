package xyz.charliezhang.shooter.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import xyz.charliezhang.shooter.MainGame;

/**
 * Created by Charlie on 2015-12-03.
 */
public class Explosion extends Entity
{
    public Explosion(MainGame game)
    {
        textureAtlas = game.manager.get("data/textures/explosion.atlas", TextureAtlas.class);
        animation = new Animation(1/15f, textureAtlas.getRegions()) ;

        sprite.setSize(15, 15);
    }

    @Override
    public void update() {
    }

    @Override
    public void render(SpriteBatch sb)
    {
        sprite.setRegion(animation.getKeyFrame(animationTime));
        super.render(sb);
    }

    public boolean isDone(){
        return animation.isAnimationFinished(animationTime);
    }
}
