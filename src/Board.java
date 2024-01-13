import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Board implements Serializable, Cloneable {
    private Board prevSt = null;
    private Piece.PieceColor turn;
    private List<Piece> pcs = new ArrayList<Piece>();

    private Piece inChk = null;
    private Piece lastMvd = null;
    private Ai ai = null;
    public void setAi(Ai compPlayer) {
        this.ai = compPlayer;
    }
    public Ai getAi() {
        return ai;
    }
    public Piece getPieceInCheck() {
        return inChk;
    }
    public Piece getLastMovedPiece() {
        return lastMvd;
    }

    public Board(boolean initPieces, int challengeLevel) {
        turn = Piece.PieceColor.WHITE;

        if (initPieces) {
            BoardInitializer.initializeBoard(this,challengeLevel);
        }
    }

    private Board(Piece.PieceColor turn, Board prevSt, List<Piece> pcs,
                  Piece lastMvd, Piece inChk, Ai ai) {
        this.turn = turn;
        if (inChk != null)
            this.inChk = inChk.clone();
        if (lastMvd != null)
            this.lastMvd = lastMvd.clone();
        this.ai = ai;
        this.prevSt = prevSt;
        for (Piece p : pcs) {
            this.pcs.add(p.clone());
        }
    }

    public List<Piece> getPieces() {
        return pcs;
    }

    public Piece getPieceAt(Point p) {
        for (Piece pc : pcs) {
            if (pc.getPosition().x == p.x && pc.getPosition().y == p.y)
                return pc;
        }
        return null;
    }

    public void removePiece(Piece p) {
        if (pcs.contains(p)) {
            pcs.remove(p);
            return;
        }
    }

    public Piece.PieceColor getTurn() {
        return turn;
    }

    public void doMove(Move m, boolean playerMove) {
        this.prevSt = this.clone();

        for (Piece pc : pcs)
            if (pc.getColor() == turn && pc instanceof Pawn)
                ((Pawn) pc).enPassantOk = false;

        if (m instanceof Castling) {
            Castling c = (Castling) m;
            c.getPieceMoving().movePieceTo(c.getDestination());
            c.getRook().movePieceTo(c.getRookMoveTo());
        } else {

            if (m.getCapturedPiece() != null) ;
            this.removePiece(m.getCapturedPiece());

            if (m.getPieceMoving() instanceof Pawn)
                if (Math.abs(m.getPieceMoving().getPosition().y - m.getDestination().y) == 2)
                    ((Pawn) m.getPieceMoving()).enPassantOk = true;

            m.getPieceMoving().movePieceTo(m.getDestination());

            checkPawnPromotion(m.getPieceMoving(), playerMove);
        }

        this.lastMvd = m.getPieceMoving();
        this.inChk = kingInCheck();

        turn = Piece.PieceColor.values()[(turn.ordinal() + 1) % 2];
    }

    private void checkPawnPromotion(Piece pawn, boolean showDialog) {
        if (pawn instanceof Pawn && (pawn.getPosition().y == 0 || pawn.getPosition().y == 7)) {
            Piece promoted;

            if (!showDialog || (ai != null && ai.getColor() == pawn.getColor())) {
                promoted = new Queen(pawn.getPosition(), pawn.getColor());
            } else {
                Object type = javax.swing.JOptionPane.showInputDialog(
                        null,
                        "",
                        "Choose promotion:",
                        javax.swing.JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[]{"Queen", "Rook", "Bishop", "Knight"},
                        "Queen");

                if (type == null)
                    type = "Queen";

                if (type.toString().equals("Queen"))
                    promoted = new Queen(pawn.getPosition(), pawn.getColor());
                else if (type.toString().equals("Rook"))
                    promoted = new Rook(pawn.getPosition(), pawn.getColor());
                else if (type.toString().equals("Bishop"))
                    promoted = new Bishop(pawn.getPosition(), pawn.getColor());
                else
                    promoted = new Knight(pawn.getPosition(), pawn.getColor());
            }

            pcs.remove(pawn);
            pcs.add(promoted);
        }
    }

    public Board tryMove(Move m) {
        Board helper = this.clone();

        if (m instanceof Castling) {
            Castling c = (Castling) m;
            Piece king = helper.getPieceAt(c.getPieceMoving().getPosition());
            Piece rook = helper.getPieceAt(c.getRook().getPosition());

            helper.doMove(new Castling(king, c.getDestination(),
                    rook, c.getRookMoveTo()), false);
        } else {
            Piece capture = null;
            if (m.getCapturedPiece() != null)
                capture = helper.getPieceAt(m.getCapturedPiece().getPosition());

            Piece moving = helper.getPieceAt(m.getPieceMoving().getPosition());

            helper.doMove(new Move(moving,
                    m.getDestination(), capture), false);
        }

        return helper;
    }

    private Piece kingInCheck() {
        for (Piece pc : pcs)
            for (Move mv : pc.getValidMoves(this, false))
                if (mv.getCapturedPiece() instanceof King) {
                    this.inChk = mv.getCapturedPiece();
                    return mv.getCapturedPiece();
                }
        return null;
    }

    public boolean movePutsKingInCheck(Move m, Piece.PieceColor kingPieceColor) {
        Board helper = tryMove(m);

        for (Piece pc : helper.getPieces())
            if (pc.pieceColor != kingPieceColor)
                for (Move mv : pc.getValidMoves(helper, false))
                    if (mv.getCapturedPiece() instanceof King)
                        return true;
        return false;
    }

    public boolean gameOver() {
        List<Move> whiteMoves = new ArrayList<Move>();
        List<Move> blackMoves = new ArrayList<Move>();

        for (Piece p : pcs) {
            if (p.getColor() == Piece.PieceColor.WHITE)
                whiteMoves.addAll(p.getValidMoves(this, true));
            else
                blackMoves.addAll(p.getValidMoves(this, true));
        }

        return (whiteMoves.size() == 0 || blackMoves.size() == 0);
    }

    @Override
    public Board clone() {
        return new Board(turn, prevSt, pcs, lastMvd, inChk, ai);
    }

    public Board getPreviousState() {
        if (prevSt != null)
            return prevSt;
        return this;
    }

    public boolean validLocation(Point p) {
        return (p.x >= 0 && p.x <= 7) && (p.y >= 0 && p.y <= 7);
    }

    public void addPiece(Piece piece) {
        pcs.add(piece);
    }

    public void clearBoardPieces() {
        pcs = new ArrayList<Piece>();
    }
}
