package xyz.charliezhang.shooter.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import xyz.charliezhang.shooter.FileHandler;
import xyz.charliezhang.shooter.MainGame;
import xyz.charliezhang.shooter.music.MusicPlayer;

/**
 * Created by Charlie on 2015-12-19.
 */
public class WinScreen implements Screen {

    private MainGame game;

    private Stage stage;
    private Table table;

    private int score;
    private int lives;
    private int time;

    private Label lblScore;
    private Label lblLives;
    private Label lblTime;
    private Label lblTotal;
    private Label lblScoreValue;
    private Label lblLivesValue;
    private Label lblTimeValue;
    private Label lblTotalValue;


    private Texture background;

    private Skin skin;
    private TextButton btnMenu;

    private int level;

    public WinScreen(MainGame game, int score, int lives, int time, int level)
    {
        this.game = game;
        this.score = score;
        this.lives = lives;
        this.time = time;
        this.level = level;
        System.out.println(time);
    }

    @Override
    public void show() {
        stage = new Stage();
        stage.setViewport(new ScreenViewport());

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        background = game.manager.get("data/ui/background.png");

        skin = new Skin();

        skin.addRegions((TextureAtlas) game.manager.get("data/ui/futureui.atlas"));
        skin.add("default-font", game.manager.get("hud.ttf"));

        skin.load(Gdx.files.internal("data/ui/uiskin.json"));

        btnMenu = new TextButton("OK", skin);
        btnMenu.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                MusicPlayer.stop("win");
                game.setScreen(new MenuScreen(game));
                dispose();
                event.stop();
            }
        });

        String timeMod = (1-time > 0) ? "+" : "";
        int timeScore = (1-time)*10;
        int livesScore = lives*500;
        int total = score + timeScore + livesScore;

        if(total > FileHandler.getScore(level)) {
            FileHandler.updateScore(level, total);
        }

        lblScore = new Label("Score: ", skin);
        lblTime = new Label("Time ( " + time/60 + " min. " + time%60 + " sec.): ", skin);
        lblLives = new Label("Lives (" + lives + " X 500): ", skin);
        lblTotal = new Label("Total: ", skin);
        lblScoreValue = new Label(" " +score + "p", skin);
        lblTimeValue = new Label(timeMod + timeScore + "p", skin);
        lblLivesValue = new Label(" " + livesScore + "p", skin);
        lblTotalValue = new Label(" " + total + "p", skin);

       // table.setDebug(true);
        table.add(lblScore).uniform().right();
        table.add(lblScoreValue).uniform().left();
        table.row();
        table.add(lblLives).right();
        table.add(lblLivesValue).left();
        table.row();
        table.add(lblTime).right();
        table.add(lblTimeValue).left();
        table.row();
        table.add(lblTotal).right();
        table.add(lblTotalValue).left();
        table.row();
        table.add(btnMenu).width(350).height(125).center().colspan(2);

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
