package ro.bfc.cpee_233.models;

/**
 * Created by catalin on 1/11/15.
 */
public class TotalComponent {
    public String Name;
    public Double Pret;
    public int NameId;

    public TotalComponent(String name, Double pret) {
        Name = name;
        Pret = pret;
    }

    public TotalComponent(int nameId, Double pret) {
        NameId = nameId;
        Pret = pret;
    }

    public TotalComponent() {
    }
}
