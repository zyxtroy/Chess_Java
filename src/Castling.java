import java.awt.*;

public class Castling extends Move {
    private Piece r;
    private Point mvRookTo;

    public Castling(Piece king, Point mvKing, Piece rook, Point mvRook) {
        super(king, mvKing, null);
        this.mvRookTo = mvRook;
        this.r = rook;
    }

    public Point getRookMoveTo() {
        return mvRookTo;
    }

    public Piece getRook() {
        return r;
    }
}
