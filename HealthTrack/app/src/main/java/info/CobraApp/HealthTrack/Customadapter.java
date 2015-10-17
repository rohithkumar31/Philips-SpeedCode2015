package info.CobraApp.HealthTrack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Sachin on 17-10-2015.
 */
public class Customadapter extends ArrayAdapter<ListDataAdapter> {
    ListDataAdapter[] items;
        Context c;
public Customadapter(Context context, int id,ListDataAdapter[] items) {
        super(context, id, items);
        this.items=items;
        c = context;

        }



@Override
public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        LayoutInflater vi = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = vi.inflate(R.layout.list_custom_row, null);


        TextView data1 = (TextView) v.findViewById(R.id.disease);
        data1.setText(items[position].date);

        TextView data2 = (TextView) v.findViewById(R.id.date);
        data2.setText(items[position].disease);


        return v;
        }
        }
