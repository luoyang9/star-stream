package xyz.charliezhang.starstream.entity.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import xyz.charliezhang.starstream.Assets;
import xyz.charliezhang.starstream.MainGame;

import static xyz.charliezhang.starstream.Config.*;

public class Kamikaze extends Enemy
{
    private int stop;

    public Kamikaze() {
        super();

        textureAtlas = Assets.manager.get(KAMIKAZE_PATH, TextureAtlas.class);
        animation = new Animation<TextureRegion>(1/10f, textureAtlas.getRegions());

        sprite.setSize(48, 45);

        health = maxHealth = KAMIKAZE_HEALTH;
        damage = KAMIKAZE_DAMAGE;
        score = KAMIKAZE_SCORE;
        coin = KAMIKAZE_COIN;
    }

    //json read method
    @Override
    public void read (Json json, JsonValue jsonMap) {
        super.read(json, jsonMap);
        stop = jsonMap.getInt("stop");
    }

    @Override
    public void update() {
        if(sprite.getY() <= MainGame.HEIGHT - stop)
        {
            if(direction.x <= 0) direction.x += 0.05;
            else direction.x -= 0.05;
        }

        if(sprite.getY() < -sprite.getHeight())
        {
            float newX = MathUtils.random()*MainGame.WIDTH;
            sprite.setPosition(newX, MainGame.HEIGHT + 200);
            //direction.x = (-newX+MainGame.WIDTH/2)*0.001f;
        }

        super.update();
    }

    @Override
    public void render(SpriteBatch sb)
    {
        sprite.setRegion(animation.getKeyFrame(animationTime, true));
        sb.draw(Assets.manager.get(HEALTH_PATH, Texture.class), sprite.getX(), sprite.getY() + sprite.getHeight(), sprite.getWidth(), 5);
        sb.draw(Assets.manager.get(HEALTH_FILL_PATH, Texture.class), sprite.getX(), sprite.getY() + sprite.getHeight(), (int)(sprite.getWidth() * ((double)health / maxHealth)), 5);
        super.render(sb);
    }

}
