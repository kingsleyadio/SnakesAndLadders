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
 * Provides the specifications for the die.
 *
 * @author Group 1, CPE 316 (2010/2011)
 * @version 1.0
 */
public interface Die {

    /**
     * Rolls the die. The value returned lies between 1 and 6 (both inclusive).
     * 
     * @return the die value.
     */
    public int roll();
}
