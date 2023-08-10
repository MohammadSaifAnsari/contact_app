package com.saif.contact.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.saif.contact.Adapter.ContactListAdapter;
import com.saif.contact.CreateContactActivity;
import com.saif.contact.Database.DatabaseHelper;
import com.saif.contact.Database.ContactModel;
import com.saif.contact.databinding.FragmentContactBinding;

import java.util.ArrayList;


public class ContactFragment extends Fragment {


    ContactListAdapter contactListAdapter;
    FragmentContactBinding fragmentContactBinding;
    ArrayList<ContactModel> contactList;
    public ContactFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentContactBinding = FragmentContactBinding.inflate(inflater,container,false);

        //Creating object of datahelper class
        DatabaseHelper databaseHelper = DatabaseHelper.getDB(getContext());

        //Creating contact arraylist from table
         contactList = (ArrayList<ContactModel>) databaseHelper.contactDao().getContactList();

        //recycler view adapter
        contactListAdapter = new ContactListAdapter(contactList,getContext());
        fragmentContactBinding.rv1.setAdapter(contactListAdapter);

        //layout manager adapter
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        fragmentContactBinding.rv1.setLayoutManager(linearLayoutManager);




        fragmentContactBinding.floatingAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactListAdapter.notifyDataSetChanged();
                Intent intent  = new Intent(getContext(), CreateContactActivity.class);
                startActivity(intent);
            }
        });

        fragmentContactBinding.searchbar.setIconifiedByDefault(false);

        fragmentContactBinding.searchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText.toLowerCase());
                return true;
            }
        });
        return fragmentContactBinding.getRoot();
    }

    private void filterList(String text) {
        ArrayList<ContactModel> contactModelArrayList = new ArrayList<ContactModel>();
        for (ContactModel song :contactList){
            if ((song.getFirstname()+song.getSurname()).toLowerCase().contains(text)){
                contactModelArrayList.add(song);
            }
        }
        if (contactModelArrayList.isEmpty()){
            Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
        }else {
            contactListAdapter.filterList(contactModelArrayList);
            fragmentContactBinding.rv1.setAdapter(contactListAdapter);
        }
    }

    @Override
    public void onResume() {
        contactListAdapter.notifyDataSetChanged();
        super.onResume();
    }

}