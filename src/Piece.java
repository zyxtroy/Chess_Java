import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.List;

public abstract class Piece implements Serializable, Cloneable {

    public static enum PieceColor {WHITE, BLACK};

    protected static BufferedImage[] whiteImages;
    protected static BufferedImage[] blackImages;

    protected int moveSteps;
    protected PieceColor pieceColor;

    protected Point position;

    public int getNumberOfMoves() {
        return moveSteps;
    }

    public PieceColor getColor() {
        return this.pieceColor;
    }

    public void movePieceTo(Point p) {
        this.position = p;
        moveSteps++;
    }

    public Point getPosition() {
        return this.position;
    }

    public abstract int getImageIndex();

    public abstract BufferedImage getWhiteImage();

    public abstract BufferedImage getBlackImage();

    public abstract List<Move> getValidMoves(Board board, boolean checkKing);

    @Override
    public abstract Piece clone();

    public static void setWhiteImages(BufferedImage[] images) {
        whiteImages = images;
    }

    public static void setBlackImages(BufferedImage[] images) {
        blackImages = images;
    }
}
