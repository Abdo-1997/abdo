package codingwithmitch.com.forsale;

import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class fillterActivity extends AppCompatActivity {
    public static String TAG="fillterActivity";
    //widgets
    private Button msave;

    private EditText mCity,mStateprovince,mCountry;
    private ImageView mBackarrow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fillter);
        msave =(Button)findViewById(R.id.btnSave);
        mCity=(EditText)findViewById(R.id.input_city);
        mStateprovince=(EditText)findViewById(R.id.input_state_province);
        mCountry=(EditText)findViewById(R.id.input_country);
        mBackarrow=(ImageView)findViewById(R.id.backArrow);
               init();
    }
    private void init(){
        getFilterPreferances();
        msave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: saving.............");
                SharedPreferences preferences =PreferenceManager.getDefaultSharedPreferences(fillterActivity.this);
                SharedPreferences.Editor editor=preferences.edit();
                Log.d(TAG, "onClick: city"+mCity.getText().toString());
                editor.putString(getString(R.string.preference_city),mCity.getText().toString());
                editor.commit();
                Log.d(TAG, "onClick: city"+mStateprovince.getText().toString());
                editor.putString(getString(R.string.preference_state_province),mStateprovince.getText().toString());
                editor.commit();
                Log.d(TAG, "onClick: city"+mCountry.getText().toString());
                editor.putString(getString(R.string.preference_country),mCountry.getText().toString());
                editor.commit();
            }
        });
        mBackarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navBack");
                finish();
            }
        });
    }
    private void getFilterPreferances(){
        Log.d(TAG, "getFilterPreferances: retariving saved perfrances");
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
           String country =preferences.getString(getString(R.string.preference_country),"");
           String state_province =preferences.getString(getString(R.string.preference_state_province),"");
           String city =preferences.getString(getString(R.string.preference_city),"");
           mCountry.setText(country);
           mCity.setText(city);
           mStateprovince.setText(state_province);
    }
}
