package codingwithmitch.com.forsale;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;
import com.squareup.picasso.Picasso;

import codingwithmitch.com.forsale.R;
import codingwithmitch.com.forsale.account.LoginActivity;
import codingwithmitch.com.forsale.models.User;

public class accountfrag extends Fragment {
    private static  final String tag ="account fragment";
    ImageView image_profile;
    //the firebase
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    //widgets
    private Button signout , editProfile;
    private TextView email,phone,address;
    FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View View =inflater.inflate(R.layout.accountfrag,container,false);
        setupFirebaseListener();
        signout =(Button)View.findViewById(R.id.sign_out);
        editProfile =(Button)View.findViewById(R.id.edit);
        email = View.findViewById(R.id.email);
        phone = View.findViewById(R.id.phone);
        address = View.findViewById(R.id.address);
        image_profile = View.findViewById(R.id.profileimage);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                startActivity(new Intent(getContext(), EditProfileActivity.class));
            }
        });
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Log.d(tag,"onClick:attempting to sign out");
FirebaseAuth.getInstance().signOut();
            }
        }) ;

        userInfo();

        return View;


    }


    private void userInfo(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (getContext() == null){
                    return;
                }
                User user = dataSnapshot.getValue(User.class);

                if (user != null) {
                    Picasso.get().load(user.getImageurl()).into(image_profile);
                    email.setText(user.getEmail());
                    phone.setText(user.getPhone());
                    address.setText(user.getAddress());
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private  void setupFirebaseListener(){
        Log.d(tag, "setupFirebaseListener: setting up ");
        mAuthStateListener =new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user =firebaseAuth.getCurrentUser();
                if (user!=null){
                    Log.d(tag, "onAuthStateChanged: signed in:"+user.getUid())
                    ;
                }else {
                    Log.d(tag, "onAuthStateChanged:sigend out ");
                    Toast.makeText(getActivity(),"signed-out",Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(getActivity(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);


                }

            }
        };

    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthStateListener!=null){
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateListener);
        }
    }
}
