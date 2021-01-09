package com.company.roomfavorites.model;

import androidx.room.Entity;

/* Esta clase define la relacion "muchos a muchos" entre Usuario y Producto */

@Entity(primaryKeys = {"userId", "productoId"})
public class Favorito {
    public int userId;
    public int productoId;

    public Favorito(int userId, int productoId) {
        this.userId = userId;
        this.productoId = productoId;
    }
}
