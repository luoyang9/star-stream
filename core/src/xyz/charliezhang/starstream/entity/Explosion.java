package xyz.charliezhang.starstream.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;
import xyz.charliezhang.starstream.Assets;

import static xyz.charliezhang.starstream.Config.*;

public class Explosion extends Entity implements Pool.Poolable
{
    private EntityManager manager;

    Explosion() {
        super();
    }

    public void init(int type, EntityManager manager)
    {
        this.manager = manager;

        float frameDuration = 1/15f;
        switch(type){
            case 1:textureAtlas = Assets.manager.get(PLAYER_EXPLOSION_PATH, TextureAtlas.class);
                sprite.setSize(20, 20);
                frameDuration = 1/30f;
                break;
            case 2:textureAtlas = Assets.manager.get(EXPLOSION_PATH, TextureAtlas.class);
                sprite.setSize(45, 45);
                frameDuration = 1/15f;
                break;
            case 3:textureAtlas = Assets.manager.get(PLAYER_EXPLOSION_R_PATH, TextureAtlas.class);
                sprite.setSize(20, 20);
                frameDuration = 1/30f;
            default:
        }
        animation = new Animation<TextureRegion>(frameDuration, textureAtlas.getRegions()) ;
    }

    @Override
    public void render(SpriteBatch sb) {
        //set sprite to current animation region
        if(animation != null && !manager.isPaused()) {
            sprite.setRegion(animation.getKeyFrame(animationTime, true));

            //add animation time
            animationTime += Gdx.graphics.getDeltaTime();
        }

        if(sprite.getX() > -sprite.getWidth() &&
                sprite.getX() < manager.getViewport().getWorldWidth() &&
                sprite.getY() > -sprite.getHeight() &&
                sprite.getY() < manager.getViewport().getWorldHeight()) {
            sprite.draw(sb);
        }
    }

    boolean isDone(){
        return animation.isAnimationFinished(animationTime);
    }

    @Override
    public void reset() {
        super.reset();
    }
}
