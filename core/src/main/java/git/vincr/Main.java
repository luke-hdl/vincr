package git.vincr;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import games.spooky.gdx.nativefilechooser.NativeFileChooser;
import games.spooky.gdx.nativefilechooser.NativeFileChooserCallback;
import games.spooky.gdx.nativefilechooser.NativeFileChooserConfiguration;

import java.io.File;
import java.util.Map;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture helpScreen;
    private Texture succeedScreen;
    private Texture failScreen;

    private Texture currentScreen;

    private Texture button;
    protected NativeFileChooser fileChooser;
    protected boolean lookingForFile = false;

    public Main(NativeFileChooser desktopFileChooser) {
        super();
        this.fileChooser = desktopFileChooser;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        helpScreen = new Texture("helppage.png");
        succeedScreen = new Texture("load_successful.png");
        failScreen = new Texture("load_unsuccessful.png");
        currentScreen = helpScreen;
        button = new Texture("loadfile.png");
        Gdx.input.setInputProcessor(new ButtonClickProcessor());
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(currentScreen, 0, 0);
        batch.draw(button, 10, 10);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        helpScreen.dispose();
    }

    private void startLookingForFile() {
        lookingForFile = true;
        NativeFileChooserConfiguration conf = new NativeFileChooserConfiguration();
        conf.directory = Gdx.files.absolute(System.getProperty("user.home"));
        conf.title = "Select a Vinc CSV...";

        fileChooser.chooseFile(conf, new NativeFileChooserCallback() {
            @Override
            public void onFileChosen(FileHandle file) {
                try {
                    lookingForFile = true;
                    System.out.println("Selected " + file.name());
                    Map<String, Map<String, Integer>> in = InOut.loadFile(file);
                    FileHandle out = new FileHandle(new File(file.pathWithoutExtension() + "_out.csv"));
                    InOut.writeFile(Calculator.calculateVinc(in), out);
                    currentScreen = succeedScreen;
                } catch (Exception e) {
                    e.printStackTrace();
                    currentScreen = failScreen;
                } finally {
                    lookingForFile = false;
                }
            }

            @Override
            public void onCancellation() {
            }

            @Override
            public void onError(Exception exception) {
                exception.printStackTrace();
                currentScreen = failScreen;
            }
        });
    }

    private boolean clickedOnButton(int x, int y) {
        return x <= button.getWidth() + 10 && x >= 10 && y >= 870 - button.getHeight() - 10 && y <= 870;
    }


    protected class ButtonClickProcessor implements InputProcessor {
        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int x, int y, int pointer, int button) {
            if (button == Input.Buttons.LEFT) {
                System.out.println(x + " " + y);
                if (clickedOnButton(x, y) && !lookingForFile) {
                    startLookingForFile();
                }
            }
            return false;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(float amountX, float amountY) {
            return false;
        }
    }
}