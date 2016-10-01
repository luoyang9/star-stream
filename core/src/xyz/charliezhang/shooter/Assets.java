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
import xyz.charliezhang.shooter.music.MusicPlayer;

public class Assets
{
    public static final AssetManager manager = new AssetManager();
    public static Skin skin;

    public static void init()
    {
        FileHandleResolver resolver = new InternalFileHandleResolver();
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
    }

    public static void load() {
        //game background
        manager.load("data/textures/background.png", Texture.class);
        //menu background
        manager.load("data/ui/background.png", Texture.class);
        //ui
        manager.load("data/ui/uiskin.atlas", TextureAtlas.class);
        manager.load("data/textures/pause.png", Texture.class);
        //health
        manager.load("data/textures/health.png", Texture.class);
        manager.load("data/textures/healthFill.png", Texture.class);
        manager.load("data/textures/livesIcon.png", Texture.class);
        //powerup icons
        manager.load("data/textures/misicon.png", Texture.class);
        manager.load("data/textures/shieldicon.png", Texture.class);
        manager.load("data/textures/atticon.png", Texture.class);
        //powerup effects
        manager.load("data/textures/shield.png", Texture.class);
        manager.load("data/textures/missile.atlas", TextureAtlas.class);
        //powerup drops
        manager.load("data/textures/attpowerup.atlas", TextureAtlas.class);
        manager.load("data/textures/mispowerup.atlas", TextureAtlas.class);
        manager.load("data/textures/shieldpowerup.atlas", TextureAtlas.class);
        //enemies
        manager.load("data/textures/ufoB.atlas", TextureAtlas.class);
        manager.load("data/textures/ufoG.atlas", TextureAtlas.class);
        manager.load("data/textures/ufoR.atlas", TextureAtlas.class);
        manager.load("data/textures/ufoY.atlas", TextureAtlas.class);
        manager.load("data/textures/icarusB.atlas", TextureAtlas.class);
        manager.load("data/textures/icarusG.atlas", TextureAtlas.class);
        manager.load("data/textures/icarusR.atlas", TextureAtlas.class);
        manager.load("data/textures/skullinator.atlas", TextureAtlas.class);
        manager.load("data/textures/striker.atlas", TextureAtlas.class);
        manager.load("data/textures/kamikaze.atlas", TextureAtlas.class);
        manager.load("data/textures/valkyrie.atlas", TextureAtlas.class);
        manager.load("data/textures/falcon.atlas", TextureAtlas.class);
        manager.load("data/textures/asteroid1.png", Texture.class);
        manager.load("data/textures/asteroid2.png", Texture.class);
        manager.load("data/textures/asteroid3.png", Texture.class);
        manager.load("data/textures/asteroid4.png", Texture.class);
        //explosions
        manager.load("data/textures/playerExplosion.atlas", TextureAtlas.class);
        manager.load("data/textures/playerExplosionR.atlas", TextureAtlas.class);
        manager.load("data/textures/bigExplosion.atlas", TextureAtlas.class);
        //lasers
        manager.load("data/textures/laserB.atlas", TextureAtlas.class);
        manager.load("data/textures/laserF.atlas", TextureAtlas.class);
        manager.load("data/textures/laserG.atlas", TextureAtlas.class);
        manager.load("data/textures/laserR.atlas", TextureAtlas.class);
        manager.load("data/textures/laserO.atlas", TextureAtlas.class);
        //player
        manager.load("data/textures/playerblue.atlas", TextureAtlas.class);
        manager.load("data/textures/playerred.atlas", TextureAtlas.class);
        //music
        manager.load("data/music/background.ogg", Music.class);
        manager.load("data/music/menu.ogg", Music.class);
        manager.load("data/music/win.mp3", Music.class);
        //sound effects
        manager.load("data/sounds/playershoot.ogg", Sound.class);
        manager.load("data/sounds/shieldDown.ogg", Sound.class);
        manager.load("data/sounds/shieldUp.ogg", Sound.class);
        manager.load("data/sounds/button.mp3", Sound.class);
        manager.load("data/sounds/explosion.wav", Sound.class);

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


    public static void dispose()
    {
        manager.dispose();
    }
}
