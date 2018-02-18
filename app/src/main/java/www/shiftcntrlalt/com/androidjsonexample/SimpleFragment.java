package www.shiftcntrlalt.com.androidjsonexample;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SimpleFragment extends Fragment {


    TextView mytv;
    LinearLayout ll;


    public SimpleFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View rootview = inflater.inflate(R.layout.fragment_simple,container,false);
        ll = (LinearLayout)rootview.findViewById(R.id.myLinearLayout);
      return  rootview;
    }


    public void addToRepoList(ArrayList<String> lastUpdated)
    {

        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        if(!(lastUpdated.isEmpty())) {
            for (int i = 0; i < lastUpdated.size(); i++) {

                TextView tv = new TextView(getActivity().getApplicationContext());
                tv.setText(lastUpdated.get(i));
                tv.setTextColor(Color.WHITE);
                tv.setLayoutParams(lp);
                tv.setGravity(Gravity.CENTER_HORIZONTAL);
                tv.setPadding(20, 20, 20, 20);
                //tv.setBackgroundColor(getResources().getColor(R.color.textViewColor));
                ll.addView(tv);

            }
        }
        else{
            TextView tv = new TextView(getActivity().getApplicationContext());
            tv.setText("No Results found");
            tv.setTextColor(Color.WHITE);
            tv.setLayoutParams(lp);
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
            tv.setBackgroundColor(getResources().getColor(R.color.fragmentBackgraoundDark));
            tv.setPadding(20, 20, 20, 20);
            ll.addView(tv);

        }


    }



}
