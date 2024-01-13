import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    private final int imgIdx = 0;

    public boolean enPassantOk = false;

    public Pawn(Point loc, PieceColor pColor) {
        this.moveSteps = 0;
        this.pieceColor = pColor;
        this.position = loc;
    }

    private Pawn(Point loc, PieceColor pColor, int moves, boolean enPassant) {
        enPassantOk = enPassant;
        this.moveSteps = moves;
        this.pieceColor = pColor;
        this.position = loc;
    }

    public Piece clone() {
        return new Pawn(new Point(this.position.x, this.position.y),
                this.pieceColor, this.moveSteps, this.enPassantOk);
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

    public List<Move> getValidMoves(Board board, boolean checkKing) {
        List<Move> moves = new ArrayList<Move>();

        if (board == null)
            return moves;

        advance(board, moves);
        capture(board, moves);
        enPassant(board, moves);

        if (checkKing)
            for(int i = 0; i < moves.size(); i++)
                if (board.movePutsKingInCheck(moves.get(i), this.pieceColor)) {
                    moves.remove(moves.get(i));
                    i--;
                }
        return moves;
    }

    private void advance(Board board, List<Move> moves) {
        int px = position.x;
        int py = position.y;

        Piece pc;
        Point pt;
        int move;

        if (pieceColor == PieceColor.WHITE)
            move = -1;
        else
            move = 1;

        pt = new Point(px, py + move);
        if (board.validLocation(pt)) {
            pc = board.getPieceAt(pt);
            if(pc == null) {
                moves.add(new Move(this, pt, pc));

                pt = new Point(px, py + move * 2);
                if (board.validLocation(pt)) {
                    pc = board.getPieceAt(pt);
                    if(pc == null && moveSteps == 0)
                        moves.add(new Move(this, pt, pc));
                }
            }
        }
    }

    private void capture(Board board, List<Move> moves) {
        int px = position.x;
        int py = position.y;

        Piece pc;
        Point pt;
        int move;

        if (pieceColor == PieceColor.WHITE)
            move = -1;
        else
            move = 1;

        pt = new Point(px - 1, py + move);
        if (board.validLocation(pt)) {
            pc = board.getPieceAt(pt);
            if (pc != null)
                if(this.pieceColor != pc.getColor())
                    moves.add(new Move(this, pt, pc));
        }
        pt = new Point(px + 1, py + move);
        if (board.validLocation(pt)) {
            pc = board.getPieceAt(pt);
            if (pc != null)
                if(this.pieceColor != pc.getColor())
                    moves.add(new Move(this, pt, pc));
        }
    }

    private void enPassant(Board board, List<Move> moves) {
        int px = position.x;
        int py = position.y;

        if (this.pieceColor == PieceColor.WHITE && this.position.y == 3) {
            if(canCaptureEnPassant(board, new Point(px - 1, py)))
                moves.add(new Move(this, new Point(px - 1, py - 1),
                        board.getPieceAt(new Point(px - 1, py))));
            if(canCaptureEnPassant(board, new Point(px + 1, py)))
                moves.add(new Move(this, new Point(px + 1, py - 1),
                        board.getPieceAt(new Point(px + 1, py))));
        }
        if (this.pieceColor == PieceColor.BLACK && this.position.y == 4) {
            if(canCaptureEnPassant(board, new Point(px - 1, py)))
                moves.add(new Move(this, new Point(px - 1, py + 1),
                        board.getPieceAt(new Point(px - 1, py))));
            if(canCaptureEnPassant(board, new Point(px + 1, py)))
                moves.add(new Move(this, new Point(px + 1, py + 1),
                        board.getPieceAt(new Point(px + 1, py))));
        }
    }

    private boolean canCaptureEnPassant(Board board, Point pt) {
        Piece temp = board.getPieceAt(pt);
        if(temp != null)
            if (temp instanceof Pawn && temp.getColor() !=  this.pieceColor)
                if (((Pawn)temp).enPassantOk)
                    return true;
        return false;
    }
}
