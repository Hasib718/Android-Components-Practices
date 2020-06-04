package com.example.roomdbwithtypeconverter;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public  interface PersonDao {

    @Insert
    void insertPerson(Person person);

    @Insert
    void insertPersons(Person[] people);

    @Update
    void updatePerson(Person person);

    @Delete
    void deletePerson(Person person);

    @Query("SELECT * FROM PERSON ORDER BY ID")
    List<Person> loadAllPersons();

    @Query("SELECT * FROM PERSON WHERE ID = :id")
    Person loadPersonById(int id);
}
