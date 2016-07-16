package xyz.charliezhang.shooter.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Projectile extends Entity {
    private EntityManager manager;
    private float currentRotation;

    public Projectile(EntityManager manager) {
        super();
        this.manager = manager;
        currentRotation = 0;
    }

    @Override
    public void render(SpriteBatch sb)
    {
        sprite.setRegion(animation.getKeyFrame(animationTime));
        super.render(sb);
    }

    @Override
    public void update() {
        currentRotation = -MathUtils.radiansToDegrees*MathUtils.atan2(direction.x , direction.y);
        if(sprite.getRotation() != currentRotation)sprite.rotate(currentRotation - sprite.getRotation());
        sprite.setPosition(sprite.getX() + direction.x, sprite.getY() + direction.y);
        super.update();
    }

    boolean checkEnd()
    {
        return sprite.getY() >= manager.getViewport().getWorldHeight() || sprite.getY() < 0;
    }
}
