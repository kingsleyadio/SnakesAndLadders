/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javasnakes.gui;

import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author oladeji
 */
public class MyGlassPane extends JPanel {
    
    private static MyGlassPane instance;
    
    public MyGlassPane() {
        super();
        setOpaque(false);
        setVisible(true);
    }
    
    public static MyGlassPane getInstance() {
        if (instance == null) {
            instance = new MyGlassPane();
        }
        return instance;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(GameWindow.BOARD_DECORATION, 0, 0, this);
    }
    
}
