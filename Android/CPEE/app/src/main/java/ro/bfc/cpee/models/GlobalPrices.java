package ro.bfc.cpee.models;

/**
 * Created by catalin on 1/3/15.
 */
public class GlobalPrices {
    public double CoGen;
    public double TG;
    public double TL;
    public double Service;

    public double getSum() { return CoGen + TG + Service; }
}
