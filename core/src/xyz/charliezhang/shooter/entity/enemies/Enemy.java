package xyz.charliezhang.shooter.entity.enemies;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import xyz.charliezhang.shooter.entity.EntityManager;
import xyz.charliezhang.shooter.entity.Entity;

import static xyz.charliezhang.shooter.Config.ENEMY_INITIAL_DEAD;
import static xyz.charliezhang.shooter.Config.ENEMY_INITIAL_SUICIDE;

public abstract class Enemy extends Entity implements Json.Serializable
{
	//entity data
	protected int health, maxHealth, damage, score;
	long lastFire;
	protected boolean dead, suicide;

	//spawning data
	private int spawnDelay;

	//manager
	protected EntityManager manager;

	protected Enemy() {
		super();
		dead = ENEMY_INITIAL_DEAD;
		suicide = ENEMY_INITIAL_SUICIDE;
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
	
	public void modifyHealth(int h) {health += h;}

	public int getDamage() {return damage;}

	public int getScore() {return score;}

	public int getSpawnDelay() {return spawnDelay;}

	public boolean isDead() {return dead;}

	public boolean isSuicide() {return suicide;}

	public EntityManager getManager() {return manager;}

}
