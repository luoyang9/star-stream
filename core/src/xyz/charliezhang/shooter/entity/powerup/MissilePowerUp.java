package xyz.charliezhang.shooter.entity.powerup;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Timer;
import xyz.charliezhang.shooter.MainGame;
import xyz.charliezhang.shooter.entity.EntityManager;
import xyz.charliezhang.shooter.entity.Missile;
import xyz.charliezhang.shooter.entity.Player;

public class MissilePowerUp extends PowerUp
{
    public MissilePowerUp(MainGame game) {
        super();

        textureAtlas = game.manager.get("data/textures/mispowerup.atlas", TextureAtlas.class);
        animation = new Animation(1/15f, textureAtlas.getRegions());

        sprite.setSize(20, 20);
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
