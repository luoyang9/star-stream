package xyz.charliezhang.shooter.entity.powerup;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import xyz.charliezhang.shooter.MainGame;

public class ShieldPowerUp extends PowerUp
{
    public ShieldPowerUp(MainGame game) {
        super();

        textureAtlas = game.manager.get("data/textures/shieldpowerup.atlas", TextureAtlas.class);
        animation = new Animation(1/15f, textureAtlas.getRegions());

        sprite.setSize(20, 20);

        delay = 0;
        interval = 1;
        numRepeats = 10;
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
