package me.charliezhang.starstream.misc;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import me.charliezhang.starstream.Assets;
import me.charliezhang.starstream.MainGame;
import me.charliezhang.starstream.entity.EntityManager;
import me.charliezhang.starstream.entity.powerup.PowerUp;
import me.charliezhang.starstream.music.MusicPlayer;
import me.charliezhang.starstream.screen.UIContainerScreen;

import static me.charliezhang.starstream.Config.*;
import static me.charliezhang.starstream.entity.powerup.PowerUp.PowerUps.*;

public class HUD
{

    private EntityManager manager;
    private Stage stage;
    private Table table;
    private Table deathTable;
    private Table deathHUDTable;
    private Table iconTable;
    private HorizontalGroup livesGroup;
    private Stack masterStack;
    private Texture healthBar;
    private Texture healthFill;
    private Stack missileStack;
    private Stack attStack;
    private Label missileCount;
    private Label attTimer;
    private Image missileIcon;
    private Image shieldIcon;
    private Image attIcon;
    private Image coinIcon;
    private Image[] livesIcons;


    private TextButton btnMenu;
    private Stack pauseStack;
    private TextButton btnPause;
    private TextButton btnPlay;
    private Skin skin;
    private Label lblGameOver;
    private Label lblScore;
    private Label lblMoney;


