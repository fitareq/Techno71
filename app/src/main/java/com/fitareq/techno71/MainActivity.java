package com.fitareq.techno71;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.fitareq.techno71.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.searchBtn.setEnabled(false);
        binding.urlClear.setVisibility(View.GONE);

        binding.searchBtn.setOnClickListener(view -> {
            Utils.saveStringToSharedPref(url, MainActivity.this);
            startActivity(new Intent(getApplicationContext(), BrowserActivity.class).putExtra(Utils.URL_KEY, url));
        });

        binding.urlInput.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                Utils.saveStringToSharedPref(url, MainActivity.this);
                startActivity(new Intent(getApplicationContext(), BrowserActivity.class).putExtra(Utils.URL_KEY, url));
                return true;
            }
            return false;
        });

        binding.urlInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                url = charSequence.toString();
                if (charSequence.length() > 0) {
                    binding.urlClear.setVisibility(View.VISIBLE);
                    binding.searchBtn.setEnabled(true);
                } else {
                    binding.urlClear.setVisibility(View.GONE);
                    binding.searchBtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.urlClear.setOnClickListener(view -> {
            binding.urlInput.setText("");
        });
//        prepareRecentSearch();
    }


    private void prepareRecentSearch() {
        ArrayList<String> arrayList = Utils.getRecentSearch(this);

        if (!arrayList.isEmpty()) {
            //HashSet<String> hashSet = new HashSet<>(arrayList);
            //arrayList = new ArrayList<>(hashSet);

            binding.list.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            binding.list.setAdapter(new RecentAdapter(arrayList, () -> {
                binding.recentSearch.setVisibility(View.GONE);
            }));
           /* ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                    arrayList
            );


            binding.list.setAdapter(adapter);
            binding.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    startActivity(new Intent(getApplicationContext(), BrowserActivity.class).putExtra(Utils.URL_KEY, adapter.getItem(i)));
                }
            });*/
            binding.recentSearch.setVisibility(View.VISIBLE);
            binding.list.setVisibility(View.VISIBLE);
        } else {
            binding.recentSearch.setVisibility(View.GONE);
            binding.list.setVisibility(View.GONE);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        prepareRecentSearch();
    }
}