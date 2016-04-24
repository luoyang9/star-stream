package xyz.charliezhang.shooter.entity.player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import xyz.charliezhang.shooter.Assets;
import xyz.charliezhang.shooter.entity.EntityManager;
import xyz.charliezhang.shooter.music.MusicPlayer;

/**
 * Created by Charlie on 2016-04-06.
 */
public class PlayerBlue extends Player
{
    public PlayerBlue(EntityManager manager)
    {
        super(manager);

        //set texture atlas and animation to player
        textureAtlas = Assets.manager.get("data/textures/playerblue.atlas", TextureAtlas.class);
        animation = new Animation(1/15f, textureAtlas.getRegions());

        //set sprite size
        sprite.setSize(75, 50);

        //set player starting data
        shootDelay = 100;

        //read player stats
        health = maxHealth = 3;
        damage = 1;
    }

    @Override
    protected void shoot()
    {
        if(playerInput.isTouching()) //if touching
        {
            if(System.currentTimeMillis() - lastFire >= shootDelay) //if its time to shoot
            {
                shootSound.play(MusicPlayer.VOLUME); //play pew
                Laser l1 = new Laser(manager, Laser.BLUE);
                Laser l2 = new Laser(manager, Laser.BLUE);
                Laser l3 = new Laser(manager, Laser.BLUE);
                Laser l4 = new Laser(manager, Laser.BLUE);
                Laser l5 = new Laser(manager, Laser.BLUE);
                Laser l6 = new Laser(manager, Laser.BLUE);
                Laser l7 = new Laser(manager, Laser.BLUE);


                if(attLevel == 2) {
                    l1.setPosition(sprite.getX() + sprite.getWidth() / 2 - l1.getSprite().getWidth()*1.5f, sprite.getY() + sprite.getHeight());
                    l1.setDirection(0, 15);
                    manager.spawnLaser(l1);
                    l2.setPosition(sprite.getX() + sprite.getWidth() / 2 + l1.getSprite().getWidth()*0.5f, sprite.getY() + sprite.getHeight());
                    l2.setDirection(0, 15);
                    manager.spawnLaser(l2);
                }
                else
                {
                    l1.setPosition(sprite.getX() + sprite.getWidth() / 2 - l1.getSprite().getWidth()/2, sprite.getY() + sprite.getHeight());
                    l1.setDirection(0, 15);
                    manager.spawnLaser(l1);
                }

                if(attLevel >= 3) {
                    l2.setPosition(sprite.getX() + sprite.getWidth() / 2 - l1.getSprite().getWidth()*2.5f, sprite.getY() + sprite.getHeight());
                    l2.setDirection(0, 15);
                    manager.spawnLaser(l2);
                    l3.setPosition(sprite.getX() + sprite.getWidth() / 2 + l1.getSprite().getWidth()*1.5f, sprite.getY() + sprite.getHeight());
                    l3.setDirection(0, 15);
                    manager.spawnLaser(l3);
                }

                if(attLevel >= 4) {
                    l4.setPosition(sprite.getX() + sprite.getWidth() / 2 - l1.getSprite().getWidth()/2, sprite.getY() + sprite.getHeight());
                    l4.setDirection(-2f, 15);
                    manager.spawnLaser(l4);
                    l5.setPosition(sprite.getX() + sprite.getWidth() / 2 - l1.getSprite().getWidth()/2, sprite.getY() + sprite.getHeight());
                    l5.setDirection(2f, 15);
                    manager.spawnLaser(l5);
                }

                if(superAttOn)
                {
                    long elapsed = (System.nanoTime() - superAttTimer) / 1000000;
                    if(elapsed > superAttDuration) superAttOn = false;
                    l6.setPosition(sprite.getX() + sprite.getWidth() / 2 - l1.getSprite().getWidth()/2, sprite.getY() + sprite.getHeight());
                    l6.setDirection(-4f, 15);
                    manager.spawnLaser(l6);
                    l7.setPosition(sprite.getX() + sprite.getWidth() / 2 - l1.getSprite().getWidth()/2, sprite.getY() + sprite.getHeight());
                    l7.setDirection(4f, 15);
                    manager.spawnLaser(l7);
                }

                lastFire = System.currentTimeMillis(); //set new last fire
            }
        }
    }
}
