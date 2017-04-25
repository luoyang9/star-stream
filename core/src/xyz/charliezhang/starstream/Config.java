package xyz.charliezhang.starstream;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Charlie Zhang on 3/8/2017.
 */
public class Config {

    // ASSET PATHS
    // game background
    public static String GAME_BACKGROUND_PATH = "data/textures/background.png";
    // menu background
    public static String MENU_BACKGROUND_PATH = "data/ui/background.png";
    //color
    public static String WHITE_PATH = "data/ui/divider.png";
    // ui
    public static String UI_SKIN_PATH = "data/ui/uiskin.atlas";
    public static String PAUSE_PATH = "data/ui/pause.png";
    public static String PLAYER_BLUE_UI_PATH = "data/ui/player0.png";
    public static String PLAYER_RED_UI_PATH = "data/ui/player1.png";
    public static String UI_COIN_PATH = "data/ui/coin.png";
    // health
    public static String HEALTH_PATH = "data/textures/health.png";
    public static String HEALTH_FILL_PATH = "data/textures/healthFill.png";
    public static String LIVES_PATH = "data/textures/livesIcon.png";
    // powerup icons
    public static String MIS_ICON_PATH = "data/textures/misicon.png";
    public static String SHIELD_ICON_PATH = "data/textures/shieldicon.png";
    public static String ATT_ICON_PATH = "data/textures/atticon.png";
    // powerup effects
    public static String SHIELD_PATH = "data/textures/shield.png";
    public static String MISSILE_PATH = "data/textures/missile.atlas";
    // powerup drops
    public static String ATT_POWERUP_PATH = "data/textures/attpowerup.atlas";
    public static String MIS_POWERUP_PATH = "data/textures/mispowerup.atlas";
    public static String SHIELD_POWERUP_PATH = "data/textures/shieldpowerup.atlas";
    // enemies
    public static String UFO_PATH = "data/textures/ufo.atlas";
    public static String ICARUS_PATH = "data/textures/icarus.atlas";
    public static String SKULLINATOR_PATH = "data/textures/skullinator.atlas";
    public static String STRIKER_PATH = "data/textures/striker.atlas";
    public static String KAMIKAZE_PATH = "data/textures/kamikaze.atlas";
    public static String VALKYRIE_PATH = "data/textures/valkyrie.atlas";
    public static String FALCON_PATH = "data/textures/falcon.atlas";
    public static String ASTEROID_1_PATH = "data/textures/asteroid1.png";
    public static String ASTEROID_2_PATH = "data/textures/asteroid2.png";
    public static String ASTEROID_3_PATH = "data/textures/asteroid3.png";
    public static String ASTEROID_4_PATH = "data/textures/asteroid4.png";
    // explosions
    public static String PLAYER_EXPLOSION_PATH = "data/textures/playerExplosion.atlas";
    public static String PLAYER_EXPLOSION_R_PATH = "data/textures/playerExplosionR.atlas";
    public static String EXPLOSION_PATH = "data/textures/explosion.atlas";
    // lasers
    public static String LASER_B_PATH = "data/textures/laserB.atlas";
    public static String LASER_F_PATH = "data/textures/laserF.atlas";
    public static String LASER_G_PATH = "data/textures/laserG.atlas";
    public static String LASER_R_PATH = "data/textures/laserR.atlas";
    public static String LASER_O_PATH = "data/textures/laserO.atlas";
    // player
    public static String PLAYER_BLUE_PATH = "data/textures/player0.atlas";
    public static String PLAYER_RED_PATH = "data/textures/player1.atlas";
    // music
    public static String GAME_MUSIC_PATH = "data/music/background.mp3";
    public static String MENU_MUSIC_PATH = "data/music/menu.mp3";
    public static String WIN_MUSIC_PATH = "data/music/win.mp3";
    // sound effects
    public static String SHOOT_SOUND_PATH = "data/sounds/playershoot.ogg";
    public static String SHIELD_DOWN_SOUND_PATH = "data/sounds/shieldDown.ogg";
    public static String SHIELD_UP_SOUND_PATH = "data/sounds/shieldUp.ogg";
    public static String BUTTON_SOUND_PATH = "data/sounds/button.mp3";
    public static String EXPLOSION_SOUND_PATH = "data/sounds/explosion.wav";

