package com.company.roomfavorites.model;


import android.app.Application;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AutenticacionManager {

    Executor executor = Executors.newSingleThreadExecutor();

    public interface IniciarSesionCallback {
        void cuandoUsuarioAutenticado(Usuario usuario);
        void cuandoNombreNoDisponible();
    }

    AppBaseDeDatos.UsuariosDao dao;

    public AutenticacionManager(Application application){
        dao = AppBaseDeDatos.getInstance(application).usuariosDao();
    }

    public void iniciarSesion(String username, String password, IniciarSesionCallback callback){
        executor.execute(() -> {
            Usuario usuario = dao.autenticar(username, password);

            if (usuario != null){
                callback.cuandoUsuarioAutenticado(usuario);
            } else {
                if (dao.comprobarNombreDisponible(username) == null) {

                    usuario = new Usuario(username, password);
                    dao.insertarUsuario(usuario);
                    callback.cuandoUsuarioAutenticado(usuario);

                } else {
                    callback.cuandoNombreNoDisponible();
                }
            }
        });
    }
}
