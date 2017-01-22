package xyz.charliezhang.shooter.entity.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import xyz.charliezhang.shooter.Assets;
import xyz.charliezhang.shooter.MainGame;
import xyz.charliezhang.shooter.entity.EntityManager;

public class Falcon extends Enemy
{
    private int bound;

    public Falcon() {
        super();

        textureAtlas = Assets.manager.get("data/textures/falcon.atlas", TextureAtlas.class);
        animation = new Animation(1/20f, textureAtlas.getRegions());

        sprite.setSize(51, 37);

        health = maxHealth = 10;
        damage = 1;
        score = 15;
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
        sb.draw(Assets.manager.get("data/textures/health.png", Texture.class), sprite.getX(), sprite.getY() + sprite.getHeight(), sprite.getWidth(), 5);
        sb.draw(Assets.manager.get("data/textures/healthFill.png", Texture.class), sprite.getX(), sprite.getY() + sprite.getHeight(), (int)(sprite.getWidth() * ((double)health / maxHealth)), 5);
        super.render(sb);
    }

}
