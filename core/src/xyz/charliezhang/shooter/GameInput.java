package xyz.charliezhang.shooter;

import com.badlogic.gdx.InputProcessor;
import xyz.charliezhang.shooter.entity.player.Player;

/**
 * Created by Charlie on 2016-03-21.
 */
public class GameInput implements InputProcessor {

    private int currX, currY;
    private boolean justTouched, isTouching;

    private Player player;

    public GameInput(Player player)
    {
        this.player = player;
        justTouched = false;
        isTouching = false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        player.setJustTouched(true);
        isTouching = true;
        currX = screenX;
        currY = screenY;
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        isTouching = false;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        currX = screenX;
        currY = screenY;
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public int getX() {return currX;}
    public int getY() {return currY;}
    public boolean isTouching() {return isTouching;}

}
