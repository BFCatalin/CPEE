package ro.bfc.cpee.models;

import java.util.ArrayList;
import java.util.List;

import ro.bfc.cpee.R;

/**
 * Created by catalin on 1/11/15.
 */
public class Total {
    private Price price;
    private NivelEnergie nivelEnergie;
    private GlobalPrices globalPrices;
    private Double total;
    private Double pretBaza;

    public List<TotalComponent> getTotalComponents() {
        return totalComponents;
    }

    private List<TotalComponent> totalComponents;

    public Total(){
        totalComponents = new ArrayList<TotalComponent>();
    }

    public Double getPretBaza() {
        return pretBaza;
    }

    public void setPretBaza(Double pretBaza) {
        this.pretBaza = pretBaza;
        reset();
    }

    private void reset() {
        total = null;
        totalComponents.clear();
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
        total = null;
    }

    public NivelEnergie getNivelEnergie() {
        return nivelEnergie;
    }

    public void setNivelEnergie(NivelEnergie nivelEnergie) {
        this.nivelEnergie = nivelEnergie;
        reset();
    }

    public GlobalPrices getGlobalPrices() {
        return globalPrices;
    }

    public void setGlobalPrices(GlobalPrices globalPrices) {
        this.globalPrices = globalPrices;
        reset();
    }

    public Double getTotal(){
        if(total == null){
            if(pretBaza != null && price != null && globalPrices != null && nivelEnergie != null) {
                totalComponents.clear();
                total = pretBaza + globalPrices.getSum() + this.getPrice().TL;
                totalComponents.add(new TotalComponent(R.string.contribCogen, globalPrices.CoGen));
                totalComponents.add(new TotalComponent(R.string.tarifTransportTG, globalPrices.TG));
                totalComponents.add(new TotalComponent(R.string.tarifServSistem, globalPrices.Service));

                totalComponents.add(new TotalComponent(R.string.tarifExtragereTL, this.getPrice().TL));

                if(this.nivelEnergie == NivelEnergie.TensiuneInalta
                        || this.nivelEnergie == NivelEnergie.TensiuneMedie
                        || this.nivelEnergie == NivelEnergie.TensiuneJoasa){
                    total += this.getPrice().IT;
                    totalComponents.add(new TotalComponent(R.string.tarifDistIT, this.getPrice().IT));
                }
                if(this.nivelEnergie == NivelEnergie.TensiuneMedie
                        || this.nivelEnergie == NivelEnergie.TensiuneJoasa){
                    total += this.getPrice().MT;
                    totalComponents.add(new TotalComponent(R.string.tarifDistMT, this.getPrice().MT));
                }
                if(this.nivelEnergie == NivelEnergie.TensiuneJoasa){
                    total += this.getPrice().JT;
                    totalComponents.add(new TotalComponent(R.string.tarifDistJT, this.getPrice().JT));
                }
            }
        }
        return total;
    }
}
