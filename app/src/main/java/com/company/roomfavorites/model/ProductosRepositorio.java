package com.company.roomfavorites.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ProductosRepositorio {
    Executor executor = Executors.newSingleThreadExecutor();

    AppBaseDeDatos.ProductosDao dao;

    public ProductosRepositorio(Application application) {
        dao = AppBaseDeDatos.getInstance(application).productosDao();
    }

    public LiveData<List<ProductoFavorito>> obtenerProductos(int userId) {
        return dao.obtenerProductos(userId);
    }

    public void invertirFavorito(int userId, int productoId) {
        executor.execute(() -> {
            if (dao.obtenerFavorito(userId, productoId) == null) {
                dao.insertarFavorito(new Favorito(userId, productoId));
            } else {
                dao.eliminarFavorito(new Favorito(userId, productoId));
            }
        });
    }
}
