package com.company.roomfavorites.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Producto {
    @PrimaryKey(autoGenerate = true)
    int id;

    public String nombre;
}
