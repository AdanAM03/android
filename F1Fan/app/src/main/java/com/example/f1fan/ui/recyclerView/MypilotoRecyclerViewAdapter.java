package com.example.f1fan.ui.recyclerView;

import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.f1fan.databinding.FragmentPilotoBinding;
import com.example.f1fan.modelo.pojos.Piloto;
import com.example.f1fan.ui.recyclerView.placeholder.PlaceholderContent.PlaceholderItem;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MypilotoRecyclerViewAdapter extends RecyclerView.Adapter<MypilotoRecyclerViewAdapter.ViewHolder> {

    private final List<Piloto> mValues;

    public MypilotoRecyclerViewAdapter(List<Piloto> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentPilotoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.nombre.setText(mValues.get(position).getNombre() + " " + mValues.get(position).getApellidos());
        holder.victorias.setText("Victorias: " + mValues.get(position).getVictorias());
        holder.edad.setText("Edad: " + String.valueOf(mValues.get(position).getEdad()));
        try {
            if (mValues.get(position).getUrl_foto() != "n" && mValues.get(position).getUrl_foto() != "0" ) {
                URL urlFoto = new URL(mValues.get(position).getUrl_foto());
                Bitmap bitmap = BitmapFactory.decodeStream(urlFoto.openConnection().getInputStream());
                holder.foto.setImageBitmap(bitmap);
            }
        } catch (IOException e) {
            //throw new RuntimeException(e);
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView nombre;
        public final TextView victorias;
        public final TextView edad;

        public final ImageView foto;

        public ViewHolder(FragmentPilotoBinding binding) {
            super(binding.getRoot());
            nombre = binding.nombre;
            victorias = binding.apellidos;
            edad = binding.edad;
            foto = binding.foto;

        }

        @Override
        public String toString() {
            return super.toString() + " '" + "'";
        }
    }
}