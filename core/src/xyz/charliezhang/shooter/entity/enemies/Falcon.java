package xyz.charliezhang.shooter.entity.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import xyz.charliezhang.shooter.Assets;
import xyz.charliezhang.shooter.MainGame;
import xyz.charliezhang.shooter.entity.EntityManager;

public class Falcon extends Enemy
{
    private boolean intro;
    public Falcon(EntityManager manager) {
        super();

        textureAtlas = Assets.manager.get("data/textures/falcon.atlas", TextureAtlas.class);
        animation = new Animation(1/20f, textureAtlas.getRegions());

        sprite.setSize(51, 37);

        health = maxHealth = 10;
        damage = 1;
        score = 15;
        this.manager = manager;

        intro = true;
    }

    @Override
    public void update() {
        super.update();

        if(sprite.getY() <= MainGame.HEIGHT - stop && intro)
        {
           direction.x *= -1;
            intro = false;
        }

        sprite.setPosition(sprite.getX() + direction.x, sprite.getY() + direction.y);


        if(sprite.getY() < -sprite.getHeight())
        {
            intro = true;
            if(MathUtils.random() >= 0.5f) {
                sprite.setPosition(MainGame.WIDTH + 200, MainGame.HEIGHT + 100);
                direction.x = -6;
            }
            else {
                sprite.setPosition(-200, MainGame.HEIGHT + 100);
                direction.x = 6;
            }
        }
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
