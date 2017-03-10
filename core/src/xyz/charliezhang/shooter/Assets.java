package xyz.charliezhang.shooter;

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

import static xyz.charliezhang.shooter.Config.*;


public class Assets
{
    public static final AssetManager manager = new AssetManager();
    public static Skin skin;

    public static void init()
    {
        FileHandleResolver resolver = new InternalFileHandleResolver();
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
    }

    public static void load() {
        //game background
        manager.load(GAME_BACKGROUND_PATH, Texture.class);
        //menu background
        manager.load(MENU_BACKGROUND_PATH, Texture.class);
        //ui
        manager.load(UI_SKIN_PATH, TextureAtlas.class);
        manager.load(PAUSE_PATH, Texture.class);
        //health
        manager.load(HEALTH_PATH, Texture.class);
        manager.load(HEALTH_FILL_PATH, Texture.class);
        manager.load(LIVES_PATH, Texture.class);
        //powerup icons
        manager.load(MIS_ICON_PATH, Texture.class);
        manager.load(SHIELD_ICON_PATH, Texture.class);
        manager.load(ATT_ICON_PATH, Texture.class);
        //powerup effects
        manager.load(SHIELD_PATH, Texture.class);
        manager.load(MISSILE_PATH, TextureAtlas.class);
        //powerup drops
        manager.load(ATT_POWERUP_PATH, TextureAtlas.class);
        manager.load(MIS_POWERUP_PATH, TextureAtlas.class);
        manager.load(SHIELD_POWERUP_PATH, TextureAtlas.class);
        //enemies
        manager.load(UFO_PATH, TextureAtlas.class);
        manager.load(ICARUS_PATH, TextureAtlas.class);
        manager.load(SKULLINATOR_PATH, TextureAtlas.class);
        manager.load(STRIKER_PATH, TextureAtlas.class);
        manager.load(KAMIKAZE_PATH, TextureAtlas.class);
        manager.load(VALKYRIE_PATH, TextureAtlas.class);
        manager.load(FALCON_PATH, TextureAtlas.class);
        manager.load(ASTEROID_1_PATH, Texture.class);
        manager.load(ASTEROID_2_PATH, Texture.class);
        manager.load(ASTEROID_3_PATH, Texture.class);
        manager.load(ASTEROID_4_PATH, Texture.class);
        //explosions
        manager.load(PLAYER_EXPLOSION_PATH, TextureAtlas.class);
        manager.load(PLAYER_EXPLOSION_R_PATH, TextureAtlas.class);
        manager.load(EXPLOSION_PATH, TextureAtlas.class);
        //lasers
        manager.load(LASER_B_PATH, TextureAtlas.class);
        manager.load(LASER_F_PATH, TextureAtlas.class);
        manager.load(LASER_G_PATH, TextureAtlas.class);
        manager.load(LASER_R_PATH, TextureAtlas.class);
        manager.load(LASER_O_PATH, TextureAtlas.class);
        //player
        manager.load(PLAYER_BLUE_PATH, TextureAtlas.class);
        manager.load(PLAYER_RED_PATH, TextureAtlas.class);
        //music
        manager.load(GAME_MUSIC_PATH, Music.class);
        manager.load(MENU_MUSIC_PATH, Music.class);
        manager.load(WIN_MUSIC_PATH, Music.class);
        //sound effects
        manager.load(SHOOT_SOUND_PATH, Sound.class);
        manager.load(SHIELD_DOWN_SOUND_PATH, Sound.class);
        manager.load(SHIELD_UP_SOUND_PATH, Sound.class);
        manager.load(BUTTON_SOUND_PATH, Sound.class);
        manager.load(EXPLOSION_SOUND_PATH, Sound.class);

        //game fonts
        FreeTypeFontLoaderParameter params = new FreeTypeFontLoaderParameter();
        params.fontFileName = "data/goodtimes.ttf";
        params.fontParameters.size = 50;
        params.fontParameters.color = Color.WHITE;
        manager.load("big.ttf", BitmapFont.class, params);

        FreeTypeFontLoaderParameter params2 = new FreeTypeFontLoaderParameter();
        params2.fontFileName = "data/goodtimes.ttf";
        params2.fontParameters.size = 30;
        params2.fontParameters.color = Color.WHITE;
        manager.load("medium.ttf", BitmapFont.class, params2);

        FreeTypeFontLoaderParameter params3 = new FreeTypeFontLoaderParameter();
        params3.fontFileName = "data/goodtimes.ttf";
        params3.fontParameters.size = 20;
        params3.fontParameters.color = Color.WHITE;
        manager.load("small.ttf", BitmapFont.class, params3);
    }


    static void dispose()
    {
        manager.dispose();
    }
}
