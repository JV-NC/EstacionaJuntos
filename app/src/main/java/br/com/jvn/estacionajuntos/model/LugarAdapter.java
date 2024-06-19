package br.com.jvn.estacionajuntos.model;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import br.com.jvn.estacionajuntos.R;
import br.com.jvn.estacionajuntos.interfaces.LugarAdapterListener;

public class LugarAdapter extends RecyclerView.Adapter<LugarAdapter.LugarViewHolder>{
    private final ArrayList<Lugar> lugares;
    private final LugarAdapterListener listener;
    public LugarAdapter(ArrayList<Lugar> lugares, LugarAdapterListener listener){
        this.lugares = lugares;
        this.listener = listener;
    }

    public ArrayList<Lugar> getLugares(){
        return lugares;
    }

    @NonNull
    @Override
    public LugarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lugar_item, parent, false);
        return new LugarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LugarViewHolder holder, int position) {
        Lugar lugar = lugares.get(position);
        holder.bind(lugar);

        holder.cvContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Click","pos:"+holder.getAdapterPosition());
                listener.onItemClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return lugares.size();
    }

    class LugarViewHolder extends RecyclerView.ViewHolder{
        TextView lblNome, lblDistanciaItem, lblEndereco, lblRating;
        ImageView imgFoto;
        CardView cvContainer;

        public LugarViewHolder(@NonNull View view) {
            super(view);
            lblNome = view.findViewById(R.id.lblNome);
            lblDistanciaItem = view.findViewById(R.id.lblDistanciaItem);
            lblEndereco = view.findViewById(R.id.lblEndereco);
            lblRating = view.findViewById(R.id.lblRating);
            imgFoto = view.findViewById(R.id.imgFoto);
            cvContainer = view.findViewById(R.id.cvContainer);
        }

        public void bind(Lugar lugar) {
            DecimalFormat df = new DecimalFormat("#");
            DecimalFormat star = new DecimalFormat("0.0");
            lblNome.setText(lugar.getNome());
            lblDistanciaItem.setText(df.format(lugar.getDistance())+"m");
            lblEndereco.setText(lugar.getEndereco());
            lblRating.setText(star.format(lugar.getRating()));

            if(lugar.isEspacoAberto()){ //gambiarra
                imgFoto.setImageResource(R.drawable.estacionamento2);
            } else if(lugar.isOpen()){
                imgFoto.setImageResource(R.drawable.estacionamento3);
            }
        }
    }
}
