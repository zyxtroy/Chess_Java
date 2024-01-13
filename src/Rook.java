import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {

    private final int imgIdx = 3;

    public Rook(Point loc, PieceColor pColor) {
        this.moveSteps = 0;
        this.pieceColor = pColor;
        this.position = loc;
    }

    private Rook(Point loc, PieceColor pColor, int moves) {
        this.moveSteps = moves;
        this.pieceColor = pColor;
        this.position = loc;
    }

    public int getImageIndex() {
        return imgIdx;
    }

    public BufferedImage getWhiteImage() {
        return whiteImages[imgIdx];
    }

    public BufferedImage getBlackImage() {
        return blackImages[imgIdx];
    }

    public Piece clone() {
        return new Rook(new Point(this.position.x, this.position.y),
                this.pieceColor, this.moveSteps);
    }

    public List<Move> getValidMoves(Board board, boolean checkKing) {
        List<Move> moves = new ArrayList<Move>();

        if (board == null)
            return moves;

        addMovesInLine(board, moves, 1, 0);
        addMovesInLine(board, moves, 0, 1);
        addMovesInLine(board, moves, -1, 0);
        addMovesInLine(board, moves, 0, -1);

        if (checkKing)
            for (int i = 0; i < moves.size(); i++)
                if (board.movePutsKingInCheck(moves.get(i), this.pieceColor)) {
                    moves.remove(moves.get(i));
                    i--;
                }
        return moves;
    }

    private void addMovesInLine(Board board, List<Move> moves, int dx, int dy) {
        int px = position.x;
        int py = position.y;

        Point pt = new Point(px + dx, py + dy);
        Piece pc;

        while (board.validLocation(pt)) {
            pc = board.getPieceAt(pt);
            if (pc == null) {
                moves.add(new Move(this, pt, pc));
            } else if (pc.getColor() != this.pieceColor) {
                moves.add(new Move(this, pt, pc));
                break;
            } else {
                break;
            }
            pt = new Point(pt.x + dx, pt.y + dy);
        }
    }
}
