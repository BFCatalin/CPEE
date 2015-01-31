package ro.bfc.cpee_233.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by catalin on 1/11/15.
 */
public class CollectionUtils {
    public static <T> T getElementAt(Collection<T> collection, int position){
        if(collection instanceof ArrayList){
            return ((ArrayList<T>)collection).get(position);
        } else if(collection instanceof List) {
            return ((List<T>)collection).get(position);
        }

        return null;
    }
}
