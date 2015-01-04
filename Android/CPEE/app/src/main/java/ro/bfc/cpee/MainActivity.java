package ro.bfc.cpee;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = "CPEE_MainActivity";
    EditText price;
    Spinner judete;;
    Spinner nivelEnergetic;
    private static final int FILE_SELECT_CODE = 0;
    CPEEDocument document;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PopulateSpinnerWithStringSequence(R.id.nivelEnergetic, R.array.nivelEnergetic, R.string.nivelEnergetic_select);
        document = FileUtils.readFromInternalStorage(this, "valori.cpee");
        if(document != null){
            //PopulateSpinnerWithStringSequence(R.id.judete, R.array.judete, R.string.judet_select);
            Spinner spinner = (Spinner) findViewById(R.id.judete);
            CountySpinnerAdapter adapter = new CountySpinnerAdapter(
                    MainActivity.this,
                    R.id.judete,
                    (County[]) document.Counties.toArray((County[]) Array.newInstance(County.class, 0)));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinner.setAdapter(
                    new NothingSelectedSpinnerAdapter(
                            adapter,
                            R.layout.contact_spinner_row_nothing_selected,
                            // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                            this, R.string.judet_select));
        }
    }

    private void PopulateSpinnerWithStringSequence(int spinnerId, int textResArryayId, int hintResId) {
        Spinner spinner = (Spinner) findViewById(spinnerId);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, textResArryayId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this, hintResId));
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
        } else if (id == R.id.action_open){
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            try {
                startActivityForResult(
                        Intent.createChooser(intent, "Select a File to Upload"),
                        FILE_SELECT_CODE);
            } catch (android.content.ActivityNotFoundException ex) {
                // Potentially direct the user to the Market with a Dialog
                Toast.makeText(this, "Please install a File Manager.",
                        Toast.LENGTH_SHORT).show();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        // Get the URI of the selected file
                        final Uri uri = data.getData();
                        Log.i(TAG, "Uri = " + uri.toString());
                        try {
                            //InputStream inputStream = getContentResolver().openInputStream(uri);
                            CPEEDocument doc = CPEEDocument.readJSON(FileUtils.getFileAsString(this,uri));
                            if(doc != null){
                                FileUtils.saveToInternalStorage(this, "valori.cpee", doc);
                            }
                        } catch (Exception e) {
                            Log.e("FileSelectorTestActivity", "File select error", e);
                            e.printStackTrace();
                        }
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
