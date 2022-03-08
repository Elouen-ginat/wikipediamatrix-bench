package fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise;

import org.jsoup.nodes.Element;

import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Visitor.Visitor;

public class Img extends Balise {

    public Img() {
        super("img");
    }

    public Img(Element self, Balise parent) {
        super(self, parent);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.Img(this);
    }

    @Override
    public String getInfo() {
        return this.getElement().attr("src");
    }

    @Override
    public Balise newInstance(Element self, Balise parent) {
        return new Img(self, parent);
    }
    
}
