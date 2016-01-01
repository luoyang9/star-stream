package xyz.charliezhang.shooter.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import xyz.charliezhang.shooter.MainGame;

/**
 * Created by Charlie on 2015-12-19.
 */
public class MenuScreen implements Screen {

    private MainGame game;

    private Stage stage;
    private Table table;

    private Texture background;
    private Music backgroundMusic;

    private TextButton btnPlay;
    private TextButton btnSurvival;
    private TextButton btnOptions;

    private Skin skin;

    public MenuScreen(MainGame game){this.game = game;}

    @Override
    public void show() {
        stage = new Stage();
        stage.setViewport(new ScreenViewport());

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        background = game.manager.get("data/ui/background.png");
        backgroundMusic = game.manager.get("data/music/menu.mp3");
        backgroundMusic.setLooping(true);
        backgroundMusic.play();



        skin = new Skin();

        skin.addRegions((TextureAtlas) game.manager.get("data/ui/uiskin.atlas"));
        skin.add("default-font", game.manager.get("menu.ttf"));

        skin.load(Gdx.files.internal("data/ui/uiskin.json"));

        btnPlay = new TextButton("Play", skin);
        btnSurvival = new TextButton("Survival", skin);
        btnOptions = new TextButton("Options", skin);

        btnSurvival.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                backgroundMusic.stop();
                dispose();
                System.out.println("START GAMESCREEN");
                game.setScreen(new GameScreen(game));
                event.stop();
            }
        });


        table.add(btnPlay).padBottom(20).minSize(400, 150);
        table.row();
        table.add(btnSurvival).padBottom(20).minSize(400, 150);
        table.row();
        table.add(btnOptions).minSize(400, 150);

        table.setDebug(true);

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
