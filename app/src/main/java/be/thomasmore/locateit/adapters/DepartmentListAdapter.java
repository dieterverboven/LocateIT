package be.thomasmore.locateit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import be.thomasmore.locateit.R;
import be.thomasmore.locateit.classes.Department;
import be.thomasmore.locateit.classes.Product;

public class DepartmentListAdapter extends ArrayAdapter<Department> {
    private final Context context;
    private final List<Department> values;

    public DepartmentListAdapter(Context context, List<Department> values){
        super(context, R.layout.departmentslistviewitem, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.departmentslistviewitem, parent, false);

        final TextView textViewNaam = rowView.findViewById(R.id.departmentNaam);

        textViewNaam.setText(values.get(position).getNaam());

        return rowView;
    }
}
