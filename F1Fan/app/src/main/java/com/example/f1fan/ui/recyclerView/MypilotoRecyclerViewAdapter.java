package com.example.f1fan.ui.recyclerView;

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
import com.example.f1fan.databinding.FragmentPilotoBinding;
import com.example.f1fan.modelo.DAO.DAOpiloto;
import com.example.f1fan.modelo.pojos.Piloto;

import java.util.List;

public class MypilotoRecyclerViewAdapter extends RecyclerView.Adapter<MypilotoRecyclerViewAdapter.ViewHolder> {

    private final List<Piloto> mValues;
    private Context context;
    private FragmentManager fragmentManager;

    private DAOpiloto daoPiloto;

    public MypilotoRecyclerViewAdapter(List<Piloto> items, Context context, FragmentManager fragmentManager, DAOpiloto daOpiloto) {
        mValues = items;
        this.context = context;
        this.fragmentManager = fragmentManager;
        daoPiloto = daOpiloto;
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
        Drawable d = null;

        try {
            d = ContextCompat.getDrawable(context, R.drawable.class.getField(mValues.get(position).getApellidos().toLowerCase().replace(" ", "")).getInt(null));
        } catch (Exception e) {
            Log.d("package:mine", "" + e.getMessage());
            //throw new RuntimeException(e);
        }
        holder.foto.setImageDrawable(d);

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
                ft.replace(R.id.drawer_layout, new FullscreenPiloto(mValues.get(position), finalD, fragmentManager, daoPiloto));
                ft.addToBackStack("piloto");
                ft.setReorderingAllowed(true).commit();
            }
        });
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
        public final View vista;

        public ViewHolder(FragmentPilotoBinding binding) {
            super(binding.getRoot());
            nombre = binding.nombre;
            victorias = binding.apellidos;
            edad = binding.edad;
            foto = binding.foto;
            vista = binding.pilotoContent;

        }

        @Override
        public String toString() {
            return super.toString() + " '" + "'";
        }
    }

}