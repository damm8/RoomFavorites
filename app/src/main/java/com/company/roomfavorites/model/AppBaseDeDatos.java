package com.company.roomfavorites.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;

@Database(entities = {Usuario.class, Producto.class, Favorito.class}, version = 4, exportSchema = false)
public abstract class AppBaseDeDatos extends RoomDatabase {

    public abstract UsuariosDao usuariosDao();
    public abstract ProductosDao productosDao();

    private static volatile AppBaseDeDatos INSTANCE;

    public static AppBaseDeDatos getInstance(final Context context){
        if (INSTANCE == null){
            synchronized (AppBaseDeDatos.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, AppBaseDeDatos.class, "my.db")
                            .fallbackToDestructiveMigration()
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    db.execSQL("INSERT INTO Producto VALUES(\"1\",\"CHAQUETA\");");
                                    db.execSQL("INSERT INTO Producto VALUES(\"2\",\"PANTALON\");\n");
                                    db.execSQL("INSERT INTO Producto VALUES(\"3\",\"JERSEY\");");
                                    db.execSQL("INSERT INTO Producto VALUES(\"4\",\"ABRIGO\");");
                                    db.execSQL("INSERT INTO Producto VALUES(\"5\",\"CAMISETA\");\n");
                                    db.execSQL("INSERT INTO Producto VALUES(\"6\",\"SOMBRERO\");");
                                    db.execSQL("INSERT INTO Producto VALUES(\"7\",\"ZAPATOS\");");
                                    db.execSQL("INSERT INTO Producto VALUES(\"8\",\"CHALECO\");\n");
                                    db.execSQL("INSERT INTO Producto VALUES(\"9\",\"CALCETINES\");");
                                    db.execSQL("INSERT INTO Producto VALUES(\"10\",\"ZAPATOS\");");
                                    db.execSQL("INSERT INTO Producto VALUES(\"11\",\"CHALECO\");\n");
                                    db.execSQL("INSERT INTO Producto VALUES(\"12\",\"CALCETINES\");");
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    @Dao
    public interface UsuariosDao {
        @Insert
        void insertarUsuario(Usuario usuario);

        @Query("SELECT * FROM Usuario WHERE username = :nombre AND password = :contrasenya")
        Usuario autenticar(String nombre, String contrasenya);

        @Query("SELECT * FROM Usuario WHERE username = :nombre")
        Usuario comprobarNombreDisponible(String nombre);
    }

    @Dao
    public interface ProductosDao {
        @Query("SELECT Producto.id, Producto.nombre, CASE WHEN userId IS NOT NULL THEN 1 ELSE 0 END as esFavorito FROM Producto LEFT JOIN (SELECT * FROM Favorito WHERE userId = :userId) AS Fav ON Producto.id = Fav.productoId")
        LiveData<List<ProductoFavorito>> obtenerProductos(int userId);

        @Query("SELECT * FROM Favorito WHERE userId = :userId AND productoId = :productoId")
        Favorito obtenerFavorito(int userId, int productoId);

        @Insert
        void insertarFavorito(Favorito favorito);

        @Delete
        void eliminarFavorito(Favorito favorito);
    }
}