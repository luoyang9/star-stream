package xyz.charliezhang.shooter.entity.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import xyz.charliezhang.shooter.Assets;
import xyz.charliezhang.shooter.entity.EntityManager;

public class Kamikaze extends Enemy
{
    public Kamikaze(EntityManager manager) {
        super();

        textureAtlas = Assets.manager.get("data/textures/kamikaze.atlas", TextureAtlas.class);
        animation = new Animation(1/20f, textureAtlas.getRegions());

        sprite.setSize(50, 45);

        health = maxHealth = 10;
        damage = 1;
        score = 15;
        this.manager = manager;
    }

    @Override
    public void update() {
        super.update();

        if(sprite.getY() <= manager.getViewport().getWorldHeight() - stop)
        {
            if(direction.x <= 0) direction.x += 0.05;
            else direction.x -= 0.05;
        }

        sprite.setPosition(sprite.getX() + direction.x, sprite.getY() + direction.y);


        if(sprite.getY() < -sprite.getHeight())
        {
            float newX = MathUtils.random()*manager.getViewport().getWorldWidth();
            sprite.setPosition(newX, manager.getViewport().getWorldHeight() + 200);
            direction.x = (-newX+manager.getViewport().getWorldWidth()/2)*0.001f;
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
