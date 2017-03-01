package xyz.charliezhang.shooter.misc;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
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
import xyz.charliezhang.shooter.entity.powerup.AttackPowerUp;
import xyz.charliezhang.shooter.entity.powerup.MissilePowerUp;
import xyz.charliezhang.shooter.entity.powerup.PowerUp;
import xyz.charliezhang.shooter.entity.powerup.ShieldPowerUp;
import xyz.charliezhang.shooter.music.MusicPlayer;
import xyz.charliezhang.shooter.screen.MenuTable;
import xyz.charliezhang.shooter.screen.UIContainerScreen;

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
    private Image pauseIcon;


    private TextButton btnMenu;
    private ImageButton btnPause;
    private Skin skin;
    private Label lblGameOver;
    private Label lblScore;


    public HUD(final EntityManager manager)
    {
        this.manager = manager;

        stage = new Stage(new ExtendViewport(MainGame.WIDTH, MainGame.HEIGHT));
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.5f)));

        skin = Assets.skin;

        healthBar = Assets.manager.get("data/textures/health.png", Texture.class);
        healthFill = Assets.manager.get("data/textures/healthFill.png", Texture.class);

        livesGroup = new HorizontalGroup();
        livesIcons = new Image[manager.getPlayer().getMaxLives()];
        for(int i = 0; i < livesIcons.length; i++)
        {
            livesIcons[i] = new Image(Assets.manager.get("data/textures/livesIcon.png", Texture.class));
            livesGroup.addActor(livesIcons[i]);
        }

        iconTable = new Table();
        missileIcon = new Image(Assets.manager.get("data/textures/misicon.png", Texture.class));
        missileIcon.setSize(50, 50);
        shieldIcon = new Image(Assets.manager.get("data/textures/shieldicon.png", Texture.class));
        shieldIcon.setSize(50, 50);
        attIcon = new Image(Assets.manager.get("data/textures/atticon.png", Texture.class));
        attIcon.setSize(50, 50);

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
                Assets.manager.get("data/sounds/button.mp3", Sound.class).play(MusicPlayer.VOLUME);
                manager.getGame().setScreen(new UIContainerScreen(manager.getGame()));
                manager.canDispose(true);
                event.stop();
            }
        });
        btnMenu.setTouchable(Touchable.disabled);

        pauseIcon = new Image(Assets.manager.get("data/textures/pause.png", Texture.class));
        btnPause = new ImageButton(pauseIcon.getDrawable());
        btnPause.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Assets.manager.get("data/sounds/button.mp3", Sound.class).play(MusicPlayer.VOLUME);
                manager.pause();
                event.stop();
            }
        });

        table.add(lblScore).expandX().fillX().height(40).padLeft(5).left();
        table.add(livesGroup).right().width(120).height(40);
        table.row();
        table.add(iconTable).expandY().left().bottom().padLeft(5).height(50).width(150);
        table.row();
        table.add(btnPause).colspan(2).right().height(54).width(60);

        lblGameOver.setVisible(false);
        deathHUDTable.add(lblGameOver).padBottom(30);
        deathHUDTable.row();
        deathHUDTable.add(btnMenu).width(350).height(125);
        deathTable.add(deathHUDTable).expandX().height(400);
        deathTable.addAction(Actions.alpha(0));

        masterStack.add(table);
        masterStack.add(deathTable);

        table.debug();
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
        }
        else
        {
            deathTable.addAction(Actions.fadeOut(0.1f));
            btnMenu.setTouchable(Touchable.disabled);
        }
    }

    public void activatePowerUp(PowerUp.PowerUps p) {
        if(p == ATTACK) {
            iconTable.add(attIcon).width(50).height(50).left();
        } else if(p == MISSILE) {
            iconTable.add(missileIcon).width(50).height(50).left();
        } else if(p == SHIELD) {
            iconTable.add(shieldIcon).width(50).height(50).left();
        }
    }

    public void deactivatePowerUp(PowerUp.PowerUps p) {
        System.out.println(p);
        if(p == ATTACK) {
            iconTable.removeActor(attIcon);
        } else if(p == MISSILE) {
            iconTable.removeActor(missileIcon);
        } else if(p == SHIELD) {
            iconTable.removeActor(shieldIcon);
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
