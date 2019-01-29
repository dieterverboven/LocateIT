package be.thomasmore.locateit.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import be.thomasmore.locateit.R;
import be.thomasmore.locateit.adapters.DepartmentListAdapter;
import be.thomasmore.locateit.adapters.ProductListAdapter;
import be.thomasmore.locateit.classes.Product;
import be.thomasmore.locateit.helpers.JsonHelper;
import be.thomasmore.locateit.http.HttpReader;

public class ProductsByDepartment extends AppCompatActivity {

    String afdeling;
    List<Product> products = new ArrayList<Product>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_by_department);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        afdeling = bundle.getString("afdeling");
        getSupportActionBar().setTitle(afdeling);
        getProducts();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNavigation();
            }
        });
    }

    private void startNavigation(){
        Bundle bundleNav = new Bundle();
        bundleNav.putString("afdeling", afdeling);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtras(bundleNav);
        startActivity(intent);
    }

    private void getProducts() {
        HttpReader httpReader = new HttpReader();
        httpReader.setOnResultReadyListener(new HttpReader.OnResultReadyListener() {
            @Override
            public void resultReady(String result) {
                JsonHelper jsonHelper = new JsonHelper();
                products = jsonHelper.getProducts(result);
                useAdapter();
            }
        });
        String url = "https://locateit-backend.herokuapp.com/products/afdeling/";
        url = url.concat(afdeling);
        httpReader.execute(url);
    }

    private void useAdapter() {
        ProductListAdapter productListAdapter = new ProductListAdapter(getApplicationContext(), products);
        final ListView listViewProducts = findViewById(R.id.listProducts);
        listViewProducts.setAdapter(productListAdapter);

        listViewProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parentView, View childView, int position, long id) {
                detailPagina(products.get(position).getId());
            }
        });
    }

    private void detailPagina(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("productId", id);
        Intent intent = new Intent(this, ProductDetail.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}