package ro.bfc.cpee.views;

import android.content.Context;

import ro.bfc.cpee.models.CPEEDocument;
import ro.bfc.cpee.models.Total;

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

    void setDocument(CPEEDocument document);
}
