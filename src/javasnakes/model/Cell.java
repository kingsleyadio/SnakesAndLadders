/*
 * This source belongs to the Snake and Ladder project of GROUP 1
 * of CPE 316 (Artificial Intelligence) class of 2010/2011 session at the
 * department of computer science and engineering, Obafemi Awolowo University
 * Ile-Ife.
 * This source file should not be used out of this context and it remains an
 * property of both the department and the people involved.
 */

package javasnakes.model;

/**
 * Provides the specifications for the cells.
 *
 * @author Group 1, CPE 316 (2010/2011)
 * @version 1.0
 */
public interface Cell {

    /**
     * Fetches the content of this cell as an array of seeds.
     * 
     * @return the content. The length of the returned array is the number of
     * seeds that are on the cell.
     */
    public Player[] getContent();

    /**
     * Fetches the address of this particular cell.
     *
     * @return the address.
     */
    public int getAddress();

    /**
     * Sets the address for this cell.
     *
     * @param address the address.
     */
    public void setAddress(int address);

    /**
     * Places the specified player on this cell. 
     * 
     * @param player the player to be added to this cell.
     */
    public void placePlayer(Player player);

    /**
     * Removes the specified player from this cell. This is particularly useful
     * when moving a seed from a cell to another. Just remove it from the
     * initial cell and place it on the destination cell.
     * 
     * @param player the player to be removed from this cell.
     */
    public void removePlayer(Player player);

    /**
     * Fetches the number of players that are on this cell;
     *
     * @return the number of players.
     */
    public int getPlayerCount();
}
