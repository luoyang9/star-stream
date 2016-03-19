package xyz.charliezhang.shooter.entity.enemies;

import xyz.charliezhang.shooter.entity.EntityManager;
import xyz.charliezhang.shooter.entity.Entity;

public abstract class Enemy extends Entity
{
	//enemy data
	protected int health, maxHealth, damage, score;
	protected long lastFire;
	protected boolean dead;
	protected int stop;

	//manager
	protected EntityManager manager;

	protected Enemy() {
		super();
		dead = false;
	}

	@Override
	public void update() {
		if(health <= 0) dead = true;
	}

	public void setStop(int stop) { this.stop = stop;}
	
	public void modifyHealth(int h) {health += h;}

	public int getDamage() {return damage;}

	public int getScore() {return score;}

	public boolean isDead() {return dead;}

	public EntityManager getManager() {return manager;}

}
