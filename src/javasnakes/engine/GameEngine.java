/*
 * This source belongs to the Snake and Ladder project of GROUP 1
 * of CPE 316 (Artificial Intelligence) class of 2010/2011 session at the
 * department of computer science and engineering, Obafemi Awolowo University
 * Ile-Ife.
 * This source file should not be used out of this context and it remains an
 * property of both the department and the people involved.
 */

package javasnakes.engine;

import java.awt.Dimension;
import java.awt.Point;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javasnakes.JavaSnakes;
import javasnakes.gui.BoardImpl;
import javasnakes.gui.CellImpl;
import javasnakes.gui.GameWindow;
import javasnakes.gui.HighScoresDialog;
import javasnakes.model.GlobalConstants;
import javasnakes.model.Player;
import javasnakes.model.Seed;
import javax.swing.JOptionPane;

/**
 * The game engine; defines the rules, handles scoring, save and resume 
 * functionality, etc. This class is a singleton since it doesn't make sense for
 * more than one game engine instance exist at runtime.
 *
 * @author Group 1, CPE 316 (2010/2011)
 * @version 1.0
 */
public class GameEngine {

    private static GameEngine instance;
    private Player[] players;
    private Map<Player, Integer> playerPositionMap;
    private int currentPlayer;
    private int moves;
    private int score;
    private int prevPosition, count;
    
