package xyz.charliezhang.starstream.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import xyz.charliezhang.starstream.Assets;
import xyz.charliezhang.starstream.GameData;
import xyz.charliezhang.starstream.entity.EntityManager;
import xyz.charliezhang.starstream.shop.Upgrade;
import xyz.charliezhang.starstream.shop.UpgradeManager;

import static xyz.charliezhang.starstream.Config.MENU_BACKGROUND_PATH;
import static xyz.charliezhang.starstream.Config.UI_COIN_PATH;
import static xyz.charliezhang.starstream.Config.WHITE_PATH;
import static xyz.charliezhang.starstream.screen.UIContainerScreen.UITable.MENU;

class ShopTable extends Table {

    private Button btnBack;
    private Label lblShips;
    private Label lblUpgrades;
    private Label lblMoney;
    private Label filler;
    private Image coinImg;
    private Table moneyTable;
    private long money;
    private Image divider;

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
        lblShips = new Label("Ships", skin, "medium");
        lblShips.setAlignment(Align.center);
        lblUpgrades = new Label("Upgrades", skin, "medium");
        lblUpgrades.setAlignment(Align.center);
        lblMoney = new Label(money + "", skin, "small");
        lblMoney.setAlignment(Align.left);
        coinImg = new Image(Assets.manager.get(UI_COIN_PATH, Texture.class));
        coinImg.setAlign(Align.right);
        moneyTable = new Table();
        filler = new Label("", skin);
        divider = new Image(Assets.manager.get(WHITE_PATH, Texture.class));

        btnBack = new Button(skin, "back");
        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                container.changeTable(MENU);
            }
        });

        //player ships container
        currPlayerShip = GameData.prefs.getInteger("playerType");
        playerShipsContainer = new Table();
        btnLeftShip = new Button(skin, "shipLeft");
        btnRightShip = new Button(skin, "shipRight");
        playerShipTextures = new Texture[EntityManager.NUM_TYPES];
        playerShips = new int[EntityManager.NUM_TYPES];
        for(int i = 0; i < EntityManager.NUM_TYPES; i++) {
            playerShips[i] = i;
            playerShipTextures[i] = Assets.manager.get("data/ui/player" + i + ".png", Texture.class);
        }
        playerShipImg = new Image(playerShipTextures[currPlayerShip]);
        playerShipImg.setSize(150, 150);
        lblPlayerShip = new Label(EntityManager.getShipName(currPlayerShip), skin, "small");

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
        final Array<Upgrade> upgrades = UpgradeManager.getAllUpgrades();
        lblUpgrade = new Label[UpgradeManager.UpgradeTypes.length];
        lblUpgradeVal = new Label[UpgradeManager.UpgradeTypes.length];
        btnUpgrade = new TextButton[UpgradeManager.UpgradeTypes.length];
        upgradeBar = new ProgressBar[UpgradeManager.UpgradeMax.length];
        for(int i = 0; i < UpgradeManager.UpgradeTypes.length; i++) {
            final String upgradeName = UpgradeManager.UpgradeTypes[i];
            final long upgradeCost = UpgradeManager.getUpgradeCost(upgrades.get(i));
            final int value = upgrades.get(i).getValue();
            final int index = i;

            lblUpgrade[i] = new Label(upgradeName, skin, "small");
            upgradeBar[i] = new ProgressBar(0, UpgradeManager.UpgradeMax[i], 1, false, skin);
            upgradeBar[i].setValue(value);
            lblUpgradeVal[i] = new Label(value + "", skin, "small");
            btnUpgrade[i] = new TextButton("  " + upgradeCost, skin, "upgrade");
            if(upgradeCost > money || value >= UpgradeManager.UpgradeMax[i])  {
                btnUpgrade[i].setDisabled(true);
                btnUpgrade[i].setTouchable(Touchable.disabled);
            }
            btnUpgrade[i].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    int newVal = UpgradeManager.upgrade(upgradeName);
                    long newMoney = GameData.prefs.getLong("money");
                    upgradeBar[index].setValue(newVal);
                    lblUpgradeVal[index].setText(newVal + "");
                    lblMoney.setText(newMoney + "");
                    btnUpgrade[index].setText("  " + UpgradeManager.getUpgradeCost(new Upgrade(upgradeName, newVal)));
                    if(newVal >= UpgradeManager.UpgradeMax[index]) {
                        btnUpgrade[index].setDisabled(true);
                        btnUpgrade[index].setTouchable(Touchable.disabled);
                    }
                    for(int i = 0; i < upgrades.size; i++) {
                        if(UpgradeManager.getUpgradeCost(upgrades.get(i)) > newMoney) {
                            btnUpgrade[i].setDisabled(true);
                            btnUpgrade[i].setTouchable(Touchable.disabled);
                        }
                    }
                    event.stop();
                }
            });

            upgradesContainer.add(lblUpgrade[i]).expandX().fillX().padTop(10);
            upgradesContainer.row();
            upgradesContainer.add(upgradeBar[i]).expandX().fillX();
            upgradesContainer.add(lblUpgradeVal[i]).width(40).padLeft(5);
            upgradesContainer.add(btnUpgrade[i]).width(76).height(30).padLeft(5);
            if(i != UpgradeManager.UpgradeTypes.length - 1) {
                upgradesContainer.row();
            }
        }

        //background
        background = Assets.manager.get(MENU_BACKGROUND_PATH);

        //add to table
        playerShipsContainer.add(btnLeftShip).expandX().left().height(30).width(30);
        playerShipsContainer.add(playerShipImg).size(150, 150);
        playerShipsContainer.add(btnRightShip).expandX().right().height(30).width(30);
        playerShipsContainer.row();
        playerShipsContainer.add(lblPlayerShip).colspan(3).padTop(10);
        moneyTable.add(coinImg).width(15).height(15).right().padRight(5);
        moneyTable.add(lblMoney).height(30).width(lblMoney.getText().length * 20).right();
        add(btnBack).left().width(33).height(24).pad(15);
        add(filler).expandX().fillX();
        add(moneyTable).right().width(lblMoney.getText().length * 20 + 15).height(40).pad(10);
        row();
        add(divider).colspan(3).expandX().fillX().height(3);
        row();
        add(lblUpgrades).colspan(3).pad(20);
        row();
        add(upgradesContainer).expandX().fill().colspan(3).padLeft(20).padRight(20);
        row();
        add(lblShips).expandX().colspan(3).padTop(60).padBottom(20);
        row();
        add(playerShipsContainer).expandX().fillX().colspan(3).padLeft(20).padRight(20);
    }

}
