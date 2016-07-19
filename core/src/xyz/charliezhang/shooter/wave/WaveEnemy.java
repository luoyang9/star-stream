package xyz.charliezhang.shooter.wave;

class WaveEnemy
{
    private int id;
    private float x;
    private float y;
    private float dx;
    private float dy;
    private int stop;
    private int delay;

    public WaveEnemy() {}

    int getId(){return id;}
    public float getX(){return x;}
    public float getY(){return y;}
    public float getDx(){return dx;}
    public float getDy(){return dy;}
    public int getStop(){return stop;}
    public int getDelay() {return delay;}
}
