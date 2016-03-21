package xyz.charliezhang.shooter.entity;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import xyz.charliezhang.shooter.Assets;
import xyz.charliezhang.shooter.screen.MenuScreen;

/**
 * Created by Charlie on 2015-12-19.
 */
public class HUD
{

    private EntityManager manager;
    private Stage stage;
    private Table table;
    private Table deathTable;
    private Table deathHUDTable;
    private HorizontalGroup iconGroup;;
    private HorizontalGroup livesGroup;
    private Stack stack;
    private Stack masterStack;
    private Image healthBar;
    private Image healthFill;
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

        stage = new Stage(new ScreenViewport());

        skin = new Skin();
        skin.addRegions(Assets.manager.get("data/ui/uiskin.atlas", TextureAtlas.class));
        skin.add("default-font", Assets.manager.get("hud.ttf"));
        skin.add("small-font", Assets.manager.get("levelSelect.ttf"));
        skin.load(Gdx.files.internal("data/ui/uiskin.json"));

        healthBar = new Image(Assets.manager.get("data/textures/health.png", Texture.class));
        healthFill = new Image(Assets.manager.get("data/textures/healthFill.png", Texture.class));

        livesGroup = new HorizontalGroup();
        livesIcons = new Image[manager.getPlayer().getMaxLives()];
        for(int i = 0; i < livesIcons.length; i++)
        {
            livesIcons[i] = new Image(Assets.manager.get("data/textures/livesIcon.png", Texture.class));
            livesGroup.addActor(livesIcons[i]);
        }

        iconGroup = new HorizontalGroup();
        missileIcon = new Image(Assets.manager.get("data/textures/misicon.png", Texture.class));
        missileIcon.setWidth(20);
        missileIcon.setVisible(false);
        shieldIcon = new Image(Assets.manager.get("data/textures/shieldicon.png", Texture.class));
        shieldIcon.setWidth(20);
        shieldIcon.setVisible(false);
        attIcon = new Image(Assets.manager.get("data/textures/atticon.png", Texture.class));
        attIcon.setWidth(20);
        attIcon.setVisible(false);

        table = new Table();
        stack = new Stack();
        deathTable = new Table();
        deathHUDTable = new Table();

        masterStack = new Stack();
        masterStack.setFillParent(true);
        stage.addActor(masterStack);

        lblScore = new Label("" + manager.getScore(), skin);
        lblGameOver = new Label("Game Over", skin);


        btnMenu = new TextButton("Back to Menu", skin);
        btnMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                dispose();
                System.out.println("START MENUSCREEN   ");
                manager.getGame().setScreen(new MenuScreen(manager.getGame()));
                event.stop();
            }
        });

        pauseIcon = new Image(manager.getGame().manager.get("data/textures/pause.png", Texture.class));
        btnPause = new ImageButton(pauseIcon.getDrawable());

        table.top().left();
        table.add(lblScore).padLeft(5).expandX().left();
        table.add(livesGroup).padRight(5).right();
        table.row();
        iconGroup.addActor(missileIcon);
        iconGroup.addActor(shieldIcon);
        iconGroup.addActor(attIcon);
        table.add(iconGroup).fillX().expandY().left().bottom();
        table.row();
        stack.add(healthFill);
        stack.add(healthBar);
        table.add(stack).height(30).expandX().left().bottom();
        table.add(btnPause).height(30).right().bottom();

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

        healthFill.setWidth((manager.getPlayer().getHealth() + 0.0f)/manager.getPlayer().getMaxHealth()*healthBar.getWidth());

        if(manager.getPlayer().getMissileTask().isScheduled())
        {
            missileIcon.setVisible(true);
        }
        else
        {
            missileIcon.setVisible(false);
        }

        if(manager.getPlayer().isShieldOn())
        {
            shieldIcon.setVisible(true);
        }
        else
        {
            shieldIcon.setVisible(false);
        }

        if(manager.getPlayer().isSuperAttOn())
        {
            attIcon.setVisible(true);
        }
        else
        {
            attIcon.setVisible(false);
        }
        stage.act(delta);
    }

    public void render(SpriteBatch batch)
    {
        batch.end();
        stage.draw();
        batch.begin();
    }

    public void dispose()
    {
        stage.dispose();
    }

    public void death()
    {
        deathTable.addAction(Actions.delay(2, Actions.fadeIn(1)));
        Gdx.input.setInputProcessor(stage);
    }
}
