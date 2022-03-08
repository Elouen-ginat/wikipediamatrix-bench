package fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise;

import java.util.Arrays;

import org.jsoup.nodes.Element;

import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Visitor.Visitor;

public abstract class Balise {
    
    private Element self;
    private Balise[] children = null;
    private int num_row = 1;
    private int num_col = 1;

    public Balise(Element self) {
        this.self = self;
    }

    public abstract void accept(Visitor visitor);

    public abstract String getInfo();

    public boolean isFinal() {
        return children.equals(null);
    }

    public String[][] getGrid() {
        String[][] grid = new String[this.num_row][this.num_col];
        String info = this.getInfo();
        Arrays.fill(grid, info);        
        return grid;
    }

    public String getTag() {
        return this.self.tagName();
    }

    public void setShape(int row, int col) {
        this.num_row = row;
        this.num_col = col;
    }

    public void setNumRow(int row) {
        this.num_row = row;
    }

    public void setNumCol(int col) {
        this.num_col = col;
    }

}
