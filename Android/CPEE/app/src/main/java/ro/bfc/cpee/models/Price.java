package ro.bfc.cpee.models;

import java.util.Collection;

/**
 * Created by catalin on 1/3/15.
 */
public class Price {
    public int Id;
    public String Name;
    public double IT;
    public double MT;
    public double JT;
    public double TL;

    public static Price getPrice(Collection<Price> prices, int priceId){
        for (Price p : prices) {
            if (p.Id == priceId) {
                return p;
            }
        }
        return null;
    }
}
