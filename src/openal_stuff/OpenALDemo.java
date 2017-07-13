package openal_stuff;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.WaveData;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.lwjgl.openal.AL10.*;

public class OpenALDemo {

    public static void main(String[] args) throws FileNotFoundException {
        try {
            Display.setDisplayMode(new DisplayMode(640, 480));
            Display.setTitle("OpenAL Demo");
            Display.create();
            AL.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            Display.destroy();
            AL.destroy();
            System.exit(1);
        }
        WaveData data = WaveData.create(new BufferedInputStream(new FileInputStream("res" + File.separatorChar +
                "sounds" + File.separatorChar + "thump.wav")));
        int buffer = alGenBuffers();
        alBufferData(buffer, data.format, data.data, data.samplerate);
        data.dispose();
        int source = alGenSources();
        alSourcei(source, AL_BUFFER, buffer);
        while (!Display.isCloseRequested()) {
            while (Keyboard.next()) {
                if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                    alSourcePlay(source);
                }
            }
            Display.update();
            Display.sync(60);
        }
        alDeleteBuffers(buffer);
        AL.destroy();
        Display.destroy();
        System.exit(0);
    }
}