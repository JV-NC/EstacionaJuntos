package br.com.jvn.estacionajuntos;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import br.com.jvn.estacionajuntos.interfaces.DialogFragmentAdapter;

public class OrderFragment extends DialogFragment {
    public static final int TAG = 0;
    DialogFragmentAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order,container,false);
    }

    RadioGroup rdgOrdenar;
    Button btnBuscar;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rdgOrdenar = view.findViewById(R.id.rdgOrdenar);
        btnBuscar = view.findViewById(R.id.btnBuscar);

        rdgOrdenar.check(R.id.rdbMenorPreco);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ordenar=-1;
                if(rdgOrdenar.getCheckedRadioButtonId() == R.id.rdbMenorPreco){
                    ordenar = 0;
                } else if(rdgOrdenar.getCheckedRadioButtonId() == R.id.rdbMaiorPreco){
                    ordenar = 1;
                } else if(rdgOrdenar.getCheckedRadioButtonId() == R.id.rdbMelhorAv){
                    ordenar = 2;
                } else if(rdgOrdenar.getCheckedRadioButtonId() == R.id.rdbMenorDist){
                    ordenar = 3;
                }

                Log.i("OrderFragment","Resultado: "+ordenar);
                adapter.sendInput(TAG,ordenar);
                dismiss();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            adapter = (DialogFragmentAdapter) getActivity();
        }catch (Exception e){
            Log.e("OrderFragment","Falha onAttach Adapter!");
        }
    }
}