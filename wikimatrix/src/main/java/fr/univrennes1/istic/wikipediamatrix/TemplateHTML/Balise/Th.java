package fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise;

import org.jsoup.nodes.Element;

import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Visitor.Visitor;

public class Th extends Balise {

    public Th(Element self) {
        super(self);
    }
    
    @Override
    public void accept(Visitor visitor) {
        visitor.Th(this);
    }

    @Override
    public String getInfo() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
