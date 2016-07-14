package com.example.pete.amc;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class FavoriteListFragment extends ListFragment{

    String[] title = {"FAQ", "Like Us on FaceBook", "Email Support"};
    int[] image = {R.mipmap.ic_explore_black_24dp, R.mipmap.facebook, R.mipmap.ic_email_black_24dp};

//    ArrayList<HashMap<String,String>> arrayList;
//    HashMap<String, String> hashMap, hashMap2, hashMap3;

    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    HashMap<String, String> hashMap ;

    SimpleAdapter simpleAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        hashMap = new HashMap<>();

        for(int a = 0; a < title.length; a++)
        {
            hashMap = new HashMap<>();
            hashMap.put("title", title[a]);
            hashMap.put("image", Integer.toString(image[a]));

            arrayList.add(hashMap);
        }

//        arrayList = new ArrayList<>();
//
//        hashMap = new HashMap<>();
//        hashMap.put("title", "FAQ");
//        hashMap.put("image", R.mipmap.ic_explore_black_24dp+"");
//
//        hashMap2 = new HashMap<>();
//        hashMap2.put("title", "Like Us on FaceBook");
//        hashMap2.put("image", R.mipmap.facebook+"");
//
//        hashMap3 = new HashMap<>();
//        hashMap3.put("title", "Email Support");
//        hashMap3.put("image", R.mipmap.ic_email_black_24dp+"");
//
//        arrayList.add(hashMap);
//        arrayList.add(hashMap2);
//        arrayList.add(hashMap3);

        String[] from = {"title", "image"};
        int[] to = {R.id.imageView, R.id.textView};

        simpleAdapter = new SimpleAdapter(getActivity(), arrayList, R.layout.fragment_favorite_row, from , to);
        setListAdapter(simpleAdapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//
//        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//        });
//
//    }
}
