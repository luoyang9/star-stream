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
import me.charliezhang.starstream.entity.player.Player;

import static me.charliezhang.starstream.Config.*;

public class Seeker extends Enemy
{
    private boolean intro;
    private int stop;

    public Seeker() {
        super();

        textureAtlas = Assets.manager.get(SEEKER_PATH, TextureAtlas.class);
        animation = new Animation<TextureRegion>(1/50f, textureAtlas.getRegions());

        sprite.setSize(52, 48);
        sprite.setOrigin(26, 24);

        health = maxHealth = SEEKER_HEALTH;
        damage = SEEKER_DAMAGE;
        score = SEEKER_SCORE;
        coin = SEEKER_COIN;

        intro = SEEKER_INITIAL_INTRO;
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
        stop = jsonMap.getInt("stop");
    }

    @Override
    public void update() {
        float rotation = (float)Math.atan2(manager.getPlayer().getPosition().y - getPosition().y, manager.getPlayer().getPosition().x - getPosition().x);
        sprite.setRotation(rotation * 180 / 3.14159f + 90);

        if(intro)
        {
            if(sprite.getY() <= MainGame.HEIGHT - stop) {
                intro = false;
                setDirection(0, 0);
            }
        } else if(System.currentTimeMillis() - lastFire >= 1000) {
            EnemyLaser g = manager.getEnemyLaserPool().obtain();
            g.init(manager, this, 2);
            g.setDirection(-MathUtils.sinDeg(sprite.getRotation() + 180) * 8, MathUtils.cosDeg(sprite.getRotation() + 180) * 8);
            g.setPosition(sprite.getX() + sprite.getOriginX() - g.getSprite().getOriginX(), sprite.getY() + sprite.getOriginY() - g.getSprite().getOriginY());
            manager.spawnEnemyLaser(g);
            lastFire = System.currentTimeMillis();
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
