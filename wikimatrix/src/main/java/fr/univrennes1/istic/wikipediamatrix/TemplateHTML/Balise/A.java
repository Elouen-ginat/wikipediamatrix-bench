package fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise;

import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Visitor.Visitor;

public class A extends Balise {

    public A() {
        super("a", false);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.A(this);
    }

    @Override
    public String getInfo() {
        if (this.isFinal()) {
            return this.getElement().text() + " , URL: " + this.getElement().attr("href");
        }
        String str = this.getElement().text() + "\nURL: " + this.getElement().attr("href");
        for (Balise child: this.getChildren()) {
            str += "\n"+child.getInfo();
        }
        return str;
    }

    @Override
    public Balise newInstance() {
        return new A();
    }
    
}
