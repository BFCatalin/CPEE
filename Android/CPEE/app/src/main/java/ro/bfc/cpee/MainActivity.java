package ro.bfc.cpee;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ro.bfc.cpee.Controllers.MainActivityController;
import ro.bfc.cpee.adapters.CountySpinnerAdapter;
import ro.bfc.cpee.adapters.NothingSelectedSpinnerAdapter;
import ro.bfc.cpee.models.CPEEDocument;
import ro.bfc.cpee.models.County;
import ro.bfc.cpee.models.Total;
import ro.bfc.cpee.models.TotalComponent;
import ro.bfc.cpee.views.IMainActivityView;


public class MainActivity extends ActionBarActivity implements IMainActivityView {
    private static final String TAG = "CPEE_MainActivity";
    private static final int FILE_SELECT_CODE = 0;
    EditText price;
    ImageButton refresh_total;
    CPEEDocument document;
    MainActivityController controller;
    CountySpinnerAdapter countySpinnerAdapter;

    List<Double> groupList;
    List<TotalComponent> childList;
    Map<Double, List<TotalComponent>> totalComponents;
    ExpandableListView expListView;
    ExpandableListAdapter expListAdapter;
    TextView textDistrib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            textDistrib = (TextView) findViewById(R.id.text_distribuitor);
            initiateSpinners();
            initiatePriceField();
            initiateExpandableList();
            Intent intent = getIntent();
            controller = new MainActivityController(this);
            controller.init(intent);
        }
        catch (Exception e){
            this.showMessageLong(e.getMessage());
        }
    }

    private void initiateExpandableList() {
        groupList = new ArrayList<Double>();
        totalComponents = new LinkedHashMap<Double, List<TotalComponent>>();
        expListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expListAdapter = new ExpandableListAdapter(this, groupList, totalComponents);
        expListView.setAdapter(expListAdapter);
    }

    private void initiatePriceField() {
        price = (EditText) findViewById(R.id.price);
        price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                price = (EditText) findViewById(R.id.price);
                String priceString = price.getText().toString().trim();
                Double dPrice= 0.0;
                if(!priceString.isEmpty()) {
                    dPrice = Double.parseDouble(priceString);
                }
                controller.setPretBaza(dPrice);
            }

            @Override
            public void afterTextChanged(Editable s) {
                price = (EditText) findViewById(R.id.price);
                String priceString = price.getText().toString().trim();
                Double dPrice= 0.0;
                if(!priceString.isEmpty()) {
                    dPrice = Double.parseDouble(priceString);
                }
                controller.setPretBaza(dPrice);
            }
        });
    }

    private void initiateSpinners() {
        populateSpinnerWithStringSequence(R.id.nivelEnergetic, R.array.nivelEnergetic, R.string.nivelEnergetic_select);

        Spinner spinner = (Spinner) findViewById(R.id.judete);
        countySpinnerAdapter = new CountySpinnerAdapter(
                MainActivity.this,
                R.id.judete, new ArrayList<County>());
        countySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        countySpinnerAdapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this, R.string.judet_select));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(id >= 0)
                    controller.onCountySelected((int)id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void populateSpinnerWithStringSequence(int spinnerId, int textResArryayId, int hintResId) {
        Spinner spinner = (Spinner) findViewById(spinnerId);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, textResArryayId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this, hintResId));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(id >= 0)
                    controller.onNivelEnergeticSelected((int) id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
        } else if (id == R.id.action_open){
            openFile("*/*");
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
                        final Uri uri = data.getData();
                        this.controller.loadModel(uri);
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public CPEEDocument getDocument() {
        return document;
    }

    @Override
    public void updateCounties() {
        if(this.document != null){
            countySpinnerAdapter.clear();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                countySpinnerAdapter.addAll(this.document.Counties);
            } else {
                for(County item: this.document.Counties) {
                    countySpinnerAdapter.add(item);
                }
            }
        }
    }

    @Override
    public void showMessageShort(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showMessageLong(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateTotal(Total total) {
        if(total != null) {
            Double t = total.getTotal();
            if(t != null) {
                groupList.clear();
                groupList.add(t);
                totalComponents.put(t, total.getTotalComponents());
                expListAdapter.notifyDataSetChanged();
            }
        }
        else {
            groupList.clear();
            totalComponents.clear();
            expListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void updateDistrib(String name) {
        textDistrib.setText(name);
    }

    @Override
    public void setDocument(CPEEDocument document) {
        this.document = document;
    }

    public void openFile(String mimeType) {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(mimeType);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // special intent for Samsung file manager
        Intent sIntent = new Intent("com.sec.android.app.myfiles.PICK_DATA");
        // if you want any file type, you can skip next line
        sIntent.putExtra("CONTENT_TYPE", mimeType);
        sIntent.addCategory(Intent.CATEGORY_DEFAULT);

        Intent chooserIntent;
        if (getPackageManager().resolveActivity(sIntent, 0) != null){
            // it is device with samsung file manager
            chooserIntent = Intent.createChooser(sIntent, "Open file");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { intent});
        }
        else {
            chooserIntent = Intent.createChooser(intent, "Open file");
        }

        try {
            startActivityForResult(chooserIntent, FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(), "No suitable File Manager was found.", Toast.LENGTH_SHORT).show();
        }
    }
}
