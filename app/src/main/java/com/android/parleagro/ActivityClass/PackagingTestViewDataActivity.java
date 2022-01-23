package com.android.parleagro.ActivityClass;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.parleagro.Database.SQLiteHandler;
import com.android.parleagro.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class PackagingTestViewDataActivity extends Activity implements View.OnClickListener {
    SQLiteHandler dbHandler;

    RecyclerView RecordRecyclerview;

    String PlantName = "", MachineName = "";
    int PlantID, MachineID;
    TextView machine_name, plant_name;
    RecordAdapter record_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packaging_test_view);

        dbHandler = new SQLiteHandler(this);


        init();

    }

    private void init() {

        RecordRecyclerview = findViewById(R.id.RecordRecyclerview);
        plant_name = findViewById(R.id.plant_name);
        machine_name = findViewById(R.id.machine_name);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        RecordRecyclerview.setLayoutManager(gridLayoutManager);
        RecordRecyclerview.setHasFixedSize(true);
        RecordRecyclerview.setNestedScrollingEnabled(true);
        getRecordData();

        PlantName = getIntent().getStringExtra("PlantName");
        PlantID =  (getIntent().getIntExtra("PlantID",0));
        MachineID =  (getIntent().getIntExtra("MachineID",0));
        MachineName = getIntent().getStringExtra("MachineName");
        machine_name.setText("Machine Name :" + MachineName);
        plant_name.setText("Plant Name :" + PlantName);
    }


    private void getRecordData() {
        Log.e("PlantType", dbHandler.getRecordJSON());

        final ArrayList<HashMap<String, String>> list = new ArrayList<>();
        try {
            JSONArray jArrayValue = new JSONArray(dbHandler.getRecordJSON());
            for (int i = 0; i < jArrayValue.length(); i++) {
                JSONObject jObjectsValue = jArrayValue.getJSONObject(i);
                HashMap<String, String> map;
                map = new HashMap<>();
                map.put("id", jObjectsValue.getString("id"));
                map.put("time_id", jObjectsValue.getString("time_id"));
                map.put("time_value", jObjectsValue.getString("time_value"));
                map.put("plant_id", jObjectsValue.getString("plant_id"));
                map.put("machine_id", jObjectsValue.getString("machine_id"));
                map.put("fill_all_v", jObjectsValue.getString("fill_all_v"));
                map.put("fill_3_v", jObjectsValue.getString("fill_3_v"));
                map.put("closure_torque", jObjectsValue.getString("closure_torque"));
                map.put("jump_test", jObjectsValue.getString("jump_test"));
                map.put("pifer_band", jObjectsValue.getString("pifer_band"));
                map.put("secure_seal", jObjectsValue.getString("secure_seal"));
                map.put("crack_test", jObjectsValue.getString("crack_test"));
                map.put("drop_test", jObjectsValue.getString("drop_test"));
                map.put("package_appearance", jObjectsValue.getString("package_appearance"));
                map.put("coding_rub_test", jObjectsValue.getString("coding_rub_test"));
                map.put("date", jObjectsValue.getString("date"));
                list.add(map);
            }
            record_adapter = new RecordAdapter(getApplicationContext(), list);
            RecordRecyclerview.setAdapter(record_adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.txtSend) {
          /*  if (edt_timing.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please Select the Timing Slot ", Toast.LENGTH_SHORT).show();
            }*/
        }

    }


    public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.MyViewHolder> {
        private final ArrayList<HashMap<String, String>> mItems;

        public RecordAdapter(Context context, ArrayList<HashMap<String, String>> mItems) {
            this.mItems = mItems;
            ProgressDialog pDialog = new ProgressDialog(context);
            pDialog.setCancelable(false);
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                               int viewType) {
            View itemView =
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.row_record_adapter,
                            parent, false);
            return new MyViewHolder(itemView);
        }

        @SuppressLint({"SetTextI18n", "ResourceAsColor"})
        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
            final HashMap<String, String> home = mItems.get(position);
            holder.Id = home.get("id");
            holder.txt_timing.setText("Time Slot : " + home.get("time_value"));
            holder.edt_Package.setText(home.get("package_appearance"));
            holder.edt_Coding.setText(home.get("coding_rub_test"));
            holder.edt_Fill_3_Every.setText(home.get("fill_3_v"));
            holder.edt_Fill_All_V.setText(home.get("fill_all_v"));
            holder.Date.setText(home.get("date"));
            holder.Torque = Integer.parseInt(Objects.requireNonNull(home.get("closure_torque")));
            holder.jump = Integer.parseInt(Objects.requireNonNull(home.get("jump_test")));
            holder.Pifer = Integer.parseInt(Objects.requireNonNull(home.get("pifer_band")));
            holder.Secure = Integer.parseInt(Objects.requireNonNull(home.get("secure_seal")));
            holder.crack = Integer.parseInt(Objects.requireNonNull(home.get("crack_test")));
            holder.Drop = Integer.parseInt(Objects.requireNonNull(home.get("drop_test")));
            int value = Integer.parseInt(Objects.requireNonNull(home.get("fill_all_v")));
            if (5 <= value && value <= 10) {
                holder.edt_Fill_All_V.setTextColor(Color.GREEN);
            } else {
                holder.edt_Fill_All_V.setTextColor(Color.RED);
            }

            try {
                double value1 = Double.parseDouble(Objects.requireNonNull(home.get("fill_3_v")));
                if (2.5 <= value1 && value1 <= 5.0) {
                    holder.edt_Fill_3_Every.setTextColor(Color.GREEN);
                } else {
                    holder.edt_Fill_3_Every.setTextColor(Color.RED);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (holder.crack == 1) {
                holder.sw_crack.setChecked(true);
            } else {
                holder.sw_crack.setChecked(false);
            }

            if (holder.Torque == 1) {
                holder.sw_Torque.setChecked(true);
            } else {
                holder.sw_Torque.setChecked(false);
            }

            if (holder.jump == 1) {
                holder.sw_jump.setChecked(true);
            } else {
                holder.sw_jump.setChecked(false);
            }
            if (holder.Pifer == 1) {
                holder.sw_Pifer.setChecked(true);
            } else {
                holder.sw_Pifer.setChecked(false);
            }
            if (holder.Secure == 1) {
                holder.sw_Secure.setChecked(true);
            } else {
                holder.sw_Secure.setChecked(false);
            }

            if (holder.Drop == 1) {
                holder.sw_Drop.setChecked(true);
            } else {
                holder.sw_Drop.setChecked(false);
            }


        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            String Id = "";
            TextView txt_timing;
            EditText edt_Package, edt_Coding, edt_Fill_3_Every, edt_Fill_All_V;
            Switch sw_crack, sw_Drop, sw_jump, sw_Pifer, sw_Torque, sw_Secure;
            TextView Date;

            int Torque;
            int jump;
            int Pifer;
            int Secure;
            int crack;
            int Drop;

            MyViewHolder(View view) {
                super(view);
                txt_timing = view.findViewById(R.id.txt_timing);
                edt_Package = view.findViewById(R.id.edt_Package);
                edt_Coding = view.findViewById(R.id.edt_Coding);
                edt_Fill_3_Every = view.findViewById(R.id.edt_Fill_3_Every);
                edt_Fill_All_V = view.findViewById(R.id.edt_Fill_All_V);
                sw_crack = view.findViewById(R.id.sw_crack);
                sw_Drop = view.findViewById(R.id.sw_Drop);
                sw_jump = view.findViewById(R.id.sw_jump);
                sw_Pifer = view.findViewById(R.id.sw_Pifer);
                sw_Torque = view.findViewById(R.id.sw_Torque);
                sw_Secure = view.findViewById(R.id.sw_Secure);
                Date = view.findViewById(R.id.Date);
            }
        }

    }

}