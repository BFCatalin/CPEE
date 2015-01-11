package ro.bfc.cpee.adapters;

import android.content.Context;
import android.support.v7.internal.widget.TintCheckedTextView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import ro.bfc.cpee.models.County;

/**
 * Created by catalin on 1/4/15.
 */
public class CountySpinnerAdapter extends ArrayAdapter<County> {
    // Your sent context
    private Context context;
    // Your custom values for the spinner (User)
    private ArrayList<County> values;

    public CountySpinnerAdapter(Context context, int textViewResourceId,
                                ArrayList<County> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    public int getCount(){
        return values.size();
    }

    public County getItem(int position){
        return values.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /*
        View view = super.getView(position, convertView, parent);

        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText(values.get(position).Name);
        label.setPadding(3, 5, 3, 5);

        return label;
        */
        return this.getDropDownView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        View view = super.getDropDownView(position, null, parent);
        ((TintCheckedTextView)view).setText(values.get(position).Name);
        /*
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText(values.get(position).Name);
        label.setPadding(3, 5, 3, 5);

        return label;
        */
        return view;
    }
}
