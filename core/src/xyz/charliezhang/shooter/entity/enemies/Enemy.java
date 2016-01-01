package xyz.charliezhang.shooter.entity.enemies;

import xyz.charliezhang.shooter.entity.EntityManager;
import xyz.charliezhang.shooter.entity.Entity;

public abstract class Enemy extends Entity
{
	//enemy data
	protected int health, maxHealth, damage, score;
	protected long lastFire;
	
	//manager
	protected EntityManager manager;

	public Enemy() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}
	
	public void modifyHealth(int h) {health += h;}
	
	public int getHealth() {return health;}

	public int getDamage() {return damage;}

	public int getScore() {return score;}

	public EntityManager getManager() {return manager;}

}
