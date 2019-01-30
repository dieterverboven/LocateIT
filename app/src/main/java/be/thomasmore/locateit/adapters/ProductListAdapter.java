package be.thomasmore.locateit.adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import be.thomasmore.locateit.R;
import be.thomasmore.locateit.classes.Product;

public class ProductListAdapter extends ArrayAdapter<Product> {
    private final Context context;
    private final List<Product> values;

    public ProductListAdapter(Context context, List<Product> values){
        super(context, R.layout.productslistviewitem, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.productslistviewitem, parent, false);

        final TextView textViewNaam = rowView.findViewById(R.id.productNaam);
        final ImageView imageViewStock = rowView.findViewById(R.id.stock);

        textViewNaam.setText(values.get(position).getNaam());

        if (values.get(position).isInStock()){
            imageViewStock.setImageResource(R.drawable.ic_yes);
        } else {
            imageViewStock.setImageResource(R.drawable.ic_no);
        }

        return rowView;
    }
}