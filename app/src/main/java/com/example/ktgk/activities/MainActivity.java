package com.example.ktgk.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ktgk.R;
import com.example.ktgk.models.Employee;
import com.example.ktgk.utils.DataManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView lvEmployees;
    Button btnAdd, btnSort, btnResetSort;
    EditText etSearch;
    Spinner spFilterType;

    ArrayAdapter<String> adapter;
    List<Employee> displayList = new ArrayList<>();
    List<String> displayStrings = new ArrayList<>();

    String selectedType = "Tất cả";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvEmployees = findViewById(R.id.lvEmployees);
        btnAdd = findViewById(R.id.btnAdd);
        btnSort = findViewById(R.id.btnSortSalary);
        btnResetSort = findViewById(R.id.btnResetSort);
        etSearch = findViewById(R.id.etSearchName);
        spFilterType = findViewById(R.id.spFilterType);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayStrings);
        lvEmployees.setAdapter(adapter);

        String[] filterOptions = {"Tất cả", "Staff", "Manager", "Intern"};
        ArrayAdapter<String> filterAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, filterOptions);
        spFilterType.setAdapter(filterAdapter);

        spFilterType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedType = filterOptions[position];
                filterData(etSearch.getText().toString());
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterData(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        btnSort.setOnClickListener(v -> {
            Collections.sort(displayList, (e1, e2) -> Double.compare(e2.tinhLuong(), e1.tinhLuong()));
            updateUI();
        });

        btnResetSort.setOnClickListener(v -> {
            filterData(etSearch.getText().toString());
        });

        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
            startActivity(intent);
        });

        lvEmployees.setOnItemClickListener((parent, view, position, id) -> {
            Employee selectedEmp = displayList.get(position);
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("EMP_INDEX", DataManager.danhSachNV.indexOf(selectedEmp));
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        filterData(etSearch.getText().toString());
    }

    private void filterData(String query) {
        displayList.clear();
        String lowerCaseQuery = query.toLowerCase().trim();

        for (Employee e : DataManager.danhSachNV) {
            boolean matchesName = e.getHoTen().toLowerCase().contains(lowerCaseQuery);
            boolean matchesType = selectedType.equals("Tất cả") || e.getLoaiNhanVien().equals(selectedType);

            if (matchesName && matchesType) {
                displayList.add(e);
            }
        }
        updateUI();
    }

    private void updateUI() {
        displayStrings.clear();
        for (Employee e : displayList) {
            String info = e.getMaNV() + " - " + e.getHoTen() + "\n" +
                    "Loại: " + e.getLoaiNhanVien() + " | Lương: " + String.format("%,.0f VNĐ", e.tinhLuong());
            displayStrings.add(info);
        }
        adapter.notifyDataSetChanged();
    }
}