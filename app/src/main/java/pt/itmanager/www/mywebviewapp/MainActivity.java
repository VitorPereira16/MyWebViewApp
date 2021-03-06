package pt.itmanager.www.mywebviewapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private WebView mWebView;
    private DatabaseHandler mDb;
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

        if (isNetworkAvailable()) {
            new ServiceStubAsyncTask(this, this, mDb).execute();
        }

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
            String log = "Id: "+cn.getID2()+" ,Name: " + cn.getNome()+cn.getUltimoNome();
            // Writing Contacts to log
            Log.d("2Name: ", log);
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
                    String id2 = (String) resultJsonObject.get("id");
                    String numero_funcionario = (String) resultJsonObject.get("numero_funcionario");
                    String departamento = (String) resultJsonObject.get("departamento");
                    String mail = (String) resultJsonObject.get("mail");
                    String nome = (String) resultJsonObject.get("nome");
                    String ultimonome = (String) resultJsonObject.get("ultimonome");

                    //Log.d("Tag", "Try: " + Integer.parseInt(idd));
                    Integer status = mDb.verifyContactoExist(idd);
                    Log.d("Tag", "Status C: " + status);
                    if(status==0) {
                        //Log.d("Tag", "Insert: " + idd);
                        mDb.addContact(new Contact(Integer.parseInt(idd), id2,numero_funcionario, departamento, mail, nome, ultimonome));
                    }else{
                        Contact c = new Contact(Integer.parseInt(idd),id2, numero_funcionario, departamento, mail, nome, ultimonome);
                        mDb.updateContact(c);
                    }



                }

                jsonArray1 = jsonResponse.getJSONArray("contacts");
                for(int i = 0; i < jsonArray1.length(); i++){
                    String a = jsonArray1.getString(i);
                    JSONObject resultJsonObject = new JSONObject(a);

                    String id = (String) resultJsonObject.get("id");
                    String idd = (String) resultJsonObject.get("id");
                    String type = (String) resultJsonObject.get("type");
                    String type_name = (String) resultJsonObject.get("type_name");
                    String contact_id = (String) resultJsonObject.get("contact_id");
                    String contact_name = (String) resultJsonObject.get("contact_name");
                    String contact_number = (String) resultJsonObject.get("contact_number");

                    //Log.d("Tag", "Try: " + Integer.parseInt(idd));
                    Integer status = mDb.verifyContactoExistAd(idd, contact_id);
                    Log.d("Tag", "Status insert: " + status +" " + idd +" " +contact_id);
                    if(status==0) {
                        mDb.addContactAdicionar(new ContactAdditional(Integer.parseInt(id),idd, type, type_name, contact_id, contact_name, contact_number));
                    }else{
                        ContactAdditional ca = new ContactAdditional(Integer.parseInt(id), idd, type, type_name, contact_id, contact_name, contact_number);
                        mDb.updateContactAdditional(ca);
                    }
                }
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
