package ro.bfc.cpee.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by catalin on 1/11/15.
 */
public enum NivelEnergie {
    TensiuneInalta(0),
    TensiuneMedie(1),
    TensiuneJoasa(2);

    private final int value;

    private static final Map<Integer, NivelEnergie> typesByValue = new HashMap<Integer, NivelEnergie>();

    static {
        for (NivelEnergie type : NivelEnergie.values()) {
            typesByValue.put(type.value, type);
        }
    }

    NivelEnergie(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    public static NivelEnergie forValue(int value) {
        return typesByValue.get(value);
    }
}