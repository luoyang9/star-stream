package xyz.charliezhang.shooter.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import xyz.charliezhang.shooter.Assets;
import xyz.charliezhang.shooter.GameData;
import xyz.charliezhang.shooter.MainGame;
import xyz.charliezhang.shooter.music.MusicPlayer;

class WinScreen implements Screen {

    private MainGame game;

    private Stage stage;
    private Table table;

    private int score;
    private int lives;
    private int time;
    private int timeScore;
    private int livesScore;
    private int total;

    private int animScore;
    private int animTimeScore;
    private int animLivesScore;
    private int animTotal;

    private String timeMod;

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

    WinScreen(MainGame game, int score, int lives, int time, int level)
    {
        this.game = game;
        this.score = score;
        this.lives = lives;
        this.time = time;
        this.level = level;
    }

    @Override
    public void show() {
        stage = new Stage();
        stage.setViewport(new ExtendViewport(MainGame.WIDTH, MainGame.HEIGHT));
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        background = Assets.manager.get("data/ui/background.png");

        skin = Assets.skin;

        btnMenu = new TextButton("OK", skin);
        btnMenu.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen(new MenuScreen(game));
                dispose();
                event.stop();
            }
        });

        timeMod = (1-time > 0) ? "+" : "";
        timeScore = (1-time)*10;
        livesScore = lives*500;
        total = score + timeScore + livesScore;

        if(total > GameData.getScore(level)) {
            GameData.updateScore(level, total);
        }

        //animation
        animLivesScore = 0;
        animScore = 0;
        animTimeScore = 0;
        animTotal = 0;

        //labels
        lblScore = new Label("Score:", skin, "medium");
        lblScore.setWrap(true);
        lblTime = new Label("Time (" + time/60 + ":" + time%60 + "):", skin, "medium");
        lblTime.setWrap(true);
        lblLives = new Label("Lives (" + lives + "X500):", skin, "medium");
        lblLives.setWrap(true);
        lblTotal = new Label("Total:", skin, "medium");
        lblScoreValue = new Label(" " +animScore + "p", skin, "medium");
        lblTimeValue = new Label(timeMod + animTimeScore + "p", skin, "medium");
        lblLivesValue = new Label(" " + animLivesScore + "p", skin, "medium");
        lblTotalValue = new Label(" " + animTotal + "p", skin, "medium");

       // table.setDebug(true);
        table.add(lblScore).size(230, 100).uniform().right();
        table.add(lblScoreValue).uniform().left();
        table.row();
        table.add(lblLives).size(230, 100).right();
        table.add(lblLivesValue).left();
        table.row();
        table.add(lblTime).size(230, 100).right();
        table.add(lblTimeValue).left();
        table.row();
        table.add(lblTotal).size(230, 100).right();
        table.add(lblTotalValue).left();
        table.row();
        table.add(btnMenu).width(350).height(125).center().colspan(2);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        //animation
        if(animScore < score)
        {
            animScore+=10;
            lblScoreValue.setText(" " +animScore);
        }
        else if(animLivesScore < livesScore)
        {
            animLivesScore+=10;
            lblLivesValue.setText(" " + animLivesScore);
        }
        else if(animTimeScore < timeScore)
        {
            animTimeScore+=10;
            lblTimeValue.setText(timeMod + animTimeScore);
        }
        else if(animTimeScore > timeScore)
        {
            animTimeScore-=10;
            lblTimeValue.setText(timeMod + animTimeScore);
        }
        else if(animTotal < total)
        {
            animTotal+=10;
            lblTotalValue.setText(" " + animTotal);
        }

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
