package xyz.charliezhang.starstream.screen;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import xyz.charliezhang.starstream.Assets;
import xyz.charliezhang.starstream.GameData;
import xyz.charliezhang.starstream.entity.Entity;
import xyz.charliezhang.starstream.entity.EntityManager;
import xyz.charliezhang.starstream.music.MusicPlayer;
import xyz.charliezhang.starstream.shop.Upgrade;
import xyz.charliezhang.starstream.shop.UpgradeManager;

import static xyz.charliezhang.starstream.Config.BUTTON_SOUND_PATH;
import static xyz.charliezhang.starstream.Config.MENU_BACKGROUND_PATH;
import static xyz.charliezhang.starstream.screen.UIContainerScreen.UITable.MENU;

class ShopTable extends Table {

    private TextButton btnBack;
    private TextButton btnProfile;
    private Label lblShips;
    private Label lblUpgrades;
    private Label lblMoney;
    private long money;

    private Table playerShipsContainer;
    private int[] playerShips;
    private int currPlayerShip;
    private Label lblPlayerShip;
    private Texture[] playerShipTextures;
    private Image playerShipImg;
    private Button btnLeftShip;
    private Button btnRightShip;

    private Table upgradesContainer;
    private Label[] lblUpgrade;
    private Label[] lblUpgradeVal;
    private TextButton[] btnUpgrade;
    private ProgressBar[] upgradeBar;

    private Texture background;

    private Skin skin;

    ShopTable(final UIContainerScreen container) {
        super();

        skin = Assets.skin;
        money = GameData.prefs.getLong("money");

        //header
        lblShips = new Label("Ships", Assets.skin, "medium");
        lblShips.setAlignment(Align.center);
        lblUpgrades = new Label("Upgrades", Assets.skin, "medium");
        lblUpgrades.setAlignment(Align.center);
        lblMoney = new Label(money + "", Assets.skin, "small");
        lblMoney.setAlignment(Align.center);

        btnBack = new TextButton("Back", skin, "small");
        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                container.changeTable(MENU);
            }
        });
        btnProfile = new TextButton("Profile", skin, "small");

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
        lblPlayerShip = new Label(EntityManager.getShipName(currPlayerShip), Assets.skin, "small");

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
                lblPlayerShip.setText(EntityManager.getShipName(currPlayerShip));
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
                lblPlayerShip.setText(EntityManager.getShipName(currPlayerShip));
                event.stop();
            }
        });

        //upgrades
        upgradesContainer = new Table();
        Array<Upgrade> upgrades = UpgradeManager.getAllUpgrades();
        lblUpgrade = new Label[UpgradeManager.UpgradeTypes.length];
        lblUpgradeVal = new Label[UpgradeManager.UpgradeTypes.length];
        btnUpgrade = new TextButton[UpgradeManager.UpgradeTypes.length];
        upgradeBar = new ProgressBar[UpgradeManager.UpgradeMax.length];
        for(int i = 0; i < UpgradeManager.UpgradeTypes.length; i++) {
            String upgradeName = UpgradeManager.UpgradeTypes[i];
            long upgradeCost = UpgradeManager.getUpgradeCost(upgrades.get(i));
            int value = upgrades.get(i).getValue();

            lblUpgrade[i] = new Label(upgradeName.substring(0, 1).toUpperCase() + upgradeName.substring(1), Assets.skin, "small");
            upgradeBar[i] = new ProgressBar(0, UpgradeManager.UpgradeMax[i], 1, false, Assets.skin);
            upgradeBar[i].setValue(value);
            lblUpgradeVal[i] = new Label(value + "", Assets.skin, "small");
            btnUpgrade[i] = new TextButton("     " + upgradeCost, Assets.skin, "upgrade");
            if(upgradeCost > money) {
                btnUpgrade[i].setDisabled(true);
            }

            upgradesContainer.add(lblUpgrade[i]).width(130).padRight(5);
            upgradesContainer.add(upgradeBar[i]).expandX().fillX();
            upgradesContainer.add(lblUpgradeVal[i]).width(40).padLeft(5);
            upgradesContainer.add(btnUpgrade[i]).width(90).height(30).padLeft(5);
            if(i != UpgradeManager.UpgradeTypes.length - 1) {
                upgradesContainer.row();
            }
        }

        //background
        background = Assets.manager.get(MENU_BACKGROUND_PATH);

        //add to table
        playerShipsContainer.add(btnLeftShip).expandX().left();
        playerShipsContainer.add(playerShipImg).size(150, 150);
        playerShipsContainer.add(btnRightShip).expandX().right();
        playerShipsContainer.row();
        playerShipsContainer.add(lblPlayerShip).colspan(3).padTop(10);
        add(btnBack).left().width(100).height(65).pad(20);
        add(lblMoney).padTop(20);
        add(btnProfile).right().width(100).height(65).pad(20);
        row();
        add(lblShips).expandX().colspan(3).pad(20);
        row();
        add(playerShipsContainer).expandX().fillX().colspan(3).padLeft(20).padRight(20);
        row();
        add(lblUpgrades).colspan(3).padTop(60).padBottom(20);
        row();
        add(upgradesContainer).expandX().fill().colspan(3).padLeft(20).padRight(20);
    }

}
