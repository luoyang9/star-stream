package xyz.charliezhang.starstream.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import xyz.charliezhang.starstream.Assets;
import xyz.charliezhang.starstream.GameData;
import xyz.charliezhang.starstream.entity.EntityManager;

import static xyz.charliezhang.starstream.Config.MENU_BACKGROUND_PATH;
import static xyz.charliezhang.starstream.screen.UIContainerScreen.UITable.MENU;

class ShopTable extends Table {

    private Table playerTypesTable;

    private TextButton btnBack;
    private CheckBox[] btnPlayerTypes;
    private ButtonGroup<CheckBox> btnPlayerTypesGroup;

    private Texture background;

    private Skin skin;

    ShopTable(final UIContainerScreen container) {
        super();

        playerTypesTable = new Table();

        skin = Assets.skin;

        btnPlayerTypes = new CheckBox[EntityManager.NUM_TYPES];
        btnPlayerTypesGroup = new ButtonGroup<CheckBox>();
        btnPlayerTypesGroup.setMaxCheckCount(1);

        for(int i = 0; i < EntityManager.NUM_TYPES; i++)
        {
            final int currType = i + 1;
            btnPlayerTypes[i] = new CheckBox(EntityManager.getShipName(currType), skin, "player" + i);
            btnPlayerTypes[i].getCells().get(0).size(50, 50).padRight(15);
            if(GameData.prefs.getBoolean("type-" + currType)) {
                if (GameData.prefs.getInteger("playerType") == currType) btnPlayerTypes[i].setChecked(true);
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
                    GameData.prefs.putInteger("playerType", currType).flush();
                }
            });

            btnPlayerTypesGroup.add(btnPlayerTypes[i]);
            playerTypesTable.add(btnPlayerTypes[i]).width(200).height(200);
            playerTypesTable.row();
        }

        btnBack = new TextButton("Back", skin);
        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                container.changeTable(MENU);
            }
        });

        background = Assets.manager.get(MENU_BACKGROUND_PATH);


        add(btnBack).width(450).height(200);
        row();
        add(playerTypesTable);
        row();
        //setDebug(true);
    }

}
