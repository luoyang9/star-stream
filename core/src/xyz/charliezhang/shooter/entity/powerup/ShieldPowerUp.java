package xyz.charliezhang.shooter.entity.powerup;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import xyz.charliezhang.shooter.Assets;

import static xyz.charliezhang.shooter.Config.SHIELD_POWERUP_PATH;

public class ShieldPowerUp extends PowerUp
{
    @Override
    public PowerUps getType() {return PowerUps.SHIELD;}

    public ShieldPowerUp() {
        super();

        textureAtlas = Assets.manager.get(SHIELD_POWERUP_PATH, TextureAtlas.class);
        animation = new Animation(1/15f, textureAtlas.getRegions());

        sprite.setSize(26, 26);
    }
}
