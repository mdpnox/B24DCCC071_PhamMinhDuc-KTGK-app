package com.example.ktgk.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ktgk.R;
import com.example.ktgk.models.*;
import com.example.ktgk.utils.DataManager;

public class AddEditActivity extends AppCompatActivity {
    Spinner spType;
    EditText etId, etName, etBaseSalary, etDynamicField;
    Button btnSave;
    int editIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        spType = findViewById(R.id.spType);
        etId = findViewById(R.id.etId);
        etName = findViewById(R.id.etName);
        etBaseSalary = findViewById(R.id.etBaseSalary);
        etDynamicField = findViewById(R.id.etDynamicField);
        btnSave = findViewById(R.id.btnSave);

        String[] types = {"Staff", "Manager", "Intern"};
        spType.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, types));

        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) etDynamicField.setHint("Số ngày công (0-26)");
                else if (position == 1) etDynamicField.setHint("Phụ cấp chức vụ");
                else etDynamicField.setHint("Số giờ làm việc");
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        editIndex = getIntent().getIntExtra("EDIT_INDEX", -1);
        if (editIndex != -1) {
            Employee e = DataManager.danhSachNV.get(editIndex);
            etId.setText(e.getMaNV());
            etId.setEnabled(false);
            etName.setText(e.getHoTen());
            etBaseSalary.setText(String.valueOf(e.getLuongCoBan()));

            if (e instanceof Staff) {
                spType.setSelection(0);
                etDynamicField.setText(String.valueOf(((Staff) e).getSoNgayCong()));
            } else if (e instanceof Manager) {
                spType.setSelection(1);
                etDynamicField.setText(String.valueOf(((Manager) e).getPhuCapChucVu()));
            } else {
                spType.setSelection(2);
                etDynamicField.setText(String.valueOf(((Intern) e).getSoGioLam()));
            }
            spType.setEnabled(false);
        }

        btnSave.setOnClickListener(v -> saveEmployee());
    }

    private void saveEmployee() {
        try {
            String id = etId.getText().toString();
            String name = etName.getText().toString();
            double baseSal = Double.parseDouble(etBaseSalary.getText().toString());
            double dynamicVal = Double.parseDouble(etDynamicField.getText().toString());
            int typePos = spType.getSelectedItemPosition();

            Employee newEmp = null;
            if (typePos == 0) newEmp = new Staff(id, name, baseSal, (int) dynamicVal);
            else if (typePos == 1) newEmp = new Manager(id, name, baseSal, dynamicVal);
            else if (typePos == 2) newEmp = new Intern(id, name, baseSal, (int) dynamicVal);

            if (editIndex == -1) {
                DataManager.danhSachNV.add(newEmp); // Thêm
                Toast.makeText(this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
            } else {
                DataManager.danhSachNV.set(editIndex, newEmp); // Cập nhật
                Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
            }
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi dữ liệu: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}