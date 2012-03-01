/*
 * This source belongs to the Snake and Ladder project of GROUP 1
 * of CPE 316 (Artificial Intelligence) class of 2010/2011 session at the
 * department of computer science and engineering, Obafemi Awolowo University
 * Ile-Ife.
 * This source file should not be used out of this context and it remains an
 * property of both the department and the people involved.
 */
package javasnakes.engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Handles script interpretation. This enables the game to process specialized
 * script files, describing the layout of the game.
 <b>* The script file's layout is as follows:
 * <p> 
 * 
 * @author Group 1, CPE 316 (2010/2011)
 * @version 1.0
 */
public class ScriptInterpreter {
    private File scriptFile;
    private Scanner scanner;
    private GameFeature[][] boardData;

    /**
     * The main constructor.
     *
     * @param scriptFile the script file from which the game should be setup.
     * @throws FileNotFoundException it the specified script file could not be
     * found.
     */
    public ScriptInterpreter(File scriptFile) throws FileNotFoundException {
        this.scriptFile = scriptFile;
        this.scanner = new Scanner(scriptFile);
        boardData = new GameFeature[10][10];
        inteprete();
    }

    /**
     * Interpretes the script file.
     */
    private void inteprete() {
        
        //initialize every cell to free space
        for (int a = 0; a < getBoardData().length; a++) {
            Arrays.fill(getBoardData()[a], null);
        }

        int line = 9;
        while (true) {
            String lineData = scanner.nextLine();
            if (lineData == null) {
                //we have reached the end of the document, we can now stop
                //parsing
                break;
            } else if (lineData.equals("")) {
                //that means there is no special feature on this line
                line--;
                if (line < 0) {
                    break;
                }
            } else if (lineData.startsWith("#")) {
                //the line is a comment
                continue;
            } else {
                String[] data = lineData.split("\t");
                for (int a = 0; a < data.length; a++) {
                    GameFeature feature = null;
                    if (data[a].startsWith("-")) {
                        int address = Math.abs(Integer.parseInt(data[a]));
                        feature = new GameFeature(GameFeatureType.SNAKE, 
				address);
                    } else if (data[a].startsWith("+")) {
                        int address = Math.abs(Integer.parseInt(data[a]));
                        feature = new GameFeature(GameFeatureType.LADDER, 
				address);
                    }
                    boardData[line][a] = feature;
                }

                line--;
                if (line < 0) {
                    break;
                }
            }
        }
    }

    /**
     * Fetches the board data interpreted from the game script.
     *
     * @return the boardData
     */
    public GameFeature[][] getBoardData() {
        return boardData;
    }
}
