/*
 * This source belongs to the Snake and Ladder project of GROUP 1
 * of CPE 316 (Artificial Intelligence) class of 2010/2011 session at the
 * department of computer science and engineering, Obafemi Awolowo University
 * Ile-Ife.
 * This source file should not be used out of this context and it remains an
 * property of both the department and the people involved.
 */

package javasnakes.model;

import java.util.Random;

/**
 * This class acts as a service provider for the instance service as 
 * specified in the <code><i>codebase</i>.api.Die</code> interface.
 *
 * @author Group 1, CPE 316 (2010/2011)
 * @version 1.0
 */
public class DieImpl implements Die {

    /**The Die object*/
    private static Die instance;
    
    /**
     * The random number generator used to simulate the rolling of the instance.
     */
    private Random random = new Random();

    /**
     * Private constructor to block illegal object creation.
     */
    private DieImpl() {
	//make this class a singleton
    }
    
    public static Die getInstance() {
	if (DieImpl.instance == null) {
	    DieImpl.instance = new DieImpl();
	}
	return instance;
    }
    
    @Override
    public int roll() {
        return 1 + random.nextInt(6);
    }

}
