package xyz.charliezhang.shooter.entity.powerup;
import xyz.charliezhang.shooter.MainGame;
import xyz.charliezhang.shooter.entity.Entity;

public class PowerUp extends Entity
{
    public enum PowerUps { SHIELD, ATTACK, MISSILE }

    protected float delay;
    float interval;
    int numRepeats;

    PowerUp() {
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

    public float getDelay() {return delay;}
    public float getInterval(){return interval;}
    public int getNumRepeats(){return numRepeats;}
    public PowerUps getType() {return null;}
}
