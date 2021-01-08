package com.company.roomfavorites;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.company.roomfavorites.model.ProductoFavorito;
import com.company.roomfavorites.model.ProductosRepositorio;

import java.util.List;

public class ProductosViewModel extends AndroidViewModel {
    private final ProductosRepositorio productosRepositorio;

    public ProductosViewModel(@NonNull Application application) {
        super(application);

        productosRepositorio = new ProductosRepositorio(application);
    }

    LiveData<List<ProductoFavorito>> obtenerProductos(int userId) {
        return productosRepositorio.obtenerProductos(userId);
    }

    public void invertirFavorito(int userId, int productoId) {
        productosRepositorio.invertirFavorito(userId, productoId);
    }
}
