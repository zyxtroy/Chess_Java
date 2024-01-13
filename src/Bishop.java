import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {
    private final int imgNumber = 2;

    public Bishop(Point position, PieceColor pieceColor) {
        this.moveSteps = 0;
        this.pieceColor = pieceColor;
        this.position = position;
    }

    private Bishop(Point location, PieceColor pieceColor, int moves) {
        this.moveSteps = moves;
        this.pieceColor = pieceColor;
        this.position = location;
    }

    public int getImageIndex() {
        return imgNumber;
    }

    public BufferedImage getWhiteImage() {
        return whiteImages[imgNumber];
    }

    public BufferedImage getBlackImage() {
        return blackImages[imgNumber];
    }

    public Piece clone() {
        return new Bishop(new Point(this.position.x, this.position.y),
                this.pieceColor, this.moveSteps);
    }

    public List<Move> getValidMoves(Board board, boolean checkKing) {
        List<Move> moves = new ArrayList<Move>();

        if (board == null)
            return moves;

        addValidMoves(board, moves, 1, 1);
        addValidMoves(board, moves, -1, 1);
        addValidMoves(board, moves, 1, -1);
        addValidMoves(board, moves, -1, -1);

        if (checkKing)
            for (int i = 0; i < moves.size(); i++)
                if (board.movePutsKingInCheck(moves.get(i), this.pieceColor)) {
                    moves.remove(moves.get(i));
                    i--;
                }

        return moves;
    }

    private void addValidMoves(Board board, List<Move> moves, int xi, int yi) {
        int x = position.x;
        int y = position.y;

        Point pt = new Point(x + xi, y + yi);
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
