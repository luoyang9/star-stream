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

    public Missile() {
        super();

        textureAtlas = Assets.manager.get("data/textures/missile.atlas", TextureAtlas.class);
        animation = new Animation(1/15f, textureAtlas.getRegions());

        sprite.setSize(9, 38);

        acceleration = 0;
    }

    public void init(EntityManager manager, float acceleration) {
        super.init(manager);
        this.acceleration = acceleration;
    }

    @Override
    public void update() {
        if(direction.y <= 20) direction.y += acceleration;
        super.update();
    }

    public void reset() {
        acceleration = 0;
        super.reset();
    }
}
