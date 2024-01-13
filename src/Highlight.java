import java.awt.*;
import java.util.List;

public class Highlight {
    private final Color selColor;
    private final Color validMoveColor;
    private final Color invColor;
    private final Color chkColor;
    private final Color lastMovedColor;

    public Highlight(Color selected, Color validMove, Color invalid,
                     Color check, Color lastMoved) {
        this.selColor = selected;
        this.validMoveColor = validMove;
        this.invColor = invalid;
        this.chkColor = check;
        this.lastMovedColor = lastMoved;
    }

    public void highlightGameStates(Graphics g, int width, int height, Piece selected,
                                    Piece invalid, List<Move> legalMoves, Board gameBoard) {
        highlightSelected(g, width, height, selected, legalMoves);
        highlightInvalid(g, width, height, invalid);
        highlightCheck(g, width, height, gameBoard);
        highlightLastMoved(g, width, height, gameBoard);
    }

    private void highlightSelected(Graphics g, int width, int height, Piece selected, List<Move> legalMoves) {
        if (selected == null) return;

        Point pos = selected.getPosition();
        g.setColor(selColor);
        g.fillOval(pos.x * width, pos.y * height, width, height);

        g.setColor(validMoveColor);
        for (Move move : legalMoves) {
            Point dest = move.getDestination();
            g.fillOval(dest.x * width, dest.y * height, width, height);
        }
    }

    private void highlightInvalid(Graphics g, int width, int height, Piece invalid) {
        if (invalid == null) return;

        Point pos = invalid.getPosition();
        g.setColor(invColor);
        g.fillOval(pos.x * width, pos.y * height, width, height);
    }

    private void highlightCheck(Graphics g, int width, int height, Board gameBoard) {
        Piece checked = gameBoard.getPieceInCheck();
        if (checked == null) return;

        Point pos = checked.getPosition();
        g.setColor(chkColor);
        g.fillOval(pos.x * width, pos.y * height, width, height);
    }

    private void highlightLastMoved(Graphics g, int width, int height, Board gameBoard) {
        Piece lastMoved = gameBoard.getLastMovedPiece();
        if (lastMoved == null) return;

        Point pos = lastMoved.getPosition();
        g.setColor(lastMovedColor);
        g.fillOval(pos.x * width, pos.y * height, width, height);
    }
}
