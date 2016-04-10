package petosa.com.SignBook;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import static petosa.com.SignBook.DBHelper.DATABASE_NAME;
import static petosa.com.SignBook.DBHelper.initTable;
import static petosa.com.SignBook.DBHelper.addPhrase;

import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity {

    final Context CONTEXT = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);

        Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
        Button button = (Button)findViewById( R.id.buttonSignBook);
        button.setTypeface(font);
        button = (Button)findViewById( R.id.buttonCamera);
        button.setTypeface(font);

        if(World.DB == null) {
            World.DB = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
            initTable();
            addPhrase("test2");
        }

        (findViewById(R.id.buttonCamera)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(MainActivity.this,
                        CameraActivity.class);
                startActivity(myIntent);
            }
        });
        (findViewById(R.id.buttonCamera)).setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                new TTS(CONTEXT, false).execute("CAMERA");
                return true;
            }
        });
        (findViewById(R.id.buttonSignBook)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(MainActivity.this,
                        MyBrailleActivity.class);
                startActivity(myIntent);
            }
        });
        (findViewById(R.id.buttonSignBook)).setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                new TTS(CONTEXT, false).execute("Braille Book");
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
