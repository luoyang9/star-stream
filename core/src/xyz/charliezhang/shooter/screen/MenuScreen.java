package xyz.charliezhang.shooter.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import xyz.charliezhang.shooter.Assets;
import xyz.charliezhang.shooter.MainGame;
import xyz.charliezhang.shooter.music.MusicPlayer;

/**
 * Created by Charlie on 2015-12-19.
 */
public class MenuScreen implements Screen {

    private MainGame game;

    private Stage stage;
    private Table table;

    private Texture background;

    private TextButton btnPlay;
    private TextButton btnShop;
    private TextButton btnOptions;

    private Skin skin;

    public MenuScreen(MainGame game){this.game = game;}

    @Override
    public void show() {
        stage = new Stage();
        stage.setViewport(new ExtendViewport(MainGame.WIDTH, MainGame.HEIGHT));
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        skin = Assets.skin;

        background = Assets.manager.get("data/ui/background.png");

        if(!MusicPlayer.loaded("menu")) {
            MusicPlayer.loadMusic("menu", Assets.manager.get("data/music/menu.ogg", Music.class));
        }
        if(!MusicPlayer.isPlaying("menu")) {
            MusicPlayer.loop("menu");
        }

        btnPlay = new TextButton("Play", skin);
        btnShop = new TextButton("Shop", skin);
        btnOptions = new TextButton("Options", skin);

        btnPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                System.out.println("START LEVELSELECTSCREEN");
                Assets.manager.get("data/sounds/button.mp3", Sound.class).play(MusicPlayer.VOLUME);
                game.setScreen(new LevelSelectScreen(game));
                dispose();
                event.stop();
            }
        });

        btnOptions.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                System.out.println("START OPTIONSSCREEN");
                Assets.manager.get("data/sounds/button.mp3", Sound.class).play(MusicPlayer.VOLUME);
                game.setScreen(new OptionsScreen(game));
                dispose();
                event.stop();
            }
        });


        table.add(btnPlay).pad(20).width(384).height(160);
        table.row();
        table.add(btnShop).pad(20).width(384).height(160);
        table.row();
        table.add(btnOptions).pad(20).width(384).height(160);

        //table.setDebug(true);

        Gdx.input.setInputProcessor(stage);
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
