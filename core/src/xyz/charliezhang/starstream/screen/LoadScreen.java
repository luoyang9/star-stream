package xyz.charliezhang.starstream.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import xyz.charliezhang.starstream.Assets;
import xyz.charliezhang.starstream.MainGame;
import xyz.charliezhang.starstream.music.MusicPlayer;

import static xyz.charliezhang.starstream.Config.UI_SKIN_PATH;

public class LoadScreen implements Screen
{
    private MainGame game;

    private Stage stage;
    private boolean canDispose;

    private Image logo;
    private Image loadingFrame;
    private Image loadingBarHidden;
    private Image screenBg;
    private Image loadingBg;

    private float startX, endX, percent;

    private Actor loadingBar;

    public LoadScreen(MainGame game)
    {
        this.game = game;
    }

    @Override
    public void show() {

        //load loading ui
        Assets.manager.load("data/ui/loading.pack", TextureAtlas.class);
        Assets.manager.finishLoading();

        stage = new Stage();
        stage.setViewport(new ExtendViewport(MainGame.WIDTH, MainGame.HEIGHT));
        canDispose = false;

        TextureAtlas loadingAtlas = Assets.manager.get("data/ui/loading.pack", TextureAtlas.class);

        logo = new Image(loadingAtlas.findRegion("libgdx-logo"));
        loadingFrame = new Image(loadingAtlas.findRegion("loading-frame"));
        loadingBarHidden = new Image(loadingAtlas.findRegion("loading-bar-hidden"));
        screenBg = new Image(loadingAtlas.findRegion("screen-bg"));
        loadingBg = new Image(loadingAtlas.findRegion("loading-frame-bg"));
        loadingBar = new Image(loadingAtlas.findRegion("loading-bar1"));

        stage.addActor(screenBg);
        stage.addActor(loadingBar);
        stage.addActor(loadingBg);
        stage.addActor(loadingBarHidden);
        stage.addActor(loadingFrame);
        stage.addActor(logo);

        Assets.load();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Assets.manager.update())
        {
            Assets.skin = new Skin();
            Assets.skin.add("big-font",  Assets.manager.get("big.ttf", BitmapFont.class));
            Assets.skin.add("medium-font",  Assets.manager.get("medium.ttf", BitmapFont.class));
            Assets.skin.add("small-font",  Assets.manager.get("small.ttf", BitmapFont.class));
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

        percent = Interpolation.linear.apply(percent, Assets.manager.getProgress(), 0.1f);

        loadingBarHidden.setX(startX + endX * percent);
        loadingBg.setX(loadingBarHidden.getX() + 30);
        loadingBg.setWidth(stage.getWidth() - stage.getWidth() * percent);
        loadingBg.invalidate();

        stage.act();
        stage.draw();

        game.batch.begin();
        game.font.draw(game.batch, Assets.manager.getProgress()*100 + "%", 100, 100);
        game.batch.end();

        if(canDispose) dispose();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

        screenBg.setSize(stage.getWidth(), stage.getHeight());
        logo.setX(stage.getWidth()/2 - logo.getWidth()/2);
        logo.setY(stage.getHeight()/2 - logo.getHeight()/2 + 300);

        loadingFrame.setX(0);
        loadingFrame.setY((stage.getHeight()/2 - loadingFrame.getHeight()) / 2);
        loadingFrame.setWidth(stage.getWidth());

        loadingBar.setX(loadingFrame.getX() + 15);
        loadingBar.setY(loadingFrame.getY() + 5);
        loadingBar.setWidth(stage.getWidth());

        loadingBarHidden.setX(loadingBar.getX() + 35);
        loadingBarHidden.setY(loadingBar.getY() - 3);

        startX = loadingBarHidden.getX();
        endX = stage.getWidth();

        loadingBg.setSize(stage.getWidth() , loadingBar.getHeight());
        loadingBg.setX(loadingBarHidden.getX() + 30);
        loadingBg.setY(loadingBarHidden.getY() + 3);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Assets.manager.unload("data/ui/loading.pack");
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
