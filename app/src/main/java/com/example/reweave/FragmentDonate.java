//package com.example.reweave;
//
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import android.widget.SearchView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.material.search.SearchBar;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import javax.annotation.Nullable;
//
//public class FragmentDonate extends AppCompatActivity {
//
//    private SearchView searchView;
//    private SearchBar searchBar;
//    private ListView listView;
//    private ArrayAdapter<String> adapter;
//
//    private final List<String> dataList = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve", "Frank");
//    private final List<String> filteredList = new ArrayList<>();
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        searchView = findViewById(R.id.search_view);
//        searchBar = findViewById(R.id.search_bar);
//        listView = findViewById(R.id.listView);
//
//        // Hide search on submit and carry the text to search bar
//        searchView.getEditText().setOnEditorActionListener((v, actionId, event) -> {
//            searchBar.setText(searchView.getText().toString());
//            searchView.hide();
//            return false;
//        });
//
//        // Initialize ListView with data
//        filteredList.addAll(dataList);
//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filteredList);
//        listView.setAdapter(adapter);
//
//        // Show searchView when searchBar is clicked
//        searchBar.setOnClickListener(v -> searchView.show());
//
//        // Handle text change in SearchView input
//        searchView.getEditText().addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                filterList(s.toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {}
//        });
//    }
//
//    private void filterList(String query) {
//        filteredList.clear();
//        if (query == null || query.isEmpty()) {
//            filteredList.addAll(dataList);
//        } else {
//            for (String item : dataList) {
//                if (item.toLowerCase().contains(query.toLowerCase())) {
//                    filteredList.add(item);
//                }
//            }
//        }
//        adapter.notifyDataSetChanged();
//    }
//}