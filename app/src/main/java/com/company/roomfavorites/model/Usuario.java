package com.company.roomfavorites.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Usuario {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String username;
    public String password;

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }
}