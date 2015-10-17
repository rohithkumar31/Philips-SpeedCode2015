package info.CobraApp.HealthTrack;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class CommunityFragment extends Fragment{
    private  String SIGNUP_URL = "https://bms-qit.herokuapp.com/insert_dlog/";
    EditText disease,doctor,medic;
    private TextView Output;
    private Button changeDate,submit,camera;
    private int year, month, day;
    static final int DATE_PICKER_ID = 1111;
	public CommunityFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View v = inflater.inflate(R.layout.fragment_community, container, false);
        disease=(EditText)v.findViewById(R.id.etdisease);
        doctor=(EditText)v.findViewById(R.id.etDoctor);
        medic=(EditText)v.findViewById(R.id.medic);
        submit=(Button)v.findViewById(R.id.submit);
        Output=(TextView)v.findViewById(R.id.output);
        changeDate=(Button)v.findViewById(R.id.date);
        camera=(Button)v.findViewById(R.id.camera);

        submit.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {

            }
        });


        final Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day   = c.get(Calendar.DAY_OF_MONTH);

        Output.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(month + 1).append("-").append(day).append("-")
                .append(year).append(" "));

        // Button listener to show date picker dialog

        changeDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // On button click show datepicker dialog
            //    showDialog(DATE_PICKER_ID);

            }

        });
        return v;
    }

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID:

                // open datepicker dialog.
                // set date picker for current date
                // add pickerListener listner to date picker
                return new DatePickerDialog(getActivity(), pickerListener, year, month,day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year  = selectedYear;
            month = selectedMonth;
            day   = selectedDay;

            // Show selected date
            Output.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));

        }
    };
    private class bghandler extends AsyncTask<String, String, String> {
        //private ProgressDialog Dialog = new ProgressDialog(Register.this);
        String res;

        @Override
        protected String doInBackground(String... voids) {
            try {
                GlobalDataHandler d = new GlobalDataHandler();
                URL u = new URL(SIGNUP_URL+d.getUsername()+"/"+disease+"/"+Output+"/"+doctor+"/"+medic);
                URLConnection uc = u.openConnection();
                uc.connect();
                BufferedReader reader = new BufferedReader(new InputStreamReader(u.openStream(), "UTF-8"));

                for (String line; (line = reader.readLine()) != null;) {
                    //System.out.println(line);
                    res += line;
                }

                //System.out.println("Status : "+res);
                if(res.equals("null1")){
                    Toast.makeText(getActivity(),"Error in data insertion",Toast.LENGTH_LONG).show();
                }else{

                }

            } catch (MalformedURLException e) {
                Log.e("Errorvurl", e.getMessage());

            } catch (IOException e) {
                Log.e("Errors", e.getLocalizedMessage());
            }


            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          //  Dialog.setMessage("Validating...");
            //.show();
        }
        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
           // Dialog.dismiss();
        }


    }


    }


