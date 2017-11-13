package solomonkey.informatics;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    static ActionBar staticActionbar;

    public static boolean homeIsShown;
    public static NavigationView navigationView;
    static FragmentManager fragmentManager;
    static FragmentTransaction fragmentTransaction;
    public static Menu nav_menu;

    static TextView nav_name,nav_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        staticActionbar = getSupportActionBar();
        fragmentManager = getSupportFragmentManager();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        nav_name =  header.findViewById(R.id.nav_studentName);
        nav_email =  header.findViewById(R.id.nav_studentEmail);

        //to display first the home fragment
        navigationView.setCheckedItem(R.id.nav_home);
        changeBackstack(false,new Fragment_Home(),"Home");

        //check muna kung merong nakalogin na account, pwede to sa sharedpref nalang kunin ung value
        if(TemporaryHolder.tempSomeonelogged){
            someoneislogged(true);
        }else{
            someoneislogged(false);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Log.wtf("onBackpressed","Stack count: "+fragmentManager.getBackStackEntryCount()+"\nHomeIsShown:"+homeIsShown);
            if(fragmentManager.getBackStackEntryCount()==0 && homeIsShown){
                confirmExit();
            }
            else if(fragmentManager.getBackStackEntryCount()==0 && !homeIsShown){
                //wala sa home fragment ung user pero 0 ung stack means nasa ibang tab lang si user na walang additional stack, balik lang sa home
                navigationView.getMenu().getItem(0).setChecked(true);
                changeBackstack(false,new Fragment_Home(),"Home");
            }
            else{
                Log.wtf("onbackpressed","else");
                super.onBackPressed();
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            clearBackstack();
            changeBackstack(false, new Fragment_Home(), "Home");
        }
        else if (id == R.id.nav_courses) {
            clearBackstack();
            changeBackstack(false, new Fragment_CoursesCategory(), "Courses");
        }
        else if (id == R.id.nav_grades) {
            clearBackstack();
            //check muna kung may nakaSign-in na account kung wala show login
            //nasa temporary variable lang muna sa ngayon, sharedpref mo na lang or sqlite kapag actual coding na
            if(TemporaryHolder.tempSomeonelogged){
                changeBackstack(false, new Fragment_Grades(), "Grades");
            }else{
                MainActivity.changeBackstack(false,new Fragment_Login(),"Login");
            }
        }
        else if (id == R.id.nav_tuition) {
            clearBackstack();
            changeBackstack(false,new Fragment_Tuition(),"Tuition");
        }
        else if (id == R.id.nav_uniform) {
            clearBackstack();
            TemporaryHolder.uniformMode = true;
            changeBackstack(false,new Fragment_CoursesCategory(),"Fragment_CoursesCategory");
        }
        else if (id == R.id.nav_signout) {
            logout();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //BACKSTACK
    public static void changeBackstack(boolean addToBackStack, Fragment fragment, String name){
        try{
            fragmentTransaction = fragmentManager.beginTransaction();
            if(addToBackStack){
                //ma-add sa stacks ung fragment
                Log.wtf("changeBackstack","Adding to backstack");
                fragmentTransaction.replace(R.id.main_container,fragment);
                fragmentTransaction.addToBackStack(name);
            }else{
                //kapag dito, di ma-add sa stacks, papalitan nya mismo ung stack, mawawala na ung stack na luma
                Log.wtf("changeBackstack","Replacing backstack");
                fragmentTransaction.replace(R.id.main_container,fragment);
            }
            fragmentTransaction.commit();
            Log.wtf("changeBackstack","Backstack count: "+fragmentManager.getBackStackEntryCount());
        }catch (Exception ee){
            Log.wtf("addToBackStack","ERROR: "+ee.getMessage());
        }
    }
    protected static void clearBackstack(){
        TemporaryHolder.uniformMode = false;
        for(int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
            fragmentManager.popBackStack();
        }
    }


    protected void confirmExit(){
        Log.wtf("confirmExit","Method is called");
        new AlertDialog.Builder(this)
                .setMessage("You're about to exit the app, continue?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //do nothing
                    }
                })
                .show();
    }
    protected void logout(){
        //Clear saved user data, refresh sliding navigation header info: picture, name, email
        //Hide sign-out menu item from sliding navigation
        new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Sign-out from the app?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //do clearing of user data here

                        someoneislogged(false);

                        navigationView.setCheckedItem(R.id.nav_home);
                        changeBackstack(false,new Fragment_Home(),"Home");
                        Toast.makeText(MainActivity.this, "Signed-out", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //do nothing
                    }
                })
                .show();
    }

    public static void someoneislogged(boolean logged){

        nav_menu = navigationView.getMenu();

        if(logged){
            //show the user's name (and email) in the navigation
            //show sigg-out item in the navigation
            nav_menu.findItem(R.id.nav_signout).setVisible(true);

            //get data from sharedpref/sqlite
            //nav_name.setText("value");
           // nav_email.setText("value");

            nav_name.setVisibility(View.VISIBLE);
            nav_email.setVisibility(View.VISIBLE);
        }else{
            //hide all shown items for logged user
            nav_menu.findItem(R.id.nav_signout).setVisible(false);
            nav_name.setVisibility(View.GONE);
            nav_email.setVisibility(View.GONE);
        }
    }
}
