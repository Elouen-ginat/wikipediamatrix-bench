package fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Visitor;

import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Table;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Tbody;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Tr;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Th;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Thead;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Td;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Tfoot;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.A;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Img;

public interface Visitor {
    
    public void Table(Table table);
    public void Thead(Thead thead);
    public void Tbody(Tbody tbody);
    public void Tfoot(Tfoot tfoot);
    public void Tr(Tr tr);
    public void Th(Th th);
    public void Td(Td td);
    public void A(A a);
    public void Img(Img img);


}
