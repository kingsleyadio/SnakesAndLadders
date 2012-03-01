/*
 * This source belongs to the Snake and Ladder project of GROUP 1
 * of CPE 316 (Artificial Intelligence) class of 2010/2011 session at the
 * department of computer science and engineering, Obafemi Awolowo University
 * Ile-Ife.
 * This source file should not be used out of this context and it remains an
 * property of both the department and the people involved.
 */

package javasnakes.model;

import java.awt.Point;

/**
 * Provides the specifications for the game board.
 * The game board is a 100 cell (10 by 10) board similar to the board used for
 * draft game.
 *
 * @author Group 1, CPE 316 (2010/2011)
 * @version 1.0
 */
public interface Board {

    /**
     * Fetches the cell at the specified address.
     *
     * @param address the 1-based address of the desired cell. The addressing
     * scheme is such that the counting is continuous.
     * @return the cell at the specified address.
     */
    public Cell getCell(int address);

    /**
     * Fetches the location of the cell at the specified address.
     * <br>
     * The location is based on the cell being in an array of arrays where the
     * zero-based index of the outer array is specified first, then followed by
     * that of the inner array e.g address <code>1</code> corresponds to
     * location <code>[0,0]</code> while <code>3</code> corresponds to
     * <code>[0,3]</code> and 12 corresponds to <code>[1,8]</code>.
     *
     * @param address the address of the cell in consideration.
     * @return the location of the cell. Basically the <code>x</code> and
     * <code>y</code> fields of the returned object is the important thing.
     */
    public Point getLocation(int address);

    /**
     * Fetches the address for the cell at the specified location.
     *
     * @param y the index of the outer array (vertical) holding the cell.
     * @param x the index of the inner array (horizontal) holding the cell.
     * @return the address.
     */
    public int getAddressFor(int y, int x);

    /**
     * Moves the specified player from the previous address using the specified
     * count.
     *
     * @param player the player to be moved.
     * @param previousAddress the previous address of the player.
     * @param count the number of steps in which to move the player.
     */
    public void move(Player player, int previousAddress, int count);

    /**
     * Fetches the grid of cells.
     *
     * @return the cell grid.
     */
    public Cell[][] getCellGrid();

    public void indicateImpossibleMove();

    public void placePlayerOnBoard(Player player);

    public void setDieButtonEnabled(boolean enable);
}
