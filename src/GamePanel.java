import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;

public class GamePanel extends JComponent implements MouseListener {

    private final SaveBoard saveB = new SaveBoard(this);

    private DrawChessBoard drawChBoard;
    private Highlight highLight;

    public Object getGameBoard() {
        return gameBrd;
    }

    private enum GameSt {Idle, Error, Started, Checkmate, Stalemate};

    GameSt st = GameSt.Idle;
    boolean imgsLoaded = false;
    Board gameBrd;
    Piece selectedPc = null;
    Piece invalidPc = null;
    List<Move> legalMvs;

    final Color invalidCol = new Color(255, 0, 0, 127);
    final Color selectionCol = new Color(255, 255, 0, 127);
    final Color validMoveCol = new Color(0, 255, 0, 127);
    final Color checkCol = new Color(127, 0, 255, 127);
    final Color lastMovedCol = new Color(0, 255, 255, 75);
    final Color lightCol = new Color(186, 164, 132);
    final Color darkCol = new Color(92, 64, 51);

    public GamePanel(int w, int h) {
        this.setSize(w, h);
        loadImgs();

        drawChBoard = new DrawChessBoard(lightCol, darkCol);

        highLight = new Highlight(selectionCol, validMoveCol, invalidCol, checkCol, lastMovedCol);
        newGame();
        this.addMouseListener(this);
    }

    public void newGame() { //creates a standard PVP game
        newGame(0);
    }

    public void newGame(int challengeLevel) {
        gameBrd = new Board(true, challengeLevel);
        st = GameSt.Started;
        selectedPc = null;
        invalidPc = null;
        this.repaint();
    }

    public void newAiGame() {newCustomGame(false, true);}
    public void newAiChallengeGame() {newCustomGame(true, true);}
    public void newChallengeGame() {newCustomGame(true, false);}
    public void newCustomGame(boolean challengeMode, boolean singlePlayer) {
        int aiDepth = 2;
        Piece.PieceColor aiPieceColor = Piece.PieceColor.BLACK;
        int challengeLevel = 0;

        if (challengeMode) {
            // Create a JOptionPane to ask the user for the challenge level
            Object challenge = JOptionPane.showInputDialog(this, "Select challenge level:",
                    "New challenge game",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new Object[] {"Level1", "Level2", "Horde", "Fischer Random Chess"},
                    "Level1");

            // interprets JOptionPane result
            if (challenge.toString().equals("Level1"))
                challengeLevel = 1;
            else if (challenge.toString().equals("Level2"))
                challengeLevel = 2;
            else if (challenge.toString().equals("Horde"))
                challengeLevel = 3;
            else
                challengeLevel = 4;
        }

        if (singlePlayer) {
            // creates a JOptionPane to ask the user for the difficulty of the ai
            Object level = JOptionPane.showInputDialog(this, "Select AI level:",
                    "New 1-Player game",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new Object[]{"Easy", "Normal"},
                    "Easy");

            // interprets JOptionPane result
            if (level == null)
                return;
            else if (level.toString().equals("Easy"))
                aiDepth = 2;
            else
                aiDepth = 3;

            Object color = JOptionPane.showInputDialog(this, "Select AI Color:",
                    "New 1-Player game",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new Object[]{"Black", "White"},
                    "Black");

            if (color == null)
                return;
            else if (color.toString().equals("White"))
                aiPieceColor = Piece.PieceColor.WHITE;
            else
                aiPieceColor = Piece.PieceColor.BLACK;
        }

        newGame(challengeLevel);

        if (singlePlayer) {
            gameBrd.setAi(new Ai(aiPieceColor, aiDepth));
            if (aiPieceColor == Piece.PieceColor.WHITE)
                mousePressed(null);
        }
    }

