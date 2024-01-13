import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {

    private final int imgIdx = 4;

    public Queen(Point loc, PieceColor pColor) {
        this.moveSteps = 0;
        this.pieceColor = pColor;
        this.position = loc;
    }

    private Queen(Point loc, PieceColor pColor, int moves) {
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
        return new Queen(new Point(this.position.x, this.position.y),
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

        addMovesInLine(board, moves, 1, 1);
        addMovesInLine(board, moves, -1, 1);
        addMovesInLine(board, moves, 1, -1);
        addMovesInLine(board, moves, -1, -1);

        if (checkKing)
            for (int i = 0; i < moves.size(); i++)
                if (board.movePutsKingInCheck(moves.get(i), this.pieceColor)) {
                    moves.remove(moves.get(i));
                    i--;
                }
        return moves;
    }

    private void addMovesInLine(Board board, List<Move> moves, int xi, int yi) {
        int px = position.x;
        int py = position.y;

        Point pt = new Point(px + xi, py + yi);
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
            pt = new Point(pt.x + xi, pt.y + yi);
        }
    }
}
