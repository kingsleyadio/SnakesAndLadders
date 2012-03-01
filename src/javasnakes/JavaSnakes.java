/*
 * This source belongs to the Snake and Ladder project of GROUP 1
 * of CPE 316 (Artificial Intelligence) class of 2010/2011 session at the
 * department of computer science and engineering, Obafemi Awolowo University
 * Ile-Ife.
 * This source file should not be used out of this context and it remains an
 * property of both the department and the people involved.
 */
package javasnakes;

import java.util.logging.Level;
import java.util.logging.Logger;
import javasnakes.engine.GameEngine;
import javasnakes.gui.GameWindow;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * This is the application launcher.
 *
 * @author Group 1, CPE 316 (2010/2011)
 * @version 1.0
 */
public class JavaSnakes {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            //load the native system look and feel so as to make the user feel 
            //more at home.
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JavaSnakes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(JavaSnakes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(JavaSnakes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(JavaSnakes.class.getName()).log(Level.SEVERE, null, ex);
        }
        
	//display the game window on screen.
        GameWindow.getInstance().setVisible(true);
        GameEngine.getInstance().startGame();
    }
}
