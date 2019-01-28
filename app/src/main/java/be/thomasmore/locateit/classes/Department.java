package be.thomasmore.locateit.classes;

public class Department {
    private String id;
    private String naam;

    public Department() {
    }

    public Department(String id, String naam) {
        this.id = id;
        this.naam = naam;
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
}
