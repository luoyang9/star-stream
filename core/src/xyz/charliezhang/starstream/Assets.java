package xyz.charliezhang.starstream;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


public class Assets {
    public static final AssetManager manager = new AssetManager();
    public static Skin skin;

    public static void init() {
        FileHandleResolver resolver = new InternalFileHandleResolver();
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
    }

    public static void load() {
        //game background
        manager.load(Config.GAME_BACKGROUND_PATH, Texture.class);
        //ui
        manager.load(Config.UI_SKIN_PATH, TextureAtlas.class);
        manager.load(Config.PLAYER_BLUE_UI_PATH, Texture.class);
        manager.load(Config.PLAYER_RED_UI_PATH, Texture.class);
        manager.load(Config.UI_COIN_PATH, Texture.class);
        manager.load(Config.WHITE_PATH, Texture.class);
        //health
        manager.load(Config.HEALTH_PATH, Texture.class);
        manager.load(Config.HEALTH_FILL_PATH, Texture.class);
        manager.load(Config.LIVES_PATH, Texture.class);
        //coin
        manager.load(Config.COIN_PATH, TextureAtlas.class);
        //powerup icons
        manager.load(Config.MIS_ICON_PATH, Texture.class);
        manager.load(Config.SHIELD_ICON_PATH, Texture.class);
        manager.load(Config.ATT_ICON_PATH, Texture.class);
        //powerup effects
        manager.load(Config.SHIELD_PATH, Texture.class);
        manager.load(Config.MISSILE_PATH, TextureAtlas.class);
        //powerup drops
        manager.load(Config.ATT_POWERUP_PATH, TextureAtlas.class);
        manager.load(Config.MIS_POWERUP_PATH, TextureAtlas.class);
        manager.load(Config.SHIELD_POWERUP_PATH, TextureAtlas.class);
        //enemies
        manager.load(Config.UFO_PATH, TextureAtlas.class);
        manager.load(Config.ICARUS_PATH, TextureAtlas.class);
        manager.load(Config.SKULLINATOR_PATH, TextureAtlas.class);
        manager.load(Config.STRIKER_PATH, TextureAtlas.class);
        manager.load(Config.KAMIKAZE_PATH, TextureAtlas.class);
        manager.load(Config.VALKYRIE_PATH, TextureAtlas.class);
        manager.load(Config.FALCON_PATH, TextureAtlas.class);
        manager.load(Config.ASTEROID_1_PATH, Texture.class);
        manager.load(Config.ASTEROID_2_PATH, Texture.class);
        manager.load(Config.ASTEROID_3_PATH, Texture.class);
        manager.load(Config.ASTEROID_4_PATH, Texture.class);
        //explosions
        manager.load(Config.PLAYER_EXPLOSION_PATH, TextureAtlas.class);
        manager.load(Config.PLAYER_EXPLOSION_R_PATH, TextureAtlas.class);
        manager.load(Config.EXPLOSION_PATH, TextureAtlas.class);
        //lasers
        manager.load(Config.LASER_B_PATH, TextureAtlas.class);
        manager.load(Config.LASER_F_PATH, TextureAtlas.class);
        manager.load(Config.LASER_G_PATH, TextureAtlas.class);
        manager.load(Config.LASER_R_PATH, TextureAtlas.class);
        manager.load(Config.LASER_O_PATH, TextureAtlas.class);
        //player
        manager.load(Config.PLAYER_BLUE_PATH, TextureAtlas.class);
        manager.load(Config.PLAYER_RED_PATH, TextureAtlas.class);
        //music
        manager.load(Config.GAME_MUSIC_PATH, Music.class);
        manager.load(Config.MENU_MUSIC_PATH, Music.class);
        manager.load(Config.WIN_MUSIC_PATH, Music.class);
        //sound effects
        manager.load(Config.SHOOT_SOUND_PATH, Sound.class);
        manager.load(Config.SHIELD_DOWN_SOUND_PATH, Sound.class);
        manager.load(Config.SHIELD_UP_SOUND_PATH, Sound.class);
        manager.load(Config.BUTTON_SOUND_PATH, Sound.class);
        manager.load(Config.EXPLOSION_SOUND_PATH, Sound.class);

        //game fonts
        FreeTypeFontLoaderParameter params = new FreeTypeFontLoaderParameter();
        params.fontFileName = "data/pixel.ttf";
        params.fontParameters.size = 50;
        params.fontParameters.color = Color.WHITE;
        manager.load("xlarge.ttf", BitmapFont.class, params);

        FreeTypeFontLoaderParameter params2 = new FreeTypeFontLoaderParameter();
        params2.fontFileName = "data/pixel.ttf";
        params2.fontParameters.size = 40;
        params2.fontParameters.color = Color.WHITE;
        manager.load("large.ttf", BitmapFont.class, params2);

        FreeTypeFontLoaderParameter params3 = new FreeTypeFontLoaderParameter();
        params3.fontFileName = "data/pixel.ttf";
        params3.fontParameters.size = 30;
        params3.fontParameters.color = Color.WHITE;
        manager.load("medium.ttf", BitmapFont.class, params3);

        FreeTypeFontLoaderParameter params4 = new FreeTypeFontLoaderParameter();
        params4.fontFileName = "data/pixel.ttf";
        params4.fontParameters.size = 20;
        params4.fontParameters.color = Color.WHITE;
        manager.load("small.ttf", BitmapFont.class, params4);

        FreeTypeFontLoaderParameter params5 = new FreeTypeFontLoaderParameter();
        params5.fontFileName = "data/pixel.ttf";
        params5.fontParameters.size = 14;
        params5.fontParameters.color = Color.WHITE;
        manager.load("xsmall.ttf", BitmapFont.class, params5);
    }


    static void dispose()
    {
        manager.dispose();
    }
}
