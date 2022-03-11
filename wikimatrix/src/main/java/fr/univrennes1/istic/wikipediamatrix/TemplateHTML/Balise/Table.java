package fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise;

import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Grid.Grid;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Visitor.Visitor;

public class Table extends Balise {

    public Table() {
        super("table", true);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.Table(this);
    }

    @Override
    public String getInfo() {
        if (this.isFinal()) {
            return this.getElement().text();
        }
        String str = "";
        for (Balise child: this.getChildren()) {
            str += child.getInfo()+"\n";
        }
        str.subSequence(0, str.length()-1);
        return str;
    }

    @Override
    public void initGrid() {
        this.setGrid(new Grid(this));
    }

    @Override
    public Balise newInstance() {
        return new Table();
    }
    
}
