package info.CobraApp.HealthTrack;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class PersonalLogFragment extends ListFragment {

    ListView lv;
    ArrayAdapter<String> adapter;
    String DATA_URL="https://bms-qit.herokuapp.com/get_plog/";
    ListDataAdapter[] data;
    EditText et;
    Context c;
    String[] numbers_text=new String[50] ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //get();
        c=inflater.getContext();

        new updates().execute();
        return super.onCreateView(inflater, container, savedInstanceState);
    }



    private class updates extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter = new ArrayAdapter<String>(
                    c, android.R.layout.simple_list_item_1,
                    numbers_text);
            setListAdapter(adapter);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                GlobalDataHandler gh = new GlobalDataHandler();
                URL u = new URL(DATA_URL+gh.getUsername());
                URLConnection uc = u.openConnection();
                uc.connect();
                BufferedReader reader = new BufferedReader(new InputStreamReader(u.openStream(), "UTF-8"));
                String res = "";
                int i=0;
                String[] tempd = new String[0];
// data = new ListDataAdapter[50];
                for (String line; (line = reader.readLine()) != null;) {
                   // String[] data_line=line.split(",");
                    //tempd[i]=data_line[1];
                    numbers_text[i]=line.split(",")[1];
                    // numbers_text[i]=row_data[2].split("'")[1];
                    //data[i].addData(row_data[2].split("'")[1],row_data[3].split("'")[1],row_data[4].split("'")[1],row_data[5].split("'")[1]);
                    // numbers_text[i]=line;
                    i++;
                }
//                numbers_text=new String[] {"fdsfdsf","fsdf"};

            } catch (MalformedURLException e) {
                Log.e("Errorvurl", e.getMessage());

            } catch (IOException e) {
                Log.e("Errors", e.getLocalizedMessage());
            }

            return null;
        }
    }
}
