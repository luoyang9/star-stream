package xyz.charliezhang.shooter.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Charlie on 2015-12-03.
 */
public class PlayerLaser extends Entity{

    private EntityManager manager;

    public PlayerLaser(EntityManager manager) {
        super();
        this.manager = manager;
    }

    @Override
    public void render(SpriteBatch sb)
    {
        sprite.setRegion(animation.getKeyFrame(animationTime));
        super.render(sb);
    }

    @Override
    public void update() {

    }

    public boolean checkEnd()
    {
        return sprite.getY() >= manager.getViewport().getWorldHeight();
    }
}
