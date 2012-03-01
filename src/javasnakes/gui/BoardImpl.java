/*
 * This source belongs to the Snake and Ladder project of GROUP 1
 * of CPE 316 (Artificial Intelligence) class of 2010/2011 session at the
 * department of computer science and engineering, Obafemi Awolowo University
 * Ile-Ife.
 * This source file should not be used out of this context and it remains an
 * property of both the department and the people involved.
 */

/*
 * BoardImpl.java
 *
 * Created on Nov 12, 2011, 11:59:23 PM
 */
package javasnakes.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javasnakes.engine.GameEngine;
import javasnakes.engine.GameFeature;
import javasnakes.engine.GameFeatureType;
import javasnakes.engine.GameTimer;
import javasnakes.model.Board;
import javasnakes.model.Cell;
import javasnakes.model.Die;
import javasnakes.model.DieImpl;
import javasnakes.model.GlobalConstants;
import javasnakes.model.Player;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * This is the implementation of the <code>Board</code> interface with GUI 
 * and rendering capabilities.
 * 
 * @author Group 1, CPE 316 (2010/2011)
 * @version 1.0
 */
public class BoardImpl extends JPanel implements Board {

    private Cell[][] cellGrid = new Cell[10][10];
    private GameTimer gameTimer;
    private Die die;
    private static BoardImpl instance = new BoardImpl();
    private JPanel gridPanel;
    private SidePanel sidePanel;
    private int sidePanelWidth;

    /**
     * private constructor to make this class a singleton
     */
    private BoardImpl() {
        initGrid();
        initComponents();
        layoutComponents();

        sidePanelWidth = (int) (0.2
                * Toolkit.getDefaultToolkit().getScreenSize().width);

        gameTimer = new GameTimer(new LabelUpdater());
    }

    /**
     * Fetches the Board instance
     * 
     * @return the instance
     */
    public static BoardImpl getInstance() {
        if (instance == null) {
            return createNew();
        } else {
            return instance;
        }
    }
    
    /**
     * Creates a new game board.
     * 
     * @return the newly created board.
     */
    public static BoardImpl createNew() {
        instance = new BoardImpl();
        return instance;
    }

    @Override
    public Cell getCell(int address) {
        Point p = this.getLocation(address);
        return getCellGrid()[p.y][p.x];
    }

    @Override
    public Point getLocation(int address) {
        int x, y = 0;
        y = address / 10;
        x = ((y % 2) == 0) ? (address % 10) : (9 - (address % 10));

        return new Point(x, y);
    }

    @Override
    public int getAddressFor(int y, int x) {
        return ((y % 2) == 0) ? ((10 * y) + x) : ((10 * y) + (9 - x));
    }

    @Override
    public void move(Player player, int previousAddress, int count) {
        //remove seed from the previous cell
        getInstance().getCell(previousAddress).removePlayer(player);
        ((CellImpl) getInstance().getCell(previousAddress)).repaint();
        //place in new cell
        getInstance().getCell(previousAddress + count).placePlayer(player);
        ((CellImpl) getInstance().getCell(previousAddress + count)).repaint();
        //animateMove(player, previousAddress, previousAddress + count);
    }

    @Override
    public Cell[][] getCellGrid() {
        return this.cellGrid;
    }

