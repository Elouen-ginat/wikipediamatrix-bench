package fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise;

import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Visitor.Visitor;

public class Td extends Balise {

    public Td() {
        super("td", true);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.Td(this);
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
    public Balise newInstance() {
        return new Td();
    }
    
}
