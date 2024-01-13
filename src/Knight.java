import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {

    private final int imgNum = 1;

    public Knight(Point loc, PieceColor color) {
        moveSteps = 0;
        this.pieceColor = color;
        this.position = loc;
    }

    private Knight(Point loc, PieceColor color, int moves) {
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
        return new Knight(new Point(this.position.x, this.position.y),
                this.pieceColor, this.moveSteps);
    }

    public List<Move> getValidMoves(Board brd, boolean chkKing) {
        int posX = position.x;
        int posY = position.y;

        List<Move> mvs = new ArrayList<Move>();

        if (brd == null)
            return mvs;

        addValidMoves(brd, mvs, new Point(posX + 1, posY + 2));
        addValidMoves(brd, mvs, new Point(posX - 1, posY + 2));
        addValidMoves(brd, mvs, new Point(posX + 1, posY - 2));
        addValidMoves(brd, mvs, new Point(posX - 1, posY - 2));
        addValidMoves(brd, mvs, new Point(posX + 2, posY - 1));
        addValidMoves(brd, mvs, new Point(posX + 2, posY + 1));
        addValidMoves(brd, mvs, new Point(posX - 2, posY - 1));
        addValidMoves(brd, mvs, new Point(posX - 2, posY + 1));

        if (chkKing)
            for (int i = 0; i < mvs.size(); i++)
                if (brd.movePutsKingInCheck(mvs.get(i), this.pieceColor)) {
                    mvs.remove(mvs.get(i));
                    i--;
                }

        return mvs;
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
