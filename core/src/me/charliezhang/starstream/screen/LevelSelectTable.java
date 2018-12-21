package me.charliezhang.starstream.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import me.charliezhang.starstream.Assets;
import me.charliezhang.starstream.GameData;
import me.charliezhang.starstream.MainGame;

import static me.charliezhang.starstream.Config.WHITE_PATH;
import static me.charliezhang.starstream.screen.UIContainerScreen.UITable.MENU;

class LevelSelectTable extends Table {

    private Table lvlTable;
    private Table[] scoreTables;
    private Image divider;

    private Button btnBack;
    private Label[] lblScore;

    LevelSelectTable(final UIContainerScreen container) {

        lvlTable = new Table();

        scoreTables = new Table[MainGame.NUM_LEVELS];
        lblScore = new Label[MainGame.NUM_LEVELS];
        for(int i = 0; i < scoreTables.length; i++)
        {
            scoreTables[i] = new Table();
            lblScore[i] = new Label("BEST: " + GameData.prefs.getInteger("level-" + (i+1)), Assets.skin, "small");
        }

        divider = new Image(Assets.manager.get(WHITE_PATH, Texture.class));
        btnBack = new Button(Assets.skin, "back");
        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                container.changeTable(MENU);
            }
        });

        int count = 0;
        for(int i = 0; i < MainGame.NUM_LEVELS; i++)
        {
            final int level = i;
            final TextButton btnTemp = new TextButton("" + (i + 1), Assets.skin);
            btnTemp.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y){
                    container.play(level);
                    btnTemp.setTouchable(Touchable.disabled);
                }
            });
            if(i >= 3) {
                btnTemp.setTouchable(Touchable.disabled);
                lblScore[i].setText("Upcoming");
            }
            scoreTables[i].add(btnTemp).width(140).height(100);
            scoreTables[i].row();
            scoreTables[i].add(lblScore[i]).height(50);
            lvlTable.add(scoreTables[i]).height(150).width(140).pad(5);
            count++;
            if(count % 3 == 0) lvlTable.row();
        }

        top();
        add(btnBack).left().width(33).height(24).pad(15);
        row();
        add(divider).colspan(3).expandX().fillX().height(3);
        row();
        add(lvlTable).pad(10).expand().fill().align(Align.center);

        //setDebug(true);
    }
}
