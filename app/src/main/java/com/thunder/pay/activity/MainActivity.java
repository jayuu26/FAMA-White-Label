package com.thunder.pay.activity;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.thunder.pay.R;
import com.thunder.pay.adapter.drawerlistadapter.ExpandableListAdapter;
import com.thunder.pay.constant.CollectionObject;
import com.thunder.pay.constant.MessageConstant;
import com.thunder.pay.customlayout.SlidingTabLayout;
import com.thunder.pay.daomodel.DataHandler;
import com.thunder.pay.fragments.account.AccountsFragment;
import com.thunder.pay.fragments.common.AboutFragment;
import com.thunder.pay.fragments.common.CommonAccountsFragment;
import com.thunder.pay.fragments.bills.BillsFragment;
import com.thunder.pay.fragments.home.HomeFragment;
import com.thunder.pay.fragments.moneytransfer.MoneyTransferFragment;
import com.thunder.pay.fragments.moneytransfer.banktransfer.BankTransferFragment;
import com.thunder.pay.fragments.transactionhistory.TransactionHistoryFragment;
import com.thunder.pay.greendaodb.Inventory;
import com.thunder.pay.util.AppUtills;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,ExpandableListAdapter.OnSelectedListner {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    private ExpandableListView expandableList;
    private HashMap<String, List<String>> listDataChild;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private TypedArray iconListDataHeader;

    public static SearchView searchView;
    public static MenuItem searchMenuItem;
    public static ActionBar actionBar;
    Context mcontext;
    CircleImageView userProfileImage;
    DrawerLayout drawer;
    NavigationView navigationView;
    Inventory inventory;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);
        mcontext = this;
        inventory = DataHandler.Single.INSTANCE.getInstance().getInventory();
        initActionBar();
        AppUtills.initCountyList(AppUtills.loadJsonFromAssets(this, "isocountry"));
        AppUtills.loadFragment(HomeFragment.Single.INSTANCE.getInstance(), this, R.id.container);
    }

    public int GetPixelFromDips(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scaleexplvList
        return (int) (pixels * scale + 0.5f);
    }
    private void setArrow(){

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            expandableList.setIndicatorBounds(width-GetPixelFromDips(35), width-GetPixelFromDips(5));
        } else {
            expandableList.setIndicatorBoundsRelative(width-GetPixelFromDips(35), width-GetPixelFromDips(5));
        }
    }

    private void initDrawerist(){
        expandableList = (ExpandableListView) findViewById(R.id.navigationmenu);
        setArrow();
        prepareNavigationList();
        listAdapter = new ExpandableListAdapter(this, listDataHeader,iconListDataHeader, listDataChild, expandableList,this);
        expandableList.setAdapter(listAdapter);
//

        expandableList.setChildDivider(getResources().getDrawable(R.color.text_color_white));
        expandableList.setDivider(getResources().getDrawable(R.color.text_color_white));
        expandableList.setSelectedGroup(0);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.getMenu().getItem(0).setChecked(true);

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }


        View headerLayout = navigationView.getHeaderView(0); // 0-index header

        TextView tvuserName = (TextView) headerLayout.findViewById(R.id.tvuserName);
        TextView tvUserEmail = (TextView) headerLayout.findViewById(R.id.tvUserEmail);
        if (tvuserName != null) {
            tvuserName.setText(""+inventory.getFirstname()+" "+inventory.getLastname());
        }
        if (tvUserEmail != null) {
            tvUserEmail.setText(""+inventory.getEmail());
        }
        userProfileImage = (CircleImageView) headerLayout.findViewById(R.id.userProfileImage);
        if (userProfileImage != null)
            userProfileImage.setImageResource(R.drawable.avatar);
    }

    private void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.action_toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true); //Set this to true if selecting "home" returns up by a single level in your UI rather than back to the top level or front page.
