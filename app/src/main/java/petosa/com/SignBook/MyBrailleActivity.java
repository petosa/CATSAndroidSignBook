package petosa.com.SignBook;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

/**
 * Created by ChaityaShah on 2/20/16.
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.speech.RecognizerIntent;
import android.widget.AdapterView;

import petosa.com.SignBook.R;

public class MyBrailleActivity extends ListActivity {

    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1001;

    private final Context CONTEXT = this;
    private FloatingActionButton captainFabulous;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_braille);
        Firebase.setAndroidContext(this);

        captainFabulous = (FloatingActionButton) findViewById(R.id.voice);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Setup our view and list adapter. Ensure it scrolls to the bottom as data changes
        final ListView listView = getListView();
        // Tell our list adapter that we only want 50 messages at a time
        ArrayAdapter<String> a = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, DBHelper.getAllPhrases());

        listView.setAdapter(a);
        listView.setLongClickable(true);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                new TTS(CONTEXT, false).execute((((TextView) ((RelativeLayout) (listView.getAdapter().getView(pos, null, listView))).findViewById(R.id.english))).getText().toString());
                return true;
            }
        });

        captainFabulous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak(v);
            }
        });

        captainFabulous.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new TTS(CONTEXT, false).execute("Speak a word or phrase to add it to your Braille Book.");
                return false;
            }
        });
    }

    public void learn(View v) {
        ((TextView)v.findViewById(R.id.english)).getText().toString();
        Intent myIntent = new Intent(MyBrailleActivity.this, LearnActivity.class);
        if(!((((TextView) v.findViewById(R.id.english)).getText().toString()).trim() == "")) {

            myIntent.putExtra("english", ((TextView) v.findViewById(R.id.english)).getText().toString().trim());
            myIntent.putExtra("current", new Integer(0));
            //myIntent.putExtra("next", 1);
            startActivity(myIntent);
        }
    }

    public void speak(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        // Specify the calling package to identify your application
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass()
                .getPackage().getName());

        // Display an hint to the user about what he should say.
        //intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak a word or phrase to ");
        // Given an hint to the recognizer about what the user is going to say
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE)

            //If Voice recognition is successful then it returns RESULT_OK
            if(resultCode == RESULT_OK) {
                ArrayList<String> textMatchList = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                Map<String, String> push = new HashMap<String, String>();
                push.put("english", textMatchList.get(0));
                //mFirebaseRef.push().setValue(push);
                //Result code for various error.
            }else if(resultCode == RecognizerIntent.RESULT_AUDIO_ERROR){
                showToastMessage("Audio Error");
            }else if(resultCode == RecognizerIntent.RESULT_CLIENT_ERROR){
                showToastMessage("Client Error");
            }else if(resultCode == RecognizerIntent.RESULT_NETWORK_ERROR){
                showToastMessage("Network Error");
            }else if(resultCode == RecognizerIntent.RESULT_NO_MATCH){
                showToastMessage("No Match");
            }else if(resultCode == RecognizerIntent.RESULT_SERVER_ERROR){
                showToastMessage("Server Error");
            }
        super.onActivityResult(requestCode, resultCode, data);
    }
    void showToastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent(MyBrailleActivity.this, MainActivity.class);

        startActivity(in);

        finish();
    }
}