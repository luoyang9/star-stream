package xyz.charliezhang.shooter.entity;

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

    boolean checkEnd()
    {
        return sprite.getY() >= manager.getViewport().getWorldHeight() || sprite.getY() < 0;
    }
}
