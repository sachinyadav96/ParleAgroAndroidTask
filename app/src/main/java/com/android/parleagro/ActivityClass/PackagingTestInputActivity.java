package com.android.parleagro.ActivityClass;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.parleagro.Database.SQLiteHandler;
import com.android.parleagro.R;
import com.android.parleagro.Util.PreferencesServices;
import com.android.parleagro.model.RecordModel;
import com.android.parleagro.model.TimeModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;


public class PackagingTestInputActivity extends Activity implements View.OnClickListener {
    SQLiteHandler dbHandler;
    EditText edt_timing;
    private boolean isSheetExpanded;
    BottomSheetBehavior sheetBehavior;
    RecyclerView TimeRecyclerview;
    TimeAdapter time_adapter;
    TextView txtSend;
    EditText edt_Fill_All_V;
    EditText edt_Fill_3_Every;
    Switch sw_Torque;
    int Torque = 1;
    Switch sw_jump;
    int jump = 1;
    Switch sw_Pifer;
    int Pifer = 1;
    Switch sw_Secure;
    int Secure = 1;
    Switch sw_crack;
    int crack = 1;
    Switch sw_Drop;
    int Drop = 1;
    EditText edt_Package;
    EditText edt_Coding;
    String TimeValue = "";
    int TimeID;
    String PlantName = "", MachineName = "";
    TextView machine_name, plant_name;
    int PlantID, MachineID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packaging_test_input);

        dbHandler = new SQLiteHandler(this);

        AddTiming();

        init();

    }

    private void init() {
        edt_timing = findViewById(R.id.edt_timing);
        txtSend = findViewById(R.id.txtSend);
        edt_Fill_All_V = findViewById(R.id.edt_Fill_All_V);
        edt_Fill_3_Every = findViewById(R.id.edt_Fill_3_Every);
        sw_Torque = findViewById(R.id.sw_Torque);
        sw_jump = findViewById(R.id.sw_jump);
        sw_Pifer = findViewById(R.id.sw_Pifer);
        sw_Secure = findViewById(R.id.sw_Secure);
        sw_crack = findViewById(R.id.sw_crack);
        sw_Drop = findViewById(R.id.sw_Drop);
        edt_Package = findViewById(R.id.edt_Package);
        edt_Coding = findViewById(R.id.edt_Coding);
        plant_name = findViewById(R.id.plant_name);
        machine_name = findViewById(R.id.machine_name);
        SetTimeBottomSheet();
        getTimeData();
        txtSend.setOnClickListener(this);

        PlantName = getIntent().getStringExtra("PlantName");
        PlantID = Integer.parseInt(getIntent().getStringExtra("PlantID"));
        MachineName = getIntent().getStringExtra("MachineName");
        MachineID = Integer.parseInt(getIntent().getStringExtra("MachineID"));

        machine_name.setText("Machine Name :" + MachineName);
        plant_name.setText("Plant Name :" + PlantName);
        edt_Fill_All_V.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {

                try {
                    int value = Integer.parseInt(edt_Fill_All_V.getText().toString());
                    if (5 <= value && value <= 10) {
                        edt_Fill_All_V.setTextColor(Color.GREEN);
                    } else {
                        edt_Fill_All_V.setTextColor(Color.RED);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

        });
        edt_Fill_3_Every.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                try {
                    double value = Double.parseDouble(edt_Fill_3_Every.getText().toString());
                    if (2.5 <= value && value <= 5.0) {
                        edt_Fill_3_Every.setTextColor(Color.GREEN);
                    } else {
                        edt_Fill_3_Every.setTextColor(Color.RED);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        sw_Torque.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (sw_Torque.isChecked()) {
                Torque = 1;
            } else {
                Torque = 0;
            }
        });

        sw_jump.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (sw_jump.isChecked()) {
                jump = 1;
            } else {
                jump = 0;
            }
        });

        sw_Pifer.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (sw_Pifer.isChecked()) {
                Pifer = 1;
            } else {
                Pifer = 0;
            }
        });

        sw_Secure.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (sw_Secure.isChecked()) {
                Secure = 1;
            } else {
                Secure = 0;
            }
        });

        sw_crack.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (sw_crack.isChecked()) {
                crack = 1;
            } else {
                crack = 0;
            }
        });

        sw_Drop.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (sw_Drop.isChecked()) {
                Drop = 1;
            } else {
                Drop = 0;
            }
        });
    }


    private void getTimeData() {
        Log.e("PlantType", dbHandler.getTimeJSON());

        final ArrayList<HashMap<String, String>> list = new ArrayList<>();
        try {
            JSONArray jArrayValue = new JSONArray(dbHandler.getTimeJSON());
            for (int i = 0; i < jArrayValue.length(); i++) {
                JSONObject jObjectsValue = jArrayValue.getJSONObject(i);
                HashMap<String, String> map;
                map = new HashMap<>();
                map.put("time_id", jObjectsValue.getString("time_id"));
                map.put("time_value", jObjectsValue.getString("time_value"));
                list.add(map);
            }
            time_adapter = new TimeAdapter(getApplicationContext(), list);
            TimeRecyclerview.setAdapter(time_adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm =
                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    private void SetTimeBottomSheet() {
        LinearLayout TimeLayoutBottomSheet = findViewById(R.id.time_bottom_sheet);
        TimeRecyclerview = TimeLayoutBottomSheet.findViewById(R.id.TimeRecyclerview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        TimeRecyclerview.setLayoutManager(gridLayoutManager);
        TimeRecyclerview.setHasFixedSize(true);
        TimeRecyclerview.setNestedScrollingEnabled(true);
        sheetBehavior = BottomSheetBehavior.from(TimeLayoutBottomSheet);
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @SuppressLint("SwitchIntDef")
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                switch (i) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        hideKeyboard();
                        isSheetExpanded = false;
                        break;

                    case BottomSheetBehavior.STATE_EXPANDED:
                        isSheetExpanded = true;
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

        TextView Close = TimeLayoutBottomSheet.findViewById(R.id.Close);
        Close.setOnClickListener(v -> sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED));

        edt_timing.setOnClickListener(v -> {
            if (isSheetExpanded) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            } else {
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
            isSheetExpanded = !isSheetExpanded;
        });
    }


    private void AddTiming() {
        if (!PreferencesServices.getISFirstTime(this).equalsIgnoreCase("yes")) {
            TimeModel time = new TimeModel();
            time.setTitle("7:00 to 7:30 ");
            dbHandler.insertIntoTime(time);
            time.setTitle("7:30 to 8:00 ");
            dbHandler.insertIntoTime(time);
            time.setTitle("8:00 to 8:30 ");
            dbHandler.insertIntoTime(time);
            time.setTitle("8:30 to 9:00 ");
            dbHandler.insertIntoTime(time);
            time.setTitle("9:00 to 9:30 ");
            dbHandler.insertIntoTime(time);
            time.setTitle("9:30 to 10:00 ");
            dbHandler.insertIntoTime(time);
            time.setTitle("10:00 to 10:30 ");
            dbHandler.insertIntoTime(time);
            time.setTitle("10:30 to 11:00 ");
            dbHandler.insertIntoTime(time);
            time.setTitle("11:00 to 11:30 ");
            dbHandler.insertIntoTime(time);
            time.setTitle("11:30 to 12:00 ");
            dbHandler.insertIntoTime(time);
            time.setTitle("12:00 to 12:30 ");
            dbHandler.insertIntoTime(time);
            time.setTitle("12:30 to 13:00 ");
            dbHandler.insertIntoTime(time);
            time.setTitle("13:00 to 13:30 ");
            dbHandler.insertIntoTime(time);
            time.setTitle("13:30 to 14:00 ");
            PreferencesServices.SetISFirstTime(PackagingTestInputActivity.this, "yes");
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.txtSend) {
            if (edt_timing.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please Select the Timing Slot ", Toast.LENGTH_SHORT).show();
            } else if (edt_Fill_All_V.getText().toString().isEmpty()) {
                edt_Fill_All_V.requestFocus();
                Toast.makeText(this, "Please enter the valid input for Fill Volume All Valves ", Toast.LENGTH_SHORT).show();
            } else if (edt_Fill_3_Every.getText().toString().isEmpty()) {
                edt_Fill_3_Every.requestFocus();
                Toast.makeText(this, "Please enter the valid input for Fill Volume 3 valves every Hour", Toast.LENGTH_SHORT).show();
            } else if (edt_Package.getText().toString().isEmpty()) {
                edt_Package.requestFocus();
                Toast.makeText(this, "Please enter the valid input for Package Appearance ", Toast.LENGTH_SHORT).show();
            } else if (edt_Coding.getText().toString().isEmpty()) {
                edt_Coding.requestFocus();
                Toast.makeText(this, "Please enter the valid input Date Coding and Rub test ", Toast.LENGTH_SHORT).show();
            } else {
                dbHandler = new SQLiteHandler(this);
                RecordModel record = new RecordModel();
                record.setTimeID(TimeID);
                record.setTimeValue(TimeValue);
                record.setPlantID(PlantID);
                record.setMachineID(MachineID);
                record.setFill_Volume_All_Valves(Integer.parseInt(edt_Fill_All_V.getText().toString()));
                record.setFill_Volume_3_valves_every_Hour(Double.parseDouble(edt_Fill_3_Every.getText().toString()));
                record.setFill_Closure_Torque(Torque);
                record.setClosure_jump_test(jump);
                record.setClosure_Pifer_band(Pifer);
                record.setClosure_Secure_Seal(Secure);
                record.setStress_crack_Test(crack);
                record.setDrop_Test(Drop);
                record.setPackage_Appearance((edt_Package.getText().toString()));
                record.setDate_Coding_Rub_test((edt_Coding.getText().toString()));
                record.setDATE(DateFormat.getDateInstance().format(new Date()));
                dbHandler.insertIntoRecord(record);
                edt_Package.setText("");
                edt_Coding.setText("");
                edt_Fill_3_Every.setText("");
                edt_Fill_All_V.setText("");
                sw_crack.setChecked(true);
                sw_Drop.setChecked(true);
                sw_jump.setChecked(true);
                sw_Pifer.setChecked(true);
                sw_Torque.setChecked(true);
                sw_Secure.setChecked(true);
                edt_timing.setText("");
                Toast.makeText(this, "Data Added Successfully", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void onClickView(View view) {

        startActivity(new Intent(getApplicationContext(), PackagingTestViewDataActivity.class)
                .putExtra("PlantName", PlantName)
                .putExtra("PlantID", PlantID)
                .putExtra("MachineID", MachineID)
                .putExtra("MachineName", MachineName));


    }


    public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.MyViewHolder> {
        private final ArrayList<HashMap<String, String>> mItems;

        public TimeAdapter(Context context, ArrayList<HashMap<String, String>> mItems) {
            this.mItems = mItems;
            ProgressDialog pDialog = new ProgressDialog(context);
            pDialog.setCancelable(false);
        }

        @NonNull
        @Override
        public TimeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                           int viewType) {
            View itemView =
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.row_time_adapter,
                            parent, false);
            return new TimeAdapter.MyViewHolder(itemView);
        }

        @SuppressLint({"SetTextI18n", "ResourceAsColor"})
        @Override
        public void onBindViewHolder(@NonNull TimeAdapter.MyViewHolder holder, final int position) {
            final HashMap<String, String> home = mItems.get(position);
            holder.Id = home.get("time_id");
            holder.txtCat.setText(home.get("time_value"));
            holder.delete.setVisibility(View.GONE);
            holder.txtCat.setOnClickListener(v -> {
                edt_timing.setText("Selected Timing Slot : " + home.get("time_value"));
                TimeValue = home.get("time_value");
                TimeID = Integer.parseInt(Objects.requireNonNull(home.get("time_id")));
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            });
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            String Id = "";
            TextView txtCat;
            ImageView delete;

            MyViewHolder(View view) {
                super(view);
                txtCat = view.findViewById(R.id.txtCat);
                delete = view.findViewById(R.id.delete);
            }
        }

    }

}