package me.charliezhang.starstream.entity.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import me.charliezhang.starstream.Assets;

import static me.charliezhang.starstream.Config.*;

public class Asteroid extends Enemy
{
    public Asteroid() {
        super();

        //random texture
        switch(MathUtils.random(1, 4))
        {
            case 1:  sprite.setRegion(Assets.manager.get(ASTEROID_1_PATH, Texture.class)); break;
            case 2:  sprite.setRegion(Assets.manager.get(ASTEROID_2_PATH, Texture.class)); break;
            case 3:  sprite.setRegion(Assets.manager.get(ASTEROID_3_PATH, Texture.class)); break;
            default:  sprite.setRegion(Assets.manager.get(ASTEROID_4_PATH, Texture.class));
        }
        sprite.setSize(50, 50);
        sprite.setOrigin(25, 25f);

        health = maxHealth = ASTEROID_HEALTH;
        damage = ASTEROID_DAMAGE;
        score = ASTEROID_SCORE;
        coin = ASTEROID_COIN;
    }

    @Override
    public void applyUpgrades() {
        this.health += manager.getEnemyModifier();
        this.maxHealth += manager.getEnemyModifier();
        this.damage += manager.getEnemyModifier() / 2;
    }


    @Override
    public void update() {
        sprite.rotate(1);

        if(sprite.getY() < -sprite.getHeight())
        {
            suicide = true;
        }

        super.update();
    }

}
