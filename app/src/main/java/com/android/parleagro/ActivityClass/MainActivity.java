package com.android.parleagro.ActivityClass;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.parleagro.Database.SQLiteHandler;
import com.android.parleagro.R;
import com.android.parleagro.Util.PreferencesServices;
import com.android.parleagro.model.MachineModel;
import com.android.parleagro.model.PlantTypeModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class MainActivity extends Activity {
    SQLiteHandler dbHandler;
    private boolean doubleBackToExitPressedOnce;
    EditText edt_plant;
    EditText edt_machine;
    BottomSheetBehavior sheetBehavior;
    BottomSheetBehavior sheetBehaviorMachine;
    EditText et_Type;
    EditText et_machine_name;
    RecyclerView recyclerview;
    RecyclerView MachineRecyclerview;
    private boolean isSheetExpanded;
    private boolean isSheetMachineExpanded;
    PlantAdapter plant_Adapter;
    MachineAdapter machine_Adapter;
    Integer SelectedPlantID;
    Integer SelectedMachineID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {

        dbHandler = new SQLiteHandler(this);
        hideKeyboard();

        AddEntryPlantType();

        edt_machine = findViewById(R.id.edt_machine);

        edt_plant = findViewById(R.id.edt_plant);


        SetPlantBottomSheet();

        SetMachineBottomSheet();


    }

    private void SetPlantBottomSheet() {


        LinearLayout layoutBottomSheet = findViewById(R.id.job_list_bottom_sheet);
        et_Type = layoutBottomSheet.findViewById(R.id.et_Type);
        recyclerview = layoutBottomSheet.findViewById(R.id.recyclerview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(gridLayoutManager);
        recyclerview.setHasFixedSize(true);
        recyclerview.setNestedScrollingEnabled(true);
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
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
        Log.e("dddd-", dbHandler.getPlantType().toString());
        getPlantTypeData();
        TextView btnDone = layoutBottomSheet.findViewById(R.id.bt_done);
        TextView Close = layoutBottomSheet.findViewById(R.id.Close);
        Close.setOnClickListener(v -> sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED));

        btnDone.setOnClickListener(v -> {
            if (et_Type.getText().toString().equalsIgnoreCase("")) {
                Toast.makeText(this, "Enter The Plant type Value", Toast.LENGTH_SHORT).show();
            } else {
                getPlantTypeData();
                getMachineData(SelectedPlantID);
                //edt_plant.setText(et_Type.getText().toString());
                // sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                PlantTypeModel card = new PlantTypeModel();
                card.setTitle(et_Type.getText().toString());
                dbHandler.insertIntoPlantType(card);
                et_Type.setText("");

                getPlantTypeData();
                getMachineData(SelectedPlantID);

            }
        });
        edt_plant.setOnClickListener(v -> {
            if (isSheetExpanded) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            } else {
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
            isSheetExpanded = !isSheetExpanded;
        });

    }

    private void SetMachineBottomSheet() {

        LinearLayout MachineLayoutBottomSheet = findViewById(R.id.machine_bottom_sheet);
        et_machine_name = MachineLayoutBottomSheet.findViewById(R.id.et_machine_name);
        MachineRecyclerview = MachineLayoutBottomSheet.findViewById(R.id.MachineRecyclerview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        MachineRecyclerview.setLayoutManager(gridLayoutManager);
        MachineRecyclerview.setHasFixedSize(true);
        MachineRecyclerview.setNestedScrollingEnabled(true);
        sheetBehaviorMachine = BottomSheetBehavior.from(MachineLayoutBottomSheet);
        sheetBehaviorMachine.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
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
        // getMachineData(SelectedPlantID);
        TextView bt_machine_done = MachineLayoutBottomSheet.findViewById(R.id.bt_machine_done);
        TextView MAchineClose = MachineLayoutBottomSheet.findViewById(R.id.MAchineClose);
        MAchineClose.setOnClickListener(v -> sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED));

        bt_machine_done.setOnClickListener(v -> {
            if (et_machine_name.getText().toString().equalsIgnoreCase("")) {
                Toast.makeText(this, "Enter The Machine Value", Toast.LENGTH_SHORT).show();
            } else {
                getMachineData(SelectedPlantID);
                // edt_machine.setText(et_machine_name.getText().toString());

                //   sheetBehaviorMachine.setState(BottomSheetBehavior.STATE_COLLAPSED);
                MachineModel machine = new MachineModel();
                machine.setTitle(et_machine_name.getText().toString());
                machine.setPantid(SelectedPlantID);
                dbHandler.insertIntoMachine(machine);
                et_machine_name.setText("");
                getMachineData(SelectedPlantID);
            }

        });
        edt_machine.setOnClickListener(v -> {
            if (isSheetMachineExpanded) {
                sheetBehaviorMachine.setState(BottomSheetBehavior.STATE_COLLAPSED);
            } else {
                sheetBehaviorMachine.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
            isSheetMachineExpanded = !isSheetMachineExpanded;
        });
    }

    private void getPlantTypeData() {
        Log.e("PlantType", dbHandler.getPlantTypeJSON());

        final ArrayList<HashMap<String, String>> list = new ArrayList<>();
        try {
            JSONArray jArrayValue = new JSONArray(dbHandler.getPlantTypeJSON());
            for (int i = 0; i < jArrayValue.length(); i++) {
                JSONObject jObjectsValue = jArrayValue.getJSONObject(i);
                HashMap<String, String> map;
                map = new HashMap<>();
                map.put("plant_type_id", jObjectsValue.getString("plant_type_id"));
                map.put("plant_type_name", jObjectsValue.getString("plant_type_name"));
                list.add(map);
            }
            plant_Adapter = new PlantAdapter(getApplicationContext(), list);
            recyclerview.setAdapter(plant_Adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getMachineData(Integer SelectedPlantID) {
        Log.e("machine", dbHandler.getMachineJSON(SelectedPlantID));
        final ArrayList<HashMap<String, String>> list = new ArrayList<>();
        try {
            JSONArray jArrayValue = new JSONArray(dbHandler.getMachineJSON(SelectedPlantID));
            if (jArrayValue.length() > 0) {
                for (int i = 0; i < jArrayValue.length(); i++) {
                    JSONObject jObjectsValue = jArrayValue.getJSONObject(i);
                    HashMap<String, String> map;
                    map = new HashMap<>();
                    map.put("machine_id", jObjectsValue.getString("machine_id"));
                    map.put("machine_name", jObjectsValue.getString("machine_name"));
                    map.put("plant_type_id", jObjectsValue.getString("plant_type_id"));
                    list.add(map);
                }
                machine_Adapter = new MachineAdapter(getApplicationContext(), list);
                MachineRecyclerview.setAdapter(machine_Adapter);
            } else {
                MachineRecyclerview.setAdapter(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClickSubmit(View view) {
        if (edt_plant.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Select the Plant Name", Toast.LENGTH_SHORT).show();
        } else if (edt_machine.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Select The Machine Name", Toast.LENGTH_SHORT).show();
        } else {

            startActivity(new Intent(getApplicationContext(), PackagingTestInputActivity.class)
                    .putExtra("PlantName", edt_plant.getText().toString())
                    .putExtra("PlantID", SelectedPlantID.toString())
                    .putExtra("MachineID", SelectedMachineID.toString())
                    .putExtra("MachineName", edt_machine.getText().toString())

            );
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

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce)
            finish();
        Toast.makeText(this, R.string.back_double_press, Toast.LENGTH_SHORT).show();
        doubleBackToExitPressedOnce = true;
        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }

    private void AddEntryPlantType() {
        if (!PreferencesServices.getISInsertValue(this).equalsIgnoreCase("done")) {
            PlantTypeModel plant = new PlantTypeModel();
            plant.setTitle("Mumbai Plant ");
            dbHandler.insertIntoPlantType(plant);
            plant.setTitle("Pune Plant  ");
            dbHandler.insertIntoPlantType(plant);
            plant.setTitle("Plant 1 ");
            dbHandler.insertIntoPlantType(plant);
            plant.setTitle("Plant 2");
            dbHandler.insertIntoPlantType(plant);
            plant.setTitle("India Plant");
            dbHandler.insertIntoPlantType(plant);
            plant.setTitle("Mira road Plant");
            dbHandler.insertIntoPlantType(plant);
            plant.setTitle("Agro Plant ");
            dbHandler.insertIntoPlantType(plant);
            plant.setTitle("IT Plant");
            dbHandler.insertIntoPlantType(plant);
            plant.setTitle("Coding Plant");
            PreferencesServices.SetISInsertValue(MainActivity.this, "done");
        }
    }

    public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.MyViewHolder> {
        private ArrayList<HashMap<String, String>> mItems;
        private ProgressDialog pDialog;
        private Context context;

        public PlantAdapter(Context context, ArrayList<HashMap<String, String>> mItems) {
            this.context = context;
            this.mItems = mItems;
            pDialog = new ProgressDialog(context);
            pDialog.setCancelable(false);
        }

        @NonNull
        @Override
        public PlantAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                            int viewType) {
            View itemView =
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.row_type_adapter,
                            parent, false);
            return new PlantAdapter.MyViewHolder(itemView);
        }

        @SuppressLint({"SetTextI18n", "ResourceAsColor"})
        @Override
        public void onBindViewHolder(@NonNull PlantAdapter.MyViewHolder holder, final int position) {
            final HashMap<String, String> home = mItems.get(position);
            holder.Id = home.get("plant_type_id");
            holder.txtCat.setText(home.get("plant_type_name"));
            holder.delete.setVisibility(View.GONE);
            holder.txtCat.setOnClickListener(v -> {
                edt_plant.setText(home.get("plant_type_name"));
                SelectedPlantID = Integer.valueOf(home.get("plant_type_id"));
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                getMachineData(SelectedPlantID);

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

    public class MachineAdapter extends RecyclerView.Adapter<MachineAdapter.MyViewHolder> {
        private ArrayList<HashMap<String, String>> mItems;
        private ProgressDialog pDialog;
        private Context context;

        public MachineAdapter(Context context, ArrayList<HashMap<String, String>> mItems) {
            this.context = context;
            this.mItems = mItems;
            pDialog = new ProgressDialog(context);
            pDialog.setCancelable(false);
        }

        @NonNull
        @Override
        public MachineAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                              int viewType) {
            View itemView =
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.row_type_adapter,
                            parent, false);
            return new MachineAdapter.MyViewHolder(itemView);
        }

        @SuppressLint({"SetTextI18n", "ResourceAsColor"})
        @Override
        public void onBindViewHolder(@NonNull MachineAdapter.MyViewHolder holder, final int position) {
            final HashMap<String, String> home = mItems.get(position);
            holder.Id = home.get("machine_id");
            holder.txtCat.setText(home.get("machine_name"));
            holder.delete.setVisibility(View.GONE);
            holder.txtCat.setOnClickListener(v -> {
                edt_machine.setText(home.get("machine_name"));
                SelectedMachineID = Integer.valueOf(Objects.requireNonNull(home.get("machine_id")));
                sheetBehaviorMachine.setState(BottomSheetBehavior.STATE_COLLAPSED);
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