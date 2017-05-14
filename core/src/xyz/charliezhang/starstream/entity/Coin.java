package xyz.charliezhang.starstream.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import xyz.charliezhang.starstream.Assets;

import static xyz.charliezhang.starstream.Config.COIN_PATH;

/**
 * Created by Charlie on 2017-05-12.
 */
public class Coin extends Entity implements Pool.Poolable {

    private EntityManager manager;
    private boolean done;

    public void init(EntityManager manager) {
        this.manager = manager;
        textureAtlas = Assets.manager.get(COIN_PATH, TextureAtlas.class);
        sprite.setSize(5, 5);
        animation = new Animation<TextureRegion>(1/15f, textureAtlas.getRegions());
        done = false;
        setDirection(MathUtils.random(1f, 2f), MathUtils.random(1f, 2f));
    }

    @Override
    public void update() {


        //coin moves towards player
        Vector2 playerPos = manager.getPlayer().getPosition();
        float x = playerPos.x - sprite.getX();
        float y = playerPos.y - sprite.getY() / 10;
        setDirection(direction.x + x, direction.y + y);

        if(Math.abs(playerPos.x - sprite.getX()) < 0.1 && Math.abs(playerPos.y - sprite.getY()) < 0.1) {
            done = true;
        }

        super.update();
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

    @Override
    public void reset() { super.reset(); }

    boolean isDone() {return done;}
}
