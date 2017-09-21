package xyz.charliezhang.starstream.entity.player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;
import xyz.charliezhang.starstream.Assets;
import xyz.charliezhang.starstream.entity.EntityManager;
import xyz.charliezhang.starstream.entity.Projectile;

import static xyz.charliezhang.starstream.Config.MISSILE_PATH;

public class Missile extends Projectile implements Pool.Poolable
{
    private float acceleration;

    public Missile() {
        super();

        textureAtlas = Assets.manager.get(MISSILE_PATH, TextureAtlas.class);
        animation = new Animation<TextureRegion>(1/15f, textureAtlas.getRegions());

        sprite.setSize(14f,38);

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
