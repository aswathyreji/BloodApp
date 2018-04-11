package com.bloodbank.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface AppDao {
    @Insert
    long insert(Registration registration);

    @Query("SELECT * FROM Registration WHERE bloodGroup = :bloodGroup")
    List<Registration> search(String bloodGroup);

    @Query("SELECT bloodGroup, COUNT(id) AS count FROM Registration GROUP BY bloodGroup")
    List<BloodGroupResult> statistics();

    @Query("SELECT * FROM Registration WHERE email = :email")
    Registration findByEmail(String email);
}
