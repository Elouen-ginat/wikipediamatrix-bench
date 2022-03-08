package fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise;

public abstract class Balise {
    
    private String tag;

    public Balise(String tag) {
        this.tag = tag;
    }

    public abstract String getInfo();

    public String getTag() {
        return this.tag;
    }

}
