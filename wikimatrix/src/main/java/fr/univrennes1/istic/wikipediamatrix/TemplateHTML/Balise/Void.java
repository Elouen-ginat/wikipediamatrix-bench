package fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise;

import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Visitor.Visitor;

public class Void extends Balise{
    
    public Void() {
        super("void", false);
    }

    @Override
    public void accept(Visitor visitor) {
        //visitor.Void(this);
    }

    @Override
    public String getInfo() {
        return "";
    }

    @Override
    public Balise newInstance() {
        return new Void();
    }
}
