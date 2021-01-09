package com.company.roomfavorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.company.roomfavorites.databinding.FragmentLoginBinding;


public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentLoginBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AutenticacionViewModel autenticacionViewModel = new ViewModelProvider(requireActivity()).get(AutenticacionViewModel.class);
        NavController navController = Navigation.findNavController(view);

        binding.login.setOnClickListener(v -> {
            autenticacionViewModel.iniciarSesion(binding.user.getText().toString(), binding.password.getText().toString());
        });

        autenticacionViewModel.estadoDeLaAutenticacion.observe(getViewLifecycleOwner(), estadoDeLaAutenticacion -> {
            switch (estadoDeLaAutenticacion) {
                case AUTENTICADO:
                    navController.navigate(R.id.action_loginFragment_to_listFragment);
                    break;
                case INVALIDA:
                    Toast.makeText(getContext(), "PASSWORD INCORRECTO", Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }
}