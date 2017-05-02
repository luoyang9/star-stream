package xyz.charliezhang.starstream.entity.player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import xyz.charliezhang.starstream.Assets;
import xyz.charliezhang.starstream.entity.EntityManager;
import xyz.charliezhang.starstream.music.MusicPlayer;

import static xyz.charliezhang.starstream.Config.*;
import static xyz.charliezhang.starstream.entity.powerup.PowerUp.PowerUps.ATTACK;

public class RedDragon extends Player
{
    public RedDragon(EntityManager manager)
    {
        super(manager);

        //set texture atlas and animation to player
        textureAtlas = Assets.manager.get(PLAYER_RED_PATH, TextureAtlas.class);
        animation = new Animation<TextureRegion>(1/15f, textureAtlas.getRegions());

        //set sprite size
        sprite.setSize(72, 62);

        //set player starting data
        shootDelay = RED_DRAGON_SHOOT_DELAY;

        //read player stats
        health = maxHealth = RED_DRAGON_MAX_HEALTH;
        damage = RED_DRAGON_DAMAGE;
        missileDamage = damage * 2;
    }

    private void checkPowerups() {
        super.checkPowerUps();

        if(superAttOn)
        {
            shootDelay = RED_DRAGON_SUPER_SHOOT_DELAY;
            long elapsed = (System.nanoTime() - superAttTimer) / 1000000;
            if(elapsed > superAttDuration)
            {
                superAttOn = false;
                shootDelay = RED_DRAGON_SHOOT_DELAY;
                manager.deactivatePowerUp(ATTACK);
            }
        }
    }

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

                l1.init(manager, Laser.ORANGE);
                l1.setPosition(sprite.getX() + sprite.getWidth() / 2 - l1.getSprite().getWidth()/2, sprite.getY() + sprite.getHeight());
                l1.setDirection(0, 15);
                manager.spawnLaser(l1);
                l2.init(manager, Laser.ORANGE);
                l2.setPosition(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight());
                l2.setDirection(1.5f, 15);
                manager.spawnLaser(l2);
                l3.init(manager, Laser.ORANGE);
                l3.setPosition(sprite.getX() + sprite.getWidth() / 2 - l3.getSprite().getWidth(), sprite.getY() + sprite.getHeight());
                l3.setDirection(-1.5f, 15);
                manager.spawnLaser(l3);


                if(attLevel >= 2) {
                    l4.init(manager, Laser.ORANGE);
                    l4.setPosition(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight());
                    l4.setDirection(3, 15);
                    manager.spawnLaser(l4);
                    l5.init(manager, Laser.ORANGE);
                    l5.setPosition(sprite.getX() + sprite.getWidth() / 2 - l5.getSprite().getWidth(), sprite.getY() + sprite.getHeight());
                    l5.setDirection(-3, 15);
                    manager.spawnLaser(l5);
                }

                if(attLevel >= 3) {
                    l6.init(manager, Laser.ORANGE);
                    l6.setPosition(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight());
                    l6.setDirection(4.5f, 15);
                    manager.spawnLaser(l6);
                    l7.init(manager, Laser.ORANGE);
                    l7.setPosition(sprite.getX() + sprite.getWidth() / 2 - l7.getSprite().getWidth(), sprite.getY() + sprite.getHeight());
                    l7.setDirection(-4.5f, 15);
                    manager.spawnLaser(l7);
                }

                lastFire = System.currentTimeMillis(); //set new last fire
            }
        }
    }

    public void update() {
        super.update();

        //check powerups
        checkPowerups();
    }
}
