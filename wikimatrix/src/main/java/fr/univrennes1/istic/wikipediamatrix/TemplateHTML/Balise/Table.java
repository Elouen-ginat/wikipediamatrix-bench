package fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise;

import org.jsoup.nodes.Element;

import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Visitor.Visitor;

public class Table extends Balise {

    public Table() {
        super("table");
    }

    public Table(Element self, Balise parent) {
        super(self, parent);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.Table(this);
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public Balise newInstance(Element self, Balise parent) {
        return new Table(self, parent);
    }
    
}
