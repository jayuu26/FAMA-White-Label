//package com.thunder.pay.fragment.home;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.v4.app.Fragment;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.gson.Gson;
//import com.thunder.pay.Listener.PageSelectedListener;
//import com.thunder.pay.Listener.SearchListener;
//import com.thunder.pay.R;
//import com.thunder.pay.adapter.ItuneListAdapter;
//import com.thunder.pay.customlayout.FilterDialog;
//import com.thunder.pay.daomodel.ItuneDataModel;
//import com.thunder.pay.rest.RestCall;
//import com.thunder.pay.rest.RestClient;
//import com.thunder.pay.service.DownloadData;
//
//import java.util.ArrayList;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
///**
// * A fragment representing a list of Items.
// * <p/>
// * interface.
// */
//public class SongHomeFragment extends Fragment implements ItuneListAdapter.OnItemClickListener, SearchListener, SwipeRefreshLayout.OnRefreshListener,PageSelectedListener {
//
//    private Context mContext;
//    private static final String ARG_COLUMN_COUNT = "column-count";
//    private int mColumnCount = 1;
//    private static RecyclerView recyclerView;
//    private LinearLayoutManager layoutManager;
//    private ItuneListAdapter songListAdapter;
//    private ViewGroup mainView;
//    private ArrayList<ItuneDataDetails> songList = new ArrayList<>();
//    private TextView heading;
//    public static String searchQuery;
//    public SearchListener searchListener;
//    public PageSelectedListener pageSelectedListener;
//    public FilterDialog.DialogOnClickListener myListener;
//    FilterDialog filterDialog;
//    String serach_txt, country_code;
//    private SwipeRefreshLayout swipeRefreshLayout;
//
//    /**
//     * Mandatory empty constructor for the fragment manager to instantiate the
//     * fragment (e.g. upon screen orientation changes).
//     */
//    public SongHomeFragment() {
//    }
//
//    @Override
//    public void onSearchContent(String searchContent) {
//
//        if (searchContent != null && searchContent.length() >= 3) {
//            this.searchQuery = searchContent;
//            swipeRefreshLayout.setRefreshing(true);
//            loadList();
//        }
//    }
//
//
//    public SearchListener getSearchListener() {
//        return searchListener;
//    }
//
//    public PageSelectedListener getPageSelectedListener() {
//        return pageSelectedListener;
//    }
//
//    @Override
//    public void onRefresh() {
//        loadList();
//    }
//
//    public enum Single {
//        INSTANCE;
//        SongHomeFragment s = new SongHomeFragment();
//
//        public SongHomeFragment getInstance() {
//            if (s == null)
//                return new SongHomeFragment();
//            else return s;
//        }
//    }
//
//    // TODO: Customize parameter initialization
//    @SuppressWarnings("unused")
//    public static SongHomeFragment newInstance(int columnCount) {
//        SongHomeFragment fragment = new SongHomeFragment();
//        Bundle args = new Bundle();
//        args.putInt(ARG_COLUMN_COUNT, columnCount);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//    }
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.action_filter:
////                showPopup();
//                filterDialog = new FilterDialog(getActivity(), myListener);
//                filterDialog.setCancelable(false);
//                filterDialog.show();
//
//                Toast.makeText(getActivity(), "Filter Toast", Toast.LENGTH_SHORT).show();
//                break;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    public void showPopup() {
//
//        myListener = new FilterDialog.DialogOnClickListener() {
//            @Override
//            public void onOkButtonClick() {
//                country_code = filterDialog.getCountrycode();
//                filterDialog.dismiss();
//            }
//
//            @Override
//            public void onCancelButtonClick() {
//                filterDialog.dismiss();
//            }
//        };
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        mainView = (ViewGroup) inflater.inflate(R.layout.song_list_main_layout, container, false);
//        mContext = getActivity();
//        searchListener = this;
//        pageSelectedListener = this;
//        recyclerView = (RecyclerView) mainView.findViewById(R.id.my_recycler_view);
//        layoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(layoutManager);
//        swipeRefreshLayout = (SwipeRefreshLayout) mainView.findViewById(R.id.swipe_refresh_layout);
//
//        swipeRefreshLayout.setOnRefreshListener(this);
//
//        /**
//         * Showing Swipe Refresh animation on activity create
//         * As animation won't start on onCreate, post runnable is used
//         */
//        swipeRefreshLayout.post(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        swipeRefreshLayout.setRefreshing(true);
////                                        loadList();
//                                        initList();
//                                    }
//                                }
//        );
//
//         if (mainView != null) {
//            mainView.setFocusableInTouchMode(true);
//            mainView.requestFocus();
//            mainView.setOnKeyListener(new View.OnKeyListener() {
//                @Override
//                public boolean onKey(View v, int keyCode, KeyEvent event) {
//                    if (keyCode == KeyEvent.KEYCODE_BACK) {
//                       getActivity().onBackPressed();
//                    }
//                    return false;
//                }
//            });
//        }
////        initList();
////        loadList();
//        showPopup();
//        return mainView;
//
//    }
//
//    private void initList() {
//            songList = ListDBHelper.single.INSTANCE.getInstnce().getItemList(getActivity(),"song");
//        if(songList!=null && songList.size()>0) {
//            heading = (TextView) mainView.findViewById(R.id.heading);
//            System.out.println(" initList songList size " + songList.size());
//            if (songList != null && songList.size() > 0) {
//                heading.setText("" + songList.get(0).getArtistName() + " : Songs");
//            }
//            songListAdapter = new ItuneListAdapter(mContext, songList, this);
//            recyclerView.setAdapter(songListAdapter);
//            swipeRefreshLayout.setRefreshing(false);
//        }else{
//            //loadList();
//        }
//    }
//
//    private void loadList() {
//
//        RestCall service = RestClient.Single.INSTANCE.getInstance().getRestCallsConnection(mContext);
//
//        if(searchQuery!=null && searchQuery.equalsIgnoreCase("") && searchQuery.length()<2){
//            searchQuery = "new";
//        }
//        final Call<ItuneDataModel> repos = service.listReposNew(""+searchQuery,"musicTrack");
//        repos.enqueue(new Callback<ItuneDataModel>() {
//            @Override
//            public void onResponse(Call<ItuneDataModel> call, Response<ItuneDataModel> response) {
//
//                int code = response.code();
//                if (code == 200) {
//                    ItuneDataModel trackDatas = response.body();
//                    System.out.println("onResponse "+ new Gson().toJson(trackDatas));
//                    if(trackDatas!=null) {
////                        DataHandler.Single.INSTANCE.getInstance().setTrackData(null);
////                        DataHandler.Single.INSTANCE.getInstance().setTrackData(trackDatas);
//
//                        ListDBHelper.single.INSTANCE.getInstnce().insertItemList(trackDatas.getResults(),getActivity());
//                    }
//                    initList();
//                    swipeRefreshLayout.setRefreshing(false);
//                } else {
//                    Toast.makeText(getActivity(), "Did not work: " + String.valueOf(code), Toast.LENGTH_LONG).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ItuneDataModel> call, Throwable t) {
//                System.out.println("onResponse ");
//            }
//        });
//
//        /*String url = getURL();
//
//        if (AppUtill.isNetworkAvailable(mContext)) {
//            new DownloadData(getActivity(), url, handler);
//        } else {
//            Toast.makeText(mContext, "Check Internet Connection.", Toast.LENGTH_SHORT).show();
//        }*/
//    }
//
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//    }
//
//    @Override
//    public void onPopupBtnClicked(ItuneDataDetails results) {
//
////        MusicPlayerFragment playerFragment = MusicPlayerFragment.Single.INSTANCE.getInstance();
////
////        Bundle bundle = new Bundle();
////        bundle.putSerializable("trackData", results);
////        playerFragment.setArguments(bundle);
////        AppUtill.hideShowFrag(Single.INSTANCE.getInstance(), playerFragment, getActivity(), R.id.container);
////        //AppUtill.loadChildFragment(Single.INSTANCE.getInstance(),playerFragment,getActivity(),R.id.childe_container);
//
//    }
//
//    private final Handler handler = new Handler() {
//        @Override
//        public void handleMessage(android.os.Message msg) {
//            switch (msg.what) {
//                case DownloadData.DONE:
//                    initList();
//                    break;
//                case DownloadData.ERROR:
//                    Toast.makeText(mContext, "Oops Some Problem occur.", Toast.LENGTH_SHORT).show();
//                    break;
//            }
//            swipeRefreshLayout.setRefreshing(false);
//        }
//    };
//
//
//    String getURL() {
//
//        String url = "https://itunes.apple.com/search?term=";
//        if (searchQuery != null && !searchQuery.equalsIgnoreCase("")) {
//            searchQuery = searchQuery.replace(" ","+");
//            url = url + searchQuery;
//        } else {
//            url = url + "new";
//        }
//        if (country_code != null && !country_code.equalsIgnoreCase("") && country_code.length() > 1) {
//            url = url + "&country=" + country_code;
//        } else {
//            url = url + "&country=" + "IN";
//        }
//        url = url + "&entity=musicTrack";
//        System.out.println(" final iTune URl " + url);
//        return url;
//    }
//
//    @Override
//    public void onPageSelected() {
//        swipeRefreshLayout.post(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        swipeRefreshLayout.setRefreshing(true);
//                                        initList();
//                                    }
//                                }
//        );
//    }
//}
