import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Ai implements Serializable {

    private Piece.PieceColor aiPlayerColor;
    private int aiDepth;

    public Ai(Piece.PieceColor pieceColor, int depth) {
        this.aiPlayerColor = pieceColor;
        this.aiDepth = depth;
    }

    public Piece.PieceColor getColor() {
        return aiPlayerColor;
    }


    public Move getMove(Board gameState) {

        if (gameState == null || gameState.getTurn() != aiPlayerColor)
            return null;

        int topValue = Integer.MIN_VALUE;
        Move topMove = null;

        for (Move currentMove : getMoves(gameState)) {
            int moveVal = min(gameState.tryMove(currentMove), aiDepth - 1, topValue, Integer.MAX_VALUE);
            if (moveVal > topValue) {
                topValue = moveVal;
                topMove = currentMove;
            }
        }

        return topMove;
    }

    private int max(Board gameState, int depth, int alpha, int beta) {

        if (depth == 0 || getMoves(gameState).isEmpty()) return valueOfBoard(gameState);


        for (Move m : getMoves(gameState)) {
            int currentValue = min(gameState.tryMove(m), depth - 1, alpha, beta);
            alpha = Math.max(alpha, currentValue);
            if (alpha >= beta) break; // beta cut-off
        }

        return alpha;
    }

    private int min(Board gameState, int depth, int alpha, int beta) {

        if (depth == 0 || getMoves(gameState).isEmpty()) return valueOfBoard(gameState);


        for (Move m : getMoves(gameState)) {
            int currentValue = max(gameState.tryMove(m), depth - 1, alpha, beta);
            beta = Math.min(beta, currentValue);
            if (alpha >= beta) break;
        }
        return beta;
    }


    private List<Move> getMoves(Board gameState) {
        List<Move> possibleMoves = new ArrayList<>();

        for (Piece p : gameState.getPieces())
            if (p.getColor() == gameState.getTurn())
                possibleMoves.addAll(p.getValidMoves(gameState, true));
        return possibleMoves;
    }

    private int valueOfBoard(Board gameState) {
        int boardValue = 0, aiPieceCount = 0, playerPieceCount = 0, aiMoveCount = 0, playerMoveCount = 0, aiCaptureValue = 0, playerCaptureValue = 0;

        for (Piece p : gameState.getPieces()) {
            if (p.getColor() == aiPlayerColor) {
                aiPieceCount += valueOfPiece(p);
                if (aiPlayerColor == gameState.getTurn()) {
                    for (Move m : p.getValidMoves(gameState, true)) {
                        aiMoveCount++;
                        if (m.getCapturedPiece() != null) aiCaptureValue += valueOfPiece(m.getCapturedPiece());
                    }
                }
            } else {
                playerPieceCount += valueOfPiece(p);
                if (aiPlayerColor != gameState.getTurn()) {
                    for (Move m : p.getValidMoves(gameState, true)) {
                        playerMoveCount++;
                        if (m.getCapturedPiece() != null) playerCaptureValue += valueOfPiece(m.getCapturedPiece());
                    }
                }
            }
        }

        boardValue = (aiPieceCount - playerPieceCount) + (aiMoveCount - playerMoveCount) + (aiCaptureValue - playerCaptureValue);
        if (gameState.getTurn() == aiPlayerColor && aiMoveCount == 0) boardValue = Integer.MIN_VALUE;
        else if (gameState.getTurn() != aiPlayerColor && playerMoveCount == 0) boardValue = Integer.MAX_VALUE;

        return boardValue;
    }

    private int valueOfPiece(Piece chessPiece) {
        return (int) Math.pow(chessPiece.getImageIndex() + 1, 3) * 100;
    }
}
