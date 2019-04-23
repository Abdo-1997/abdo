package codingwithmitch.com.forsale;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.net.Uri;
import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import codingwithmitch.com.forsale.util.SectionsPagerAdapter;


public class SearchActivity extends AppCompatActivity {
 private static  final String TAG = "SearchActivity";
 //widgets
    private TabLayout mTablayOut;
    public ViewPager mViewPager;
    public static final int REQCODE=14;
    //variables
    public SectionsPagerAdapter mPagerAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
      mTablayOut=(TabLayout)findViewById(R.id.tabs);
      mViewPager=(ViewPager)findViewById(R.id.viewPager_container);
        permissions();
    }
    private void SetupViewPager(){
        mPagerAdapter=new SectionsPagerAdapter(getSupportFragmentManager());
        mPagerAdapter.addFragment(new searchfrag());

        mPagerAdapter.addFragment(new postfrag());
        mPagerAdapter.addFragment(new watchListfrag());
        mPagerAdapter.addFragment(new accountfrag());

        mViewPager.setAdapter(mPagerAdapter);

        mTablayOut.setupWithViewPager(mViewPager);
        mTablayOut.getTabAt(0).setText(getString(R.string.searchfrag));
        mTablayOut.getTabAt(1).setText(getString(R.string.postfrag));

        mTablayOut.getTabAt(2).setText(getString(R.string.watchfrag));

        mTablayOut.getTabAt(3).setText(getString(R.string.account));


    }
 private void permissions(){
     Log.d(TAG, "permissions: ask user for permission");
     String[]permissions ={android.Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,
             Manifest.permission.CAMERA};
     if (ContextCompat.checkSelfPermission(this.getApplicationContext(),permissions[0])== PackageManager.PERMISSION_GRANTED
             &&ContextCompat.checkSelfPermission(this.getApplicationContext(),permissions[1])== PackageManager.PERMISSION_GRANTED
         &&ContextCompat.checkSelfPermission(this.getApplicationContext(),permissions[2])== PackageManager.PERMISSION_GRANTED){
         SetupViewPager();
     }else {
         ActivityCompat.requestPermissions(SearchActivity.this,permissions,REQCODE);
     }
     }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissions();
    }
}

