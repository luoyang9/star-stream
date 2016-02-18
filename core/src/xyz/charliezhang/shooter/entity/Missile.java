package xyz.charliezhang.shooter.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import xyz.charliezhang.shooter.MainGame;

public class Missile extends PlayerLaser
{
    private float accel;

    public Missile(EntityManager manager) {
        super(manager);

        textureAtlas = manager.getGame().manager.get("data/textures/missile.atlas", TextureAtlas.class);
        animation = new Animation(1/15f, textureAtlas.getRegions());

        sprite.setSize(9, 38);

        accel = 0.5f;
        direction.x = 0;
        direction.y = 0;
    }

    @Override
    public void update() {
        direction.y += accel;
        if(direction.y > 20) direction.y = 20;
        sprite.setPosition(sprite.getX() + direction.x, sprite.getY() + direction.y);
    }
}
