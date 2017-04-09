package xyz.charliezhang.starstream.screen;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import xyz.charliezhang.starstream.Assets;
import xyz.charliezhang.starstream.GameData;
import xyz.charliezhang.starstream.music.MusicPlayer;

import static xyz.charliezhang.starstream.screen.UIContainerScreen.UITable.MENU;

class OptionsTable extends Table {

    private CheckBox soundOn;
    private TextButton btnBack;

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

        btnBack = new TextButton("Back", Assets.skin);
        btnBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                container.changeTable(MENU);
            }
        });

        add(btnBack).width(450).height(200);
        row();
        add(soundOn);
        row();
        //setDebug(true);
    }
}
