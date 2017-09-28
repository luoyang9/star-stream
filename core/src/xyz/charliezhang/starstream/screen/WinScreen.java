package xyz.charliezhang.starstream.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import xyz.charliezhang.starstream.Assets;
import xyz.charliezhang.starstream.GameData;
import xyz.charliezhang.starstream.MainGame;
import xyz.charliezhang.starstream.misc.Background;

class WinScreen implements Screen {

    private MainGame game;
    private Background background;

    private Stage stage;
    private Table table;
    private boolean canDispose;

    private int score;
    private int money;
    private int lives;
    private int time;
    private int timeScore;
    private int livesScore;
    private int total;

    private int animScore;
    private int animMoney;
    private int animTimeScore;
    private int animLivesScore;
    private int animTotal;

    private String timeMod;

    private Label lblScore;
    private Label lblMoney;
    private Label lblLives;
    private Label lblTime;
    private Label lblTotal;
    private Label lblScoreValue;
    private Label lblMoneyValue;
    private Label lblLivesValue;
    private Label lblTimeValue;
    private Label lblTotalValue;

    private Skin skin;
    private TextButton btnMenu;

    private int level;
    private int[] levelPar = {90, 80, 80, 85, 80, 80, 80, 80, 80};

    WinScreen(MainGame game, Background background, int score, int money, int lives, int time, int level)
    {
        this.game = game;
        this.score = score;
        this.money = money;
        this.lives = lives;
        this.time = time;
        this.level = level;
        this.background = background;
    }

    @Override
    public void show() {
        stage = new Stage();
        stage.setViewport(new ExtendViewport(MainGame.WIDTH, MainGame.HEIGHT));
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        canDispose = false;

        stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.5f)));

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        skin = Assets.skin;

        btnMenu = new TextButton("OK", skin);
        btnMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            stage.addAction(Actions.sequence(Actions.fadeOut(0.5f), Actions.run(new Runnable() {
                @Override
                public void run() {
                    game.setScreen(new UIContainerScreen(game));
                    canDispose = true;
                }
            })));
            btnMenu.setTouchable(Touchable.disabled);
            }
        });

        timeMod = (levelPar[level-1]-time > 0) ? " " : "-";
        timeScore = (levelPar[level-1]-time)*10;
        livesScore = lives*500;
        total = score + timeScore + livesScore;

        if(total > GameData.prefs.getInteger("level-" + level)) {
            GameData.prefs.putInteger("level-" + level, total).flush();
        }
        long currMoney = GameData.prefs.getLong("money");
        GameData.prefs.putLong("money",  currMoney + money).flush();

        //animation
        animLivesScore = 0;
        animScore = 0;
        animMoney = 0;
        animTimeScore = 0;
        animTotal = 0;

        //labels
        lblMoney = new Label("Money:", skin, "medium");
        lblMoney.setWrap(true);
        lblMoney.setAlignment(Align.right);
        lblScore = new Label("Score:", skin, "medium");
        lblScore.setWrap(true);
        lblScore.setAlignment(Align.right);
        lblTime = new Label("Time (" + time/60 + ":" + time%60 + "):", skin, "medium");
        lblTime.setWrap(true);
        lblTime.setAlignment(Align.right);
        lblLives = new Label("Lives (" + lives + "X500):", skin, "medium");
        lblLives.setWrap(true);
        lblLives.setAlignment(Align.right);
        lblTotal = new Label("Total:", skin, "medium");
        lblTotal.setAlignment(Align.right);
        lblScoreValue = new Label(" " + animScore + "p", skin, "medium");
        lblMoneyValue = new Label(" " + animMoney, skin, "medium");
        lblTimeValue = new Label(timeMod + animTimeScore + "p", skin, "medium");
        lblLivesValue = new Label(" " + animLivesScore + "p", skin, "medium");
        lblTotalValue = new Label(" " + animTotal + "p", skin, "medium");

        table.add(lblMoney).size(230, 100).uniform().right();
        table.add(lblMoneyValue).uniform().left();
        table.row();
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
        if(animMoney < money) {
            animMoney += 1;
            lblMoneyValue.setText("" + animMoney);
        } else if(animScore < score) {
            animScore+=10;
            lblScoreValue.setText(" " + animScore);
        }
        else if(animLivesScore < livesScore) {
            animLivesScore+=10;
            lblLivesValue.setText(" " + animLivesScore);
        }
        else if(animTimeScore < timeScore) {
            animTimeScore+=10;
            lblTimeValue.setText(timeMod + animTimeScore);
        }
        else if(animTimeScore > timeScore) {
            animTimeScore-=10;
            lblTimeValue.setText(timeMod + animTimeScore);
        }
        else if(animTotal < total) {
            animTotal+=10;
            lblTotalValue.setText(" " + animTotal);
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);

        //background
        game.batch.begin();
        game.batch.setProjectionMatrix(stage.getCamera().combined);
        background.update();
        background.render(game.batch);
        game.batch.end();

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
