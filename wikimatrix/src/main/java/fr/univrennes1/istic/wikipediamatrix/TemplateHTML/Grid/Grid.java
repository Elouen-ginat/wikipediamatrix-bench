package fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Grid;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Balise;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Table;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Void;

public class Grid {

    private List<List<Balise>> grid;

    // Does this grid is a table or just a display balise
    private boolean from_table = true;

    public final static Balise DEFAULT_VALUE = new Void();

    public Grid() {
        this.grid = new ArrayList<List<Balise>>();
    }

    public Grid(Balise final_balise) {
        this.grid = new ArrayList<List<Balise>>();
        ArrayList<Balise> row = new ArrayList<Balise>();
        row.add(final_balise);
        this.grid.add(row);
        this.from_table = final_balise.getFromTable();
    }

    public void span(int span_row, int span_col) {

        // Make the grid a square
        this.squareUp();

        int init_size = this.getRowSize();

        // Span row
        for (int j = 0; j < span_row-1; j++) {
            for (int i = 0; i < init_size; i++) {
                List<Balise> row_copy = new ArrayList<Balise>();
                row_copy.addAll(this.getRow(i));
                grid.add(row_copy);
            }
        }

        init_size = this.getRowSize();

        // Span col
        for (int i = 0; i < init_size; i++) {
            List<Balise> row_copy = new ArrayList<Balise>();
            row_copy.addAll(this.getRow(i));
            for (int j = 0; j < span_col-1; j++) {
                this.getRow(i).addAll(row_copy);
            }
        }
    }

    public void mergeRow(Grid grid_row, int i) {
        // Square both grids
        this.squareUp();
        grid_row.squareUp();
        // Get starting pos of merging
        int j = 0;
        Balise value = null;
        if (i < this.getRowSize()) {
            for (j = 0; j < this.getRow(i).size(); j++) {
                value = this.getValue(i, j);
                if (value == Grid.DEFAULT_VALUE) {
                    break;
                }
            }
        }
        if (value != Grid.DEFAULT_VALUE) {
            j = 0;
        }
         
        // Fill values in grid with grid_row values
        int last_m = j;
        for (int l = 0; l < grid_row.getMaxColSize(); l++) {
            for (int m = last_m; m < grid_row.getMaxColSize()+last_m; m++) {
                if (this.isEmpty(i, m)) {
                    for (int k = 0; k < grid_row.getRowSize(); k++) {
                        value = grid_row.getValue(k, l);
                        this.setValue(value, k+i, m);
                    }
                    last_m = m+1;
                    break;
                }
            }
        }
    }

    public void mergeCol(Grid grid_col, int j) {
        // Square both grids
        this.squareUp();
        grid_col.squareUp();
        // Get starting pos of merging
        int i = 0;
        Balise value = new Table();
        if (j < this.getMaxColSize()) {
            for (i = 0; i < this.getRowSize(); i++) {
                value = this.getValue(i, j);
                if (value == Grid.DEFAULT_VALUE) {
                    break;
                }
            }
        }
        if (value != Grid.DEFAULT_VALUE) {
            i = 0;
        }

        // Fill values in grid with grid_row values
        for (int k = 0; k < grid_col.getMaxColSize(); k++) {
            for (int l = 0; l < grid_col.getRowSize(); l++) {
                value = grid_col.getValue(l, k);
                for (int m = i; m < grid_col.getRowSize()+i; m++) {
                    if (this.isEmpty(m, k+j)) {
                        this.setValue(value, m, k+j);
                        break;
                    }
                }
            }
        }
    }

    public boolean isEmpty(int i, int j) {
        if (i >= this.getRowSize()) {
            return true;
        }else if (j >= this.getRow(i).size()) {
            return true;
        }else if (this.getValue(i, j) == Grid.DEFAULT_VALUE) {
            return true;
        }
        return false;
    }

    public Balise getValue(int i, int j) {
        return this.getRow(i).get(j);
    }

