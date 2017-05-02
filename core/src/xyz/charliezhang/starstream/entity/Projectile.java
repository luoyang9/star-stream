package xyz.charliezhang.starstream.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Projectile extends Entity {
    private EntityManager manager;
    private float currentRotation;

    protected Projectile() {
        super();
        currentRotation = 0;
        manager = null;
    }

    protected void init(EntityManager manager) {
        this.manager = manager;
    }

    protected void reset() {
        currentRotation = 0;
        manager = null;
        super.reset();
    }

    @Override
    protected void update() {
        currentRotation = -MathUtils.radiansToDegrees*MathUtils.atan2(direction.x , direction.y);
        if(sprite.getRotation() != currentRotation)sprite.rotate(currentRotation - sprite.getRotation());
        super.update();
    }

    @Override
    protected void render(SpriteBatch sb) {
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

    boolean checkEnd()
    {
        return sprite.getY() >= manager.getViewport().getWorldHeight() || sprite.getY() < 0 || sprite.getX() < 0 || sprite.getX() > manager.getViewport().getWorldWidth();
    }
}