    // PLAYER DATA
    public static int PLAYER_ATT_LEVEL = 4;
    public static int PLAYER_MAX_LIVES = 3;
    public static boolean PLAYER_INITIAL_FLINCHING = false;
    public static boolean PLAYER_INITIAL_CONTROLLABLE = false;
    public static boolean PLAYER_INITIAL_JUST_CONTROLLABLE = false;
    public static boolean PLAYER_INITIAL_JUST_SPAWNED = true;
    public static boolean PLAYER_INITIAL_JUST_TOUCHED = false;
    public static Vector2 PLAYER_INITIAL_DIRECTION = new Vector2(0, 6);
    public static float PLAYER_INITIAL_Y = -2000;
    public static final int NUM_TYPES = 2;
    public static final int PLAYER_BLUE = 0;
    public static final int PLAYER_RED = 1;
    public static String[] SHIP_NAME = new String[]{
            "Blue Fury",
            "Red Dragon"
    };
    public static String[] SHIP_DESCRIPTION = new String[]{
            "Your trusty fighter, Blue Fury. With its high speed laser cannon and sleek form, this beauty will shred through enemies.",
            "Used widely during the Battle of Mars, the Red Dragon is a formidable ship equipped with powerful cannons that shoot armor penetrating blasts."
    };

    // BLUE FURY DATA
    public static long BLUE_FURY_SHOOT_DELAY = 100;
    public static int BLUE_FURY_MAX_HEALTH = 10;
    public static int BLUE_FURY_DAMAGE = 1;

    // RED DRAGON DATA
    public static long RED_DRAGON_SHOOT_DELAY = 500;
    public static long RED_DRAGON_SUPER_SHOOT_DELAY = 200;
    public static int RED_DRAGON_MAX_HEALTH = 10;
    public static int RED_DRAGON_DAMAGE = 3;

    // POWERUP DATA
    public static Vector2 MIS_INITIAL_DIRECTION = new Vector2(0, 10);
    public static float MIS_INITIAL_ACCEL = 5;
    public static boolean MIS_INITIAL_ON = false;
    public static float MIS_DELAY = 0;
    public static float MIS_INTERVAL = 1;
    public static int MIS_NUM_REPEATS = 8;

    public static boolean SHIELD_INITIAL_ON = false;

    public static boolean ATT_INITIAL_ON = false;
    public static long ATT_DURATION = 3000;

    public static boolean INVINCIBLE_INITIAL_ON = false;
    public static long INVINCIBLE_DURATION = 3000;

    // ENEMY DATA
    public static boolean ENEMY_INITIAL_DEAD = false;
    public static boolean ENEMY_INITIAL_SUICIDE = false;

    // ASTEROID DATA
    public static int ASTEROID_HEALTH = 3;
    public static int ASTEROID_DAMAGE = 1;
    public static int ASTEROID_SCORE = 5;

    // FALCON DATA
    public static int FALCON_HEALTH = 10;
    public static int FALCON_DAMAGE = 1;
    public static int FALCON_SCORE = 15;

    // ICARUS DATA
    public static int ICARUS_HEALTH = 60;
    public static int ICARUS_DAMAGE = 2;
    public static int ICARUS_SCORE = 150;
    public static boolean ICARUS_INITIAL_INTRO = true;

    // KAMIKAZE DATA
    public static int KAMIKAZE_HEALTH = 10;
    public static int KAMIKAZE_DAMAGE = 1;
    public static int KAMIKAZE_SCORE = 15;

    // STRIKER DATA
    public static int STRIKER_HEALTH = 25;
    public static int STRIKER_DAMAGE = 2;
    public static int STRIKER_SCORE = 75;
    public static boolean STRIKER_INITIAL_INTRO = true;

    // UFO DATA
    public static int UFO_HEALTH = 30;
    public static int UFO_DAMAGE = 1;
    public static int UFO_SCORE = 100;
    public static int UFO_INITIAL_ROTATION = 0;

    // VALKYRIE DATA
    public static int VALKYRIE_HEALTH = 40;
    public static int VALKYRIE_DAMAGE = 1;
    public static int VALKYRIE_SCORE = 100;
    public static boolean VALKYRIE_INITIAL_INTRO = true;

    // SKULLINATOR DATA
    public static int SKULLINATOR_HEALTH = 600;
    public static int SKULLINATOR_DAMAGE = 2;
    public static int SKULLINATOR_SCORE = 300;
    public static boolean SKULLINATOR_INITIAL_ENTERED = false;
}
