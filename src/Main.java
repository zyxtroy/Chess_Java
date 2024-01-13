import javax.swing.*;

public class Main extends javax.swing.JFrame {

    private GamePanel gameScreen;

    public Main() {
        initComponents();
        initialize();
    }

    private void initialize() {
        gameScreen = new GamePanel(jPanelChessBoard.getWidth(), jPanelChessBoard.getHeight());
        jPanelChessBoard.add(gameScreen);
    }

    private void initComponents() {
        jPanelChessBoard = new javax.swing.JPanel();
        jMenuBarMain = new javax.swing.JMenuBar();
        jMenuGame = new javax.swing.JMenu();
        jMenuItemNew1P = new javax.swing.JMenuItem();
        jMenuItemNew2P = new javax.swing.JMenuItem();
        jMenuItemNew1C = new javax.swing.JMenuItem();
        jMenuItemNew2C = new javax.swing.JMenuItem();
        jMenuItemUndo = new javax.swing.JMenuItem();
        jMenuItemClose = new javax.swing.JMenuItem();
        jMenuFile = new javax.swing.JMenu();
        jMenuItemSave = new javax.swing.JMenuItem();
        jMenuItemLoad = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chess");
        setLocationByPlatform(true);

        jPanelChessBoard.setMaximumSize(new java.awt.Dimension(10000, 10000));
        jPanelChessBoard.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanelChessBoard.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jPanelChessBoardComponentResized(evt);
            }
        });

        javax.swing.GroupLayout jPanelChessBoardLayout = new javax.swing.GroupLayout(jPanelChessBoard);
        jPanelChessBoard.setLayout(jPanelChessBoardLayout);
        jPanelChessBoardLayout.setHorizontalGroup(
                jPanelChessBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 800, Short.MAX_VALUE)
        );
        jPanelChessBoardLayout.setVerticalGroup(
                jPanelChessBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 800, Short.MAX_VALUE)
        );

        jMenuGame.setText("Game");

        jMenuItemNew2P.setText("New 2-Player");
        jMenuItemNew2P.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNew2PActionPerformed(evt);
            }
        });
        jMenuGame.add(jMenuItemNew2P);

        jMenuItemNew1P.setText("New 1-Player");
        jMenuItemNew1P.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNew1PActionPerformed(evt);
            }
        });
        jMenuGame.add(jMenuItemNew1P);

        jMenuItemNew2C.setText("New 2-Player Challenge");
        jMenuItemNew2C.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNew2CActionPerformed(evt);
            }
        });
        jMenuGame.add(jMenuItemNew2C);

        jMenuItemNew1C.setText("New 1-Player Challenge");
        jMenuItemNew1C.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNew1CActionPerformed(evt);
            }
        });
        jMenuGame.add(jMenuItemNew1C);

        jMenuItemUndo.setText("Undo");
        jMenuItemUndo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemUndoActionPerformed(evt);
            }
        });
        jMenuGame.add(jMenuItemUndo);

        jMenuItemClose.setText("Close");
        jMenuItemClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCloseActionPerformed(evt);
            }
        });
        jMenuGame.add(jMenuItemClose);

        jMenuBarMain.add(jMenuGame);

        jMenuFile.setText("File");

        jMenuItemSave.setText("Save");
        jMenuItemSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSaveActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemSave);

        jMenuItemLoad.setText("Load");
        jMenuItemLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemLoadActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemLoad);

        jMenuBarMain.add(jMenuFile);

        setJMenuBar(jMenuBarMain);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanelChessBoard, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanelChessBoard, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                                .addContainerGap())
        );

        pack();
    }

    private void jMenuItemCloseActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
    }

    private void jMenuItemNew2PActionPerformed(java.awt.event.ActionEvent evt) { gameScreen.newGame();}

    private void jMenuItemLoadActionPerformed(java.awt.event.ActionEvent evt) {
        gameScreen.loadBoard();
    }

    private void jMenuItemSaveActionPerformed(java.awt.event.ActionEvent evt) {
        gameScreen.saveBoard();
    }

    private void jMenuItemUndoActionPerformed(java.awt.event.ActionEvent evt) {
        gameScreen.undo();
    }

    private void jMenuItemNew1PActionPerformed(java.awt.event.ActionEvent evt) {
        gameScreen.newAiGame();
    }

    private void jMenuItemNew1CActionPerformed(java.awt.event.ActionEvent evt) {
        gameScreen.newAiChallengeGame();
    }
    private void jMenuItemNew2CActionPerformed(java.awt.event.ActionEvent evt) {
        gameScreen.newChallengeGame();
    }

    private void jPanelChessBoardComponentResized(java.awt.event.ComponentEvent evt) {
        if (jPanelChessBoard != null && gameScreen != null) {
            int smallerDimension = jPanelChessBoard.getHeight();
            if (jPanelChessBoard.getWidth() < smallerDimension)
                smallerDimension = jPanelChessBoard.getWidth();
            gameScreen.setSize(smallerDimension, smallerDimension);
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Main chessGame = new Main();
                IntroFrame introFrame = new IntroFrame(chessGame, chessGame.gameScreen);
                introFrame.setVisible(true);
            }
        });
    }

    private javax.swing.JMenuBar jMenuBarMain;
    private javax.swing.JMenuItem jMenuItemClose;
    private javax.swing.JMenuItem jMenuItemLoad;
    private javax.swing.JMenuItem jMenuItemNew1P;
    private javax.swing.JMenuItem jMenuItemNew2P;
    private javax.swing.JMenuItem jMenuItemNew1C;
    private javax.swing.JMenuItem jMenuItemNew2C;
    private javax.swing.JMenuItem jMenuItemSave;
    private javax.swing.JMenuItem jMenuItemUndo;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenu jMenuGame;
    private javax.swing.JPanel jPanelChessBoard;
}
