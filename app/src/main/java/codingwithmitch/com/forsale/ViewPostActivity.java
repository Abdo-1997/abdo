package codingwithmitch.com.forsale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import codingwithmitch.com.forsale.models.Post;
import codingwithmitch.com.forsale.util.UniversalImageLoader;

public class ViewPostActivity extends AppCompatActivity {

    //widgets
    private TextView mContactSeller, mTitle, mDescription, mPrice, mLocation, mSavePost;
    private ImageView mClose, mWatchList, mPostImage;

    //vars
    private String mPostId;
    private Post mPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);


        Intent intent = getIntent();
        mPostId = intent.getStringExtra("postId");

        mContactSeller = (TextView) findViewById(R.id.post_contact);
        mTitle = (TextView) findViewById(R.id.post_title);
        mDescription = (TextView) findViewById(R.id.post_description);
        mPrice = (TextView) findViewById(R.id.post_price);
        mLocation = (TextView) findViewById(R.id.post_location);
        mClose = (ImageView) findViewById(R.id.post_close);
        mWatchList = (ImageView) findViewById(R.id.add_watch_list);
        mPostImage = (ImageView) findViewById(R.id.post_image);
        mSavePost = (TextView) findViewById(R.id.save_post);

        init();

        mSavePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(ViewPostActivity.this, "Saved Is Click", Toast.LENGTH_SHORT).show();
                FirebaseDatabase.getInstance().getReference().child("Saves").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child(mPostId).setValue(true);
            }
        });



        //hideSoftKeyboard();

    }

    private void init(){
        getPostInfo();

        mContactSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent emailIntent = new Intent(Intent.ACTION_SEND);
//                emailIntent.setType("plain/text");
//                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {mPost.getContact_email()});
//                getApplication().startActivity(emailIntent);
            }
        });

        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d(TAG, "onClick: closing post.");
                finish();

            }
        });

        mSavePost.setShadowLayer(5, 0 , 0, Color.BLUE);
        mWatchList.setImageBitmap(createOutline(BitmapFactory.decodeResource(getResources(), R.drawable.ic_save_white)));
        mWatchList.setColorFilter(Color.BLUE);
        mClose.setImageBitmap(createOutline(BitmapFactory.decodeResource(getResources(), R.drawable.ic_x_white)));
        mClose.setColorFilter(Color.BLUE);
    }

    private void getPostInfo(){
        //Log.d(TAG, "getPostInfo: getting the post information.");

//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
//
//        Query query = reference.child(getString(R.string.node_posts))
//                .orderByKey()
//                .equalTo(mPostId);
//
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot != null){
//                    mPost = dataSnapshot.getValue(Post.class);
//                    //Log.d(TAG, "onDataChange: found the post: " + mPost.getTitle());
//
//                    if (mPost != null) {
//                        mTitle.setText(mPost.getTitle());
//                        mDescription.setText(mPost.getDescription());
//                    }
//
//
//                    String price = "FREE";
//                    if(mPost.getPrice() != null){
//                        price = "$" + mPost.getPrice();
//                    }
//                    mPrice.setText(price);
//                    String location = mPost.getCity() + ", " + mPost.getState_province() + ", " +
//                            mPost.getCountry();
//                    mLocation.setText(location);
//                    UniversalImageLoader.setImage(mPost.getImage(), mPostImage);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("post").child(mPostId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mPost = dataSnapshot.getValue(Post.class);
                //Log.d(TAG, "onDataChange: found the post: " + mPost.getTitle());

                if (mPost != null) {
                    mTitle.setText(mPost.getTitle());
                    mDescription.setText(mPost.getDescription());
                }


                String price = "FREE";
                if(mPost.getPrice() != null){
                    price = "$" + mPost.getPrice();
                }
                mPrice.setText(price);
                String location = mPost.getCity() + ", " + mPost.getState_province() + ", " +
                        mPost.getCountry();
                mLocation.setText(location);
                UniversalImageLoader.setImage(mPost.getImage(), mPostImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void hideSoftKeyboard(){
        final Activity activity = getParent();
        final InputMethodManager inputManager = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private Bitmap createOutline(Bitmap src){
        Paint p = new Paint();
        p.setMaskFilter(new BlurMaskFilter(2, BlurMaskFilter.Blur.OUTER));
        return src.extractAlpha(p, null);
    }

}
