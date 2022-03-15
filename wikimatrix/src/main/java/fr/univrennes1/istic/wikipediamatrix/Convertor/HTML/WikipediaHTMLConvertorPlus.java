package fr.univrennes1.istic.wikipediamatrix.Convertor.HTML;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;

import org.jsoup.select.Elements;

import fr.univrennes1.istic.wikipediamatrix.App;
import fr.univrennes1.istic.wikipediamatrix.Convertor.Convertor;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Balise;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Table;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Grid.Grid;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Visitor.CreateVisitor;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Visitor.GridVisitor;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Visitor.ShowVisitor;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Visitor.TableVisitor;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Visitor.Visitor;

public class WikipediaHTMLConvertorPlus implements Convertor {

    @Override
    public String[][] toStringTable(Element table) {
        
        // Creation of the tree
        Balise first_table = new Table();
        first_table.init(table, null, 0);
        CreateVisitor create_visitor = new CreateVisitor();
        first_table.accept(create_visitor);

        // Reverse depth balise
        List<List<Balise>> rev_depth_balise = new ArrayList<List<Balise>>();
        for (int i = create_visitor.depth_balise.size()-1; i >= 0; i--) {
            rev_depth_balise.add(create_visitor.depth_balise.get(i));
        }

        //Calculate grid of each balise except Table
        GridVisitor grid_visitor = new GridVisitor();
        for (int i = 1; i < rev_depth_balise.size(); i++) {
            for (Balise balise : rev_depth_balise.get(i)) {
                if (!balise.isFinal()) {
                    balise.accept(grid_visitor);
                }
            }
        }

        // Get all table balise and reset table Grids
        TableVisitor table_visitor = new TableVisitor();
        first_table.accept(table_visitor);

        // Calculate grid of each Table
        for (Balise table_balise : table_visitor.tables) {
            // merge grids
            grid_visitor.mergeRow(table_balise);
        }
        
        // Show table tree
        // Visitor show_Visitor = new ShowVisitor();
        // first_table.accept(show_Visitor);

        Grid expended_grid = first_table.getGrid().expend();

        //App.LOGGER.debug("\n"+expended_grid.toString());

        return expended_grid.toStringArray();

    }

    @Override
    public ArrayList<String[][]> toStringTables(Elements tables) {

        ArrayList<String[][]> string_tables = new ArrayList<String[][]>();
        for (Element table : tables) {
            String[][] string_table = this.toStringTable(table);
            string_tables.add(string_table);
        }
        
        return string_tables;
    }

    
}
