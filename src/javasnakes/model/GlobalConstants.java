/*
 * This source belongs to the Snake and Ladder project of GROUP 1
 * of CPE 316 (Artificial Intelligence) class of 2010/2011 session at the
 * department of computer science and engineering, Obafemi Awolowo University
 * Ile-Ife.
 * This source file should not be used out of this context and it remains an
 * property of both the department and the people involved.
 */

package javasnakes.model;

import java.awt.Color;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javasnakes.engine.GameFeature;
import javax.imageio.ImageIO;

/**
 * Defines the constants for the entire game.
 *
 * @author Group 1, CPE 316 (2010/2011)
 * @version 1.0
 */
public class GlobalConstants {

    //defines the color constants that will be used to paint the game board
    public static final Color LIGHT_RED = new Color(255, 153, 153);
    public static final Color FAINT_RED = new Color(255, 204, 204);
    public static final Color LIGHT_BLUE = new Color(153, 153, 255);
    public static final Color FAINT_BLUE = new Color(204, 204, 255);
    public static final Color LIGHT_GRAY = new Color(153, 153, 153);
    public static final Color FAINT_GRAY = new Color(204, 204, 204);
    public static final Color LIGHT_YELLOW = new Color(255, 255, 153);
    public static final Color FAINT_YELLOW = new Color(255, 255, 204);

    //defines the board data for the game.
    public static final GameFeature[][] boardData;
    static {
        int[][] tempData = new int[10][10];
        
        tempData[9] = new int[]{0, 0, -66, 0, 0, 0, -70, 0, 0, 0};
        tempData[8] = new int[]{-58, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        tempData[7] = new int[]{0, 0, 0, +95, 0, -23, 0, 0, 0, 0};
        tempData[6] = new int[]{0, 0, 0, 0, 0, 0, 0, +89, 0, 0};
        tempData[5] = new int[]{+83, 0, 0, 0, -40, 0, 0, 0, 0, 0};
        tempData[4] = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        tempData[3] = new int[]{0, 0, 0, +51, 0, 0, 0, 0, 0, 0};
        tempData[2] = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, -8};
        tempData[1] = new int[]{0, 0, 0, 0, 0, 0, 0, +56, 0, 0};
        tempData[0] = new int[]{0, 0, 0, +32, 0, 0, 0, 0, 0, 0};
        
        GameFeature[][] myData = new GameFeature[10][10];

        for (int m = 0; m < 10; m++) {
            for (int n = 0; n < 10; n++) {
                myData[m][n] = GameFeature.getGameFeature(tempData[m][n]);
            }
        }
	
	boardData = myData;
    }
    
    //defines the rendering hints used for rendering the graphics of this game.
    public static final Map qualityHints;
    static {
	qualityHints = new HashMap();
	
        //apply antialiasing effect on the renderings to improve apprearance
        //(especially on LCD screens).
	qualityHints.put(RenderingHints.KEY_ANTIALIASING, 
		RenderingHints.VALUE_ANTIALIAS_ON);
	
	qualityHints.put(RenderingHints.KEY_TEXT_ANTIALIASING, 
		RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	
	qualityHints.put(RenderingHints.KEY_RENDERING, 
		RenderingHints.VALUE_RENDER_QUALITY);
    }
    
    public static Image BLACK_SEED, RED_SEED;
    {
        try {
            BLACK_SEED = ImageIO.read(getClass().getResourceAsStream("/black_seed.png"));
            RED_SEED = ImageIO.read(getClass().getResourceAsStream("/red_seed.png"));
        } catch (IOException ex) {
            Logger.getLogger(GlobalConstants.class.getName()).log(Level.SEVERE, "Unable to load the seeds.", ex);
        }
    }
}
