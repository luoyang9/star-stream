package xyz.charliezhang.shooter.screen;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import xyz.charliezhang.shooter.Assets;
import xyz.charliezhang.shooter.music.MusicPlayer;

import static xyz.charliezhang.shooter.screen.UIContainerScreen.UITable.*;

public class MenuTable extends Table {

    private TextButton btnPlay;
    private TextButton btnShop;
    private TextButton btnOptions;

    public MenuTable(final UIContainerScreen container){

        if(!MusicPlayer.isPlaying(MusicPlayer.MENU)) {
            MusicPlayer.loop(MusicPlayer.MENU);
        }

        btnPlay = new TextButton("Play", Assets.skin);
        btnShop = new TextButton("Shop", Assets.skin);
        btnOptions = new TextButton("Options", Assets.skin);

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

        add(btnPlay).pad(20).width(384).height(160);
        row();
        add(btnShop).pad(20).width(384).height(160);
        row();
        add(btnOptions).pad(20).width(384).height(160);

        //setDebug(true);
    }
}
