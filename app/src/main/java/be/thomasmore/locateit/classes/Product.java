package be.thomasmore.locateit.classes;

public class Product {
    private String id;
    private String naam;
    private String beschrijving;
    private String afdeling;
    private double prijs;
    private String afbeelding;

    public Product() {
    }

    public Product(String id, String naam, String beschrijving, String afdeling, double prijs, String afbeelding) {
        this.id = id;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.afdeling = afdeling;
        this.prijs = prijs;
        this.afbeelding = afbeelding;
    }

    public String getAfbeelding() {
        return afbeelding;
    }

    public void setAfbeelding(String afbeelding) {
        this.afbeelding = afbeelding;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public String getAfdeling() {
        return afdeling;
    }

    public void setAfdeling(String afdeling) {
        this.afdeling = afdeling;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }
}
