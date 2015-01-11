package ro.bfc.cpee.Controllers;

import android.net.Uri;

import ro.bfc.cpee.models.CPEEDocument;
import ro.bfc.cpee.models.County;
import ro.bfc.cpee.models.NivelEnergie;
import ro.bfc.cpee.models.Price;
import ro.bfc.cpee.models.Total;
import ro.bfc.cpee.utils.CollectionUtils;
import ro.bfc.cpee.utils.FileUtils;
import ro.bfc.cpee.views.IMainActivityView;

/**
 * Created by catalin on 1/5/15.
 */
public class MainActivityController {
    CPEEDocument document;
    IMainActivityView activity;
    Total total;

    public MainActivityController(CPEEDocument document, IMainActivityView activity) {
        this.document = document;
        this.activity = activity;
        total = new Total();
        total.setGlobalPrices(document.Global);
    }

    public void init() {
        if(this.document != null){
            activity.updateCounties();
        }
        else
            activity.showMessageLong("Nu exista valori definite. Incarcati un document CPEE.");
    }

    public void loadModel(Uri uri) {
        try {
        CPEEDocument doc = CPEEDocument.readJSON(FileUtils.getFileAsString(this.activity.getContext(), uri));
        if(doc != null){
            FileUtils.saveToInternalStorage(this.activity.getContext(), "valori.cpee", doc);
            this.document = doc;
            this.activity.updateCounties();
        }
        } catch (Exception e) {
            this.activity.showMessageLong(e.getMessage());
            e.printStackTrace();
        }
    }

    public void onCountySelected(int position) {
        final County county = CollectionUtils.getElementAt(this.document.Counties, position);
        if(county != null) {
            Price countyPrice = Price.getPrice(document.Prices, county.PriceId);
            if(countyPrice != null){
                total.setPrice(countyPrice);
                this.activity.updateTotal(total);
            }
            else this.activity.showMessageShort(String.format("Pret invalid pentru judetul '%s'.", county.Name));
        }
    }

    public void onNivelEnergeticSelected(int position) {
        final NivelEnergie ne = NivelEnergie.forValue(position);
        if(ne != null) {
            total.setNivelEnergie(ne);
            this.activity.updateTotal(total);
        }
        else this.activity.showMessageShort("Nivel selecat invalid");
    }

    public void setPretBaza(double pretBaza) {
        total.setPretBaza(pretBaza);
        this.activity.updateTotal(total);
    }

    public void refreshTotal(){
        this.activity.updateTotal(total);
    }
}
