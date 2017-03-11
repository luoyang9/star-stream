package xyz.charliezhang.shooter.misc;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import xyz.charliezhang.shooter.Assets;
import xyz.charliezhang.shooter.MainGame;
import xyz.charliezhang.shooter.entity.EntityManager;
import xyz.charliezhang.shooter.entity.powerup.PowerUp;
import xyz.charliezhang.shooter.music.MusicPlayer;
import xyz.charliezhang.shooter.screen.UIContainerScreen;

import static xyz.charliezhang.shooter.Config.*;
import static xyz.charliezhang.shooter.entity.powerup.PowerUp.PowerUps.*;

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
    private Image missileIcon;
    private Image shieldIcon;
    private Image attIcon;
    private Image[] livesIcons;


    private TextButton btnMenu;
    private Stack pauseStack;
    private TextButton btnPause;
    private TextButton btnPlay;
    private Skin skin;
    private Label lblGameOver;
    private Label lblScore;


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
        missileIcon.setVisible(false);
        shieldIcon = new Image(Assets.manager.get(SHIELD_ICON_PATH, Texture.class));
        shieldIcon.setSize(50, 50);
        shieldIcon.setVisible(false);
        attIcon = new Image(Assets.manager.get(ATT_ICON_PATH, Texture.class));
        attIcon.setSize(50, 50);
        attIcon.setVisible(false);

        table = new Table();
        deathTable = new Table();
        deathHUDTable = new Table();

        masterStack = new Stack();
        masterStack.setFillParent(true);
        stage.addActor(masterStack);

        lblScore = new Label("" + manager.getScore(), skin, "medium");
        lblGameOver = new Label("Game Over", skin);


        btnMenu = new TextButton("Back to Menu", skin, "medium");
        btnMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                Assets.manager.get(BUTTON_SOUND_PATH, Sound.class).play(MusicPlayer.VOLUME);
                manager.getGame().setScreen(new UIContainerScreen(manager.getGame()));
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

        table.add(lblScore).expandX().fillX().height(40).padLeft(5).left();
        table.add(livesGroup).right().width(120).height(40);
        table.row();
        iconTable.left();
        iconTable.add(shieldIcon).width(50).height(50);;
        iconTable.add(missileIcon).width(50).height(50);;
        iconTable.add(attIcon).width(50).height(50);;
        table.add(iconTable).expandY().left().bottom().padLeft(5).height(50).width(150);
        table.row();
        pauseStack.add(btnPause);
        pauseStack.add(btnPlay);
        table.add(pauseStack).colspan(2).right().height(40).width(40).padRight(10).padBottom(10);

        lblGameOver.setVisible(false);
        deathHUDTable.add(lblGameOver).padBottom(30);
        deathHUDTable.row();
        deathHUDTable.add(btnMenu).width(350).height(125);
        deathTable.add(deathHUDTable).expandX().height(400);
        deathTable.addAction(Actions.alpha(0));

        masterStack.add(table);
        masterStack.add(deathTable);

        //table.debug();
        //deathTable.debug();
    }

    public void update(float delta)
    {
        lblScore.setText("" + manager.getScore());
        if(manager.getPlayer().getLives() != livesGroup.getChildren().size)
        {
            livesGroup.removeActor(livesIcons[livesGroup.getChildren().size-1]);
        }
        stage.act(delta);
    }

    public void render(SpriteBatch batch)
    {
        batch.end();
        stage.draw();
        float healthWidth = (manager.getPlayer().getHealth() + 0.0f)/manager.getPlayer().getMaxHealth()*healthFill.getWidth();
        batch.begin();
        batch.draw(healthBar, 5, 5, healthBar.getWidth(), healthBar.getHeight());
        batch.draw(healthFill, 15, 10, healthWidth, healthFill.getHeight());
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
            attIcon.setVisible(true);
        } else if(p == MISSILE) {
            missileIcon.setVisible(true);
        } else if(p == SHIELD) {
            shieldIcon.setVisible(true);
        }
    }

    public void deactivatePowerUp(PowerUp.PowerUps p) {
        System.out.println(p);
        if(p == ATTACK) {
            attIcon.setVisible(false);
        } else if(p == MISSILE) {
            missileIcon.setVisible(false);
        } else if(p == SHIELD) {
            shieldIcon.setVisible(false);
        }
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
