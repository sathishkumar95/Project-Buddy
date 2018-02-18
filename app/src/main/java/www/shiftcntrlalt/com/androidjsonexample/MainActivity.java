package www.shiftcntrlalt.com.androidjsonexample;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    EditText etGitHubUser, gitlang; // This will be a reference to our GitHub username input.
    Button btnGetRepos;  // This is a reference to the "Get Repos" button.
    RequestQueue requestQueue;  // This is our requests queue to process our HTTP requests.
    String baseUrl = "https://api.github.com/users/";  // This is the API base URL (GitHub API)
    String url, proj_url;  // This will hold the full URL which will include the username entered in the etGitHubUser.
    String baseSearch = "https://api.github.com/search/repositories?q=";
    String baseSearchlang = "+language:";
    String finishurl = "&sort=stars&order=desc";
    ArrayList<String> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etGitHubUser = (EditText)findViewById(R.id.myEditText);
        gitlang = (EditText)findViewById(R.id.myEditText2);
        btnGetRepos = (Button)findViewById(R.id.myButton);
        requestQueue = Volley.newRequestQueue(this);


        btnGetRepos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                    // Check if no view has focus:
                    View view = getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }



                final SimpleFragment sf = (SimpleFragment)getSupportFragmentManager().findFragmentById(R.id.fragment);

                String title = etGitHubUser.getText().toString().replace(" ","-").trim().toLowerCase();
                String lang =gitlang.getText().toString().replace(" ","-").trim().toLowerCase();


                if (title.matches("")) {
                    Toast.makeText(v.getContext(), "Enter title of project", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(lang.matches(""))
                {
                    Toast.makeText(v.getContext(), "Programming language is missing", Toast.LENGTH_SHORT).show();
                    return;

                }


                proj_url = baseSearch + title + baseSearchlang + lang + finishurl;
                Log.i("String title value",""+title);
                Log.i("String language value",""+lang);
                try{
                    new Async().execute(proj_url);
                }
                catch (Exception e){
                    e.printStackTrace();


                }

            }
        });

    }
    private class Async extends AsyncTask<String,Integer, String>{
        String server_response;
        ProgressBar pDialog;


        @Override
        protected void onPreExecute() {
            pDialog = (ProgressBar)findViewById(R.id.progressBar);
            pDialog.setVisibility(View.VISIBLE);


        }

        @Override
        protected String doInBackground(String... strings) {
            URL myurl;
            HttpURLConnection urlConnection = null;

            try {
                myurl = new URL(strings[0]);
                urlConnection = (HttpURLConnection) myurl.openConnection();

                int responseCode = urlConnection.getResponseCode();

                if(responseCode == HttpURLConnection.HTTP_OK){
                    server_response = readStream(urlConnection.getInputStream());
                    Log.v("CatalogClient", server_response);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return server_response;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.v("Response", "" + server_response);
            pDialog.setVisibility(View.INVISIBLE);
            pDialog.setVisibility(View.GONE);
            SimpleFragment sf = (SimpleFragment)getSupportFragmentManager().findFragmentById(R.id.fragment);
            arrayList = new ArrayList<String>();
            if( s != null )
            {
                try{
                    JSONObject jsonobject = new JSONObject(s);
                    JSONArray items = jsonobject.getJSONArray("items");
                    for(int i=0;i<items.length();i++)
                    {
                        JSONObject c = items.getJSONObject(i);
                        String name = c.getString("name");
                        arrayList.add(name);
                    }
                    sf.addToRepoList(arrayList);
                }
                catch(final JSONException e)
                {
                    e.printStackTrace();
                }
            }
            else {

                sf.addToRepoList(null);
            }


        }


    }

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }


    public void printDummyData()
    {
        ArrayList<String> dummy = new ArrayList<String>();
        dummy.add(" Dummy data 1");
        dummy.add(" Dummy data 2");
        dummy.add("Dummy data 3");
        SimpleFragment mysf = (SimpleFragment)getSupportFragmentManager().findFragmentById(R.id.fragment);
        mysf.addToRepoList(dummy);
    }













}
