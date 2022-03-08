package fr.univrennes1.istic.wikipediamatrix.Convertor.HTML;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Element;

import org.jsoup.select.Elements;

import fr.univrennes1.istic.wikipediamatrix.Convertor.Convertor;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Balise;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Table;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Visitor.CreateVisitor;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Visitor.FinalVisitor;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Visitor.ShowVisitor;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Visitor.SizeVisitor;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Visitor.Visitor;

public class WikipediaHTMLConvertorPlus implements Convertor {

    @Override
    public String[][] toStringTable(Element table) {
        
        // Creation of the tree
        Table first_table = new Table(table, null);
        Visitor create_visitor = new CreateVisitor();
        first_table.accept(create_visitor);

        // Show tree (debug)
        Visitor show_Visitor = new ShowVisitor();
        first_table.accept(show_Visitor);

        // Get all final balise
        FinalVisitor final_visitor = new FinalVisitor();
        first_table.accept(final_visitor);

        // Calculate sizes of each balise
        Visitor size_visitor = new SizeVisitor();
        Set<Balise> final_balise_parent = new HashSet<Balise>(final_visitor.final_balise_parent);
        for (Balise parent : final_balise_parent) {
            parent.accept(size_visitor);
        }

        show_Visitor = new ShowVisitor();
        first_table.accept(show_Visitor);

        return null;

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
