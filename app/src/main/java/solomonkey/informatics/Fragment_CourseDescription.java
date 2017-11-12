package solomonkey.informatics;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

public class Fragment_CourseDescription extends Fragment {

    Context context;
    ScrollView layout_result;
    LinearLayout layout_loading;
    ProgressBar loading_progressbar;
    TextView loading_textview;
    Button loading_button;

    public Fragment_CourseDescription() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course_description, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layout_result = getActivity().findViewById(R.id.layout_result);
        layout_loading = getActivity().findViewById(R.id.layout_loading);
        loading_progressbar = getActivity().findViewById(R.id.loading_progressbar);
        loading_textview = getActivity().findViewById(R.id.loading_textview);
        loading_button = getActivity().findViewById(R.id.loading_button);
        loading_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prepareCourseDescription();
            }
        });
        prepareCourseDescription();
    }

    protected void prepareCourseDescription(){
        //to show loading screen
        showMessageLayout(true,true,true,"Loading",false,null);

        //populate data here depending on the primary key from the last fragment

        //SELECT * FROM PROGRAMS WHERE PROGRAMID = TemporaryHolder.primarykeyholder;

        //pwede mo din gamitin ung ginawa kong method na transition ng screen kapag error ung pagGet mo ng data from database
        // showMessageLayout(true,false,true,"Error encountered",true,"Retry");

        TextView progName = getActivity().findViewById(R.id.txtProgramName);
        TextView progDesc = getActivity().findViewById(R.id.txtProgramDescription);

        progName.setText("Bachelor of Science in Information Technology");
        progDesc.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum felis vel placerat auctor. Phasellus vel magna a est ullamcorper vehicula. Maecenas suscipit, tortor vitae ultrices imperdiet, libero sem molestie neque, id varius ex sem sit amet sapien. Etiam sed tortor dictum, vehicula est et, eleifend sapien. Sed ultricies ultrices varius. Nullam porta lectus vitae pellentesque mattis. Maecenas facilisis lacus nulla, a rutrum ante viverra eu. Curabitur eget pharetra eros, nec malesuada dolor.\n" +
                "Nulla mattis ex non massa posuere commodo. Nulla mattis magna vitae tincidunt vestibulum. Suspendisse potenti. Nullam vel diam lacus. Nunc lorem elit, iaculis quis facilisis a, rhoncus sed dui. Sed sed accumsan dui. Duis dictum lobortis diam, at ultrices felis rhoncus id. Morbi elementum aliquam finibus.\n" +
                "Nulla facilisi. Vivamus interdum posuere placerat. Nullam tortor tellus, semper eget viverra eu, sodales vel eros. Curabitur elementum neque orci, quis fermentum libero aliquet vitae. In eget condimentum dolor. Nam at mollis arcu. Donec vel mi quis nulla maximus vulputate ut non nunc.\n" +
                "Duis molestie lorem id diam pulvinar consectetur. Cras sit amet nisl suscipit, imperdiet purus vitae, hendrerit nisl. Maecenas pellentesque condimentum consequat. Mauris maximus tempus convallis. Maecenas ut metus tincidunt, faucibus ipsum nec, auctor tortor. Vivamus et dui nec sem lobortis maximus et sit amet justo. Donec euismod consequat lacus at semper. Sed tincidunt quis orci quis placerat. Proin ac elit odio. Quisque a tortor massa. Vivamus consequat, sapien in imperdiet aliquam, nisl neque porttitor ante, eu ullamcorper nunc tellus non massa.\n" +
                "Etiam sagittis nisi at tellus facilisis, ac molestie leo ornare. Suspendisse molestie faucibus ligula, eget lacinia nisl vestibulum ac. Sed id libero in enim condimentum faucibus accumsan ac felis. Aliquam elementum libero in arcu lacinia, et rutrum risus iaculis. Suspendisse condimentum, nisi volutpat facilisis molestie, diam dui luctus diam, sed ultricies neque ex quis diam. Phasellus sit amet ligula vel orci varius posuere nec et lectus. Morbi ultrices orci id nibh dapibus, ac blandit dui aliquam. Maecenas eros quam, sagittis quis justo vitae, suscipit ultrices lacus. Cras feugiat nunc eget tincidunt ornare. Aliquam venenatis dignissim nunc at vehicula. Vivamus dapibus sapien sit amet mauris porta pulvinar in congue felis. Donec fermentum finibus ante sed dapibus. Sed lacinia eleifend nisl eu auctor. Fusce efficitur arcu purus, vel venenatis velit cursus ut. Cras augue ipsum, vehicula in porttitor et, euismod et orci.");

        showMessageLayout(false,false,false,null,false,null);
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


}