import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

    private final int imgNum = 5;
    public King(Point loc, PieceColor color) {
        moveSteps = 0;
        this.pieceColor = color;
        this.position = loc;
    }

    private King(Point loc, PieceColor color, int moves) {
        this.moveSteps = moves;
        this.pieceColor = color;
        this.position = loc;
    }

    public int getImageIndex() {
        return imgNum;
    }
    public BufferedImage getWhiteImage() {
        return whiteImages[imgNum];
    }
    public BufferedImage getBlackImage() {
        return blackImages[imgNum];
    }

    public Piece clone() {
        return new King(new Point(this.position.x, this.position.y),
                this.pieceColor, this.moveSteps);
    }

    public List<Move> getValidMoves(Board board, boolean chkKing) {
        int x = position.x;
        int y = position.y;

        List<Move> moves = new ArrayList<Move>();

        if (board == null)
            return moves;

        addValidMoves(board, moves, new Point(x - 1, y - 1));
        addValidMoves(board, moves, new Point(x, y - 1));
        addValidMoves(board, moves, new Point(x + 1, y - 1));
        addValidMoves(board, moves, new Point(x + 1, y));
        addValidMoves(board, moves, new Point(x + 1, y + 1));
        addValidMoves(board, moves, new Point(x, y + 1));
        addValidMoves(board, moves, new Point(x - 1, y + 1));
        addValidMoves(board, moves, new Point(x - 1, y));

        if (this.moveSteps == 0) {
            if (chkKing && this != board.getPieceInCheck()) {
                List<Piece> pcs = board.getPieces();
                List<Piece> okRooks = new ArrayList<Piece>();

                for (int i = 0; i < pcs.size(); i++)
                    if (pcs.get(i).getColor() == this.pieceColor &&
                            pcs.get(i) instanceof Rook &&
                            pcs.get(i).getNumberOfMoves() == 0)
                        okRooks.add(pcs.get(i));

                for (Piece p : okRooks) {
                    boolean canCastle = true;
                    if (p.getPosition().x == 7) {
                        for (int ix = this.position.x + 1; ix < 7; ix++) {
                            if (board.getPieceAt(new Point(ix, y)) != null) {
                                canCastle = false;
                                break;
                            }
                        }
                        if (canCastle)
                            moves.add(new Castling(this, new Point(x + 2, y),
                                    p, new Point(x + 1, y)));
                    } else if (p.getPosition().x == 0) {
                        for (int ix = this.position.x - 1; ix > 0; ix--) {
                            if (board.getPieceAt(new Point(ix, y)) != null) {
                                canCastle = false;
                                break;
                            }
                        }
                        if (canCastle)
                            moves.add(new Castling(this, new Point(x - 2, y),
                                    p, new Point(x - 1, y)));
                    }
                }
            }
        }

        if (chkKing)
            for (int i = 0; i < moves.size(); i++)
                if (board.movePutsKingInCheck(moves.get(i), this.pieceColor)) {
                    moves.remove(moves.get(i));
                    i--;
                }

        return moves;
    }

    private void addValidMoves(Board board, List<Move> list, Point pt) {
        if (board.validLocation(pt)) {
            Piece pc = board.getPieceAt(pt);
            if (pc == null || pc.getColor() != this.pieceColor) {
                list.add(new Move(this, pt, pc));
            }
        }
    }
}
