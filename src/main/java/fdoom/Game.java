package fdoom;

import javax.swing.*;
import java.io.PrintWriter;
import java.io.StringWriter;

public class Game {

    Game() {}

    static boolean running = true;

    public static void quit() {
        running = false;
    }

    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            throwable.printStackTrace();

            StringWriter string = new StringWriter();
            PrintWriter printer = new PrintWriter(string);
            throwable.printStackTrace(printer);

            JTextArea errorDisplay = new JTextArea(string.toString());
            errorDisplay.setEditable(false);
            JScrollPane errorPane = new JScrollPane(errorDisplay);
            JOptionPane.showMessageDialog(null, errorPane, "An error has occurred", JOptionPane.ERROR_MESSAGE);
        });
    }
}
