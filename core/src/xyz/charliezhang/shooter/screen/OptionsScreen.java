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
import xyz.charliezhang.shooter.music.MusicPlayer;

/**
 * Created by Charlie on 2015-12-19.
 */
public class OptionsScreen implements Screen {

    private MainGame game;

    private Stage stage;
    private Table table;

    private CheckBox soundOn;
    private TextButton btnBack;
    private Label lblSound;

    private Texture background;

    private Skin skin;

    public OptionsScreen(MainGame game){this.game = game;}

    @Override
    public void show() {
        stage = new Stage();
        stage.setViewport(new ExtendViewport(MainGame.WIDTH, MainGame.HEIGHT));
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        skin = Assets.skin;

        soundOn = new CheckBox(" Sound On", skin);
        soundOn.getCells().get(0).size(50, 50);
        soundOn.setChecked(!GameData.soundOn());
        soundOn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                if(soundOn.isChecked())
                {
                    MusicPlayer.mute();
                    GameData.updateSoundOn(false);
                }
                else
                {
                    MusicPlayer.unmute("menu");
                    GameData.updateSoundOn(true);
                }
                event.stop();
            }
        });

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
        table.add(soundOn);
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
