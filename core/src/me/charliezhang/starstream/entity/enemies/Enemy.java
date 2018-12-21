package me.charliezhang.starstream.entity.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import me.charliezhang.starstream.MainGame;
import me.charliezhang.starstream.entity.Entity;
import me.charliezhang.starstream.entity.EntityManager;

import static me.charliezhang.starstream.Config.ENEMY_INITIAL_DEAD;
import static me.charliezhang.starstream.Config.ENEMY_INITIAL_SUICIDE;

public abstract class Enemy extends Entity implements Json.Serializable
{
	//entity data
	protected int health, maxHealth, damage, score, coin;
	long lastFire;
	protected boolean dead, suicide, isBoss;

	//spawning data
	private int spawnDelay;

	//manager
	protected EntityManager manager;

	protected Enemy() {
		super();
		dead = ENEMY_INITIAL_DEAD;
		suicide = ENEMY_INITIAL_SUICIDE;
		isBoss = false;
	}

	@Override
	public void update() {
		super.update();
		if(health <= 0) dead = true;
	}

	//json write method
	@Override
	public void write(Json json) {

	}

	//json read method
	@Override
	public void read (Json json, JsonValue jsonMap) {
		setPosition(jsonMap.getFloat("x"), jsonMap.getFloat("y"));
		setDirection(jsonMap.getFloat("dx"), jsonMap.getFloat("dy"));
		spawnDelay = jsonMap.getInt("delay");
	}

	public void setEntityManager(EntityManager manager) {this.manager = manager;}
	public void reposition() {setPosition(sprite.getX(), sprite.getY() - 800 + manager.getViewport().getWorldHeight());}
	public void applyUpgrades() {}

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
	
	public void modifyHealth(int h) {health += h;}

	public int getDamage() {return damage;}

	public int getScore() {return score;}

	public int getCoin() {return coin;}

	public int getSpawnDelay() {return spawnDelay;}

	public boolean isDead() {return dead;}

	public boolean isSuicide() {return suicide;}

	public boolean isBoss() {return isBoss;}

	public EntityManager getManager() {return manager;}

}
