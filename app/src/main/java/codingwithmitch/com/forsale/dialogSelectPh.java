package codingwithmitch.com.forsale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.security.PrivateKey;

public class dialogSelectPh extends DialogFragment {
    public static final String TAG ="dialogSelectPh";
    private static final int pic_file_re_co=111;
    private static final int pic_im_co=101;
    public interface OnPhotoSeletedListener{
        void getImagePath(Uri imagePath);
        void getimagebitmab(Bitmap bitmap);


    }
    OnPhotoSeletedListener mOnPhotoSeletedListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //return super.onCreateView(inflater, container, savedInstanceState);
        View view= inflater.inflate(R.layout.select_photo_dialog,container,false);
        TextView selectphot =(TextView)view.findViewById(R.id.dialogChoosePhoto);
        selectphot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: accessing memory");
               Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
               intent.setType("image/*");
               startActivityForResult(intent,pic_file_re_co);


            }
        });
        TextView tackephot =(TextView)view.findViewById(R.id.dialogOpenCamera);
        tackephot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "camera");
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intent,pic_im_co);


            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==pic_file_re_co&&resultCode== Activity.RESULT_OK){
            Uri SelectedImageUri =data.getData();
            Log.d(TAG, "onActivityResult:image uri "+SelectedImageUri);

            mOnPhotoSeletedListener.getImagePath(SelectedImageUri);
            getDialog().dismiss();
        }
        else if (requestCode==pic_im_co&&resultCode== Activity.RESULT_OK){
            Log.d(TAG, "onActivityResult: done talking new photo");
            Bitmap bitmap;
            bitmap=(Bitmap)data.getExtras().get("data");
            mOnPhotoSeletedListener.getimagebitmab(bitmap);
            getDialog().dismiss();

            }

    }

    @Override
    public void onAttach(Context context) {
        try {
            mOnPhotoSeletedListener = (OnPhotoSeletedListener)getTargetFragment();
        }catch (ClassCastException e){
            Log.e(TAG, "onAttach:ClassCastException "+e.getMessage() );
        }
        super.onAttach(context);
    }
}
