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
    private long timer;
    private boolean win;

    public void init(EntityManager manager) {
        this.manager = manager;
        textureAtlas = Assets.manager.get(COIN_PATH, TextureAtlas.class);
        sprite.setSize(15, 15);
        animation = new Animation<TextureRegion>(1/15f, textureAtlas.getRegions());
        setDirection(MathUtils.randomSign() * MathUtils.random(1f, 2f), MathUtils.randomSign() * MathUtils.random(1f, 2f));
        timer = System.currentTimeMillis();
        win = false;
    }

    @Override
    public void update() {
        long elapsed = System.currentTimeMillis() - timer;
        System.out.println(elapsed);
        if(elapsed > 400) {
            if(!win) {
                float x = 0;
                float y = 0;

                if (direction.x < 0) {
                    x = 0.01f;
                } else if (direction.x > 0) {
                    x = -0.01f;
                }
                if (sprite.getX() <= 0) {
                    x = 0.05f;
                } else if (sprite.getX() >= manager.getViewport().getWorldWidth()) {
                    x = -0.05f;
                }
                if (direction.y > -2.5f) {
                    y = -0.05f;
                }

                setDirection(direction.x + x, direction.y + y);
            } else {
                //coin moves towards player
                Vector2 winPos = new Vector2(manager.getViewport().getWorldWidth() / 2, manager.getViewport().getWorldHeight() + 20);
                float x = winPos.x - sprite.getX();
                float y = winPos.y - sprite.getY();
                float hyp = (float)Math.sqrt((double)(x*x + y*y));
                x /= hyp;
                y /= hyp;

                setDirection(x * 20, y * 20);
            }
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

    public void win() {
        win = true;
    }
}
