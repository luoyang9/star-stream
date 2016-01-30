package xyz.charliezhang.shooter.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import xyz.charliezhang.shooter.MainGame;
import xyz.charliezhang.shooter.music.MusicPlayer;

/**
 * Created by Charlie on 2015-12-19.
 */
public class LevelSelectScreen implements Screen {

    private MainGame game;

    private Stage stage;
    private Table table;
    private Table lvlTable;

    private Texture background;

    private Skin skin;
    private TextButton btnBack;

    private final int NUM_LEVELS = 9;

    public LevelSelectScreen(MainGame game){this.game = game;}

    @Override
    public void show() {
        stage = new Stage();
        stage.setViewport(new ScreenViewport());

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        lvlTable = new Table();

        background = game.manager.get("data/ui/background.png");

        skin = new Skin();

        skin.addRegions((TextureAtlas) game.manager.get("data/ui/uiskin.atlas"));
        skin.add("default-font", game.manager.get("menu.ttf"));

        skin.load(Gdx.files.internal("data/ui/uiskin.json"));

        btnBack = new TextButton("BACK", skin);
        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen(new MenuScreen(game));
                dispose();
                event.stop();
            }
        });

        int count = 0;
        for(int i = 0; i < NUM_LEVELS; i++)
        {
            final int level = i;
            TextButton btnTemp = new TextButton("" + (i + 1), skin);
            btnTemp.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y){
                    MusicPlayer.stop("menu");
                    game.setScreen(new GameScreen(game, level+1));
                    System.out.println("START GAMESCREEN");
                    dispose();
                    event.stop();
                }
            });
            lvlTable.add(btnTemp).width(200).height(200).pad(20);
            count++;
            if(count % 3 == 0) lvlTable.row();
        }

        table.setDebug(true);
        table.add(btnBack);
        table.row();
        table.add(lvlTable);

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
