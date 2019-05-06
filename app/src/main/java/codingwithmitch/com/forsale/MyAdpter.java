package codingwithmitch.com.forsale;

import android.content.Context;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import codingwithmitch.com.forsale.models.Post;
import codingwithmitch.com.forsale.util.UniversalImageLoader;

public class MyAdpter extends RecyclerView.Adapter<MyAdpter.MyViewHolder> {

    String url;
    Context context;
    ArrayList<Post> mPosts;

    public MyAdpter(Context c , ArrayList<Post> p)
    {
        context = c;
        mPosts = p;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(context).inflate(R.layout.cardview,parent,false);
        MyViewHolder viewHolder =new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        holder.name.setText(mPosts.get(position).getTitle());
        holder.email.setText(mPosts.get(position).getPrice());
          url = mPosts.get(position).getImage();

        // UniversalImageLoader.setImage(mPosts.get(position).getImage(), holder.image);
        Picasso.get()
                .load(url)
                //.resize(170, 150)
               // .centerCrop()
                .into(holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,email;
        ImageView image;
        Button btn;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            email = (TextView) itemView.findViewById(R.id.email);
            image = (ImageView) itemView.findViewById(R.id.pic);
            //  btn = (Button) itemView.findViewById(R.id.checkDetails);
        }
        public void onClick(final int position)
        {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, position+" is clicked", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}