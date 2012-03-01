/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javasnakes.gui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javasnakes.engine.GameEngine;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author oladeji
 */
public class GameWindow extends JFrame {
    
    private static GameWindow instance;

    private JMenuBar mb;
    private JMenu gameMenu, helpMenu;
    private JMenuItem newMenuItem, openMenuItem, saveMenuItem, 
            highScoresMenuItem, exitMenuItem, helpMenuItem, aboutMenuItem;
    private ActionListener actionHandler;
    private final WindowListener windowHandler;
    private final WindowFocusListener windowFocusHandler;
    public static Image BOARD_DECORATION;
    
    private GameWindow() {
        
        try {
            BOARD_DECORATION = ImageIO.read(getClass().getResourceAsStream("/board.png"));
        } catch (IOException ex) {
            Logger.getLogger(CellImpl.class.getName()).log(Level.SEVERE, "Unable to load the seed.", ex);
        }
        
        actionHandler = new ActionHandler();
        windowHandler = new WindowHandler();
        windowFocusHandler = new WindowFocusHandler();
	initComponents();
        
        MyGlassPane.getInstance().setVisible(true);
        setGlassPane(MyGlassPane.getInstance());
	
	//make the window fullscreen
        ImageIcon i = new ImageIcon(getClass().getResource("/icon.png"));
        setIconImage(i.getImage());
        setTitle("Snakes and Ladders");
	setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(windowHandler);
        addWindowFocusListener(windowFocusHandler);
    }
    
    public static GameWindow getInstance() {
	if (instance == null) {
	    return createNew();
	}else {
            return instance;
        }
    }
    
    public static GameWindow createNew() {
        BoardImpl.createNew();
        instance = new GameWindow();
        return instance;
    }

    private void initComponents() {
	mb = new JMenuBar();
        gameMenu = new JMenu("Game");
        helpMenu = new JMenu("Help");
        
        newMenuItem = new JMenuItem("New");
        newMenuItem.setActionCommand("new");
        newMenuItem.addActionListener(actionHandler);
        
        openMenuItem = new JMenuItem("Open...");
        openMenuItem.setActionCommand("open");
        openMenuItem.addActionListener(actionHandler);
        openMenuItem.setEnabled(false);
        
        saveMenuItem = new JMenuItem("Save...");
        saveMenuItem.setActionCommand("save");
        saveMenuItem.addActionListener(actionHandler);
        saveMenuItem.setEnabled(false);
        
        highScoresMenuItem = new JMenuItem("High Scores...");
        highScoresMenuItem.setActionCommand("highScores");
        highScoresMenuItem.addActionListener(actionHandler);
        highScoresMenuItem.setEnabled(false);
        
        exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setActionCommand("exit");
        exitMenuItem.addActionListener(actionHandler);
        
        helpMenuItem = new JMenuItem("Help Contents...");
        helpMenuItem.setActionCommand("help");
        helpMenuItem.addActionListener(actionHandler);
        
        aboutMenuItem = new JMenuItem("About...");
        aboutMenuItem.setActionCommand("about");
        aboutMenuItem.addActionListener(actionHandler);
        
        gameMenu.add(newMenuItem);
        gameMenu.addSeparator();
        gameMenu.add(openMenuItem);
        gameMenu.add(saveMenuItem);
        gameMenu.addSeparator();
        gameMenu.add(highScoresMenuItem);
        gameMenu.addSeparator();
        gameMenu.add(exitMenuItem);
        
        helpMenu.add(helpMenuItem);
        helpMenu.addSeparator();
        helpMenu.add(aboutMenuItem);
        
        mb.add(gameMenu);
        mb.add(helpMenu);
        
        setJMenuBar(mb);
        
        //add the board
        getContentPane().add(BoardImpl.getInstance(), BorderLayout.CENTER);
    }

