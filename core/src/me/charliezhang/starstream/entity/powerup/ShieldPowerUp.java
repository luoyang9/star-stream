package me.charliezhang.starstream.entity.powerup;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import me.charliezhang.starstream.Assets;
import me.charliezhang.starstream.entity.Entity;
import me.charliezhang.starstream.entity.EntityManager;

import static me.charliezhang.starstream.Config.SHIELD_POWERUP_PATH;

public class ShieldPowerUp extends PowerUp
{
    @Override
    public PowerUps getType() {return PowerUps.SHIELD;}

    public ShieldPowerUp(EntityManager manager) {
        super(manager);

        textureAtlas = Assets.manager.get(SHIELD_POWERUP_PATH, TextureAtlas.class);
        animation = new Animation<TextureRegion>(1/15f, textureAtlas.getRegions());

        sprite.setSize(26, 26);
    }
}
