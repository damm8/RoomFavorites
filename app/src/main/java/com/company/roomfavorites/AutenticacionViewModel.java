package com.company.roomfavorites;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.company.roomfavorites.model.AutenticacionManager;
import com.company.roomfavorites.model.Usuario;

public class AutenticacionViewModel extends AndroidViewModel {

    enum EstadoDeLaAutenticacion {
        NO_AUTENTICADO,
        AUTENTICADO,
        INVALIDA,
    }

    MutableLiveData<EstadoDeLaAutenticacion> estadoDeLaAutenticacion = new MutableLiveData<>(EstadoDeLaAutenticacion.NO_AUTENTICADO);
    MutableLiveData<Usuario> usuarioAutenticado = new MutableLiveData<>();

    AutenticacionManager autenticacionManager;

    public AutenticacionViewModel(@NonNull Application application) {
        super(application);
        autenticacionManager = new AutenticacionManager(application);
    }


    void iniciarSesion(String username, String password){
        autenticacionManager.iniciarSesion(username, password, new AutenticacionManager.IniciarSesionCallback() {
            @Override
            public void cuandoUsuarioAutenticado(Usuario usuario) {
                usuarioAutenticado.postValue(usuario);
                estadoDeLaAutenticacion.postValue(EstadoDeLaAutenticacion.AUTENTICADO);
            }

            @Override
            public void cuandoContrasenyaInvalida() {
                estadoDeLaAutenticacion.postValue(EstadoDeLaAutenticacion.INVALIDA);
            }
        });
    }
}
