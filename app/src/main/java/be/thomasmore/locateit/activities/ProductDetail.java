package be.thomasmore.locateit.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import be.thomasmore.locateit.R;
import be.thomasmore.locateit.classes.Product;
import be.thomasmore.locateit.helpers.JsonHelper;
import be.thomasmore.locateit.http.HttpReader;

public class ProductDetail extends AppCompatActivity {
    String productId;
    Product product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        productId = bundle.getString("productId");
        getSupportActionBar().setTitle("Detail product");

        getProduct(productId);
    }

    private void getProduct(String id){
        HttpReader httpReader = new HttpReader();
        httpReader.setOnResultReadyListener(new HttpReader.OnResultReadyListener() {
            @Override
            public void resultReady(String result) {
                JsonHelper jsonHelper = new JsonHelper();
                product = jsonHelper.getProduct(result);
                showProduct(product);
            }
        });
        String url = "https://locateit-backend.herokuapp.com/products/";
        url = url.concat(productId);
        httpReader.execute(url);
    }

    private void showProduct(Product product){
        TextView naam = findViewById(R.id.productDetailNaam);
        TextView afdeling = findViewById(R.id.productDetailAfdeling);
        TextView beschrijving = findViewById(R.id.productDetailBeschrijving);
        TextView prijs = findViewById(R.id.productDetailPrijs);
        ImageView afbeelding = findViewById(R.id.productDetailAfbeelding);

        naam.setText(product.getNaam());
        afdeling.setText(product.getAfdeling());
        beschrijving.setText(product.getBeschrijving());
        prijs.setText("€"+Double.toString(product.getPrijs()));

        String imageUrl = "https://locateit.jordyliebens.be/imagesApp/";
        imageUrl = imageUrl.concat(product.getAfbeelding());

        Picasso.get().load(imageUrl).into(afbeelding);
    };
}
