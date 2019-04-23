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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.core.Tag;

import codingwithmitch.com.forsale.R;
import codingwithmitch.com.forsale.account.LoginActivity;

public class accountfrag extends Fragment {
    private static  final String tag ="account fragment";
    //the firebase
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    //widgets
    private Button signout;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View View =inflater.inflate(R.layout.accountfrag,container,false);
        setupFirebaseListener();
        signout =(Button)View.findViewById(R.id.sign_out);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Log.d(tag,"onClick:attempting to sign out");
FirebaseAuth.getInstance().signOut();
            }
        }) ;

        return View;


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
