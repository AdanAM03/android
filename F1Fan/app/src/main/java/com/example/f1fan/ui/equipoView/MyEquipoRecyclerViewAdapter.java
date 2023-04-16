package com.example.f1fan.ui.equipoView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.f1fan.R;
import com.example.f1fan.modelo.pojos.Equipo;
import com.example.f1fan.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.f1fan.databinding.FragmentEquipoBinding;
import com.example.f1fan.ui.FullscreenPiloto;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyEquipoRecyclerViewAdapter extends RecyclerView.Adapter<MyEquipoRecyclerViewAdapter.ViewHolder> {

    private final List<Equipo> mValues;
    private Context context;
    private FragmentManager fragmentManager;
    public MyEquipoRecyclerViewAdapter(List<Equipo> items, Context context, FragmentManager fragmentManager) {
        mValues = items;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentEquipoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.nombre.setText(mValues.get(position).getNombre());
        holder.anhosActivo.setText("Años activo: " + String.valueOf(mValues.get(position).getAnhos_activo()));
        holder.victorias.setText("Victorias: " + String.valueOf(mValues.get(position).getVictorias()));
        holder.teamPrincipal.setText("Team Principal: " + mValues.get(position).getTeam_principal());

        Drawable d = null;
        try {
            d = ContextCompat.getDrawable(context, R.drawable.class.getField(mValues.get(position).getUrl_foto()).getInt(null));
        } catch (Exception e) {
            Log.d("package:mine", "" + e.getMessage());
            //throw new RuntimeException(e);
        }
        holder.imagen.setImageDrawable(d);

        Drawable finalD = d;
        holder.vista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = fragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.slide_out
                );
                ft.replace(R.id.drawer_layout, new FullscreenFragmentEquipo(mValues.get(position), finalD, fragmentManager));
                ft.addToBackStack(null);
                ft.setReorderingAllowed(false).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView nombre;
        public final TextView anhosActivo;
        public final TextView victorias;
        public final TextView teamPrincipal;
        public final ImageView imagen;
        public final View vista;


        public ViewHolder(FragmentEquipoBinding binding) {
            super(binding.getRoot());
            nombre = binding.nombreEquipo;
            anhosActivo = binding.anhos;
            victorias = binding.victoriasEquipo;
            teamPrincipal = binding.teamPrincipal;
            imagen = binding.fotoEquipo;
            vista = binding.equipoContent;
        }

        @Override
        public String toString() {
            return super.toString() + " '" ;
        }
    }
}