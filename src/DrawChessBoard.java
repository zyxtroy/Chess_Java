import java.awt.*;

public class DrawChessBoard {

    private final Color lColor;
    private final Color dColor;

    public DrawChessBoard(Color lightColor, Color darkColor) {
        this.lColor = lightColor;
        this.dColor = darkColor;
    }

    public void draw(Graphics g, int width, int height) {
        fillLightSquares(g, width, height);
        drawDarkSquares(g, width, height);
    }

    private void fillLightSquares(Graphics g, int width, int height) {
        g.setColor(lColor);
        g.fillRect(0, 0, width * 8, height * 8);
    }

    private void drawDarkSquares(Graphics g, int width, int height) {
        g.setColor(dColor);

        boolean isDarkSq = true;

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (isDarkSq) {
                    g.fillRect(x * width, y * height, width, height);
                }
                isDarkSq = !isDarkSq;
            }
            isDarkSq = !isDarkSq;
        }
    }
}
