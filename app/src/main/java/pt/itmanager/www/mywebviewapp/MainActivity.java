package pt.itmanager.www.mywebviewapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.MailTo;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private WebView mWebView;
    private DatabaseHandler mDb;
    //String weatherWebserviceURL = "http://api.openweathermap.org/data/2.5/weather?q=ariana,tn&appid=2156e2dd5b92590ab69c0ae1b2d24586&units=metric";
    String weatherWebserviceURL = "http://itmanager.pt/mobile_contacts/get_contacts.php";

    private String apiPath = "http://itmanager.pt/mobile_contacts/get_contacts.php";
    private ProgressDialog processDialog;
    private JSONArray restulJsonArray;
    private int success = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        mDb = new DatabaseHandler(this);

        new ServiceStubAsyncTask(this, this, mDb).execute();

        mWebView = (WebView) findViewById(R.id.activity_main_webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        mWebView.getSettings().setAppCacheMaxSize(20 * 1024 * 1024); // 20MB
        mWebView.getSettings().setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDatabaseEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT); // load online by default

        // Reading all contacts
        //Log.d("Reading: ", "Reading all contacts..");
        //db.addContact(new Contact("Ravi", "9100000000","","","","","","",""));
        List<Contact> contacts = mDb.getAllContacts();

        for (Contact cn : contacts) {
            String log = "Id: "+cn.getID()+" ,Name: " + cn.getNome() + " ,Phone: " + cn.getTelemovel();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }
        if (!isNetworkAvailable()) { // loading offline
            //mWebView.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );
            mWebView.loadUrl("http://www.itmanager.pt/mobile_contacts");
        }else{
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
            mWebView.loadUrl("http://www.itmanager.pt/mobile_contacts");
        }


        // Force links and redirects to open in the WebView instead of in a browser
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (url.startsWith("mailto:")) {
                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                        emailIntent.setData(Uri.parse(url));
                        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(emailIntent);
                    }


                if( URLUtil.isNetworkUrl(url) ) {
                    return false;
                }
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity( intent );
                return true;
            }

        });

    }



    private class ServiceStubAsyncTask extends AsyncTask<Void, Void, Void> {

        private Context mContext;
        private Activity mActivity;
        String response = "";
        HashMap<String, String> postDataParams;

        public ServiceStubAsyncTask(Context context, Activity activity, DatabaseHandler db) {
            mContext = context;
            mActivity = activity;
            mDb = db;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            postDataParams = new HashMap<String, String>();
            postDataParams.put("HTTP_ACCEPT", "application/json");

            HttpConnectionService service = new HttpConnectionService();
            response = service.sendRequest(apiPath, postDataParams);
            JSONArray jsonArray = null;
            JSONArray jsonArray1 = null;

            try {

                JSONObject jsonResponse = new JSONObject(response);
                jsonArray = jsonResponse.getJSONArray("users");

                for(int i = 0; i < jsonArray.length(); i++){
                    String a = jsonArray.getString(i);
                    JSONObject resultJsonObject = new JSONObject(a);


                    String idd = (String) resultJsonObject.get("id");
                    String numero_funcionario = (String) resultJsonObject.get("numero_funcionario");
                    String departamento = (String) resultJsonObject.get("departamento");
                    String telemovel = (String) resultJsonObject.get("telemovel");
                    String ext_telemovel = (String) resultJsonObject.get("ext_telemovel");
                    String telefone = (String) resultJsonObject.get("telefone");
                    String ext_telefone = (String) resultJsonObject.get("ext_telefone");
                    String mail = (String) resultJsonObject.get("mail");
                    String nome = (String) resultJsonObject.get("nome");
                    String ultimonome = (String) resultJsonObject.get("ultimonome");

                    //Log.d("Tag", "Try: " + Integer.parseInt(idd));
                    Integer status = mDb.verifyContactoExist(Integer.parseInt(idd));
                    //Log.d("Tag", "Status: " + status);
                    if(status==0) {
                        //Log.d("Tag", "Insert: " + idd);
                        mDb.addContact(new Contact(Integer.parseInt(idd), numero_funcionario, departamento, telemovel, ext_telemovel, telefone, ext_telefone, mail, nome, ultimonome));
                    }



                }
                /*
                JSONObject jsonResponse1 = new JSONObject(response);
                jsonArray1 = jsonResponse.getJSONArray("contacts");
                for(int i = 0; i < jsonArray1.length(); i++){
                    String idd = (String) jsonResponse1.get("id");
                    String type = (String) jsonResponse1.get("type");
                    String type_name = (String) jsonResponse1.get("type_name");
                    String contact_id = (String) jsonResponse1.get("contact_id");
                    String contact_name = (String) jsonResponse1.get("contact_name");
                    String contact_number = (String) jsonResponse1.get("contact_number");

                    //Log.d("Tag", "Try: " + Integer.parseInt(idd));
                    Integer status = mDb.verifyContactoExist(Integer.parseInt(idd));
                    //Log.d("Tag", "Status: " + status);
                    if(status==0) {
                        //Log.d("Tag", "Insert: " + idd);
                        mDb.addContactAdicionar(new ContactAdditional(Integer.parseInt(idd), type, type_name, contact_id, contact_name, contact_number));
                    }
                }*/
            } catch (JSONException e) {
                e.printStackTrace();
            }
            success = 1;

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService( CONNECTIVITY_SERVICE );
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
