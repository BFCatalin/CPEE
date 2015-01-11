package ro.bfc.cpee;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import ro.bfc.cpee.models.TotalComponent;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Activity context;

    private Map<Double, List<TotalComponent>> totalComponents;

    private List<Double> totals;


    public ExpandableListAdapter(Activity context, List<Double> totals,
                                 Map<Double, List<TotalComponent>> totalComponents) {
        this.context = context;
        this.totalComponents = totalComponents;
        this.totals = totals;
    }


    public Object getChild(int groupPosition, int childPosition) {
        return totalComponents.get(totals.get(groupPosition)).get(childPosition);
    }


    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final TotalComponent comp_total = (TotalComponent) getChild(groupPosition, childPosition);
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.child_item, null);
        }

        TextView item = (TextView) convertView.findViewById(R.id.comp_name);
        TextView delete = (TextView) convertView.findViewById(R.id.comp_total);

        item.setText(context.getString(comp_total.NameId));
        DecimalFormat df = new DecimalFormat("#.##");
        delete.setText(df.format(comp_total.Pret) + String.format(" %s", context.getString(R.string.coin)));
        return convertView;
    }


    public int getChildrenCount(int groupPosition) {
        if(totalComponents == null || totals == null)
            return 0;
        return totalComponents.get(totals.get(groupPosition)).size();
    }


    public Object getGroup(int groupPosition) {

        return totals.get(groupPosition);
    }


    public int getGroupCount() {
        if(totals == null)
            return 0;
        return totals.size();
    }


    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        Double total = (Double) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_item,
                    null);
        }
        TextView item = (TextView) convertView.findViewById(R.id.total);
        item.setTypeface(null, Typeface.BOLD);
        DecimalFormat df = new DecimalFormat("#.##");
        item.setText(df.format(total) + String.format(" %s", context.getString(R.string.coin)));
        return convertView;
    }


    public boolean hasStableIds() {
        return true;
    }


    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}