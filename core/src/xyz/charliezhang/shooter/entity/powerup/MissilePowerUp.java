package xyz.charliezhang.shooter.entity.powerup;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import xyz.charliezhang.shooter.Assets;

public class MissilePowerUp extends PowerUp
{
    public MissilePowerUp() {
        super();

        textureAtlas = Assets.manager.get("data/textures/mispowerup.atlas", TextureAtlas.class);
        animation = new Animation(1/15f, textureAtlas.getRegions());

        sprite.setSize(20, 20);

        delay = 0;
        interval = 1.5f;
        numRepeats = 6;
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void render(SpriteBatch sb)
    {
        super.render(sb);
    }

}
