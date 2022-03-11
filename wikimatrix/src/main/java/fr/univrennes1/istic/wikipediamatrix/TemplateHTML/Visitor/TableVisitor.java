package fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Visitor;

import java.util.ArrayList;
import java.util.List;

import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Balise;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Table;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Tbody;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Tr;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Grid.Grid;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Th;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Thead;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Td;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Tfoot;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.A;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Img;

public class TableVisitor implements Visitor {

    public  List<Balise> tables = new ArrayList<Balise>();

    public void visitChildren(Balise balise) {
        for (Balise child : balise.getChildren()) {
            child.accept(this);
        }
    }

    @Override
    public void Table(Table table) {
       this.tables.add(table);
       // Reset Grid
       table.setGrid(new Grid());
       visitChildren(table);
    }

    @Override
    public void Thead(Thead thead) {
        visitChildren(thead);
    }

    @Override
    public void Tbody(Tbody tbody) {
        visitChildren(tbody);
    }

    @Override
    public void Tfoot(Tfoot tfoot) {
        visitChildren(tfoot);
    }

    @Override
    public void Tr(Tr tr) {
        visitChildren(tr);
    }

    @Override
    public void Th(Th th) {
        visitChildren(th);
    }

    @Override
    public void Td(Td td) {
        visitChildren(td);
    }

    @Override
    public void A(A a) {
        visitChildren(a);
    }
    
    @Override
    public void Img(Img img) {
        visitChildren(img);
    }
}
