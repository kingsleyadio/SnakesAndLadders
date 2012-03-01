/*
 * This source belongs to the Snake and Ladder project of GROUP 1
 * of CPE 316 (Artificial Intelligence) class of 2010/2011 session at the
 * department of computer science and engineering, Obafemi Awolowo University
 * Ile-Ife.
 * This source file should not be used out of this context and it remains an
 * property of both the department and the people involved.
 */

package javasnakes.engine;

/**
 * This is a value-object class that represents a game feature. A game feature
 * can either be a Snake or a Ladder as specified by a field in the object.
 * A game feature also holds a destination which is the address of the cell that
 * it points to. For example a snake on cell 84 may have a destination of 17,
 * which means that the head of the snake will be at 84 and the tail at 16.
 *
 * @author Group 1, CPE 316 (2010/2011)
 * @version 1.0
 */
public class GameFeature {
    private int destination;
    private GameFeatureType type;

    /**
     * The main constructor.
     * 
     * @param type the game feature type.
     * @param destination the destination this feature points to.
     */
    public GameFeature(GameFeatureType type, int destination) {
        //a bit of secure programming here (as opposed to contract programming).
        //ensure that the destination never gets less that zero.
        if (destination >= 0) {
            this.destination = destination;
        } else {
            destination = 0;
        }
        
        this.type = type;
    }

    /**
     * Sets the game feature type.
     *
     * @param type the type to set.
     */
    public void setType(GameFeatureType type) {
        this.type = type;
    }

    /**
     * Fetches this game feature's type.
     * 
     * @return the game feature type.
     */
    public GameFeatureType getType() {
        return this.type;
    }
    
    /**
     * Fetches the destination.
     * 
     * @return the destination.
     */
    public int getDestination() {
        return this.destination;
    }

    /**
     * Sets the destination.
     *
     * @param destination the destination to set.
     */
    public void setDestination(int destination) {
        this.destination = destination;
    }
    
    /**
     * This is a utility method for creating objects of this class.<br>
     * This method creates a game feature based on the number passed to it.
     * <br>If the number passed to it is negative, a game feature
     * of type <code>GameFeatureType.SNAKE</code> is created, if it is positive,
     * a game feature of type <code>GameFeatureType.LADDER</code> is created.
     * <br> In any case, the <code>destination</code> of the returned game
     * feature is the magnitude of the number received as argument.<br>
     * If the argument is zero, null is returned.
     * 
     * @param number the number from which the game feature is to be created.
     * @return feature the created game feature.
     */
    public static GameFeature getGameFeature(int number) {
        GameFeature feature = null;
        if (number < 0) {
            feature = new GameFeature(GameFeatureType.SNAKE, Math.abs(number));
        } else if (number > 0) {
            feature = new GameFeature(GameFeatureType.LADDER, Math.abs(number));
        }
        return feature;
    }
}
