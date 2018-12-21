package me.charliezhang.starstream.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import me.charliezhang.starstream.Assets;
import me.charliezhang.starstream.GameData;
import me.charliezhang.starstream.music.MusicPlayer;

import static me.charliezhang.starstream.Config.WHITE_PATH;
import static me.charliezhang.starstream.screen.UIContainerScreen.UITable.MENU;

class OptionsTable extends Table {

    private CheckBox soundOn;
    private Button btnBack;
    private Image divider;

    OptionsTable(final UIContainerScreen container) {

        soundOn = new CheckBox(" Sound On", Assets.skin);
        soundOn.getCells().get(0).size(50, 50).padRight(5);
        soundOn.setChecked(!GameData.prefs.getBoolean("soundOn", true));
        soundOn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                if(soundOn.isChecked())
                {
                    MusicPlayer.mute();
                    GameData.prefs.putBoolean("soundOn", false).flush();
                }
                else
                {
                    MusicPlayer.unmute(MusicPlayer.MENU);
                    GameData.prefs.putBoolean("soundOn", true).flush();
                }
                event.stop();
            }
        });

        divider = new Image(Assets.manager.get(WHITE_PATH, Texture.class));
        btnBack = new Button(Assets.skin, "back");
        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                container.changeTable(MENU);
            }
        });

        top();
        add(btnBack).left().width(33).height(24).pad(15);
        row();
        add(divider).colspan(3).expandX().fillX().height(3);
        row();
        add(soundOn).pad(10).expand().fill().align(Align.center);
    }
}
