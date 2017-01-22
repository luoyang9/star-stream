package xyz.charliezhang.shooter.entity.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import xyz.charliezhang.shooter.Assets;
import xyz.charliezhang.shooter.entity.EntityManager;

public class Asteroid extends Enemy
{
    public Asteroid() {
        super();

        //random texture
        switch(MathUtils.random(1, 4))
        {
            case 1:  sprite.setRegion(Assets.manager.get("data/textures/asteroid1.png", Texture.class)); break;
            case 2:  sprite.setRegion(Assets.manager.get("data/textures/asteroid2.png", Texture.class)); break;
            case 3:  sprite.setRegion(Assets.manager.get("data/textures/asteroid3.png", Texture.class)); break;
            default:  sprite.setRegion(Assets.manager.get("data/textures/asteroid4.png", Texture.class));
        }
        sprite.setSize(50, 45);
        sprite.setOrigin(25, 22.5f);

        health = maxHealth = 3;
        damage = 1;
        score = 0;
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

    @Override
    public void render(SpriteBatch sb)
    {
        super.render(sb);
    }

}
