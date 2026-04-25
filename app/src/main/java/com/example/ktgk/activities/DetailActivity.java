package com.example.ktgk.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ktgk.R;
import com.example.ktgk.models.Employee;
import com.example.ktgk.utils.DataManager;

public class DetailActivity extends AppCompatActivity {
    TextView tvDetails;
    Button btnEdit, btnDelete;
    int empIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvDetails = findViewById(R.id.tvDetails);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);

        empIndex = getIntent().getIntExtra("EMP_INDEX", -1);

        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(DetailActivity.this, AddEditActivity.class);
            intent.putExtra("EDIT_INDEX", empIndex);
            startActivity(intent);
        });

        btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc chắn muốn xóa nhân viên này?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        DataManager.danhSachNV.remove(empIndex);
                        finish();
                    })
                    .setNegativeButton("Không", null)
                    .show();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (empIndex != -1 && empIndex < DataManager.danhSachNV.size()) {
            Employee e = DataManager.danhSachNV.get(empIndex);
            String info = "Mã NV: " + e.getMaNV() + "\n" +
                    "Họ Tên: " + e.getHoTen() + "\n" +
                    "Loại: " + e.getLoaiNhanVien() + "\n" +
                    "Lương CB: " + String.format("%.0f", e.getLuongCoBan()) + "\n" +
                    e.hienThiThongTin() + "\n" +
                    "Lương Thực Nhận: " + String.format("%.0f", e.tinhLuong());
            tvDetails.setText(info);
        }
    }
}