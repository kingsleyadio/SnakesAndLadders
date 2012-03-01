/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javasnakes.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.EnumSet;
import java.util.Set;
import javasnakes.model.GlobalConstants;
import javasnakes.model.Seed;
import javax.swing.JPanel;

/**
 * This class sets up the panel where the seeds will be initially placed.
 * It also holds the seeds in place until the player is able to move it unto the game board.
 * @author Group 1, CPE 316 (2010/2011)
 * @version 1.0
 */
public class SeedsPanel extends JPanel {
    private Set<Seed> seedSet;
    
    /**
     * the constructor.
     */
    public SeedsPanel() {
	seedSet = EnumSet.allOf(Seed.class);
	setPreferredSize(new Dimension(120, 50));
    }
    
    @Override
    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	Graphics2D g2D = (Graphics2D) g;
	g2D.setRenderingHints(GlobalConstants.qualityHints);
	
	int myX = 20;
	for (Seed element : seedSet) {
	    
	    if (element == Seed.BLACK_SEED) {
		g2D.setColor(Color.BLACK);
                g2D.drawImage(CellImpl.BLACK_SEED_IMAGE, myX, 0, 70, 70, this);
	    } else {
		g2D.setColor(Color.RED);
                g2D.drawImage(CellImpl.RED_SEED_IMAGE, myX, 0, 70, 70, this);
	    }
	    
	    //g2D.fillOval(myX, 0, 70, 70);
	    myX += 80;
	}
	
    }
    
    /**
     * This method places the seed on the panel.
     * @param seed the seed to be added
     */
    public void addSeed(Seed seed) {
	seedSet.add(seed);
        repaint();
    }
    
    /**
     * This method removes a seed from the panel.
     * @param seed the seed to be removed.
     */
    public void removeSeed(Seed seed) {
	seedSet.remove(seed);
        repaint();
    }
    
    /**
     * checks the presence of a particular seed on the panel and returns true if present, else returns false.
     * @param seed seed to check
     * @return true or false
     */
    public boolean containsSeed(Seed seed) {
	return seedSet.contains(seed);
    }
    
    /**
     * This returns the number of seeds present on the seed panel at a particular time.
     * @return number of seeds available.
     */
    public int getNumberOfSeeds() {
	return seedSet.size();
    }
}
