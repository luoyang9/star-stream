package xyz.charliezhang.shooter.entity.powerup;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import xyz.charliezhang.shooter.Assets;

import static xyz.charliezhang.shooter.Config.*;

public class MissilePowerUp extends PowerUp
{
    @Override
    public PowerUps getType() {return PowerUps.MISSILE;}

    public MissilePowerUp() {
        super();

        textureAtlas = Assets.manager.get(MIS_POWERUP_PATH, TextureAtlas.class);
        animation = new Animation(1/15f, textureAtlas.getRegions());

        sprite.setSize(28, 28);

        delay = MIS_DELAY;
        interval = MIS_INTERVAL;
        numRepeats = MIS_NUM_REPEATS;
    }
}
