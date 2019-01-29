package be.thomasmore.locateit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import be.thomasmore.locateit.R;
import be.thomasmore.locateit.adapters.DepartmentListAdapter;
import be.thomasmore.locateit.adapters.ProductListAdapter;
import be.thomasmore.locateit.classes.Department;
import be.thomasmore.locateit.classes.Product;
import be.thomasmore.locateit.helpers.JsonHelper;
import be.thomasmore.locateit.http.HttpReader;

public class ProductOverview extends AppCompatActivity {

    List<Department> departments = new ArrayList<Department>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_overview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Producten in de buurt");
        readDepartments();
    }

    private void readDepartments() {
        HttpReader httpReader = new HttpReader();
        httpReader.setOnResultReadyListener(new HttpReader.OnResultReadyListener() {
            @Override
            public void resultReady(String result) {
                JsonHelper jsonHelper = new JsonHelper();
                departments = jsonHelper.getDepartments(result);
                useAdapter();
            }
        });
        httpReader.execute("https://locateit-backend.herokuapp.com/afdelingen");
    }

    private void useAdapter() {
        DepartmentListAdapter departmentListAdapter = new DepartmentListAdapter(getApplicationContext(), departments);
        final ListView listViewDepartments = findViewById(R.id.listDepartments);
        listViewDepartments.setAdapter(departmentListAdapter);

        listViewDepartments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parentView, View childView, int position, long id) {
                detailPagina(departments.get(position).getNaam());
            }
        });
    }

    private void detailPagina(String afdeling) {
        Bundle bundle = new Bundle();
        bundle.putString("afdeling", afdeling);
        Intent intent = new Intent(this, ProductsByDepartment.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}