//        actionBar.setHomeAsUpIndicator(R.drawable.Your_Icon);
        AppUtills.setActionBarTitle("Home",actionBar,this,false);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.super.onBackPressed();
            }
        });


        initDrawerist();
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        drawer.closeDrawers();
                        return true;
                    }
                });
    }

    private void prepareNavigationList() {
        listDataHeader = Arrays.asList(getResources().getStringArray(R.array.nav_items));
         iconListDataHeader = getResources().obtainTypedArray(R.array.nav_icon);
//        iconListDataHeader =   getResources().obtainTypedArray(R.array.nav_icon);
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        List<String> account= new ArrayList<String>();
        account.add("View Account");
        account.add("Manage Beneficiary");

        List<String> transfer= new ArrayList<String>();
        transfer.add("Bank Transfer");
        transfer.add("Wallet Transfer");

        List<String> transaction= new ArrayList<String>();
        transaction.add("Bank Transaction");
        transaction.add("Wallet Transaction");

        listDataChild.put(listDataHeader.get(3), account);// Header, Child data
        listDataChild.put(listDataHeader.get(4), transfer);
        listDataChild.put(listDataHeader.get(5), transaction);
    }
    private void initSearch(Menu menu) {
        searchMenuItem = menu.findItem(R.id.action_search);

        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setBackgroundResource(0);

        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(navigationView)){
            drawer.closeDrawer(navigationView);
        }else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.option_menu, menu);
//        initSearch(menu);

        MenuItemCompat.setOnActionExpandListener(menu.findItem(R.id.action_search), new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {

                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {

                //DO SOMETHING WHEN THE SEARCHVIEW IS CLOSING

                return true;
            }
        });

        return true;
    }

    @Override
    public boolean  onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    super.onBackPressed();
                    return true;
            }
        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
          navigationItemClick(item.getItemId());
        return true;
    }

    private void navigationItemClick(int itemID){
        int id = itemID;
        if (id == R.id.nav_about) {
            AppUtills.loadFragment(AboutFragment.Single.INSTANCE.getInstance(), this, R.id.container);
        } else if (id == R.id.nav_home) {
            AppUtills.loadFragment(HomeFragment.Single.INSTANCE.getInstance(), this, R.id.container);
        } else if (id == R.id.nav_bills_payment) {
            AppUtills.loadFragment(BillsFragment.Single.INSTANCE.getInstance(), this, R.id.container);
        } else if (id == R.id.nav_money_transfer) {
            AppUtills.loadFragment(BankTransferFragment.Single.INSTANCE.getInstance(), this, R.id.container);
        } else if (id == R.id.nav_accounts) {
            AppUtills.loadFragment(CommonAccountsFragment.Single.INSTANCE.getInstance(), this, R.id.container);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onParentItemClicked(int groupPosition, int childPosition, boolean isExpand) {
         if(!isExpand){
             if(groupPosition == MessageConstant.About){
                 AppUtills.loadFragment(AboutFragment.Single.INSTANCE.getInstance(), this, R.id.container);
                 DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                 drawer.closeDrawer(GravityCompat.START);
             }else if(groupPosition == MessageConstant.Home){
                 AppUtills.loadFragment(HomeFragment.Single.INSTANCE.getInstance(), this, R.id.container);
                 DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                 drawer.closeDrawer(GravityCompat.START);
             }else if(groupPosition == MessageConstant.Bills_Payment){
                 AppUtills.loadFragment(BillsFragment.Single.INSTANCE.getInstance(), this, R.id.container);
                 DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                 drawer.closeDrawer(GravityCompat.START);
             }
         }
    }

    @Override
    public void onChildItemClicked(int groupPosition, int childPosition) {

        if (groupPosition == MessageConstant.Money_Transfer) {
            if (childPosition == MessageConstant.SUB_Money_Transfer_1) {
                AppUtills.loadFragment(MoneyTransferFragment.Single.INSTANCE.getInstance(), this, R.id.container);
            } else if (childPosition == MessageConstant.SUB_Money_Transfer_2) {
                AppUtills.loadFragment(MoneyTransferFragment.Single.INSTANCE.getInstance(), this, R.id.container);
            }

        } else if (groupPosition == MessageConstant.Accounts) {
            if (childPosition == MessageConstant.SUB_Accounts_1) {
                AppUtills.loadFragment(AccountsFragment.Single.INSTANCE.getInstance(), this, R.id.container);
            } else if (childPosition == MessageConstant.SUB_Accounts_2) {
                AppUtills.loadFragment(AccountsFragment.Single.INSTANCE.getInstance(), this, R.id.container);
            }

        } else if (groupPosition == MessageConstant.Transaction_Histroy) {
            if (childPosition == MessageConstant.SUB_Accounts_1) {
                AppUtills.loadFragment(TransactionHistoryFragment.Single.INSTANCE.getInstance(), this, R.id.container);
            } else if (childPosition == MessageConstant.SUB_Accounts_2) {
                AppUtills.loadFragment(TransactionHistoryFragment.Single.INSTANCE.getInstance(), this, R.id.container);
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
}
