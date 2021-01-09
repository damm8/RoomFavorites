package com.company.roomfavorites.model;


import android.app.Application;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AutenticacionManager {

    Executor executor = Executors.newSingleThreadExecutor();

    public interface IniciarSesionCallback {
        void cuandoUsuarioAutenticado(Usuario usuario);
        void cuandoContrasenyaInvalida();
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

                    dao.insertarUsuario(new Usuario(username, password));

                    callback.cuandoUsuarioAutenticado(dao.autenticar(username, password));

                } else {
                    callback.cuandoContrasenyaInvalida();
                }
            }
        });
    }
}
