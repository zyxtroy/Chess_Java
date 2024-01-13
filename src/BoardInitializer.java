import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Random;


public class BoardInitializer {

    // returns a new board pieces with pieces based on challenge level,
    // and returns the new board
    public static void initializeBoard(Board board, int challengeLevel) {

        board.clearBoardPieces();

        if (challengeLevel == 0) {
            for (int i = 0; i < 8; i++) {
                Piece blackPawn = new Pawn(new Point(i, 1), Piece.PieceColor.BLACK);
                Piece whitePawn = new Pawn(new Point(i, 6), Piece.PieceColor.WHITE);
                board.addPiece(blackPawn);
                board.addPiece(whitePawn);
            }

            // black pieces
            board.addPiece(new Rook(new Point(0, 0), Piece.PieceColor.BLACK));
            board.addPiece(new Knight(new Point(1, 0), Piece.PieceColor.BLACK));
            board.addPiece(new Bishop(new Point(2, 0), Piece.PieceColor.BLACK));
            board.addPiece(new Queen(new Point(3, 0), Piece.PieceColor.BLACK));
            board.addPiece(new King(new Point(4, 0), Piece.PieceColor.BLACK));
            board.addPiece(new Bishop(new Point(5, 0), Piece.PieceColor.BLACK));
            board.addPiece(new Knight(new Point(6, 0), Piece.PieceColor.BLACK));
            board.addPiece(new Rook(new Point(7, 0), Piece.PieceColor.BLACK));

            // white pieces

            board.addPiece(new Rook(new Point(0, 7), Piece.PieceColor.WHITE));
            board.addPiece(new Knight(new Point(1, 7), Piece.PieceColor.WHITE));
            board.addPiece(new Bishop(new Point(2, 7), Piece.PieceColor.WHITE));
            board.addPiece(new Queen(new Point(3, 7), Piece.PieceColor.WHITE));
            board.addPiece(new King(new Point(4, 7), Piece.PieceColor.WHITE));
            board.addPiece(new Bishop(new Point(5, 7), Piece.PieceColor.WHITE));
            board.addPiece(new Knight(new Point(6, 7), Piece.PieceColor.WHITE));
            board.addPiece(new Rook(new Point(7, 7), Piece.PieceColor.WHITE));
        }

        else if (challengeLevel == 1) {

            board.addPiece(new Pawn(new Point(3, 3), Piece.PieceColor.BLACK));
            board.addPiece(new Pawn(new Point(5, 2), Piece.PieceColor.BLACK));
            board.addPiece(new Pawn(new Point(6, 3), Piece.PieceColor.BLACK));
            board.addPiece(new Pawn(new Point(7, 2), Piece.PieceColor.BLACK));

            board.addPiece(new Pawn(new Point(2, 2), Piece.PieceColor.WHITE));
            board.addPiece(new Pawn(new Point(3, 5), Piece.PieceColor.WHITE));
            board.addPiece(new Pawn(new Point(4, 5), Piece.PieceColor.WHITE));
            board.addPiece(new Pawn(new Point(7, 5), Piece.PieceColor.WHITE));
            board.addPiece(new Pawn(new Point(7, 3), Piece.PieceColor.WHITE));


            board.addPiece(new Queen(new Point(2, 3), Piece.PieceColor.BLACK));
            board.addPiece(new Knight(new Point(0, 1), Piece.PieceColor.WHITE));
            board.addPiece(new Knight(new Point(5, 0), Piece.PieceColor.WHITE));

            board.addPiece(new King(new Point(5, 3), Piece.PieceColor.BLACK));
            board.addPiece(new King(new Point(3, 7), Piece.PieceColor.WHITE));


        }

        else if (challengeLevel == 2) {
            board.addPiece(new Pawn(new Point(1, 1), Piece.PieceColor.BLACK));
            board.addPiece(new Pawn(new Point(3, 1), Piece.PieceColor.BLACK));
            board.addPiece(new Pawn(new Point(5, 1), Piece.PieceColor.BLACK));
            board.addPiece(new Pawn(new Point(6, 1), Piece.PieceColor.BLACK));

            board.addPiece(new Rook(new Point(0, 0), Piece.PieceColor.BLACK));
            board.addPiece(new Knight(new Point(1, 0), Piece.PieceColor.BLACK));
            board.addPiece(new Bishop(new Point(2, 0), Piece.PieceColor.BLACK));
            board.addPiece(new King(new Point(4, 0), Piece.PieceColor.BLACK));

            // white pieces
            board.addPiece(new Pawn(new Point(0, 6), Piece.PieceColor.WHITE));
            board.addPiece(new Pawn(new Point(1, 6), Piece.PieceColor.WHITE));
            board.addPiece(new Pawn(new Point(2, 6), Piece.PieceColor.WHITE));
            board.addPiece(new Pawn(new Point(4, 6), Piece.PieceColor.WHITE));

            board.addPiece(new Rook(new Point(0, 7), Piece.PieceColor.WHITE));
            board.addPiece(new Knight(new Point(1, 7), Piece.PieceColor.WHITE));
            board.addPiece(new Bishop(new Point(2, 7), Piece.PieceColor.WHITE));
            board.addPiece(new King(new Point(4, 7), Piece.PieceColor.WHITE));

            // Historical scenario: Inspired by a mid-game position
            board.addPiece(new Queen(new Point(3, 3), Piece.PieceColor.BLACK));
            board.addPiece(new Knight(new Point(4, 3), Piece.PieceColor.BLACK));
            board.addPiece(new Bishop(new Point(3, 4), Piece.PieceColor.BLACK));

            board.addPiece(new Queen(new Point(3, 5), Piece.PieceColor.WHITE));
            board.addPiece(new Knight(new Point(4, 5), Piece.PieceColor.WHITE));
            board.addPiece(new Bishop(new Point(3, 2), Piece.PieceColor.WHITE));
        }

        else if (challengeLevel == 3) {
            board.addPiece(new Rook(new Point(0, 0), Piece.PieceColor.BLACK));
            board.addPiece(new Knight(new Point(1, 0), Piece.PieceColor.BLACK));
            board.addPiece(new Bishop(new Point(2, 0), Piece.PieceColor.BLACK));
            board.addPiece(new Queen(new Point(3, 0), Piece.PieceColor.BLACK));
            board.addPiece(new King(new Point(4, 0), Piece.PieceColor.BLACK));
            board.addPiece(new Bishop(new Point(5, 0), Piece.PieceColor.BLACK));
            board.addPiece(new Knight(new Point(6, 0), Piece.PieceColor.BLACK));
            board.addPiece(new Rook(new Point(7, 0), Piece.PieceColor.BLACK));
            for (int i = 0; i < 8; i++) {
                board.addPiece(new Pawn(new Point(i, 1), Piece.PieceColor.BLACK));
            }

            // white pieces
            board.addPiece(new Pawn(new Point(0, 7), Piece.PieceColor.WHITE));
            board.addPiece(new Pawn(new Point(1, 7), Piece.PieceColor.WHITE));
            board.addPiece(new Pawn(new Point(2, 7), Piece.PieceColor.WHITE));
            board.addPiece(new Pawn(new Point(3, 7), Piece.PieceColor.WHITE));
            board.addPiece(new King(new Point(4, 7), Piece.PieceColor.WHITE));
            board.addPiece(new Pawn(new Point(5, 7), Piece.PieceColor.WHITE));
            board.addPiece(new Pawn(new Point(6, 7), Piece.PieceColor.WHITE));
            board.addPiece(new Pawn(new Point(7, 7), Piece.PieceColor.WHITE));
            for (int i = 0; i < 8; i++) {
                for (int j = 6; j > 3; j--) {
                    board.addPiece(new Pawn(new Point(i, j), Piece.PieceColor.WHITE));
                }
            }
            board.addPiece(new Pawn(new Point(1, 3), Piece.PieceColor.WHITE));
            board.addPiece(new Pawn(new Point(2, 3), Piece.PieceColor.WHITE));
            board.addPiece(new Pawn(new Point(5, 3), Piece.PieceColor.WHITE));
            board.addPiece(new Pawn(new Point(6, 3), Piece.PieceColor.WHITE));
        }

        else {

            for (int i = 0; i < 8; i++) {
                Piece blackPawn = new Pawn(new Point(i, 1), Piece.PieceColor.BLACK);
                Piece whitePawn = new Pawn(new Point(i, 6), Piece.PieceColor.WHITE);
                board.addPiece(blackPawn);
                board.addPiece(whitePawn);
            }

            long seed = System.currentTimeMillis();
            Random random = new Random(seed);
            List<Integer> availableColumns = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                availableColumns.add(i);
            }
            Collections.shuffle(availableColumns,random);

            int rookCol1 = availableColumns.remove(0);
            int rookCol2 = availableColumns.remove(0);
            int kingCol = availableColumns.remove(0);

            // makes sure that king is not on corners
            while (kingCol == 0 || kingCol == 7) {
                availableColumns.add(kingCol);
                kingCol = availableColumns.remove(0);
            }

            // makes sure that king is between rooks
            while (((rookCol1 > kingCol) && (rookCol2 > kingCol)) || ((rookCol1 < kingCol) && (rookCol2 < kingCol))) {
                availableColumns.add(rookCol1);
                rookCol1 = availableColumns.remove(0);
            }

            int bishopCol1 = availableColumns.remove(0);
            int bishopCol2 = availableColumns.remove(0);

            // makes sure that bishops are in different colour tiles
            while (bishopCol1 % 2 == bishopCol2 % 2) {
                availableColumns.add(bishopCol1);
                bishopCol1 = availableColumns.remove(0);
            }

            int queenCol = availableColumns.remove(0);
            int knightCol1 = availableColumns.remove(0);
            int knightCol2 = availableColumns.remove(0);

            // Place bishops on opposite-colored squares
            board.addPiece(new Bishop(new Point(bishopCol1, 0), Piece.PieceColor.BLACK));
            board.addPiece(new Bishop(new Point(bishopCol2, 0), Piece.PieceColor.BLACK));
            board.addPiece(new Bishop(new Point(bishopCol1, 7), Piece.PieceColor.WHITE));
            board.addPiece(new Bishop(new Point(bishopCol2, 7), Piece.PieceColor.WHITE));

            // Place rooks
            board.addPiece(new Rook(new Point(rookCol1, 0), Piece.PieceColor.BLACK));
            board.addPiece(new Rook(new Point(rookCol2, 0), Piece.PieceColor.BLACK));
            board.addPiece(new Rook(new Point(rookCol1, 7), Piece.PieceColor.WHITE));
            board.addPiece(new Rook(new Point(rookCol2, 7), Piece.PieceColor.WHITE));

            // Place kings
            board.addPiece(new King(new Point(kingCol, 0), Piece.PieceColor.BLACK));
            board.addPiece(new King(new Point(kingCol, 7), Piece.PieceColor.WHITE));

            // Place queens
            board.addPiece(new Queen(new Point(queenCol, 0), Piece.PieceColor.BLACK));
            board.addPiece(new Queen(new Point(queenCol, 7), Piece.PieceColor.WHITE));

            // Place knights
            board.addPiece(new Knight(new Point(knightCol1, 0), Piece.PieceColor.BLACK));
            board.addPiece(new Knight(new Point(knightCol2, 0), Piece.PieceColor.BLACK));
            board.addPiece(new Knight(new Point(knightCol1, 7), Piece.PieceColor.WHITE));
            board.addPiece(new Knight(new Point(knightCol2, 7), Piece.PieceColor.WHITE));

        }
    }
}