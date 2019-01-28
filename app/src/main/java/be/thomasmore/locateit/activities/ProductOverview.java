package be.thomasmore.locateit.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import be.thomasmore.locateit.R;
import be.thomasmore.locateit.adapters.ProductListAdapter;
import be.thomasmore.locateit.classes.Product;
import be.thomasmore.locateit.helpers.JsonHelper;
import be.thomasmore.locateit.http.HttpReader;

public class ProductOverview extends AppCompatActivity {

    List<Product> products = new ArrayList<Product>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_overview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        readProducts();
    }

    private void readProducts(){
        HttpReader httpReader = new HttpReader();
        httpReader.setOnResultReadyListener(new HttpReader.OnResultReadyListener() {
            @Override
            public void resultReady(String result) {
                JsonHelper jsonHelper = new JsonHelper();
                products = jsonHelper.getProducts(result);
                useAdapter();
            }
        });
        httpReader.execute("https://locateit-backend.herokuapp.com/products");
    }

    private void useAdapter(){
        ProductListAdapter productListAdapter = new ProductListAdapter(getApplicationContext(), products);
        final ListView listViewProducts = findViewById(R.id.listProducts);
        listViewProducts.setAdapter(productListAdapter);
    }

}
