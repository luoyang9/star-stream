package xyz.charliezhang.shooter.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import xyz.charliezhang.shooter.FileHandler;
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
    private Table[] scoreTables;

    private Texture background;

    private Skin skin;
    private TextButton btnBack;
    private Label[] lblScore;

    public LevelSelectScreen(MainGame game){this.game = game;}

    @Override
    public void show() {
        stage = new Stage();
        stage.setViewport(new ExtendViewport(MainGame.WIDTH, MainGame.HEIGHT));
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        skin = new Skin();
        skin.addRegions(game.manager.get("data/ui/uiskin.atlas", TextureAtlas.class));
        skin.add("default-font", game.manager.get("menu.ttf"));
        skin.add("small-font", game.manager.get("levelSelect.ttf"));
        skin.load(Gdx.files.internal("data/ui/uiskin.json"));

        lvlTable = new Table();

        scoreTables = new Table[MainGame.NUM_LEVELS];
        lblScore = new Label[MainGame.NUM_LEVELS];
        for(int i = 0; i < scoreTables.length; i++)
        {
            scoreTables[i] = new Table();
            lblScore[i] = new Label("BEST: " + FileHandler.getScore(i+1), skin, "small");
        }

        background = game.manager.get("data/ui/background.png");

        btnBack = new TextButton("BACK", skin);
        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.manager.get("data/sounds/button.mp3", Sound.class).play();
                game.setScreen(new MenuScreen(game));
                dispose();
                event.stop();
            }
        });

        int count = 0;
        for(int i = 0; i < MainGame.NUM_LEVELS; i++)
        {
            final int level = i;
            TextButton btnTemp = new TextButton("" + (i + 1), skin);
            btnTemp.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y){
                    MusicPlayer.stop("menu");
                    game.manager.get("data/sounds/button.mp3", Sound.class).play();
                    game.setScreen(new GameScreen(game, level+1));
                    System.out.println("START GAMESCREEN");
                    dispose();
                    event.stop();
                }
            });
            scoreTables[i].add(btnTemp).width(140).height(100);
            scoreTables[i].row();
            scoreTables[i].add(lblScore[i]).height(50);
            lvlTable.add(scoreTables[i]).height(150).width(140).pad(5);
            count++;
            if(count % 3 == 0) lvlTable.row();
        }

        table.setDebug(true);
        table.add(btnBack).width(450).height(200);
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
