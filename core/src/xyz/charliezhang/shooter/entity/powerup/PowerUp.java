package xyz.charliezhang.shooter.entity.powerup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import xyz.charliezhang.shooter.MainGame;
import xyz.charliezhang.shooter.entity.Entity;

public class PowerUp extends Entity
{
    protected int duration;

    public PowerUp() {
        super();
    }

    @Override
    public void update() {
        if(sprite.getX() <  100)
            modifyDirection(0.05f, 0);
        else if(sprite.getX() > MainGame.WIDTH - 100)
            modifyDirection(-0.05f, 0);

        if(sprite.getY() > MainGame.HEIGHT - 200)
            modifyDirection(0, -0.05f);
        else if(sprite.getY() < 200)
            modifyDirection(0, 0.05f);

        sprite.setPosition(sprite.getX() + direction.x, sprite.getY() + direction.y);
    }

    @Override
    public void render(SpriteBatch sb)
    {
        sprite.setRegion(animation.getKeyFrame(animationTime));
        super.render(sb);
    }

    public int getDuration(){return duration;}
}