    public void drawGameGraphics() {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                GameWindow.getInstance().getGlassPane().setVisible(true);
                Graphics g = GameWindow.getInstance().getGlassPane().getGraphics();
                Graphics2D g2D = (Graphics2D) g;
                g2D.drawImage(BOARD_DECORATION, 0, 0, GameWindow.getInstance().getGlassPane());
                
                /*
                g2D.setRenderingHints(GlobalConstants.qualityHints);
                Rectangle bounds = GameWindow.getInstance().getBounds();
                Rectangle drawingArea = new Rectangle(bounds.x,
                        bounds.y + GameWindow.getInstance().getJMenuBar().getHeight(),
                        bounds.width, bounds.height);

                for (int a = 0; a < 10; a++) {
                    for (int b = 0; b < 10; b++) {
                        if (GlobalConstants.boardData[a][b] != null) {
                            CellImpl presentCell = (CellImpl) BoardImpl.getInstance().getCellGrid()[a][b];
                            CellImpl destinationCell = (CellImpl) BoardImpl.getInstance().getCell(GlobalConstants.boardData[a][b].getDestination());
                            //Point p = gameBoard.getLocation();
                            //System.out.println("[" + p.x + ", " + p.y + "]");

                            //System.out.println("destcell: " + destinationCell);
                            Point presentPoint = presentCell.getLocationOnScreen();
                            presentPoint.x += (presentCell.getWidth() / 2);
                            presentPoint.y += (presentCell.getHeight() / 2);
                            Point destinationPoint = destinationCell.getLocationOnScreen();
                            destinationPoint.x += (destinationCell.getWidth() / 2);
                            destinationPoint.y += (destinationCell.getHeight() / 2);

                            //System.out.println(presentPoint);

                            if (GlobalConstants.boardData[a][b].getType() == GameFeatureType.LADDER) {
                                g2D.setColor(Color.BLUE);
                                Stroke s = new BasicStroke(4);
                                g2D.setStroke(s);
                                g2D.drawLine(presentPoint.x, presentPoint.y,
                                        destinationPoint.x, destinationPoint.y);
                            } else {
                                //this means we have a snake

                                g2D.setColor(Color.RED);
                                /*g2D.drawLine(presentPoint.x, presentPoint.y,
                                        destinationPoint.x, destinationPoint.y);
                 * 
                                //WARNING - deep magic begins here!
                                Image snake = null;
                                try {
                                    snake = ImageIO.read(getClass().getResourceAsStream("/snake.png"));
                                } catch (IOException ex) {
                                    Logger.getLogger(BoardImpl.class.getName()).log(Level.SEVERE,
                                            "Unable to load the wallpaper.", ex);
                                }

                                double angle = Math.atan((presentPoint.y - destinationPoint.y) / (presentPoint.x - destinationPoint.x));
                                //g2D.drawImage(snake, 0, 0, this.getSize().width,
                                //this.getSize().height, this);

                                
                                AffineTransform xform = new AffineTransform();
                                xform.scale(Math.abs(presentPoint.x - destinationPoint.x) / snake.getWidth(rootPane), 1);
                                xform.rotate(angle) ;
                                g2D.drawImage(snake, xform, this);


                                /*
                                g2D.rotate(angle);
                                g2D.dr
                                g2D.drawImage(snake, presentPoint.x, presentPoint.y, 
                                Math.abs(presentPoint.x - destinationPoint.x), 
                                Math.abs(presentPoint.y - destinationPoint.y), this);
                                //keep the orientation of the rendering consistent
                                g2D.rotate(-angle);
                            }
                        }

                    }
                    
                }
                 */
            }
        });
    }

    private class ActionHandler implements ActionListener {

        public ActionHandler() {
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (ae.getActionCommand().equals("new")) {
                newActionPerformed();
            } else if (ae.getActionCommand().equals("open")) {
                openActionPerformed();
            } else if (ae.getActionCommand().equals("save")) {
                saveActionPerformed();
            } else if (ae.getActionCommand().equals("highScores")) {
                highScoresActionPerformed();
            } else if (ae.getActionCommand().equals("exit")) {
                exitActionPerformed();
            } else if (ae.getActionCommand().equals("help")) {
                helpActionPerformed();
            } else if (ae.getActionCommand().equals("about")) {
                aboutActionPerformed();
            }
        }

        void newActionPerformed() {
            GameEngine.getInstance().newGame();
        }

        void openActionPerformed() {
            throw new UnsupportedOperationException("Not yet implemented");
        }

        void saveActionPerformed() {
            throw new UnsupportedOperationException("Not yet implemented");
        }

        void highScoresActionPerformed() {
            GameEngine.getInstance().displayHighScores();
        }

        void exitActionPerformed() {
            int toExit = JOptionPane.showConfirmDialog(GameWindow.getInstance(),
                    "Are you sure you want to exit?", "Oh no! Don't shy away please...", 
                    JOptionPane.YES_NO_OPTION);
            if (toExit == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }

        void helpActionPerformed() {
            StringBuilder message = new StringBuilder();
            message.append("<HTML><BODY><FONT SIZE=+1 COLOR=2266CC><B>"
                        + "<u>Help</u>"
                        + "</B></FONT> <br><br>"
                        + "Your aim is to play your way through to tile <code>100</code>.<br>"
                        + "You roll the die when its your turn to advance.<br>"
                        + "If you end up at the buttom of a ladder, you climb it automatically.<br>"
                        + "If you end up in the mouth of a snake, you slide down to its tail.<br><br>"
                        + "->To start a new game, click on <i>New</i> under the \"Game\" menu.<br>"
                        + "->To save your current game, click on \"Save\" under the \"Game\" menu.<br>"
                        + "->To continue a previously saved game, click on \"Open\" under the \"Game\" menu <br>"
                        + "&nbsp &nbsp and select the file in which your previous game was saved.<br>"
                        + "->To view the high scores, click on \"High Scores\" under the \"Game\" menu.<br>"
                        + "->To exit the game at anytime, simply click on \"Exit\" "
                        + "under the \"Game\" menu.<br><br>"
                        + "</HTML>");
                JOptionPane.showMessageDialog(GameWindow.getInstance(), message.toString(),
                        "About", JOptionPane.INFORMATION_MESSAGE);
        }

        void aboutActionPerformed() {
            StringBuilder message = new StringBuilder();
            message.append("<HTML><FONT SIZE=+1 COLOR=2266CC><B>"
                        + "CPE 316 (Artificial Intelligence) Project."
                        + "</B></FONT> <br><br>"
                        + "This application forms the Programming Assignment of"
                        + " CPE 316 (Artificial Intelligence)<br> of Group 1 for "
                        + "the 2010/2011 session.<br><br>"
                        + "Special thanks to <b>Dr. Oluwaranti</b><br><br>"
                        + "<u>Developers</u><br>"
                        + "<ul>"
                        + "<li>Oladeji Oluwasayo<br>"
                        + "<li>Adio Kingsley<br>"
                        + "</ul>"
                        /**+ "<u>Group Members</u><br>"
                        + ""
                        + ""
                        + ""
                        + ""*/
                        + "</HTML>");
                JOptionPane.showMessageDialog(GameWindow.getInstance(), message.toString(),
                        "About", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private class WindowHandler extends WindowAdapter {

        public WindowHandler() {
        }

        @Override
        public void windowOpened(WindowEvent e) {
            drawGameGraphics();
            //GameWindow.getInstance().getGlassPane().repaint();
        }

        @Override
        public void windowClosing(WindowEvent e) {
            int toExit = JOptionPane.showConfirmDialog(GameWindow.getInstance(),
                    "Are you sure you want to exit?", "Oh no! Don't shy away please...", 
                    JOptionPane.YES_NO_OPTION);
            if (toExit == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }

        @Override
        public void windowDeiconified(WindowEvent e) {
            drawGameGraphics();
            //GameWindow.getInstance().getGlassPane().repaint();
        }

        @Override
        public void windowActivated(WindowEvent e) {
            drawGameGraphics();
            //GameWindow.getInstance().getGlassPane().repaint();
        }
    }
    

    private class WindowFocusHandler implements WindowFocusListener {

        public WindowFocusHandler() {
        }

        @Override
        public void windowGainedFocus(WindowEvent e) {
            drawGameGraphics();
            //GameWindow.getInstance().getGlassPane().repaint();
        }

        @Override
        public void windowLostFocus(WindowEvent e) {
            drawGameGraphics();
            //GameWindow.getInstance().getGlassPane().repaint();
        }
    }
}
