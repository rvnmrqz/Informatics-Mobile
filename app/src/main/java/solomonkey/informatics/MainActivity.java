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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    static Context staticContext;
    static ActionBar staticActionbar;

    public static boolean homeIsShown;
    public static NavigationView navigationView;
    static FragmentManager fragmentManager;
    static FragmentTransaction fragmentTransaction;
    Menu nav_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        staticContext = MainActivity.this;
        staticActionbar = getSupportActionBar();

        fragmentManager = getSupportFragmentManager();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //to display first the home fragment
        navigationView.setCheckedItem(R.id.nav_home);
        changeBackstack(false,new Fragment_Home(),"Home");

        //check muna kung merong nakalogin na account
        nav_menu = navigationView.getMenu();
        if(TemporaryHolder.tempSomeonelogged){
            nav_menu.findItem(R.id.nav_signout).setVisible(true);
        }else{
            nav_menu.findItem(R.id.nav_signout).setVisible(false);
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
            changeBackstack(false,new Fragment_Uniform(),"Uniforms");
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

                        //hiding sign-out menu item
                        nav_menu = navigationView.getMenu();
                        nav_menu.findItem(R.id.nav_signout).setVisible(false);

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
}
