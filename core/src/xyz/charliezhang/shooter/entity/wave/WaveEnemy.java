package xyz.charliezhang.shooter.entity.wave;

/**
 * Created by Charlie on 2015-11-24.
 */
public class WaveEnemy
{
    private int id;
    private float x;
    private float y;
    private float dx;
    private float dy;
    private int stop;
    private int delay;

    public WaveEnemy() {}

    public int getId(){return id;}
    public float getX(){return x;}
    public float getY(){return y;}
    public float getDx(){return dx;}
    public float getDy(){return dy;}
    public int getStop(){return stop;}
    public int getDelay() {return delay;}
}
