package com.example.project1.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.project1.Adapter.Adapter_LV_ChoO;
import com.example.project1.Adapter.Adapter_lv_MonHoc;
import com.example.project1.DAO.DAO_MonHoc;
import com.example.project1.DAO.DAO_SinhVien;
import com.example.project1.R;
import com.example.project1.model.MonHoc;
import com.example.project1.model.SinhVien;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Main2Activity extends AppCompatActivity {
    private View view;
    private List<MonHoc> monHocList;
    private DAO_MonHoc dao_monHoc;
    private Adapter_lv_MonHoc adapterLvMonHoc;
    private ListView listViewMH;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        dao_monHoc = new DAO_MonHoc(this);
//        dao_monHoc.insert(new MonHoc("Tieng Anh"));
//        dao_monHoc.insert(new MonHoc("Ngu Van"));
//        dao_monHoc.insert(new MonHoc("Sinh Hoc"));
//        dao_monHoc.insert(new MonHoc("The Duc"));


        listViewMH = (ListView) findViewById(R.id.lv_activity2);
        monHocList = dao_monHoc.laydulieu();
        adapterLvMonHoc = new Adapter_lv_MonHoc(this, R.layout.raw_activity2, monHocList);
        listViewMH.setAdapter(adapterLvMonHoc);
        listViewMH.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                dao_monHoc.update(monHocList.get(position).getMaMH(),
                        new MonHoc (monHocList.get(position).getTenMH()+" moi"));

                return true;
            }
        });
        listViewMH.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(Main2Activity.this);
                dialog.setMessage("Bạn có muốn xóa không?");
                dialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dao_monHoc.delete(monHocList.get(position).getMaMH());
                    }
                });

                dialog.setPositiveButton("không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void refreshLV() {
        if (adapterLvMonHoc != null) {
            adapterLvMonHoc.notifyDataSetChanged();
        }
    }
}
