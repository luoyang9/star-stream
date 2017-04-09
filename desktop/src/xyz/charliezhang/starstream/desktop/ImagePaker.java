package xyz.charliezhang.starstream.desktop;

/**
 * Created by Charlie on 2015-12-03.
 */
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class ImagePaker {
    public static void main(String[] args)
    {
        TexturePacker.process("C:/Development/LibGDX/Shooter/android/assets/data/textures/pack",
                "C:/Development/LibGDX/Shooter/android/assets/data/textures",
                "explosion");
    }
}
