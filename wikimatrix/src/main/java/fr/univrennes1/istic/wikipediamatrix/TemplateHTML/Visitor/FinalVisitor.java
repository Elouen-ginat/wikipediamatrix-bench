package fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Visitor;

import java.util.ArrayList;

import fr.univrennes1.istic.wikipediamatrix.App;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Balise;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Table;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Tbody;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Tr;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Th;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Td;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.A;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Img;


public class FinalVisitor implements Visitor {
    
    // List of balise that are an end of the tree
    public ArrayList<Balise> final_balise_parent = new ArrayList<Balise>();

    public void showChildren(Balise balise) {
        if (balise.isFinal() && balise.getParent() != null) {
            final_balise_parent.add(balise);
        }else {
            for (Balise child : balise.getChildren()) {
                child.accept(this);
            }
        }
    }

    @Override
    public void Table(Table table) {
       showChildren(table);
    }

    @Override
    public void Tbody(Tbody tbody) {
        showChildren(tbody);
    }

    @Override
    public void Tr(Tr tr) {
        showChildren(tr);
    }

    @Override
    public void Th(Th th) {
        showChildren(th);
    }

    @Override
    public void Td(Td td) {
        showChildren(td);
    }

    @Override
    public void A(A a) {
        showChildren(a);
    }
    
    @Override
    public void Img(Img img) {
        showChildren(img);
    }
}
