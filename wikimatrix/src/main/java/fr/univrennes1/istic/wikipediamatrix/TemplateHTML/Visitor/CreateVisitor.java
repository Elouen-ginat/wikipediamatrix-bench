package fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Visitor;

import java.util.ArrayList;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Balise;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Table;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Tbody;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Tr;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Controler.Controler;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Th;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Td;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.A;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Img;


public class CreateVisitor implements Visitor {

    Controler controler;

    public CreateVisitor() {
        this.controler = new Controler();
    }

    private void createChildren(Balise balise) {
        Element balise_el = balise.getElement();
        Elements children = balise_el.children();

        // Add childrens to the balise
        for (Element child : children) {
            Balise balise_child = this.controler.getBalise(child, balise);
            if (balise_child != null) {
                balise_child.setDepth(balise.getDepth() + 1);
                balise.addChild(balise_child);
            }
        }

        // Recursive creation
        for (Balise child : balise.getChildren()) {
            child.accept(this);
        }
    }

    @Override
    public void Table(Table table) {
        //Element table_el = table.getElement();
        //Un wrap tbody element not necessary
        //table_el.select(new Tbody().getTag()).unwrap();
        createChildren(table);

    }

    @Override
    public void Tbody(Tbody tbody) {
        createChildren(tbody);
    }

    @Override
    public void Tr(Tr tr) {
        createChildren(tr);
    }

    @Override
    public void Th(Th th) {
        createChildren(th);
    }

    @Override
    public void Td(Td td) {
        createChildren(td);
    }

    @Override
    public void A(A a) {
        createChildren(a);
    }

    @Override
    public void Img(Img img) {
        createChildren(img);
    }
    
}
