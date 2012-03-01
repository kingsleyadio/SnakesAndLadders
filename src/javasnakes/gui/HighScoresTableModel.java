/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javasnakes.gui;

import java.util.Properties;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author oladeji
 */
public class HighScoresTableModel extends AbstractTableModel {
    /**Data structure to hold the column titles.*/
    private static final String[] columnTitles = new String[] {"Name", "Score"};
    
    /**Data structure to hold the actual data to be displayed by the JTable.*/
    private Properties datastructure;
    private Integer[] dataArray;
    
    public HighScoresTableModel (Properties p) {
        this.datastructure = p;
        dataArray = datastructure.keySet().toArray(dataArray);
    }
    
    public HighScoresTableModel () {
        this (new Properties());
    }
    
    public Properties getDataStructure() {
        return this.datastructure;
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
        //forget the row and column and just return false
        return false;
    }
    
    @Override
    public String getColumnName(int column) {
        return columnTitles[column];
    }
    
    @Override
    public int getColumnCount() {
        return columnTitles.length;
    }
    
    @Override
    public int getRowCount() {
        return this.datastructure.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return datastructure.get(dataArray[rowIndex]);
        }
        else {
            return dataArray[rowIndex];
        }
    }
}