    //initialize the highscores file (create a new file on disk if it doesn't 
    //exist
    private static final File scoreDir;
    static {
        scoreDir = new File(System.getProperty("user.home", ".") + "/.snakes316");
        scoreDir.mkdir();
    }
    private static final File highScoresFile;
    static {
        highScoresFile = new File(scoreDir, "highscores.db");
        try {
            highScoresFile.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, "Unable to create the highscores file.", ex);
        }
    }
    
    private GameStatus gameStatus;

    /**
     * The main constructor. Creates a new object of this class.
     */
    private GameEngine() {
        this.players = new Player[2];
        this.currentPlayer = 0;
        
        players[0] = new Player(Seed.BLACK_SEED, 1); //Human
        players[1] = new Player(Seed.WHITE_SEED, 0); //CPU
        playerPositionMap = new HashMap<Player, Integer>();
        playerPositionMap.put(players[0], -1);
        playerPositionMap.put(players[1], -1);
	
    }

    /**
     * This method returns a new instance of the class if not already available.
     * else returns the already implemented instance.
     * @return instance the instance of the GameEngine class.
     */
    public static GameEngine getInstance() {
	if (instance == null) {
	    instance = new GameEngine();
	}
        return instance;
    }
    
    /**
     * Creates a new game engine.
     * 
     * @return the newly created engine.
     */
    public static GameEngine createNew() {
        instance = new GameEngine();
        return instance;
    }
    
    /**
     * This method implements the player move from its present cell address to
     * the new address derived from the number that was generated from the die.
     * It checks if the player is already on board, if not, the player needs a "6"
     * before he gets an opportunity to start a game.
     * If player is already on board, it verifies a possible move before moving.
     * The player to reach the end of the board first is declared as the winner.
     * 
     * @param count the number returned by the die when rolled.
     */
    public void movePlayer(int count) {
        final Player player = players[currentPlayer];
        prevPosition = playerPositionMap.get(player);
        this.count = count;
        
        if (prevPosition >= 0) {
            
            if (verifyMove(prevPosition, count)) {
                BoardImpl.getInstance().move(player, prevPosition, count);
                int newPosition = prevPosition + count;
                playerPositionMap.put(player, newPosition);
                //player.setMoves(player.getMoves() + 1);
		
                if (playerPositionMap.get(player) == 99) {
                    if (currentPlayer == 0) {
                        moves++;
                        BoardImpl.getInstance().getSidePanel().getMovesLabel().setText("Moves: " + moves);
			gameStatus = GameStatus.WIN;
		    } else {
			gameStatus = GameStatus.LOSE;
		    }
                    endGame(gameStatus);
		    return;
                }
		
                if (currentPlayer == 0) {
                    moves++;

                    BoardImpl.getInstance().getSidePanel().getMovesLabel().setText("Moves: " + moves);
                }

		int y = BoardImpl.getInstance().getLocation(newPosition).y;
		int x = BoardImpl.getInstance().getLocation(newPosition).x;
		if (GlobalConstants.boardData[y][x] != null) {
                    
		    //recursive call
                    //really, this method call will never exceed a depth of 1
		    movePlayer(GlobalConstants.boardData[y][x].getDestination() - prevPosition - count);
                    return;
		}
                
            } else {
                BoardImpl.getInstance().indicateImpossibleMove();
            } 
        } else {
            
            if (count == 6) {
                playerPositionMap.put(player, 0);
                BoardImpl.getInstance().placePlayerOnBoard(player);
                BoardImpl.getInstance().getSidePanel().getSeedsPanel().removeSeed(
                        currentPlayer == 1 ? Seed.WHITE_SEED : Seed.BLACK_SEED);
            }
                
		if (currentPlayer == 0) {
                        moves++;
                BoardImpl.getInstance().getSidePanel().getMovesLabel().setText("Moves: " + moves);
                }
                
        }
        GameWindow.getInstance().drawGameGraphics();
        
        /**Point p = ((CellImpl) BoardImpl.getInstance().getCell(prevPosition)).getLocationOnScreen();
        Dimension dim = ((CellImpl) BoardImpl.getInstance().getCell(prevPosition)).getSize();
        GameWindow.getInstance().getGlassPane().repaint(p.x, p.y, dim.width, dim.height);
        Point p2 = ((CellImpl) BoardImpl.getInstance().getCell(prevPosition + count)).getLocationOnScreen();
        GameWindow.getInstance().getGlassPane().repaint(p2.x, p2.y, dim.width, dim.height);*/
        
        passPlayerControl();
    }

    /**
     * This method asserts that a move is possible before making that move.
     * returns true for possible move and false for an impossible move.
     * 
     * @param prevPosition the current player cell.
     * @param count the value returned by roll die method.
     * @return true or false depending on whether the move is possible.
     */
    private boolean verifyMove(int prevPosition, int count) {
        boolean returnValue = false;
	
        if ((prevPosition + count) < 100) {
	    returnValue = true;
	}
        
        return returnValue;
    }

    /**
     * This frees the current player and passes the game control to the 
     * next player on board.
     */
    private void passPlayerControl() {
        if (currentPlayer != players.length - 1) {
            currentPlayer++;
        } else {
            currentPlayer = 0;
        }
        
        if (players[currentPlayer].getType() == 0){
            // CPU
            BoardImpl.getInstance().getSidePanel().getNotificationsLabel()
                    .setText("CPU playing...");
        } else {
            // Human
            BoardImpl.getInstance().getSidePanel().getNotificationsLabel()
                    .setText("Its your turn");
        }

        if (players[currentPlayer].getType() == 0) {
            // remember, cpu: type = 0, current = 1
            // human: type = 1, current = 0
            computerPlay();
            //no need of calling passPlayerControl() here again
            //since computerPlay() will call move() which will call it.
        }
    }

    /**
     * This method is called to start a new game.
     * It loads all the necessary GUI features and allows the player to 
     * start playing.
     */
    public void startGame() {
        
        //let the board load all GUI features
        //tell first player to roll the die
        
        //reset all variables that pertain to game state
        moves = 0;
        BoardImpl.getInstance().getGameTimer().restart();
	
	//GameWindow.getInstance().repaint(200);
        GameWindow.getInstance().drawGameGraphics();
	BoardImpl.getInstance().getSidePanel().getMovesLabel().setText("Moves: 0");
        BoardImpl.getInstance().getSidePanel().getNotificationsLabel()
                .setText("Game started, please roll the die.");
	BoardImpl.getInstance().setDieButtonEnabled(true);
    }

    public void newGame() {
        int toContinue = JOptionPane.showConfirmDialog(GameWindow.getInstance(),
                "Are you sure to overwrite the current game?",
                "Restart Game", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (toContinue == JOptionPane.YES_OPTION) {
            GameWindow.getInstance().dispose();
            BoardImpl.createNew();
            GameWindow.createNew();
            GameEngine.createNew();
            JavaSnakes.main(new String[]{"me"});
            BoardImpl.getInstance().setDieButtonEnabled(true);
        }
    }

    /**
     * This method is called to end the game session that is in progress.
     * It can be called either explicitly when terminating a game in progress
     * or implicitly when a player wins the game.
     * 
     * @param status indicates whether the game was won or lost.
     */
    public void endGame(GameStatus status) {
	//process players' scores
	score = 1000 - (moves * 10);
	Properties highScores = fetchHighScores();
	List<Integer> scoreList = new ArrayList<Integer>();
	for (Object element : highScores.keySet()) {
	    scoreList.add(Integer.parseInt(element.toString()));
	}
	if (status == GameStatus.WIN) {
	    JOptionPane.showMessageDialog(GameWindow.getInstance(), 
		    "Congrats, you won!");

	    if ((highScores.size() >= 10) 
		    && (score > (Collections.min(scoreList)))) {
		String playerName = JOptionPane.showInputDialog(
			GameWindow.getInstance(),
			"Please enter your name.", "Highscore", 
			JOptionPane.QUESTION_MESSAGE);
		
		highScores.remove(Collections.min(scoreList));
		highScores.setProperty(Integer.toString(score), playerName);
		FileOutputStream fos;
		try {
		    //eliminate the possibility of a FileNotFoundException
		    highScoresFile.createNewFile();
		    fos = new FileOutputStream(highScoresFile);
		    BufferedOutputStream bos = new BufferedOutputStream(fos);
		    highScores.store(bos, playerName);
		} catch (FileNotFoundException ex) {
		    Logger.getLogger(GameEngine.class.getName()).log(
			    Level.SEVERE, "Unable to locate the higbscores "
			    + "file.", ex);
		} catch (IOException ex) {
		    Logger.getLogger(GameEngine.class.getName()).log(
			    Level.SEVERE, "An error occured while attempting "
			    + "to persist the highscore.", ex);
		}
		
		displayHighScores();
	    }

	} else {
	    JOptionPane.showMessageDialog(GameWindow.getInstance(), 
		    "Sorry, you lost.");
	}
	
	int toContinue = JOptionPane.showConfirmDialog(GameWindow.getInstance(),
		"Do you want to play another round?", 
		"Play Again?", JOptionPane.YES_NO_OPTION, 
		JOptionPane.QUESTION_MESSAGE);
	
	if (toContinue == JOptionPane.YES_OPTION) {
            GameWindow.getInstance().dispose();
            BoardImpl.createNew();
            GameWindow.createNew();
            GameEngine.createNew();
            JavaSnakes.main(new String[]{"me"});
            BoardImpl.getInstance().setDieButtonEnabled(true);
	} else {
	    System.exit(0);
	}
    }

    /**
     * This method is called when control passes to the computer.
     * it actually implements the computers turn to play.
     * Rolls the die and calls the appropriate method to act on it.
     */
    private void computerPlay() {
        BoardImpl.getInstance().setDieButtonEnabled(false);
        try {
            Thread.sleep(00);
        } catch (InterruptedException ex) {
            Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        BoardImpl.getInstance().dieButtonActionPerformed(null);
        BoardImpl.getInstance().setDieButtonEnabled(true);
    }

    /**
     * Fetches the previous high scores from the high score database and returns
     * them in a <code>Properties</code> object.<br>
     * These properties include the player name and his highscore.
     * 
     * @return the high score properties.
     */
    private Properties fetchHighScores() {
	Properties properties = new Properties();
	
	try {
            if (!highScoresFile.exists()) {
                highScoresFile.createNewFile();
            }
	    FileInputStream fis = new FileInputStream(highScoresFile);
	    BufferedInputStream bis = new BufferedInputStream(fis);
	    properties.load(bis);
	} catch (IOException ex) {
	    Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, 
		    "An error occured while attemping to load the highscores.", 
		    ex);
	}
	
	return properties;
    }

    /**
     * This method gets called either when the player triggers the high score 
     * action or when he has a new high score.<br>
     * It displays a processed format of the high score properties.
     */
    public void displayHighScores() {
	(new HighScoresDialog(GameWindow.getInstance(), true)).setVisible(true);
    }
    
}