    public void loadBoard() {
        selectedPc = null;
        invalidPc = null;

        try {
            File dir = new File("SAVES");
            if (!dir.exists())
                dir.mkdir();

            File[] saves = dir.listFiles();

            if (saves.length == 0) {
                JOptionPane.showMessageDialog(this,
                        "No saved games found",
                        "Load game",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            Object response = JOptionPane.showInputDialog(this, "Select save file:",
                    "Load Game",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    saves,
                    saves[0]);

            if (response == null)
                return;

            FileInputStream fis = new FileInputStream((File)response);
            ObjectInputStream ois = new ObjectInputStream(fis);

            this.gameBrd = (Board)ois.readObject();

            ois.close();
            fis.close();
        } catch (Exception e) {
            String message = "Could not load saved game. " + "Error details: " + e.getMessage();
            JOptionPane.showMessageDialog(this, message, "Error!", JOptionPane.ERROR_MESSAGE);
        }
        if (gameBrd.gameOver()) {
            if (gameBrd.getPieceInCheck() == null)
                st = GameSt.Stalemate;
            else
                st = GameSt.Checkmate;
        }
        this.repaint();
    }

    public void saveBoard() {
        saveB.saveBoard();
    }

    public void undo() {
        selectedPc = null;
        invalidPc = null;
        legalMvs = null;

        if (gameBrd.getAi() == null)
            gameBrd = gameBrd.getPreviousState();
        else if (gameBrd.getTurn() != gameBrd.getAi().getColor())
            gameBrd = gameBrd.getPreviousState().getPreviousState();
        else
            gameBrd = gameBrd.getPreviousState();

        st = GameSt.Started;
        this.repaint();
    }

    private void loadImgs(){
        try {
            BufferedImage[] wImgs = new BufferedImage[6];
            BufferedImage[] bImgs = new BufferedImage[6];

            File dir = new File ("Pieces_pics");
            if (!dir.exists()) {
                if (dir.mkdir()) {
                    throw new Exception("The PIECES directory did not exist. " +
                            "It has been created. Ensure that it contains the following files: \n" +
                            "WHITE_PAWN.PNG\n" +
                            "WHITE_KNIGHT.PNG\n" +
                            "WHITE_BISHOP.PNG\n" +
                            "WHITE_ROOK.PNG\n" +
                            "WHITE_QUEEN.PNG\n" +
                            "WHITE_KING.PNG\n" +
                            "BLACK_PAWN.PNG\n" +
                            "BLACK_KNIGHT.PNG\n" +
                            "BLACK_BISHOP.PNG\n" +
                            "BLACK_ROOK.PNG\n" +
                            "BLACK_QUEEN.PNG\n" +
                            "BLACK_KING.PNG");
                }
            }

            wImgs[0] = ImageIO.read(new File("Pieces_pics/WHITE_PAWN.PNG"));
            wImgs[1] = ImageIO.read(new File("Pieces_pics/WHITE_KNIGHT.PNG"));
            wImgs[2] = ImageIO.read(new File("Pieces_pics/WHITE_BISHOP.PNG"));
            wImgs[3] = ImageIO.read(new File("Pieces_pics/WHITE_ROOK.PNG"));
            wImgs[4] = ImageIO.read(new File("Pieces_pics/WHITE_QUEEN.PNG"));
            wImgs[5] = ImageIO.read(new File("Pieces_pics/WHITE_KING.PNG"));

            bImgs[0] = ImageIO.read(new File("Pieces_pics/BLACK_PAWN.PNG"));
            bImgs[1] = ImageIO.read(new File("Pieces_pics/BLACK_KNIGHT.PNG"));
            bImgs[2] = ImageIO.read(new File("Pieces_pics/BLACK_BISHOP.PNG"));
            bImgs[3] = ImageIO.read(new File("Pieces_pics/BLACK_ROOK.PNG"));
            bImgs[4] = ImageIO.read(new File("Pieces_pics/BLACK_QUEEN.PNG"));
            bImgs[5] = ImageIO.read(new File("Pieces_pics/BLACK_KING.PNG"));

            Piece.setBlackImages(bImgs);
            Piece.setWhiteImages(wImgs);

            imgsLoaded = true;
        } catch (Exception e) {
            st = GameSt.Error;
            String message = e.getMessage();
            JOptionPane.showMessageDialog(this, message, "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void mousePressed(MouseEvent e) {
        if (st == GameSt.Started) {
            invalidPc = null;
            int w = getWidth();
            int h = getHeight();

            if (gameBrd.getAi() == null ||
                    gameBrd.getAi().getColor() != gameBrd.getTurn()) {
                Point bPt = new Point(e.getPoint().x / (w / 8),
                        e.getPoint().y / (h / 8));

                if (selectedPc == null) {
                    selectedPc = gameBrd.getPieceAt(bPt);
                    if (selectedPc != null) {
                        legalMvs = selectedPc.getValidMoves(gameBrd, true);
                        if (selectedPc.getColor() != gameBrd.getTurn()) {
                            legalMvs = null;
                            invalidPc = selectedPc;
                            selectedPc = null;
                        }
                    }
                } else {
                    Move playerMv = moveWithDestination(bPt);

                    if (playerMv != null) {
                        gameBrd.doMove(playerMv, true);
                        selectedPc = null;
                        legalMvs = null;
                    } else {
                        selectedPc = null;
                        legalMvs = null;
                    }
                }
            }
            if (gameBrd.getAi() != null &&
                    gameBrd.getAi().getColor() == gameBrd.getTurn()) {

                this.paintImmediately(0, 0, this.getWidth(), this.getHeight());

                Move computerMv = gameBrd.getAi().getMove(gameBrd);

                if (computerMv != null) {
                    gameBrd.doMove(computerMv, false);
                }
            }

            if (gameBrd.gameOver()) {
                this.paintImmediately(0, 0, this.getWidth(), this.getHeight());
                if (gameBrd.getPieceInCheck() == null) {
                    st = GameSt.Stalemate;
                    JOptionPane.showMessageDialog(this,
                            "Stalemate!",
                            "",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    st = GameSt.Checkmate;
                    JOptionPane.showMessageDialog(this,
                            "Checkmate!",
                            "",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }

            this.repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        int panelW = getWidth();
        int panelH = getHeight();

        int squareW = panelW / 8;
        int squareH = panelH / 8;

        Image offscreenBuf = createOffscreenBuffer(panelW, panelH);
        Graphics bufferGraphics = offscreenBuf.getGraphics();

        drawOnBuffer(bufferGraphics, squareW, squareH);

        graphics.drawImage(offscreenBuf, 0, 0, this);

        bufferGraphics.dispose();
    }

    private Image createOffscreenBuffer(int width, int height) {
        return createImage(width, height);
    }

    private void drawOnBuffer(Graphics g, int squareW, int squareH) {
        drawChBoard.draw(g, squareW, squareH);
        highLight.highlightGameStates(g, squareW, squareH, selectedPc, invalidPc, legalMvs, gameBrd);

        if (imgsLoaded) {
            drawPieces(g, squareW, squareH);
        }
    }

    private void drawPieces(Graphics g, int sW, int sH) {
        for (Piece pc : gameBrd.getPieces()) {
            if (pc.getColor() == Piece.PieceColor.WHITE) {
                g.drawImage(pc.getWhiteImage(), pc.getPosition().x * sW,
                        pc.getPosition().y * sH, sW, sH, null);
            } else {
                g.drawImage(pc.getBlackImage(), pc.getPosition().x * sW,
                        pc.getPosition().y * sH, sW, sH, null);
            }
        }
    }

    private Move moveWithDestination(Point pt) {
        for (Move m : legalMvs)
            if (m.getDestination().equals(pt))
                return m;
        return null;
    }

    public void mouseExited(MouseEvent e) { }

    public void mouseEntered(MouseEvent e) { }

    public void mouseReleased(MouseEvent e) { }

    public void mouseClicked(MouseEvent e) { }
}
