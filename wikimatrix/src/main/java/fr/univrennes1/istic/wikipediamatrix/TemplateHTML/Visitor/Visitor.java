package fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Visitor;

import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Table;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Tbody;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Tr;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Th;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Td;


public interface Visitor {
    
    public void Table(Table table);
    public void Tbody(Tbody tbody);
    public void Tr(Tr tr);
    public void Th(Th th);
    public void Td(Td td);


}
