package xyz.charliezhang.shooter.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Pool;
import xyz.charliezhang.shooter.Assets;

import static xyz.charliezhang.shooter.Config.*;

public class Explosion extends Entity implements Pool.Poolable
{
    public Explosion() {
        super();
    }

    public void init(int type)
    {
        switch(type){
            case 1:textureAtlas = Assets.manager.get(PLAYER_EXPLOSION_PATH, TextureAtlas.class);
                sprite.setSize(15, 15);
                break;
            case 2:textureAtlas = Assets.manager.get(EXPLOSION_PATH, TextureAtlas.class);
                sprite.setSize(45, 45);
                break;
            case 3:textureAtlas = Assets.manager.get(PLAYER_EXPLOSION_R_PATH, TextureAtlas.class);
                sprite.setSize(15, 15);
            default:
        }
        animation = new Animation(1/15f, textureAtlas.getRegions()) ;
    }

    @Override
    public void update() {
    }

    boolean isDone(){
        return animation.isAnimationFinished(animationTime);
    }

    @Override
    public void reset() {
        super.reset();
    }
}
