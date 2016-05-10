package xyz.charliezhang.shooter.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import xyz.charliezhang.shooter.Assets;
import xyz.charliezhang.shooter.GameData;
import xyz.charliezhang.shooter.MainGame;
import xyz.charliezhang.shooter.entity.EntityManager;
import xyz.charliezhang.shooter.music.MusicPlayer;

/**
 * Created by Charlie on 2015-12-19.
 */
public class ShopScreen implements Screen {

    private MainGame game;

    private Stage stage;
    private Table table;

    private Table playerTypesTable;

    private TextButton btnBack;
    private CheckBox[] btnPlayerTypes;
    private ButtonGroup<CheckBox> btnPlayerTypesGroup;

    private Texture background;

    private Skin skin;

    public ShopScreen(MainGame game){this.game = game;}

    @Override
    public void show() {
        stage = new Stage();
        stage.setViewport(new ExtendViewport(MainGame.WIDTH, MainGame.HEIGHT));
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        playerTypesTable = new Table();

        skin = Assets.skin;

        btnPlayerTypes = new CheckBox[EntityManager.NUM_TYPES];
        btnPlayerTypesGroup = new ButtonGroup<CheckBox>();
        btnPlayerTypesGroup.setMaxCheckCount(1);

        for(int i = 0; i < EntityManager.NUM_TYPES; i++)
        {
            final int currType = i;
            btnPlayerTypes[i] = new CheckBox(EntityManager.getShipName(i), skin, "player" + i);
            btnPlayerTypes[i].getCells().get(0).size(50, 50);
            if(GameData.getAvailableTypes()[i]) {
                if (GameData.getPlayerType() == i) btnPlayerTypes[i].setChecked(true);
                else btnPlayerTypes[i].setChecked(false);
            }
            else
            {
                btnPlayerTypes[i].setDisabled(true);
            }

            btnPlayerTypes[i].addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y)
                {
                    GameData.setPlayerType(currType);
                }
            });

            btnPlayerTypesGroup.add(btnPlayerTypes[i]);
            playerTypesTable.add(btnPlayerTypes[i]).width(200).height(200);
            playerTypesTable.row();
        }
        System.out.println(btnPlayerTypesGroup.getChecked().equals(btnPlayerTypes[0]));

        btnBack = new TextButton("Back", skin);
        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                Assets.manager.get("data/sounds/button.mp3", Sound.class).play(MusicPlayer.VOLUME);
                game.setScreen(new MenuScreen(game));
                dispose();
                event.stop();
            }
        });

        background = Assets.manager.get("data/ui/background.png");


        table.add(btnBack).width(450).height(200);
        table.row();
        table.add(playerTypesTable);
        table.row();
        //table.setDebug(true);

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
