package com.example.lovely_widget;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView listView;
    private Button button;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = sharedPreferences.edit();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        listView.setOnItemLongClickListener(new ListView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {

                removeItem(pos);

                return true;
            }
        });

        button = findViewById(R.id.add);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });

        Map<String, ?> numbers = sharedPreferences.getAll();

        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);

        for (Iterator<?> i = numbers.values().iterator(); i.hasNext(); ) {
            itemsAdapter.add((String) i.next());
        }
        ;
    }

    private void addItem() {
        EditText input = findViewById(R.id.numbers);
        String itemText = input.getText().toString();
        itemsAdapter.add(itemText);
        editor.putString(itemText, itemText);
        editor.apply();

    }


    private void removeItem(int index) {
        String itemText = itemsAdapter.getItem(index);
        itemsAdapter.remove(itemText);
        editor.remove(itemText);
        editor.apply();

    }
}