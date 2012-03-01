/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javasnakes.model;

/**
 *
 * @author Group 1, CPE 316 (2010/2011)
 * @version 1.0
 */
public class Player {
    private final Seed seed;
    /*
     * this identifies whether a player is Human or CPU
     * 0 stands for CPU
     * any other number stands for Human
     */
    private int type;
    private int moves;
    
    public Player(Seed seed, int type) {
        this.seed = seed;
        this.type = type;
        this.moves = 0;
    }

    /**
     * @return the seed
     */
    public Seed getSeed() {
        return seed;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @return the moves
     */
    public int getMoves() {
        return moves;
    }

    /**
     * @param moves the moves to set
     */
    public void setMoves(int moves) {
        this.moves = moves;
    }
    
}
