package codingwithmitch.com.forsale;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class newpostfrag extends Fragment {
    private static  final String tag ="newpostfragment";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View View =inflater.inflate(R.layout.newpost_fragment,container,false);
        return View;
    }
}
