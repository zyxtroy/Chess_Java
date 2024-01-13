import java.awt.*;

public class Move {
    private Piece piece;
    private Point dest;
    private Piece captured;

    public Move(Piece pm, Point d, Piece cp) {
        this.piece = pm;
        this.dest = d;
        this.captured = cp;
    }

    public Point getDestination() {
        return dest;
    }

    public Piece getPieceMoving() {
        return piece;
    }

    public Piece getCapturedPiece() {
        return captured;
    }
}
