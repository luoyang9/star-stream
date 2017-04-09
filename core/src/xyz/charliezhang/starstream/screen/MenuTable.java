package xyz.charliezhang.starstream.screen;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import xyz.charliezhang.starstream.Assets;
import xyz.charliezhang.starstream.music.MusicPlayer;

import static xyz.charliezhang.starstream.screen.UIContainerScreen.UITable.*;

public class MenuTable extends Table {

    private TextButton btnPlay;
    private TextButton btnShop;
    private TextButton btnProfile;
    private TextButton btnOptions;

    public MenuTable(final UIContainerScreen container){

        if(!MusicPlayer.isPlaying(MusicPlayer.MENU)) {
            MusicPlayer.loop(MusicPlayer.MENU);
        }

        btnPlay = new TextButton("Story", Assets.skin);
        btnShop = new TextButton("Shop", Assets.skin);
        btnProfile = new TextButton("Profile", Assets.skin);
        btnOptions = new TextButton("", Assets.skin, "options");

        btnPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                container.changeTable(LEVELSELECT);
            }
        });

        btnShop.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                container.changeTable(SHOP);
            }
        });

        btnOptions.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                container.changeTable(OPTIONS);
            }
        });

        row();
        add(btnPlay).pad(20).width(336).height(120);
        row();
        add(btnShop).pad(20).width(336).height(120);
        row();
        add(btnProfile).pad(20).width(336).height(120);
        row();
        add(btnOptions).left().padLeft(20).width(30).height(30);

        //setDebug(true);
    }
}
