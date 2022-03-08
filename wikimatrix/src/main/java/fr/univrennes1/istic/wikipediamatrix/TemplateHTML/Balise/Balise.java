package fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.nodes.Element;

import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Visitor.Visitor;

public abstract class Balise {
    
    private String tag;
    private Element self;
    private Balise parent;
    private ArrayList<Balise> children = new ArrayList<Balise>();
    private int num_row;
    private int num_col;
    private int span_row = 1;
    private int span_col = 1;
    private int depth = 0;

    private List<List<String>> grid = new ArrayList<List<String>>();

    public Balise(Element self, Balise parent) {
        this.tag = self.tagName();
        this.self = self;
        this.parent = parent;
        if (self.attributes().hasKey("rowspan")) {
            this.span_row = (int) Integer.parseInt(self.attr("rowspan"));
        }
        if (self.attributes().hasKey("colspan")) {
            this.span_col = (int) Integer.parseInt(self.attr("colspan"));
        }
        this.num_row = this.span_row;
        this.num_col = this.span_col;
    }
    public Balise(String tag) {
        this.tag = tag;
    }

    public abstract void accept(Visitor visitor);

    public abstract String getInfo();

    public boolean isFinal() {
        return children.isEmpty();
    }

    public void initGrid() {
        this.grid = new ArrayList<List<String>>();
        ArrayList<String> col = new ArrayList<String>();
        col.add(this.getInfo());
        this.grid.add(col);
    }

    public void spanGrid(List<List<String>> grid) {

        // Span row
        for (List<String> row : grid) {
            List<String> row_copy = new ArrayList<String>();
            row_copy.addAll(row);
            for (int i = 0; i < this.span_row; i++) {
                grid.add(row_copy);
            }
        }

        // Span col
        for (List<String> row : grid) {
            List<String> row_copy = new ArrayList<String>();
            row_copy.addAll(row);
            for (int i = 0; i < this.span_col; i++) {
                row.addAll(row_copy);
            }
        }
    }

    public List<List<String>> mergeGridRow(List<List<String>> grid1, List<List<String>> grid2) {
        int i = 0;
        int j = 0;
        String value = "";
        for (i = 0; i < grid1.size(); i++) {
            List<String> row1 = grid1.get(i);
            for (j = 0; j < row1.size(); j++) {
                value = row1.get(j);
                if (value == null) {
                    break;
                }
            }
            if (value == null) {
                break;
            }
        }
        if (value == null) {

        }



        
        return grid1;
    }

    public List<List<String>> mergeGridCol(List<List<String>> grid1, List<List<String>> grid2) {
        return null;
    }

    public List<List<String>> getGrid() {
        return this.grid;
    }

    public void setGrid(List<List<String>> grid) {
        this.grid = grid;
    }

    public String getTag() {
        return this.tag;
    }

    public Element getElement() {
        return this.self;
    }

    public Balise getParent() {
        return this.parent;
    }

    public ArrayList<Balise> getChildren() {
        return this.children;
    }

    public int getNumRow() {
        return this.num_row;
    }

    public int getNumCol() {
        return this.num_col;
    }

    public int getSpanRow() {
        return this.span_row;
    }

    public int getSpanCol() {
        return this.span_col;
    }

    public int getDepth() {
        return this.depth;
    }

    public void addChild(Balise child) {
        this.children.add(child);
    }

    public void setShape(int n_row, int n_col) {
        this.num_row = n_row;
        this.num_col = n_col;
    }

    public void setNumRow(int n_row) {
        this.num_row = n_row;
    }

    public void setNumCol(int n_col) {
        this.num_col = n_col;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String toString() {
        String repeated = new String(new char[this.depth]).replace("\0", "\t|");
        return repeated + this.tag + "| n_row = " + this.num_row + " , n_col = " + this.num_col + " , info = " + this.getInfo();
    }

    public abstract Balise newInstance(Element self, Balise parent);

}
