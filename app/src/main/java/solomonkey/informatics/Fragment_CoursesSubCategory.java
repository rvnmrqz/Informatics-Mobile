package solomonkey.informatics;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class Fragment_CoursesSubCategory extends Fragment {

    Context context;
    ArrayList<String> category_list;
    ArrayList<String> categoryPrimaryKey_list;
    ListView listView;
    CustomAdapter customAdapter;

    LinearLayout layout_result, layout_loading;
    ProgressBar loading_progressbar;
    TextView loading_textview;
    Button loading_button;

    @Override
    public void onAttach(Context context) {
        this.context=context;
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(TemporaryHolder.uniformMode) ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Proper Uniform");
        else  ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Courses/Programs");
    }

    public Fragment_CoursesSubCategory() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_courses_sub_category, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = getActivity().findViewById(R.id.result_listview);
        layout_result = getActivity().findViewById(R.id.layout_result);
        layout_loading = getActivity().findViewById(R.id.layout_loading);
        loading_progressbar = getActivity().findViewById(R.id.loading_progressbar);
        loading_textview = getActivity().findViewById(R.id.loading_textview);
        loading_button = getActivity().findViewById(R.id.loading_button);
        loading_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prepareSubCategories();
            }
        });
        prepareSubCategories();
    }

    protected void prepareSubCategories(){
        category_list = new ArrayList<>();
        categoryPrimaryKey_list = new ArrayList<>();
        //to show loading screen
        showMessageLayout(true,true,true,"Loading",false,null);

        //populate arraylist here depending on the primary key from the last fragment

        //SELECT * FROM TBLSUBCATEGORY WHERE CATEGORYID = TemporaryHolder.primarykeyholder;

        //pwede mo din gamitin ung ginawa kong method na transition ng screen kapag error ung pagGet mo ng data from database
        // showMessageLayout(true,false,true,"Error encountered",true,"Retry");


        //nagloop lang ako dito para instant may laman ung arraylist, baguhin mo nlng kapag maglagay ka na from database
        for (int x=1;x<=10;x++){
            // naglagay lang ako ng "" para instant String na sya haha
            categoryPrimaryKey_list.add(x+"");
            category_list.add("Course/Program "+x);

        }

        //adapter
        customAdapter = new CustomAdapter(context,category_list,categoryPrimaryKey_list);
        listView.setAdapter(customAdapter);

        if(category_list.size()>0){
            Log.wtf("list","category list is not 0");
            showMessageLayout(false,false,false,null,false,null);

        }else{
            Log.wtf("list","category list is 0");
            showMessageLayout(true,false,true,"No results",true,"Refresh");
        }

    }

    //Screen Transitions
    protected void showMessageLayout(boolean showMessagelayout,boolean showProgressbar,boolean showTextView, String txtViewMessage, boolean showButton, String buttonMessage){
        if(showMessagelayout){
            Log.wtf("showMessageLayout","SHOW MESSAGE LAYOUT");
            layout_result.setVisibility(View.GONE);
            layout_loading.setVisibility(View.VISIBLE);

            if(showProgressbar) loading_progressbar.setVisibility(View.VISIBLE);
            else loading_progressbar.setVisibility(View.GONE);

            if(showTextView){
                loading_textview.setVisibility(View.VISIBLE);
                loading_textview.setText(txtViewMessage);
            }else loading_textview.setVisibility(View.GONE);

            if(showButton){
                loading_button.setVisibility(View.VISIBLE);
                loading_button.setText(buttonMessage);
            }else loading_button.setVisibility(View.GONE);

        }else{
            //show result layout
            Log.wtf("showMessageLayout","SHOW RESULT LAYOUT");
            layout_result.setVisibility(View.VISIBLE);
            layout_loading.setVisibility(View.GONE);
        }
    }

    //custom adapter for listview
    //basahin mo ung code ko dun sa ONCLICK NG ROW

    class CustomAdapter extends ArrayAdapter {
        ArrayList<String> categories= new ArrayList<>();
        ArrayList<String> primarykeys= new ArrayList<>();

        public CustomAdapter(Context context, ArrayList<String> categories, ArrayList<String> primarykeys) {
            //Overriding Default Constructor off ArrayAdapter
            super(context, R.layout.row_template,R.id.row_text,categories);
            this.categories=categories;
            this.primarykeys=primarykeys;

        }
        @NonNull
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            //Inflating the layout
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.row_template,parent,false);
            //Get the reference to the view objects
            final TextView textView  = row.findViewById(R.id.row_text);
            textView.setText(categories.get(position));

            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //pasa muna sa temporary holder ung primary key
                    TemporaryHolder.primarykeyholder = primarykeys.get(position);

                    if(TemporaryHolder.uniformMode){
                        //open uniform fragment based sa primary key
                        MainActivity.changeBackstack(true,new Fragment_Uniform(),"Fragment_Uniform");
                    }else{
                        //open the description fragment of the program/course
                        MainActivity.changeBackstack(true,new Fragment_CourseDescription(),"Fragment_CourseDescription");
                    }
                }
            });

            return row;
        }
    }

}
