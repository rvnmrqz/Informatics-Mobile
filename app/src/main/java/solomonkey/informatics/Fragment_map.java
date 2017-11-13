package solomonkey.informatics;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class Fragment_map extends Fragment implements OnMapReadyCallback{

    Context context;
    public static GoogleMap mGooglemap;
    MapView mMapView;

    private LatLng manilaView = new LatLng(14.582784, 121.030009);
    View myview;

    public Fragment_map() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myview = inflater.inflate(R.layout.fragment_map, container, false);
        return myview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView =  myview.findViewById(R.id.map);
        if(mMapView !=null){
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(context);
        mGooglemap = googleMap;
        mGooglemap.getUiSettings().setMapToolbarEnabled(false);
        mGooglemap.animateCamera(CameraUpdateFactory.newLatLngZoom(manilaView,10f));
        prepareLocations();
    }

    protected void prepareLocations(){
        ArrayList<LatLng> latlng_List = new ArrayList<>();
        ArrayList<String> address_List = new ArrayList<>();

        //pwede dito isingit ung code ng pag-populate ng locations, loop na lang

        latlng_List.add(new LatLng(14.558682, 121.018769));
        address_List.add("2nd Floor, Vicente Madrigal Building, 6793, Ayala Avenue, Makati, 1226 Metro Manila");

        latlng_List.add(new LatLng(14.656735, 121.027510));
        address_List.add("4th Floor, Cyberzone, The Annex, SM North, EDSA, corner North Ave, Bago Bantay, Quezon City, 1100 Metro Manila");

        latlng_List.add(new LatLng(14.653117, 120.983710));
        address_List.add("380 Rizal Avenue,, Grace Park West, Caloocan, Metro Manila");

        //adding to map
        for (int x = 0; x<latlng_List.size();x++){
            addmarker(latlng_List.get(x),address_List.get(x));
        }
    }
    protected void addmarker(LatLng coor, String title){
        mGooglemap.addMarker(new MarkerOptions().position(coor).title("Informatics College, Inc.").snippet(title));
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!MainActivity.homeIsShown){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Locations");
        }
    }
}
