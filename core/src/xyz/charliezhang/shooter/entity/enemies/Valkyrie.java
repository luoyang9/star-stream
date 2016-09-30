package xyz.charliezhang.shooter.entity.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import xyz.charliezhang.shooter.Assets;
import xyz.charliezhang.shooter.MainGame;
import xyz.charliezhang.shooter.entity.EntityManager;

public class Valkyrie extends Enemy
{
    private boolean intro;
    private long moveTimer;
    public Valkyrie(EntityManager manager) {
        super();

        textureAtlas = Assets.manager.get("data/textures/valkyrie.atlas", TextureAtlas.class);
        animation = new Animation(1/20f, textureAtlas.getRegions());

        sprite.setSize(91, 66);

        health = maxHealth = 40;
        damage = 1;
        score = 100;
        this.manager = manager;

        intro = true;
    }

    @Override
    public void update() {
        super.update();

        System.out.println(direction.y);
        if(intro)
        {
            if(sprite.getY() <= MainGame.HEIGHT - stop) {
                intro = false;
                moveTimer = System.nanoTime();
                setDirection(0, 0);
            }
        }
        else
        {
            if((System.nanoTime() - moveTimer) / 1000000 > 500) {
                if(direction.x == 0) {
                    if(getPosition().x > manager.getViewport().getWorldWidth() - 200)
                        setDirection(-4, 0);
                    else if(getPosition().x < 200)
                        setDirection(4, 0);
                    else {
                        if(Math.random() >= 0.5) setDirection(4, 0);
                        else setDirection(-4, 0);
                    }
                }
                else {
                    setDirection(0, 0);
                }
                moveTimer = System.nanoTime();
            }
        }

        sprite.setPosition(sprite.getX() + direction.x, sprite.getY() + direction.y);


        if(sprite.getY() < MainGame.HEIGHT)
        {
            if(System.currentTimeMillis() - lastFire >= 800)
            {
                EnemyLaser g1 = manager.getEnemyLaserPool().obtain();
                EnemyLaser g2 = manager.getEnemyLaserPool().obtain();
                g1.init(manager, this, 3);
                g1.setDirection(0.1f, -5);
                g1.setPosition(sprite.getX() + sprite.getWidth() / 2 + 20, sprite.getY());
                g2.init(manager, this, 3);
                g2.setDirection(-0.1f, -5);
                g2.setPosition(sprite.getX() + sprite.getWidth() / 2 - 20, sprite.getY());
                manager.spawnEnemyLaser(g1);
                manager.spawnEnemyLaser(g2);
                lastFire = System.currentTimeMillis();
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
