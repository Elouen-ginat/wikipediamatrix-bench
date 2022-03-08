package fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Visitor;


import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Balise;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Table;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Tbody;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Tr;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Th;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Td;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.A;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Img;

public class SizeVisitor implements Visitor {

    private void goToParent(Balise balise) {
        if (balise.getParent() != null) {
            balise.getParent().accept(this);
        }
    }

    @Override
    public void Table(Table table) {
        int n_row = 0;
        int n_col = table.getNumCol();
        for (Balise child : table.getChildren()) {
            n_row += child.getNumRow();
            n_col = Math.max(n_col, child.getNumCol());
        }
        table.setShape(n_row, n_col);
        this.goToParent(table);
    }

    @Override
    public void Tbody(Tbody tbody) {
        int n_row = 0;
        int n_col = tbody.getNumCol();
        for (Balise child : tbody.getChildren()) {
            n_row += child.getNumRow();
            n_col = Math.max(n_col, child.getNumCol());
        }
        tbody.setShape(n_row, n_col);
        this.goToParent(tbody);
    }

    @Override
    public void Tr(Tr tr) {
        int n_row = tr.getNumRow();
        int n_col = 0;
        for (Balise child : tr.getChildren()) {
            n_row = Math.max(n_row, child.getNumRow());
            n_col += child.getNumCol();
        }
        tr.setShape(n_row, n_col);
        this.goToParent(tr);
    }

    @Override
    public void Th(Th th) {
        int n_row = 0;
        int n_col = th.getNumCol();
        for (Balise child : th.getChildren()) {
            n_row += child.getNumRow();
            n_col = Math.max(n_col, child.getNumCol());
        }
        n_row = Math.max(n_row, 1);
        th.setShape(n_row, n_col);
        this.goToParent(th);
    }

    @Override
    public void Td(Td td) {
        int n_row = 0;
        int n_col = td.getNumCol();
        for (Balise child : td.getChildren()) {
            n_row += child.getNumRow();
            n_col = Math.max(n_col, child.getNumCol());
        }
        n_row = Math.max(n_row, 1);
        td.setShape(n_row, n_col);
        this.goToParent(td);
    }

    @Override
    public void A(A a) {
        int n_row = a.getNumRow();
        int n_col = a.getNumCol();
        for (Balise child : a.getChildren()) {
            n_row = Math.max(n_row, child.getNumRow());
            n_col = Math.max(n_col, child.getNumCol());
        }
        a.setShape(n_row, n_col);
        this.goToParent(a);
    }

    @Override
    public void Img(Img img) {
        int n_row = img.getNumRow();
        int n_col = img.getNumCol();
        for (Balise child : img.getChildren()) {
            n_row = Math.max(n_row, child.getNumRow());
            n_col = Math.max(n_col, child.getNumCol());
        }
        img.setShape(n_row, n_col);
        this.goToParent(img);
    }
    
}
