package solomonkey.informatics;


import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Uniform extends Fragment {


    Context context;
    ImageView img_male,img_female;

    @Override
    public void onAttach(Context context) {
        this.context=context;
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Proper Uniform");
    }

    public Fragment_Uniform() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_uniform, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        img_male = getActivity().findViewById(R.id.imgview_male_uniform);
        img_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(img_male.getDrawable()!=null){
                TemporaryHolder.imageBitmap = ((BitmapDrawable)img_male.getDrawable()).getBitmap();;
                MainActivity.changeBackstack(true,new Fragment_ImageZoom(),"Zoom");
                }else{
                    Toast.makeText(context, "Image cannot be zoomed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        img_female = getActivity().findViewById(R.id.imgview_female_uniform);
        img_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(img_female.getDrawable()!=null){
                    TemporaryHolder.imageBitmap = ((BitmapDrawable)img_female.getDrawable()).getBitmap();;
                    MainActivity.changeBackstack(true,new Fragment_ImageZoom(),"Zoom");
                }else{
                    Toast.makeText(context, "Image cannot be zoomed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
