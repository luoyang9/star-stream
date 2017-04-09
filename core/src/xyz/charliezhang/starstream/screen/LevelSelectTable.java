package xyz.charliezhang.starstream.screen;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import xyz.charliezhang.starstream.Assets;
import xyz.charliezhang.starstream.GameData;
import xyz.charliezhang.starstream.MainGame;

import static xyz.charliezhang.starstream.screen.UIContainerScreen.UITable.MENU;

class LevelSelectTable extends Table {

    private Table lvlTable;
    private Table[] scoreTables;

    private TextButton btnBack;
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

        btnBack = new TextButton("BACK", Assets.skin);
        btnBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                container.changeTable(MENU);
            }
        });

        int count = 0;
        for(int i = 0; i < MainGame.NUM_LEVELS; i++)
        {
            final int level = i;
            TextButton btnTemp = new TextButton("" + (i + 1), Assets.skin);
            btnTemp.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y){
                    container.play(level);
                }
            });
            scoreTables[i].add(btnTemp).width(140).height(100);
            scoreTables[i].row();
            scoreTables[i].add(lblScore[i]).height(50);
            lvlTable.add(scoreTables[i]).height(150).width(140).pad(5);
            count++;
            if(count % 3 == 0) lvlTable.row();
        }

        add(btnBack).width(450).height(200);
        row();
        add(lvlTable);

        //setDebug(true);
    }
}
