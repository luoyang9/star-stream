package xyz.charliezhang.starstream.entity.powerup;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import xyz.charliezhang.starstream.MainGame;
import xyz.charliezhang.starstream.entity.Entity;
import xyz.charliezhang.starstream.entity.EntityManager;

public class PowerUp extends Entity
{
    public enum PowerUps { SHIELD, ATTACK, MISSILE }

    protected float delay;
    float interval;
    int numRepeats;
    private EntityManager manager;

    PowerUp(EntityManager manager) {
        super();

        this.manager = manager;
    }

    @Override
    public void update() {
        if(sprite.getX() <  100)
            modifyDirection(0.05f, 0);
        else if(sprite.getX() > manager.getViewport().getWorldWidth() - 100)
            modifyDirection(-0.05f, 0);

        if(sprite.getY() > manager.getViewport().getWorldHeight() - 200)
            modifyDirection(0, -0.05f);
        else if(sprite.getY() < 200)
            modifyDirection(0, 0.05f);

        sprite.setPosition(sprite.getX() + direction.x, sprite.getY() + direction.y);
    }

    @Override
    public void render(SpriteBatch sb) {
        //set sprite to current animation region
        if(animation != null && !manager.isPaused()) {
            sprite.setRegion(animation.getKeyFrame(animationTime, true));

            //add animation time
            animationTime += Gdx.graphics.getDeltaTime();
        }

        if(sprite.getX() > -sprite.getWidth() &&
                sprite.getX() < manager.getViewport().getWorldWidth() &&
                sprite.getY() > -sprite.getHeight() &&
                sprite.getY() < manager.getViewport().getWorldHeight()) {
            sprite.draw(sb);
        }
    }

    public float getDelay() {return delay;}
    public float getInterval(){return interval;}
    public int getNumRepeats(){return numRepeats;}
    public PowerUps getType() {return null;}
}