    public void setValue(Balise value, int i, int j) {
        if (i == this.getRowSize()) {
            List<Balise> new_row = new ArrayList<Balise>();
            while(new_row.size() < j) new_row.add(Grid.DEFAULT_VALUE);
            new_row.add(value);
            this.grid.add(new_row); 
        }else if (j >= this.getRow(i).size()) {
            while(this.getRow(i).size() < j) this.getRow(i).add(Grid.DEFAULT_VALUE);
            this.getRow(i).add(value);
        }else if (j < this.getRow(i).size()) {
            this.getRow(i).set(j, value);
        }
    }

    public Grid expend() {
        // Get all table object in the grid
        List<Balise> tables = new ArrayList<Balise>();
        for (int i = 0; i < this.getRowSize(); i++) {
            for (int j = 0; j < this.getRow(i).size(); j++) {
                Balise balise = this.getValue(i, j);
                if (balise instanceof Table) {
                    tables.add(balise);
                }
            }
        }
        if (tables.isEmpty()) {
            return this;
        }
        // Recursive expends
        Set<Balise> tables_set = new HashSet<Balise>(tables);
        for (Balise table : tables_set) {
            table.setGrid(table.getGrid().expend());
        }
        // Squareup
        this.squareUp();

        // Get expention of each row and col
        List<Integer> row_exp_l = new ArrayList<Integer>();
        List<Integer> col_exp_l = new ArrayList<Integer>();
        for (int i = 0; i < this.getRowSize(); i++) {
            int max_exp = 1;
            for (int j = 0; j < this.getRow(i).size(); j++) {
                Balise value = this.getValue(i, j);
                if (value instanceof Table) {
                    max_exp = Math.max(max_exp, value.getGrid().getRowSize());
                }
            }
            row_exp_l.add(max_exp);
        }
        for (int j = 0; j < this.getMaxColSize(); j++) {
            int max_exp = 1;
            for (int i = 0; i < this.getRowSize(); i++) {
                Balise value = this.getValue(i, j);
                if (value instanceof Table) {
                    max_exp = Math.max(max_exp, value.getGrid().getMaxColSize());
                }
            }
            col_exp_l.add(max_exp);
        }

        // Expend the grid with nested tables grids
        Grid exp_grid = new Grid();
        for (int i = 0; i < this.getRowSize(); i++) {
            Grid exp_grid_row = new Grid();
            for (int j = 0; j < this.getRow(i).size(); j++) {
                int row_exp = (int) row_exp_l.get(i);
                int col_exp = (int) col_exp_l.get(j);
                Balise value = this.getValue(i, j);
                Grid exp_value = null;
                if (value instanceof Table) {
                    exp_value = value.getGrid();
                }else {
                    exp_value = new Grid(value);
                    exp_value.span(row_exp, col_exp);
                }
                exp_grid_row.mergeCol(exp_value, exp_grid_row.getMaxColSize());
            }
            exp_grid.mergeRow(exp_grid_row, exp_grid.getRowSize());
        }
        return exp_grid;
    }

    public void squareUp() {
        int col_size = this.getMaxColSize();
        for (List<Balise> row : this.grid) {
            while (row.size() < col_size) {
                row.add(Grid.DEFAULT_VALUE);
            }
        }
    }

    public String[][] toStringArray() {
        String[][] string_array = new String[this.getRowSize()][this.getMaxColSize()];

        this.squareUp();
        for (int i = 0; i < this.getRowSize(); i++) {
            for (int j = 0; j < this.getMaxColSize(); j++) {
                Balise value = this.getValue(i, j);
                string_array[i][j] = value.getInfo();
            }
        }
        return string_array;
    }

    public int getMaxColSize() {
        int size = 0;
        for (List<Balise> row : this.grid) {
            size = Math.max(size, row.size());
        }
        return size;
    }

    public int getRowSize() {
        return grid.size();
    }

    public List<Balise> getRow(int i) {
        return this.grid.get(i);
    }

    public boolean getFromTable() {
        return this.from_table;
    }

    public void setFromTable(boolean from_table) {
        this.from_table = from_table;
    }

    public String toString() {
        String result = "";
        for(int i = 0; i < this.getRowSize(); i++){
            for(int j = 0; j < this.getRow(i).size(); j++){
                Balise val = this.getValue(i, j);
                result += val.getTag();
                result += " ";
            }
            result += "\n";
        }
        return result;
    }
}
