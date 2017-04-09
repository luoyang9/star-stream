package xyz.charliezhang.starstream.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import xyz.charliezhang.starstream.Assets;
import xyz.charliezhang.starstream.GameData;
import xyz.charliezhang.starstream.entity.EntityManager;

import static xyz.charliezhang.starstream.Config.MENU_BACKGROUND_PATH;
import static xyz.charliezhang.starstream.screen.UIContainerScreen.UITable.MENU;

class ShopTable extends Table {

    private Table playerTypesTable;

    private TextButton btnBack;
    private TextButton btnProfile;
    private CheckBox[] btnPlayerTypes;
    private ButtonGroup<CheckBox> btnPlayerTypesGroup;

    private Label lblShips;
    private Label lblUpgrades;
    private Label lblMoney;

    private Texture background;

    private Skin skin;

    ShopTable(final UIContainerScreen container) {
        super();

        playerTypesTable = new Table();

        skin = Assets.skin;

        lblShips = new Label("Ships", Assets.skin, "medium");
        lblShips.setAlignment(Align.center);
        lblUpgrades = new Label("Upgrades", Assets.skin, "medium");
        lblUpgrades.setAlignment(Align.center);
        lblMoney = new Label(GameData.prefs.getLong("money") + "", Assets.skin, "small");
        lblMoney.setAlignment(Align.center);
        btnPlayerTypes = new CheckBox[EntityManager.NUM_TYPES];
        btnPlayerTypesGroup = new ButtonGroup<CheckBox>();
        btnPlayerTypesGroup.setMaxCheckCount(1);

        for(int i = 0; i < EntityManager.NUM_TYPES; i++)
        {
            final int currType = i + 1;
            btnPlayerTypes[i] = new CheckBox(EntityManager.getShipName(currType), skin, "player" + i);
            btnPlayerTypes[i].getCells().get(0).size(75, 75).padRight(15);
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
            playerTypesTable.add(btnPlayerTypes[i]).width(200).height(100);
            playerTypesTable.row();
        }

        btnBack = new TextButton("Back", skin, "small");
        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                container.changeTable(MENU);
            }
        });

        background = Assets.manager.get(MENU_BACKGROUND_PATH);

        add(btnBack).left().width(100).height(65).padLeft(20).padTop(20);
        add(lblMoney).padTop(20);
        add(btnProfile).right().width(100).height(65).padRight(20).padTop(20);
        row();
        add(lblShips).expandX().colspan(3).padTop(20);
        row();
        add(playerTypesTable).expandX().colspan(3).padLeft(20).padRight(20);
        row();
        add(lblUpgrades).colspan(3).padTop(20);
        setDebug(true);
    }

}