    public HUD(final EntityManager manager)
    {
        this.manager = manager;

        stage = new Stage(new ExtendViewport(MainGame.WIDTH, MainGame.HEIGHT));
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(2f)));

        skin = Assets.skin;

        healthBar = Assets.manager.get(HEALTH_PATH, Texture.class);
        healthFill = Assets.manager.get(HEALTH_FILL_PATH, Texture.class);

        livesGroup = new HorizontalGroup();
        livesIcons = new Image[manager.getPlayer().getMaxLives()];
        for(int i = 0; i < livesIcons.length; i++)
        {
            livesIcons[i] = new Image(Assets.manager.get(LIVES_PATH, Texture.class));
            livesGroup.addActor(livesIcons[i]);
        }

        iconTable = new Table();
        missileIcon = new Image(Assets.manager.get(MIS_ICON_PATH, Texture.class));
        missileIcon.setSize(50, 50);
        shieldIcon = new Image(Assets.manager.get(SHIELD_ICON_PATH, Texture.class));
        shieldIcon.setSize(50, 50);
        shieldIcon.setVisible(false);
        attIcon = new Image(Assets.manager.get(ATT_ICON_PATH, Texture.class));
        attIcon.setSize(50, 50);
        missileCount = new Label("0", skin, "powerup-icon");
        missileCount.setSize(50, 50);
        attTimer = new Label("0", skin, "powerup-icon");

        Table missileTable = new Table();
        missileTable.bottom().right().add(missileCount).padRight(2);
        missileStack = new Stack(missileIcon, missileTable);
        missileStack.setVisible(false);

        Table attTable = new Table();
        attTable.bottom().right().add(attTimer).padRight(2);
        attStack = new Stack(attIcon, attTable);
        attStack.setVisible(false);

        table = new Table();
        deathTable = new Table();
        deathHUDTable = new Table();

        masterStack = new Stack();
        masterStack.setFillParent(true);
        stage.addActor(masterStack);

        coinIcon = new Image(Assets.manager.get(COIN_PATH, TextureAtlas.class).getTextures().first());
        coinIcon.setSize(15, 15);
        lblScore = new Label("" + manager.getScore(), skin, "small");
        lblMoney = new Label("" + manager.getMoney(), skin, "small");
        Table valueTable = new Table();
        valueTable.add(lblScore).expandX().fillX();
        valueTable.add(coinIcon).width(15).height(15).padRight(5);
        valueTable.add(lblMoney).expandX().fillX();

        lblGameOver = new Label("Game Over", skin);


        btnMenu = new TextButton("Back to Menu", skin, "medium");
        btnMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                Assets.manager.get(BUTTON_SOUND_PATH, Sound.class).play(MusicPlayer.VOLUME);
                manager.getGame().setScreen(new UIContainerScreen(manager.getGame(), manager.getBackground()));
                manager.canDispose(true);
                event.stop();
            }
        });
        btnMenu.setTouchable(Touchable.disabled);

        pauseStack = new Stack();
        btnPause = new TextButton("", Assets.skin, "pause");
        btnPause.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Assets.manager.get(BUTTON_SOUND_PATH, Sound.class).play(MusicPlayer.VOLUME);
                manager.pause();
                event.stop();
            }
        });
        btnPlay = new TextButton("", Assets.skin, "play");
        btnPlay.setVisible(false);
        btnPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                Assets.manager.get(BUTTON_SOUND_PATH, Sound.class).play(MusicPlayer.VOLUME);
                manager.pause();
                event.stop();
            }
        });

        table.add(valueTable).expandX().fillX().height(40).padLeft(5).left();
        table.add(livesGroup).right().height(40).padRight(5);
        table.row();
        iconTable.left();
        iconTable.add(shieldIcon).width(50).height(50);
        iconTable.add(missileStack).width(50).height(50);
        iconTable.add(attStack).width(50).height(50);
        table.add(iconTable).expandY().left().bottom().padLeft(5).height(50).width(150);
        table.row();
        pauseStack.add(btnPause);
        pauseStack.add(btnPlay);
        table.add(pauseStack).colspan(2).right().height(40).width(40).padRight(5).padBottom(5);

        lblGameOver.setVisible(false);
        deathHUDTable.add(lblGameOver).padBottom(30);
        deathHUDTable.row();
        deathHUDTable.add(btnMenu).width(350).height(125);
        deathTable.add(deathHUDTable).expandX().height(400);
        deathTable.addAction(Actions.alpha(0));

        masterStack.add(table);
        masterStack.add(deathTable);
    }

    public void update(float delta)
    {
        lblScore.setText("" + manager.getScore());
        lblMoney.setText("" + manager.getMoney());
        if(manager.getPlayer().getLives() != livesGroup.getChildren().size) {
            livesGroup.removeActor(livesIcons[livesGroup.getChildren().size-1]);
        }
        if(missileStack.isVisible()) {
            missileCount.setText(manager.getPlayer().getCurrMissileCount() + "");
        }
        if(attStack.isVisible()) {
            attTimer.setText(manager.getPlayer().getAttackTimeLeft() + "");
        }
        stage.act(delta);
    }

    public void render(SpriteBatch batch)
    {
        batch.end();
        stage.draw();
        float healthWidth = (manager.getPlayer().getHealth() + 0.0f)/manager.getPlayer().getMaxHealth()*(healthBar.getWidth()*3 - 12);
        batch.begin();
        batch.draw(healthBar, 5, 5, healthBar.getWidth()*3, healthBar.getHeight()*3);
        batch.draw(healthFill, 11, 11, healthWidth, healthFill.getHeight()*3);
    }

    public void dispose()
    {
        stage.dispose();
    }

    public void pause(boolean b)
    {
        if(b)
        {
            deathTable.addAction(Actions.fadeIn(0.1f));
            btnMenu.setTouchable(Touchable.enabled);
            btnPause.setVisible(false);
            btnPlay.setVisible(true);
        }
        else
        {
            deathTable.addAction(Actions.fadeOut(0.1f));
            btnMenu.setTouchable(Touchable.disabled);
            btnPause.setVisible(true);
            btnPlay.setVisible(false);
        }
    }

    public void activatePowerUp(PowerUp.PowerUps p) {
        if(p == ATTACK) {
            attStack.setVisible(true);
        } else if(p == MISSILE) {
            missileStack.setVisible(true);
        } else if(p == SHIELD) {
            shieldIcon.setVisible(true);
        }
    }

    public void deactivatePowerUp(PowerUp.PowerUps p) {
        if(p == ATTACK) {
            attStack.setVisible(false);
        } else if(p == MISSILE) {
            missileStack.setVisible(false);
        } else if(p == SHIELD) {
            shieldIcon.setVisible(false);
        }
    }

    public void win() {
        btnPause.setTouchable(Touchable.disabled);
    }

    public void death()
    {
        lblGameOver.setVisible(true);
        btnPause.setTouchable(Touchable.disabled);
        btnMenu.setTouchable(Touchable.enabled);
        deathTable.addAction(Actions.delay(2, Actions.fadeIn(1)));
    }

    public Stage getStage() {return stage;}
}
