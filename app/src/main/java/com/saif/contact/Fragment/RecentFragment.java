package com.saif.contact.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saif.contact.Adapter.ContactListAdapter;
import com.saif.contact.Adapter.RecentListAdapter;
import com.saif.contact.Database.DatabaseHelper;
import com.saif.contact.Database.RecentModel;
import com.saif.contact.R;
import com.saif.contact.databinding.FragmentContactBinding;
import com.saif.contact.databinding.FragmentRecentBinding;

import java.util.ArrayList;


public class RecentFragment extends Fragment {

    FragmentRecentBinding fragmentRecentBinding;
    DatabaseHelper databaseHelper;
    ArrayList<RecentModel> recentList;

    public RecentFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentRecentBinding = FragmentRecentBinding.inflate(inflater,container,false);

        databaseHelper = DatabaseHelper.getDB(getContext());

        recentList = (ArrayList<RecentModel>) databaseHelper.recentDao().getRecentList();

        RecentListAdapter recentListAdapter = new RecentListAdapter(recentList,getContext());
        fragmentRecentBinding.rv1.setAdapter(recentListAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        fragmentRecentBinding.rv1.setLayoutManager(linearLayoutManager);
        return fragmentRecentBinding.getRoot();
    }
}