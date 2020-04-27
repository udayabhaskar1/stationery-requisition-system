package com.example.logicapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AsyncToServer.IServerResponse {
    Button btnSet;
    Spinner spinner;
    ArrayList<DepartmentStaff> list;
    Command cmd;

    //datetimepicker values
    Button btnDatePicker, btnEndDatePicker;
    EditText txtDate, txtEndDate;
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSet=(Button) findViewById(R.id.btnSet);

        //datetimepicker
        btnDatePicker=(Button)findViewById(R.id.btn_date);
        btnEndDatePicker=(Button)findViewById(R.id.btn_enddate);
        txtDate=(EditText)findViewById(R.id.in_date);
        txtEndDate=(EditText)findViewById(R.id.in_time);

        btnDatePicker.setOnClickListener(this);
        btnEndDatePicker.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        cmd = new Command(this, "get", "http://10.0.2.2:64451/DeptHead/GetStaffList", null);
        new AsyncToServer().execute(cmd);
    }

    @Override
    public void onClick(View v) {
//        Command cmd;
//
        int id = v.getId();
        JSONArray array = new JSONArray();
        JSONObject jsonObj = new JSONObject();

        //datepicker if "select date is chosen"
        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnEndDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtEndDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

        //End Datepicker code


            try {
                DepartmentStaff staff = (DepartmentStaff) spinner.getSelectedItem();
                String staffname = staff.name;
                jsonObj.put("staffname", staffname);
                array.put(jsonObj);
            } catch (Exception e) {
                e.printStackTrace();
            }
            cmd = new Command(this, "set", "http://10.0.2.2:64451/DeptHead/AppointRepresentative", array);
            new AsyncToServer().execute(cmd);
        }

    @Override
    public void onServerResponse(Command jsonObj) {
        if (jsonObj == null)
            return;
        spinner=this.findViewById(R.id.EmployeeName);
        list=new ArrayList<DepartmentStaff>();
        try {
            String context = (String) jsonObj.context;
            JSONArray j=jsonObj.data;
            if (context.compareTo("get") == 0)
            {
                for(int i=0;i<j.length();i++){
                    JSONObject obj=j.getJSONObject(i);
                    String name=obj.getString("text");
                    int id=obj.getInt("id");
                    list.add(new DepartmentStaff(id,name));
                }
                spinner.setAdapter(new ArrayAdapter<DepartmentStaff>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, list));
//                System.out.println("id: " + name + ", text: " + age);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String employee=   spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
                        Toast.makeText(getApplicationContext(),employee,Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        // DO Nothing here
                    }
                });

            }else if(context.compareTo("set")==0){
                String status = (String)jsonObj.data.getJSONObject(0).get("text");
                System.out.println("status:" + status);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
