package com.company.roomfavorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.company.roomfavorites.databinding.FragmentListBinding;
import com.company.roomfavorites.databinding.ViewholderProductoBinding;
import com.company.roomfavorites.model.ProductoFavorito;

import java.util.List;


public class ListFragment extends Fragment {

    private FragmentListBinding binding;
    private ProductosViewModel productosViewModel;
    private AutenticacionViewModel autenticacionViewModel;
    private int userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentListBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        productosViewModel = new ViewModelProvider(requireActivity()).get(ProductosViewModel.class);
        autenticacionViewModel = new ViewModelProvider(requireActivity()).get(AutenticacionViewModel.class);

        ProductosAdapter productosAdapter = new ProductosAdapter();
        binding.recyclerView.setAdapter(productosAdapter);

        autenticacionViewModel.usuarioAutenticado.observe(getViewLifecycleOwner(), usuario -> {
            userId = usuario.id;

            productosViewModel.obtenerProductos(usuario.id).observe(getViewLifecycleOwner(), productos -> {
                productosAdapter.setProductoList(productos);
            });
        });
    }

    class ProductosAdapter extends RecyclerView.Adapter<ProductoViewHolder> {

        List<ProductoFavorito> productoList;

        @NonNull
        @Override
        public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ProductoViewHolder(ViewholderProductoBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
            ProductoFavorito producto = productoList.get(position);

            holder.binding.nombre.setText(producto.nombre);
            holder.binding.favorito.setChecked(producto.esFavorito);

            holder.binding.favorito.setOnClickListener(v -> {
                productosViewModel.invertirFavorito(userId, producto.id);
            });
        }

        @Override
        public int getItemCount() {
            return productoList == null ? 0 : productoList.size();
        }

        public void setProductoList(List<ProductoFavorito> productoList) {
            this.productoList = productoList;
            notifyDataSetChanged();
        }
    }

    static class ProductoViewHolder extends RecyclerView.ViewHolder {
        ViewholderProductoBinding binding;

        public ProductoViewHolder(@NonNull ViewholderProductoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}