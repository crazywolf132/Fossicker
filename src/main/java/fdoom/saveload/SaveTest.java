package fdoom.saveload;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import fdoom.core.Game;
import fdoom.level.tile.Tile;

public class SaveTest {
    public static void backupGame(Class<Game> class1, String fileName) {
        try {
            final FileOutputStream fout = new FileOutputStream(fileName);
            final ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(class1);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Game restoreGame(final String fileName) {
        Game gameState = null;
        try {
            final FileInputStream fin = new FileInputStream(fileName);
            final ObjectInputStream ois = new ObjectInputStream(fin);
            System.err.println(ois.readObject());
            gameState = (Game) ois.readObject();
            ois.close();
        } catch (ExceptionInInitializerError e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return gameState;
    }
}