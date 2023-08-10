package com.saif.contact.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RecentDao {

    @Query("SELECT * FROM RecentList")
    List<RecentModel> getRecentList();

    @Insert
    void addContact(RecentModel recentModel);

    @Update
    void  updateContact(RecentModel recentModel);

    @Delete
    void deleteContact(RecentModel recentModel);


}
