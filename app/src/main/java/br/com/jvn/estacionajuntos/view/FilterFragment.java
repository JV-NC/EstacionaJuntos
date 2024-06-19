package br.com.jvn.estacionajuntos.view;

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
import android.widget.Toast;

import br.com.jvn.estacionajuntos.R;
import br.com.jvn.estacionajuntos.interfaces.DialogFragmentAdapter;
import br.com.jvn.estacionajuntos.view.HomeActivity;

public class FilterFragment extends DialogFragment {
    public static final int TAG = 1;
    DialogFragmentAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filter,container,false);
    }

    RadioGroup rdgFiltrar;
    Button btnBuscar;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rdgFiltrar = view.findViewById(R.id.rdgFiltrar);
        btnBuscar = view.findViewById(R.id.btnBuscar);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int filtrar=-1;

                if(rdgFiltrar.getCheckedRadioButtonId() == R.id.rdbEspAb){
                    filtrar = 0;
                } else if(rdgFiltrar.getCheckedRadioButtonId() == R.id.rdbFuncionamento){
                    filtrar = 1;
                } else if(rdgFiltrar.getCheckedRadioButtonId() == R.id.rdbDisponibilidade){
                    filtrar = 2;
                }

                Log.i("FilterFragment","Results: "+filtrar);
                adapter.sendInput(TAG,filtrar);
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
            Log.e("FilterFragment","Falha onAttach Adapter!");
        }
    }
}