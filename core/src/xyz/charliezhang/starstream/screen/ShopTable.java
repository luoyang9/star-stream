package xyz.charliezhang.starstream.screen;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import xyz.charliezhang.starstream.Assets;
import xyz.charliezhang.starstream.GameData;
import xyz.charliezhang.starstream.entity.EntityManager;
import xyz.charliezhang.starstream.music.MusicPlayer;

import static xyz.charliezhang.starstream.Config.BUTTON_SOUND_PATH;
import static xyz.charliezhang.starstream.Config.MENU_BACKGROUND_PATH;
import static xyz.charliezhang.starstream.screen.UIContainerScreen.UITable.MENU;

class ShopTable extends Table {

    private Table playerTypesTable;

    private TextButton btnBack;
    private TextButton btnProfile;

    private Table playerShipsContainer;
    private int[] playerShips;
    private int currPlayerShip;
    private Texture[] playerShipTextures;
    private Image playerShipImg;
    private Button btnLeftShip;
    private Button btnRightShip;

    private Label lblShips;
    private Label lblUpgrades;
    private Label lblMoney;

    private Texture background;

    private Skin skin;

    ShopTable(final UIContainerScreen container) {
        super();

        playerTypesTable = new Table();

        skin = Assets.skin;

        //player ships container
        currPlayerShip = GameData.prefs.getInteger("playerType");
        playerShipsContainer = new Table();
        btnLeftShip = new Button(Assets.skin, "shipLeft");
        btnRightShip = new Button(Assets.skin, "shipRight");
        playerShipTextures = new Texture[EntityManager.NUM_TYPES];
        playerShips = new int[EntityManager.NUM_TYPES];
        for(int i = 0; i < EntityManager.NUM_TYPES; i++) {
            playerShips[i] = i;
            playerShipTextures[i] = Assets.manager.get("data/textures/player" + i + ".png", Texture.class);
        }
        playerShipImg = new Image(playerShipTextures[currPlayerShip]);
        playerShipImg.setSize(150, 150);
        playerShipsContainer.add(btnLeftShip).expandX().left();
        playerShipsContainer.add(playerShipImg).size(150, 150);
        playerShipsContainer.add(btnRightShip).expandX().right();
//        GameData.prefs.putInteger("playerType", currType).flush();

        btnLeftShip.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(currPlayerShip == 0) {
                    currPlayerShip = EntityManager.NUM_TYPES - 1;
                } else {
                    currPlayerShip--;
                }
                if(GameData.prefs.getBoolean("type-" + currPlayerShip)) {
                    GameData.prefs.putInteger("playerType", currPlayerShip).flush();
                }
                playerShipImg.setDrawable(new SpriteDrawable(new Sprite(playerShipTextures[currPlayerShip])));
                event.stop();
            }
        });

        btnRightShip.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(currPlayerShip == EntityManager.NUM_TYPES - 1) {
                    currPlayerShip = 0;
                } else {
                    currPlayerShip++;
                }
                if(GameData.prefs.getBoolean("type-" + currPlayerShip)) {
                    GameData.prefs.putInteger("playerType", currPlayerShip).flush();
                }
                playerShipImg.setDrawable(new SpriteDrawable(new Sprite(playerShipTextures[currPlayerShip])));
                event.stop();
            }
        });

        lblShips = new Label("Ships", Assets.skin, "medium");
        lblShips.setAlignment(Align.center);
        lblUpgrades = new Label("Upgrades", Assets.skin, "medium");
        lblUpgrades.setAlignment(Align.center);
        lblMoney = new Label(GameData.prefs.getLong("money") + "", Assets.skin, "small");
        lblMoney.setAlignment(Align.center);

        btnBack = new TextButton("Back", skin, "small");
        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                container.changeTable(MENU);
            }
        });
        btnProfile = new TextButton("Profile", skin, "small");

        background = Assets.manager.get(MENU_BACKGROUND_PATH);

        add(btnBack).left().width(100).height(65).pad(20);
        add(lblMoney).padTop(20);
        add(btnProfile).right().width(100).height(65).pad(20);
        row();
        add(lblShips).expandX().colspan(3).pad(20);
        row();
        add(playerShipsContainer).expandX().colspan(3).padLeft(20).padRight(20);
        row();
        add(lblUpgrades).colspan(3).pad(20);
    }

}
