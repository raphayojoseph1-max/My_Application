package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Locale;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Toolbar toolbar = findViewById(R.id.toolbarDetails);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        Book book = (Book) getIntent().getSerializableExtra("book");

        if (book != null) {
            ImageView cover = findViewById(R.id.imageViewDetailCover);
            TextView title = findViewById(R.id.textViewDetailTitle);
            TextView author = findViewById(R.id.textViewDetailAuthor);
            TextView genre = findViewById(R.id.textViewDetailGenre);
            TextView isbn = findViewById(R.id.textViewDetailISBN);
            TextView price = findViewById(R.id.textViewDetailPrice);
            TextView description = findViewById(R.id.textViewDetailDescription);
            Button buyButton = findViewById(R.id.buttonBuy);

            cover.setImageResource(book.getImageResource());
            title.setText(book.getTitle());
            author.setText("Author: " + book.getAuthor());
            genre.setText("Genre: " + book.getGenre());
            isbn.setText("ISBN: " + book.getIsbn());
            price.setText(String.format(Locale.getDefault(), "$%.2f", book.getPrice()));
            description.setText(book.getDescription());

            buyButton.setOnClickListener(v -> showPaymentSimulation(book));
        }
    }

    private void showPaymentSimulation(Book book) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Mobile Money Payment");
        builder.setMessage("Enter your mobile number to purchase \"" + book.getTitle() + "\" for " + 
                String.format(Locale.getDefault(), "$%.2f", book.getPrice()));

        // Simulate a simple input field if needed, but for "simulation" a confirmation is usually enough
        builder.setPositiveButton("Confirm Purchase", (dialog, which) -> {
            Toast.makeText(this, "Simulating Mobile Money API call...", Toast.LENGTH_SHORT).show();
            
            // Simulate processing delay using the main thread handler
            new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> {
                if (!isFinishing()) {
                    Toast.makeText(this, "Payment Successful! Thank you for your purchase.", Toast.LENGTH_LONG).show();
                }
            }, 2000);
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}
