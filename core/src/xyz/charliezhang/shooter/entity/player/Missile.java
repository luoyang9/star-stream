package xyz.charliezhang.shooter.entity.player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Pool;
import xyz.charliezhang.shooter.Assets;
import xyz.charliezhang.shooter.entity.EntityManager;
import xyz.charliezhang.shooter.entity.Projectile;

public class Missile extends Projectile implements Pool.Poolable
{
    private float acceleration;

    Missile(EntityManager manager) {
        super(manager);

        textureAtlas = Assets.manager.get("data/textures/missile.atlas", TextureAtlas.class);
        animation = new Animation(1/15f, textureAtlas.getRegions());

        sprite.setSize(9, 38);

        acceleration = 0.5f;
        direction.x = 0;
        direction.y = 0;
    }

    @Override
    public void update() {
        if(direction.y <= 20) direction.y += acceleration;
        super.update();
    }

    @Override
    public void reset() {
        //reset everything
    }
}
