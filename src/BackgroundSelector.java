import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class BackgroundSelector {
    private static final String BACKGROUND_PATH_FILE = "background_path.txt";

    public static Image selectBackground(JPanel targetPanel) {
        JFileChooser wallpaperChooser = new JFileChooser();
        wallpaperChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "bmp", "gif"));

        int userDecision = wallpaperChooser.showOpenDialog(null);
        if (userDecision == JFileChooser.APPROVE_OPTION) {
            File selectedWallpaper = wallpaperChooser.getSelectedFile();
            try {
                Image panelBackground = ImageIO.read(selectedWallpaper);
                saveBackgroundImagePath(selectedWallpaper.getPath());
                targetPanel.repaint();
                return panelBackground;
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }


    public static Image loadBackground() {
        String wallpaperPath = loadBackgroundImagePath();
        if (wallpaperPath == null) {
            wallpaperPath = "Backgrounds/background.jpeg";
        }
        try {
            return ImageIO.read(new File(wallpaperPath));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    private static void saveBackgroundImagePath(String wallpaperLocation) {
        try {
            FileWriter pathSaver = new FileWriter(BACKGROUND_PATH_FILE, false);
            pathSaver.write(wallpaperLocation);
            pathSaver.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private static String loadBackgroundImagePath() {
        try {
            File pathFile = new File(BACKGROUND_PATH_FILE);
            if (pathFile.exists()) {
                Scanner pathReader = new Scanner(pathFile);
                if (pathReader.hasNextLine()) {
                    return pathReader.nextLine();
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
