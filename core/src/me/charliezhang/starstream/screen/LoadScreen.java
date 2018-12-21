package me.charliezhang.starstream.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import me.charliezhang.starstream.Assets;
import me.charliezhang.starstream.MainGame;
import me.charliezhang.starstream.music.MusicPlayer;

import static me.charliezhang.starstream.Config.UI_SKIN_PATH;

public class LoadScreen implements Screen
{
    private MainGame game;

    private Stage stage;
    private boolean canDispose;

    private Image screenBg;

    public LoadScreen(MainGame game)
    {
        this.game = game;
    }

    @Override
    public void show() {

        //load loading ui
        Assets.manager.load("data/ui/loading.png", Texture.class);
        Assets.manager.finishLoading();

        stage = new Stage();
        stage.setViewport(new ExtendViewport(MainGame.WIDTH, MainGame.HEIGHT));
        canDispose = false;

        stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1)));

        Texture loadingTexture = Assets.manager.get("data/ui/loading.png", Texture.class);

        screenBg = new Image(loadingTexture);
        screenBg.setFillParent(true);

        stage.addActor(screenBg);

        Assets.load();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Assets.manager.update())
        {
            Assets.skin = new Skin();
            Assets.skin.add("xlarge-font",  Assets.manager.get("xlarge.ttf", BitmapFont.class));
            Assets.skin.add("large-font",  Assets.manager.get("large.ttf", BitmapFont.class));
            Assets.skin.add("medium-font",  Assets.manager.get("medium.ttf", BitmapFont.class));
            Assets.skin.add("small-font",  Assets.manager.get("small.ttf", BitmapFont.class));
            Assets.skin.add("xsmall-font",  Assets.manager.get("xsmall.ttf", BitmapFont.class));
            Assets.skin.addRegions( Assets.manager.get(UI_SKIN_PATH, TextureAtlas.class));
            Assets.skin.load(Gdx.files.internal("data/ui/uiskin.json"));

            //music player
            MusicPlayer.load();

            stage.addAction(Actions.sequence(Actions.fadeOut(0.25f), Actions.run(new Runnable() {
                @Override
                public void run() {
                    game.setScreen(new UIContainerScreen(game));
                    canDispose = true;
                }
            })));
        }

        stage.act();
        stage.draw();

        if(canDispose) dispose();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Assets.manager.unload("data/ui/loading.png");
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