    @Override
    public void indicateImpossibleMove() {
        //flash a message indicating that the move is impossible
        //this message stays for 2 seconds and then disappears
        //this does not mean that the game should wait for that period of time
        //For this reason, we will perform this action in the event dispatch
        //thread.
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                BoardImpl.getInstance().getSidePanel().getNotificationsLabel().setText("Move not permited.");
                /**try {
                    Thread.sleep(2 * 1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(BoardImpl.class.getName()).log(Level.SEVERE, 
                            "Thread unable to sleep for 2 seconds", ex);
                }*/
                BoardImpl.getInstance().getSidePanel().getNotificationsLabel().setText("");
            }
            
        });
    }

    @Override
    public void placePlayerOnBoard(Player player) {
        getCell(0).placePlayer(player);
        //repaint the cell to show give a visual feedback.
        ((CellImpl) getInstance().getCell(0)).repaint();
    }

    @Override
    public void setDieButtonEnabled(boolean enable) {
        getInstance().getSidePanel().getDieButton().setEnabled(enable);
    }

    /**
     * Sets the cell grid.
     * 
     * @param cellGrid the cellGrid to set
     */
    public void setCellGrid(Cell[][] cellGrid) {
        this.cellGrid = cellGrid;
    }

    /**
     * Fetches the game timer.
     * 
     * @return the gameTimer
     */
    public GameTimer getGameTimer() {
        return gameTimer;
    }

    /**
     * Sets the <code>GameTimer</code> object to be used with this object.
     * 
     * @param gameTimer the gameTimer to set
     */
    public void setGameTimer(GameTimer gameTimer) {
        this.gameTimer = gameTimer;
    }

    /**
     * Initializes the cells on the game board.
     */
    private void initGrid() {
        int address = 0;
        for (int a = 0; a < cellGrid.length; a++) {
            for (int b = 0; b < cellGrid[a].length; b++) {
                CellImpl cell = new CellImpl(address);
                
                if (a % 2 != 0) {
                    cellGrid[a][9 - b] = cell;
                    
                    GameFeature gf = GlobalConstants.boardData[a][9 - b];
                    if (gf != null) {
                        if (gf.getType() == GameFeatureType.LADDER) {
                            cell.setBackground(new Color(196,196,255));
                        } else {
                            cell.setBackground(new Color(255,196,196));
                        }
                    }
                } else {
                    cellGrid[a][b] = cell;
                    
                    GameFeature gf = GlobalConstants.boardData[a][b];
                    if (gf != null) {
                        if (gf.getType() == GameFeatureType.LADDER) {
                            cell.setBackground(new Color(196,196,255));
                        } else {
                            cell.setBackground(new Color(255,196,196));
                        }
                    }
                }
                address++;
            }
        }

        
        
        gridPanel = new JPanel();
    }

    /**
     * Initializes all the child components on this widget.
     */
    private void initComponents() {
        die = DieImpl.getInstance();
        sidePanel = new SidePanel();
        sidePanel.setOpaque(false);
        
        /*
        for (int i = 0; i <= 99; i++) {
            GameFeature gf = GlobalConstants.boardData[getLocation(i).y][getLocation(i).x];
            if (gf != null) {
                if (gf.getType() == GameFeatureType.LADDER) {
                    ((CellImpl) BoardImpl.getInstance().getCell(i)).setBackground(Color.CYAN);
                } else {
                    ((CellImpl) BoardImpl.getInstance().getCell(i)).setBackground(Color.DARK_GRAY);
                }
            }
        }
         */
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        Image wallpaper = null;
        try {
            wallpaper = ImageIO.read(getClass().getResourceAsStream("/my_green2.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(BoardImpl.class.getName()).log(Level.SEVERE,
                    "Unable to load the wallpaper.", ex);
        }

        g2D.drawImage(wallpaper, 0, 0, this.getSize().width,
                this.getSize().height, this);
        
    }

    /**
     * Does the laying out of the children components.
     */
    private void layoutComponents() {
        //layout the grid
        GridLayout gridLayout = new GridLayout(10, 10);
        gridLayout.setHgap(2);
        gridLayout.setVgap(2);
        gridPanel.setLayout(gridLayout);
        gridPanel.setOpaque(false);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        for (int a = cellGrid.length - 1; a >= 0; a--) {
            for (int b = 0; b < cellGrid[a].length; b++) {
                gridPanel.add((CellImpl) cellGrid[a][b]);
            }
        }

        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.X_AXIS);
        //FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER);
        this.setLayout(boxLayout);

        sidePanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));

        this.add(gridPanel);
        this.add(sidePanel);
    }

    /**
     * Implements an animated display when the seed gets moved.
     * 
     * @param player the current player.
     * @param previousAddress the players current address.
     * @param newAddress the destination address.
     */
    private void animateMove(Player player, int previousAddress, 
            int newAddress) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * The action to be performed when the die button is clicked.
     * 
     * @param evt the event that occurred
     */
    public void dieButtonActionPerformed(ActionEvent evt) {
        int dieValue = DieImpl.getInstance().roll();
        //display the die value
        
        Icon i = new ImageIcon(getClass().getResource("/dice_images/die" + dieValue + ".png"));
        
        //getSidePanel().getDieLabel().setText(Integer.toString(dieValue));
        getSidePanel().getDieLabel().setIcon(i);
        GameEngine.getInstance().movePlayer(dieValue);
    }

    /**
     * Fetches the <code>SidePanel</code> on this board.
     * 
     * @return the side panel.
     */
    public SidePanel getSidePanel() {
        return this.sidePanel;
    }

    /**
     * The inner class that updates the time JLabel. This class is an
     * <code>ActionListenre</code>. It updates the time label at every
     * <code>ActionEvent</code> that it receives.<br>
     * This class is intended to be used with timers. So objects of this class
     * are expected to be used for creating timers which in turn invokes the
     * <code>actionPerformed</code> method of this class to update the time
     * label with the timers current time.
     */
    class LabelUpdater implements java.awt.event.ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            BoardImpl.getInstance().getSidePanel().getTimeLabel().setText(
                    "ELAPSED TIME: " + e.getActionCommand());
        }
    }
}
