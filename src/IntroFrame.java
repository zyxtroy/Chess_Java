import javax.swing.*;
import java.awt.*;

public class IntroFrame extends JFrame {
    private Image bgImg;
    private GamePanel gamePnl;
    public static final int FRM_WIDTH = 1000;
    public static final int FRM_HEIGHT = 725;
    public static final int FRM_X_POS = 100;
    public static final int FRM_Y_POS = 100;

    public IntroFrame(Main chessMain, GamePanel gamePnl) {
        super("Chess Intro");
        this.gamePnl = gamePnl;

        setBounds(FRM_X_POS, FRM_Y_POS, FRM_WIDTH, FRM_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        bgImg = BackgroundSelector.loadBackground();

        JPanel pnl = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bgImg != null) {
                    g.drawImage(bgImg, 0, 0, this);
                }
            }
        };

        // Add both buttons to the panel
        JButton startButton = addButton(chessMain, "Play with a friend");
        pnl.add(startButton);

        JButton aiPlayButton = addButton(chessMain,"Play with AI");
        pnl.add(aiPlayButton);

        JButton challengesButton = addButton(chessMain, "Play challenge mode with a friend");
        pnl.add(challengesButton);

        JButton aiChallengesButton = addButton(chessMain, "Play AI challenge mode");
        pnl.add(aiChallengesButton);

        pnl.setLayout(new GridBagLayout());
        add(pnl);
    }

    private JButton addButton(Main chessMainFrame, String buttonText) {
        JButton Button = new JButton(buttonText);
        Button.addActionListener(e -> {

            if (buttonText.equals("Play with AI")) {
                gamePnl.newAiGame();
            }
            if (buttonText.equals("Play AI challenge mode")) {
                gamePnl.newAiChallengeGame();
            }
            if (buttonText.equals("Play challenge mode with a friend")) {
                gamePnl.newChallengeGame();
            }
            if (buttonText.equals("Play with a friend") || buttonText.equals("Play with AI")
                    || buttonText.equals("Play AI challenge mode") || buttonText.equals("Play challenge mode with a friend")) {
                chessMainFrame.setVisible(true);
                this.dispose();
            }
        });
        return Button;
    }
}
