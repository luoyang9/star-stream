package me.charliezhang.starstream.entity.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import me.charliezhang.starstream.Assets;
import me.charliezhang.starstream.MainGame;

import static me.charliezhang.starstream.Config.*;

public class Falcon extends Enemy
{
    private int bound;

    public Falcon() {
        super();

        textureAtlas = Assets.manager.get(FALCON_PATH, TextureAtlas.class);
        animation = new Animation<TextureRegion>(1/15f, textureAtlas.getRegions());

        sprite.setSize(51, 67);

        health = maxHealth = FALCON_HEALTH;
        damage = FALCON_DAMAGE;
        score = FALCON_SCORE;
        coin = FALCON_COIN;
    }

    @Override
    public void applyUpgrades() {
        this.health += manager.getEnemyModifier();
        this.maxHealth += manager.getEnemyModifier();
        this.damage += manager.getEnemyModifier() / 2;
    }


    //json read method
    @Override
    public void read (Json json, JsonValue jsonMap) {
        super.read(json, jsonMap);
        bound = jsonMap.getInt("bound");
    }


    @Override
    public void update() {

        if((sprite.getX() < bound && direction.x < 0) ||(sprite.getX() > MainGame.WIDTH - bound && direction.x > 0))
        {
            direction.x *= -1; //reverse horizontal direction
        }

        if(sprite.getY() < -sprite.getHeight())
        {
            if(MathUtils.random() >= 0.5f) {
                sprite.setPosition(MainGame.WIDTH - bound, MainGame.HEIGHT + 100);
                direction.x = -6;
            }
            else {
                sprite.setPosition(bound, MainGame.HEIGHT + 100);
                direction.x = 6;
            }
        }

        super.update();
    }

    @Override
    public void render(SpriteBatch sb)
    {
        sprite.setRegion(animation.getKeyFrame(animationTime, true));
        sb.draw(Assets.manager.get(HEALTH_PATH, Texture.class), sprite.getX(), sprite.getY() + sprite.getHeight(), sprite.getWidth(), 5);

        double healthRatio = (double)health / maxHealth;
        if(healthRatio < 0 || healthRatio > 1) healthRatio = 0;
        sb.draw(Assets.manager.get(HEALTH_FILL_PATH, Texture.class), sprite.getX(), sprite.getY() + sprite.getHeight(), (int)(sprite.getWidth() * healthRatio), 5);
        super.render(sb);
    }

}
