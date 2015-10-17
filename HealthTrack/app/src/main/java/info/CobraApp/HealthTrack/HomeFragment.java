package info.CobraApp.HealthTrack;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HomeFragment extends Fragment {
    TextView tv;
    String[] retdata;
    private  String FEED_URL = "https://bms-qit.herokuapp.com/get_feed";

    public HomeFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        tv=(TextView) rootView.findViewById(R.id.txtLabel);
        new updates().execute();
        return rootView;
    }
    private class updates extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           tv.setText("Loading...");
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
           tv.setText(retdata[0]);

        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                URL u = new URL(FEED_URL);
                URLConnection uc = u.openConnection();
                uc.connect();
                BufferedReader reader = new BufferedReader(new InputStreamReader(u.openStream(), "UTF-8"));
                String res = "";
                for (String line; (line = reader.readLine()) != null;) {
                    //System.out.println(line);
                    res += line;
                }
               retdata=res.split(",");

            } catch (MalformedURLException e) {
                Log.e("Errorvurl", e.getMessage());

            } catch (IOException e) {
                Log.e("Errors", e.getLocalizedMessage());
            }
            return null;
        }
    }
}
