import javax.swing.*;
import java.io.*;

public class SaveBoard implements Serializable {

    private final GamePanel gPanel;

    public SaveBoard(GamePanel gamePanel) {
        this.gPanel = gamePanel;
    }

    public void saveBoard() {
        String fName = JOptionPane.showInputDialog("Save file name: ");

        if (fName == null)
            return;

        File dir = new File("SAVES");
        if (!dir.exists())
            dir.mkdir();

        try (
                FileOutputStream fos = new FileOutputStream(new File("SAVES/" + fName + ".csv"));
                ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(gPanel.getGameBoard());

        } catch (IOException e) {
            String msg = "Could not save game. " +
                    "Ensure that a valid filename was given.\n\n" +
                    "Error details: " + e.getMessage();

            JOptionPane.showMessageDialog(gPanel,
                    msg, "Error!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
