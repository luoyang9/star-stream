package xyz.charliezhang.starstream.entity.player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import xyz.charliezhang.starstream.Assets;
import xyz.charliezhang.starstream.entity.EntityManager;
import xyz.charliezhang.starstream.music.MusicPlayer;

import static xyz.charliezhang.starstream.Config.*;

public class BlueFury extends Player
{
    public BlueFury(EntityManager manager)
    {
        super(manager);

        //set texture atlas and animation to player
        textureAtlas = Assets.manager.get(PLAYER_BLUE_PATH, TextureAtlas.class);
        animation = new Animation(1/10f, textureAtlas.getRegions());

        //set sprite size
        sprite.setSize(64.5f, 62);

        //set player starting data
        shootDelay = BLUE_FURY_SHOOT_DELAY;

        //read player stats
        health = maxHealth = BLUE_FURY_MAX_HEALTH;
        damage = BLUE_FURY_DAMAGE;
    }

    @Override
    protected void shoot()
    {
        if(playerInput.isTouching()) //if touching
        {
            if(System.currentTimeMillis() - lastFire >= shootDelay) //if its time to shoot
            {
                shootSound.play(MusicPlayer.VOLUME); //play pew
                Laser l1 = manager.getLaserPool().obtain();
                Laser l2 = manager.getLaserPool().obtain();
                Laser l3 = manager.getLaserPool().obtain();
                Laser l4 = manager.getLaserPool().obtain();
                Laser l5 = manager.getLaserPool().obtain();
                Laser l6 = manager.getLaserPool().obtain();
                Laser l7 = manager.getLaserPool().obtain();


                if(attLevel == 2) {
                    l1.init(manager, Laser.BLUE);
                    l1.setPosition(sprite.getX() + sprite.getWidth() / 2 - l1.getSprite().getWidth()*1.5f, sprite.getY() + sprite.getHeight());
                    l1.setDirection(0, 15);
                    manager.spawnLaser(l1);
                    l2.init(manager, Laser.BLUE);
                    l2.setPosition(sprite.getX() + sprite.getWidth() / 2 + l1.getSprite().getWidth()*0.5f, sprite.getY() + sprite.getHeight());
                    l2.setDirection(0, 15);
                    manager.spawnLaser(l2);
                }
                else
                {
                    l1.init(manager, Laser.BLUE);
                    l1.setPosition(sprite.getX() + sprite.getWidth() / 2 - l1.getSprite().getWidth()/2, sprite.getY() + sprite.getHeight());
                    l1.setDirection(0, 15);
                    manager.spawnLaser(l1);

                    if(attLevel >= 3) {
                        l2.init(manager, Laser.BLUE);
                        l2.setPosition(sprite.getX() + sprite.getWidth() / 2 - l1.getSprite().getWidth()*2.5f, sprite.getY() + sprite.getHeight());
                        l2.setDirection(0, 15);
                        manager.spawnLaser(l2);
                        l3.init(manager, Laser.BLUE);
                        l3.setPosition(sprite.getX() + sprite.getWidth() / 2 + l1.getSprite().getWidth()*1.5f, sprite.getY() + sprite.getHeight());
                        l3.setDirection(0, 15);
                        manager.spawnLaser(l3);
                    }

                    if(attLevel >= 4) {
                        l4.init(manager, Laser.BLUE);
                        l4.setPosition(sprite.getX() + sprite.getWidth() / 2 - l1.getSprite().getWidth()/2, sprite.getY() + sprite.getHeight());
                        l4.setDirection(-2f, 15);
                        manager.spawnLaser(l4);
                        l5.init(manager, Laser.BLUE);
                        l5.setPosition(sprite.getX() + sprite.getWidth() / 2 - l1.getSprite().getWidth()/2, sprite.getY() + sprite.getHeight());
                        l5.setDirection(2f, 15);
                        manager.spawnLaser(l5);
                    }
                }

                if(superAttOn)
                {
                    l6.init(manager, Laser.BLUE);
                    l6.setPosition(sprite.getX() + sprite.getWidth() / 2 - l1.getSprite().getWidth()/2, sprite.getY() + sprite.getHeight());
                    l6.setDirection(-4f, 15);
                    manager.spawnLaser(l6);
                    l7.init(manager, Laser.BLUE);
                    l7.setPosition(sprite.getX() + sprite.getWidth() / 2 - l1.getSprite().getWidth()/2, sprite.getY() + sprite.getHeight());
                    l7.setDirection(4f, 15);
                    manager.spawnLaser(l7);
                }

                lastFire = System.currentTimeMillis(); //set new last fire
            }
        }
    }
}
