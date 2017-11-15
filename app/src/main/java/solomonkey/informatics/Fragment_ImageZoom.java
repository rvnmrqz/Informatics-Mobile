package solomonkey.informatics;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_ImageZoom extends Fragment {

    Context context;
    TouchImageView touchImageView;
    TextView zoom_txtClose;

    public Fragment_ImageZoom() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_zoom, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        touchImageView = getActivity().findViewById(R.id.touchimageview);
        zoom_txtClose = getActivity().findViewById(R.id.zoom_txtClose);


        if(TemporaryHolder.imageBitmap!=null){
            touchImageView.setImageBitmap(TemporaryHolder.imageBitmap);
        }else{
            Toast.makeText(context, "Failed to Display Image", Toast.LENGTH_SHORT).show();
        }

        zoom_txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

    }
}
