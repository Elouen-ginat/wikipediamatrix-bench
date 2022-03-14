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

public class GridVisitor implements Visitor {

    public List<Grid> getGrids(Balise balise) {
        List<Grid> grids = new ArrayList<Grid>();
        for (Balise child : balise.getChildren()) {
            grids.add(child.getGrid());
        }
        return grids;
    }

    private void spanGrid(Balise balise) {
        balise.getGrid().span(balise.getSpanRow(), balise.getSpanCol());
    }

    private void mergeCol(Balise balise) {
        List<Grid> grids = getGrids(balise);
        int pos = 0;
        boolean from_table = false;
        for (int i = 0; i < grids.size(); i++) {
            if (grids.get(i).getFromTable()) {
                balise.getGrid().mergeCol(grids.get(i), pos);
                balise.getGrid().setFromTable(true);
                pos += balise.getChildren().get(i).getSpanCol();
                from_table = true;
            }
        }
        if (!from_table) {
            balise.initGrid(true);
        }else {
            spanGrid(balise);
        }
    }

    public void mergeRow(Balise balise) {
        List<Grid> grids = getGrids(balise);
        int pos = 0;
        boolean from_table = false;
        for (int i = 0; i < grids.size(); i++) {
            if (grids.get(i).getFromTable()) {
                balise.getGrid().mergeRow(grids.get(i), pos);
                balise.getGrid().setFromTable(true);
                pos += balise.getChildren().get(i).nextRowPos();
                from_table = true;
            }
        }
        if (!from_table) {
            balise.initGrid(true);
        }else {
            spanGrid(balise);
        }
    }

    @Override
    public void Table(Table table) {
    }

    @Override
    public void Thead(Thead thead) {
        mergeRow(thead);
    }

    @Override
    public void Tbody(Tbody tbody) {
        mergeRow(tbody);
    }

    @Override
    public void Tfoot(Tfoot tfoot) {
        mergeRow(tfoot);
    }

    @Override
    public void Tr(Tr tr) {
        mergeCol(tr);
    }

    @Override
    public void Th(Th th) {
        mergeRow(th);
    }

    @Override
    public void Td(Td td) {
        mergeRow(td);
    }

    @Override
    public void A(A a) {
        mergeRow(a);
    }
    
    @Override
    public void Img(Img img) {
        mergeRow(img);
    }
}
