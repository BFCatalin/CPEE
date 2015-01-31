package ro.bfc.cpee_233.views;

import android.content.Context;

import ro.bfc.cpee_233.models.CPEEDocument;
import ro.bfc.cpee_233.models.Total;

/**
 * Created by catalin on 1/5/15.
 */
public interface IMainActivityView {

    CPEEDocument getDocument();

    void updateCounties();

    void showMessageShort(String message);

    Context getContext();

    void showMessageLong(String message);

    void updateTotal(Total total);
}
