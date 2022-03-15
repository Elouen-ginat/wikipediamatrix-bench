package fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Visitor;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Balise;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Table;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Tbody;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Tr;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Controler.Controler;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Th;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Thead;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Td;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Tfoot;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.A;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Img;


public class CreateVisitor implements Visitor {

    private Controler controler;

    public CreateVisitor() {
        this.controler = new Controler();
    }

    public List<List<Balise>> depth_balise = new ArrayList<List<Balise>>();

    private void createChildren(Balise balise) {
        Element balise_el = balise.getElement();
        Elements children = balise_el.children();
        
        // Add balise to depth array
        int depth = balise.getDepth();
        if (depth < depth_balise.size()) {
            this.depth_balise.get(depth).add(balise);
        } else {
            this.depth_balise.add(new ArrayList<Balise>());
            this.depth_balise.get(depth).add(balise);
        }

        // Add childrens to the balise
        for (Element child : children) {
            Balise balise_child = this.controler.getBalise(child);
            if (balise_child != null) {
                balise_child.init(child, balise, depth + 1);
                balise.addChild(balise_child);
            }
        }

        // Init grid after adding children (depends on final)
        balise.initGrid();

        // Recursive creation
        for (Balise child : balise.getChildren()) {
            child.accept(this);
        }
    }

    @Override
    public void Table(Table table) {
        createChildren(table);

    }

    @Override
    public void Thead(Thead thead) {
        createChildren(thead);
    }

    @Override
    public void Tbody(Tbody tbody) {
        createChildren(tbody);
    }

    @Override
    public void Tfoot(Tfoot tfoot) {
        createChildren(tfoot);
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
