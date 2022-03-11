package fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise;

import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Visitor.Visitor;

public class Tr extends Balise {

    public Tr() {
        super("tr", true);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.Tr(this);
    }

    @Override
    public int nextRowPos() {
        return 1;
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
        return new Tr();
    }
    
}
