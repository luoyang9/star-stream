package xyz.charliezhang.shooter.entity.player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import xyz.charliezhang.shooter.Assets;
import xyz.charliezhang.shooter.entity.EntityManager;
import xyz.charliezhang.shooter.music.MusicPlayer;

/**
 * Created by Charlie on 2016-04-06.
 */
public class PlayerRed extends Player
{
    public PlayerRed(EntityManager manager)
    {
        super(manager);

        //set texture atlas and animation to player
        textureAtlas = Assets.manager.get("data/textures/playerred.atlas", TextureAtlas.class);
        animation = new Animation(1/15f, textureAtlas.getRegions());

        //set sprite size
        sprite.setSize(72, 62);

        //set player starting data
        shootDelay = 500;

        //read player stats
        health = maxHealth = 3;
        damage = 3;
    }

    protected void shoot()
    {
        if(playerInput.isTouching()) //if touching
        {
            if(superAttOn)
            {
                shootDelay = 200;
                long elapsed = (System.nanoTime() - superAttTimer) / 1000000;
                if(elapsed > superAttDuration)
                {
                    superAttOn = false;
                    shootDelay = 500;
                }
            }
            if(System.currentTimeMillis() - lastFire >= shootDelay) //if its time to shoot
            {
                shootSound.play(MusicPlayer.VOLUME); //play pew
                Laser l1 = new Laser(manager, Laser.ORANGE);
                Laser l2 = new Laser(manager, Laser.ORANGE);
                Laser l3 = new Laser(manager, Laser.ORANGE);
                Laser l4 = new Laser(manager, Laser.ORANGE);
                Laser l5 = new Laser(manager, Laser.ORANGE);
                Laser l6 = new Laser(manager, Laser.ORANGE);
                Laser l7 = new Laser(manager, Laser.ORANGE);



                l1.setPosition(sprite.getX() + sprite.getWidth() / 2 - l1.getSprite().getWidth()/2, sprite.getY() + sprite.getHeight());
                l1.setDirection(0, 15);
                manager.spawnLaser(l1);
                l2.setPosition(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight());
                l2.setDirection(1.5f, 15);
                manager.spawnLaser(l2);
                l3.setPosition(sprite.getX() + sprite.getWidth() / 2 - l3.getSprite().getWidth(), sprite.getY() + sprite.getHeight());
                l3.setDirection(-1.5f, 15);
                manager.spawnLaser(l3);


                if(attLevel >= 2) {
                    l4.setPosition(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight());
                    l4.setDirection(3, 15);
                    manager.spawnLaser(l4);
                    l5.setPosition(sprite.getX() + sprite.getWidth() / 2 - l5.getSprite().getWidth(), sprite.getY() + sprite.getHeight());
                    l5.setDirection(-3, 15);
                    manager.spawnLaser(l5);
                }

                if(attLevel >= 3) {
                    l6.setPosition(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight());
                    l6.setDirection(4.5f, 15);
                    manager.spawnLaser(l6);
                    l7.setPosition(sprite.getX() + sprite.getWidth() / 2 - l7.getSprite().getWidth(), sprite.getY() + sprite.getHeight());
                    l7.setDirection(-4.5f, 15);
                    manager.spawnLaser(l7);
                }

                lastFire = System.currentTimeMillis(); //set new last fire
            }
        }
    }
}
