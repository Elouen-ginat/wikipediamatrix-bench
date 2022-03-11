package fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise;

import java.util.ArrayList;

import org.jsoup.nodes.Element;

import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Grid.Grid;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Visitor.Visitor;

public abstract class Balise {
    
    private String tag;
    private Element self;
    private Balise parent;
    private ArrayList<Balise> children = new ArrayList<Balise>();
    private int span_row = 1;
    private int span_col = 1;
    private int depth = 0;
    // Does this grid is a table or just a display balise
    private boolean from_table;

    private Grid grid;

    public Balise(String tag, boolean from_table) {
        this.tag = tag;
        this.from_table = from_table;
    }

    public abstract void accept(Visitor visitor);

    public abstract String getInfo();

    public int nextRowPos() {
        return 1;
    }

    public boolean isFinal() {
        return children.isEmpty();
    }

    public void init(Element self, Balise parent, int depth) {
        this.tag = self.tagName();
        this.self = self;
        this.parent = parent;
        this.depth = depth;
        if (self.attributes().hasKey("rowspan")) {
            this.span_row = (int) Integer.parseInt(self.attr("rowspan"));
        }
        if (self.attributes().hasKey("colspan")) {
            this.span_col = (int) Integer.parseInt(self.attr("colspan"));
        }
    }

    public void initGrid() {
        this.initGrid(this.isFinal());
    }

    public void initGrid(boolean is_final) {
        if (is_final) {
            this.grid = new Grid(this);
            this.grid.span(this.span_row, this.span_col);
        }else {
            this.grid = new Grid();
        }
    }

    public Grid getGrid() {
        return this.grid;
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

    public int getSpanRow() {
        return this.span_row;
    }

    public int getSpanCol() {
        return this.span_col;
    }

    public int getDepth() {
        return this.depth;
    }

    public boolean getFromTable() {
        return this.from_table;
    }

    public void addChild(Balise child) {
        this.children.add(child);
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public String toString() {
        String repeated = new String(new char[this.depth]).replace("\0", "\t|");
        //return repeated + this.tag + "| info = " + this.getInfo() + ", grid = \n" + this.grid.toString();
        return repeated + this.tag + "| grid = \n" + this.grid.toString();
    }

    public abstract Balise newInstance();

}
