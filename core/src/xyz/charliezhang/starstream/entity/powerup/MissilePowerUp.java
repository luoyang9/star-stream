package xyz.charliezhang.starstream.entity.powerup;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import xyz.charliezhang.starstream.Assets;
import xyz.charliezhang.starstream.entity.Entity;
import xyz.charliezhang.starstream.entity.EntityManager;

import static xyz.charliezhang.starstream.Config.*;

public class MissilePowerUp extends PowerUp
{
    @Override
    public PowerUps getType() {return PowerUps.MISSILE;}

    public MissilePowerUp(EntityManager manager) {
        super(manager);

        textureAtlas = Assets.manager.get(MIS_POWERUP_PATH, TextureAtlas.class);
        animation = new Animation<TextureRegion>(1/15f, textureAtlas.getRegions());

        sprite.setSize(28, 28);

        delay = MIS_DELAY;
        interval = MIS_INTERVAL;
        numRepeats = MIS_NUM_REPEATS;
    }
}
