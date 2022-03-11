package fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise;

import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Visitor.Visitor;

public class Img extends Balise {

    public Img() {
        super("img", false);
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
    public Balise newInstance() {
        return new Img();
    }
    
}
