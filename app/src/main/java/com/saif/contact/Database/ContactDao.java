package com.saif.contact.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDao {

    @Query("SELECT * FROM ContactList")
    List<ContactModel> getContactList();

    @Insert
    void addContact(ContactModel contactModel);

    @Update
    void  updateContact(ContactModel contactModel);

    @Delete
    void deleteContact(ContactModel contactModel);


}
