package xyz.charliezhang.shooter.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import xyz.charliezhang.shooter.Assets;
import xyz.charliezhang.shooter.MainGame;

/**
 * Created by Charlie on 2015-12-03.
 */
public class Explosion extends Entity
{
    public Explosion(int type)
    {
        switch(type){
            case 1:textureAtlas = Assets.manager.get("data/textures/playerExplosion.atlas", TextureAtlas.class);
                sprite.setSize(15, 15);
                break;
            case 2:textureAtlas = Assets.manager.get("data/textures/bigExplosion.atlas", TextureAtlas.class);
                sprite.setSize(60, 60);
                break;
            default:
        }
        animation = new Animation(1/20f, textureAtlas.getRegions()) ;
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
