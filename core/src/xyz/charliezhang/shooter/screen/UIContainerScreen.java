package xyz.charliezhang.shooter.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import xyz.charliezhang.shooter.Assets;
import xyz.charliezhang.shooter.MainGame;
import xyz.charliezhang.shooter.music.MusicPlayer;

public class UIContainerScreen implements Screen {
    private MainGame game;

    private Stage stage;
    private boolean canDispose;

    private MenuTable menu;
    private OptionsTable options;
    private ShopTable shop;
    private LevelSelectTable levelSelect;

    public static enum UITable { MENU, OPTIONS, SHOP, LEVELSELECT };

    private Texture background;

    public UIContainerScreen(MainGame game){this.game = game;}

    @Override
    public void show() {

        stage = new Stage();
        stage.setViewport(new ExtendViewport(MainGame.WIDTH, MainGame.HEIGHT));
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        canDispose = false;

        stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.25f)));

        menu = new MenuTable(this);
        options = new OptionsTable(this);
        shop = new ShopTable(this);
        levelSelect = new LevelSelectTable(this);
        menu.setFillParent(true);
        options.setFillParent(true);
        shop.setFillParent(true);
        levelSelect.setFillParent(true);

        stage.addActor(menu);


        background = Assets.manager.get("data/ui/background.png");

        if(!MusicPlayer.isPlaying("menu")) {
            MusicPlayer.loop("menu");
        }

        stage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Assets.manager.get("data/sounds/button.mp3", Sound.class).play(MusicPlayer.VOLUME);
                event.stop();
            }
        });

        Gdx.input.setInputProcessor(stage);
    }

    public void play(final int level) {
        stage.addAction(Actions.sequence(Actions.fadeOut(0.25f), Actions.run(new Runnable() {
            @Override
            public void run() {
            game.setScreen(new GameScreen(game, level + 1));
            canDispose = true;
            }
        })));
    }

    public void changeTable(UITable table) {
        stage.getActors().first().remove();
        switch(table) {
            case MENU:
                stage.addActor(menu);
                break;
            case SHOP:
                stage.addActor(shop);
                break;
            case OPTIONS:
                stage.addActor(options);
                break;
            case LEVELSELECT:
                stage.addActor(levelSelect);
                break;
            default:
                stage.addActor(menu);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);

        //background
        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, stage.getHeight()/background.getHeight()*background.getWidth(), stage.getHeight());
        stage.getBatch().end();

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
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
