package solomonkey.informatics;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class Fragment_Home extends Fragment{
    // TODO: Rename parameter arguments, choose names that match

    int images[];
    CarouselView carouselView;
    Context context;
    ImageListener imageListener;
    TextView txtcourse_learnMore,txtViewLargeMap;

    @Override
    public void onAttach(Context context) {
        this.context=context;
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Home");
        MainActivity.homeIsShown=true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MainActivity.homeIsShown=false;
    }

    public Fragment_Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        carouselView =  getActivity().findViewById(R.id.carouselView);
        imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(images[position]);
            }
        };
        prepareCarousel();

        txtcourse_learnMore = getActivity().findViewById(R.id.course_learnmore);
        txtcourse_learnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.navigationView.setCheckedItem(R.id.nav_courses);
                MainActivity.changeBackstack(false,new Fragment_CoursesCategory(),"Courses");
            }
        });

        txtViewLargeMap = getActivity().findViewById(R.id.txtViewLargeMap);
        txtViewLargeMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.homeIsShown=false;
                MainActivity.changeBackstack(true,new Fragment_map(),"Map");
            }
        });

        //prepare map
        //ung mga locations dun mo edit sa "Fragment_map"
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.map_container,new Fragment_map());
        fragmentTransaction.commit();


    }

    protected void prepareCarousel(){
        //dito ka nlng magchange or mag-add ng images from net/resources
        images = new int[]{R.drawable.carousel_a, R.drawable.carousel_bbb, R.drawable.carousel_c};

        //mahalaga na last tong dalawa saka ung pagkakasunod nitong dalawa kasi mag-eerror kapag nauna ung setpagecount
        carouselView.setImageListener(imageListener);
        carouselView.setPageCount(images.length);

    }




}
