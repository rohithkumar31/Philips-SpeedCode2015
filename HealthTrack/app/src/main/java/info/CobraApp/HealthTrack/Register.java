package info.CobraApp.HealthTrack;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Sachin on 16-10-2015.
 */
public class Register  extends Activity {
    private  String SIGNUP_URL = "https://bms-qit.herokuapp.com/signup/";
    private EditText email, password;
    String username,passwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = (EditText) findViewById(R.id.email2);
        password = (EditText) findViewById(R.id.password2);
        Button b = (Button) findViewById(R.id.btnRegister);
        b.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                username=email.getText().toString();
                passwd=password.getText().toString();
                new bghandler().execute(SIGNUP_URL);
            }
        });

    }
    public void registerUser(View v){
        //code to authenticate

        Intent in = new Intent("android.intent.action.MAIN_HOME");
        startActivity(in);

    }
    public void moveToLogin(View v){
        Intent i = new Intent("android.intent.action.MAIN");
        startActivity(i);
    }

    private class bghandler extends AsyncTask<String, String, String> {
        private ProgressDialog Dialog = new ProgressDialog(Register.this);
        String res;

        @Override
        protected String doInBackground(String... voids) {
            try {
                URL u = new URL(SIGNUP_URL+username+"/"+passwd);
                URLConnection uc = u.openConnection();
                uc.connect();
                BufferedReader reader = new BufferedReader(new InputStreamReader(u.openStream(), "UTF-8"));

                for (String line; (line = reader.readLine()) != null;) {
                    //System.out.println(line);
                    res += line;
                }

                //System.out.println("Status : "+res);
                if(res.equals("null1")){
                    GlobalDataHandler d = new GlobalDataHandler();
                    d.setUsername(username);
                    Intent in = new Intent("android.intent.action.MAIN_HOME");
                    startActivity(in);
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
            Dialog.setMessage("Validating...");
            Dialog.show();
        }
        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            Dialog.dismiss();
        }


    }
}
