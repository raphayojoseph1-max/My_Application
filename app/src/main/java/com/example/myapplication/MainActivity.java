package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Book> bookList;
    private BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        Toolbar toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerViewBooks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fabHelp);
        fab.setOnClickListener(v -> Toast.makeText(this, "Opening Support Chat...", Toast.LENGTH_SHORT).show());

        bookList = new ArrayList<>();
        bookList.add(new Book("The Last Ember", "Daniel Roth", "Historical Thriller", "A former archaeologist is pulled into a deadly conspiracy when ancient secrets buried beneath Rome resurface.", "978-0001", 19.99, R.drawable.cover_1));
        bookList.add(new Book("Quantum Mirage", "Lila Chen", "Science Fiction", "In a future where time travel is illegal, a rogue physicist must choose between saving the world or saving her daughter.", "978-0002", 15.50, R.drawable.cover_2));
        bookList.add(new Book("Roots & Wings", "Maria Esteban", "Literary Fiction", "A moving generational story of a Cuban-American family searching for identity, belonging, and redemption.", "978-0003", 12.99, R.drawable.cover_3));
        bookList.add(new Book("The Mind Sculptor", "Dr. Evan Shaw", "Psychology / Non-Fiction", "A groundbreaking look at neuroplasticity and how you can rewire your brain for success and happiness.", "978-0004", 24.95, R.drawable.cover_4));
        bookList.add(new Book("Inkbound: Chronicles of the Lost Library", "J.R. Faulkner", "Fantasy / Adventure", "A young librarian discovers that ancient books can open portals to other worlds—but not all stories have happy endings.", "978-0005", 18.75, R.drawable.cover_5));
        bookList.add(new Book("Startup Savage", "Nicole Vega", "Business / Entrepreneurship", "A brutally honest guide to launching a tech startup in the real world, full of failures, pivots, and unexpected wins.", "978-0006", 21.00, R.drawable.cover_6));
        bookList.add(new Book("Beneath Crimson Skies", "Tomasz Novak", "Historical Fiction / WWII", "The intertwined lives of resistance fighters, spies, and survivors during the Nazi occupation of Warsaw.", "978-0007", 16.99, R.drawable.cover_7));
        bookList.add(new Book("The Art of Stillness", "Tara Bell", "Self-Help / Mindfulness", "Learn how to find peace in a chaotic world by mastering the ancient wisdom of stillness.", "978-0008", 14.50, R.drawable.cover_8));
        bookList.add(new Book("Neon Ghosts", "Khalid Jones", "Urban Fantasy / Mystery", "A private investigator with the ability to see spirits uncovers a supernatural conspiracy beneath the city's neon lights.", "978-0009", 17.25, R.drawable.cover_9));
        bookList.add(new Book("Eat Green, Live Clean", "Dr. Sanjay Patel", "Health & Wellness", "A practical guide to plant-based nutrition and detox living, backed by science and easy recipes.", "978-0010", 22.50, R.drawable.cover_10));

        adapter = new BookAdapter(new ArrayList<>(bookList), book -> {
            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
            intent.putExtra("book", book);
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        if (searchItem != null) {
            SearchView searchView = (SearchView) searchItem.getActionView();
            if (searchView != null) {
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        filter(newText);
                        return true;
                    }
                });
            }
        }
        return true;
    }

    private void filter(String text) {
        List<Book> filteredList = new ArrayList<>();
        for (Book item : bookList) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase()) || 
                item.getAuthor().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }
}
