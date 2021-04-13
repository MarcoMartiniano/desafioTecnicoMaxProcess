package com.marco.desafiotecnicomaxprocess;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ListAdapterBD extends ArrayAdapter<Clientes> {

    List<Clientes> listaClientes;

    public ListAdapterBD(Context context, int resource, List<Clientes> listaClientes) {
        super(context, resource, listaClientes);
        this.listaClientes = listaClientes;
    }


    @Override
    public View getView(int position, View convertView,ViewGroup parent) {

        View v = convertView;
        if(v==null){
            LayoutInflater layoutInflater;
            layoutInflater = LayoutInflater.from(getContext());
            v = layoutInflater.inflate(R.layout.layout_lista_bancodedados,null);
        }

        Clientes clientes = getItem(position);

        if(clientes!=null){

            TextView id = (TextView) v.findViewById(R.id.txtListaId);
            TextView nome = (TextView) v.findViewById(R.id.txtListaNome);
            TextView cpf = (TextView) v.findViewById(R.id.txtListaCPF);
            TextView dataNascimento = (TextView) v.findViewById(R.id.txtListadataNascimento);
            TextView dataCadastro = (TextView) v.findViewById(R.id.txtListadataCadastro);
            TextView uf = (TextView) v.findViewById(R.id.txtListaUF);


            if(id != null){
                id.setText(listaClientes.get(position).getId()+"");
            }

            if(nome != null){
                nome.setText(listaClientes.get(position).getNome());
            }

            if(cpf  != null){
                cpf.setText(listaClientes.get(position).getCpf());
            }

            if(dataNascimento != null){
                dataNascimento.setText(listaClientes.get(position).getDataNascimento());
            }
            if(dataNascimento != null){
                dataCadastro.setText(listaClientes.get(position).getDataCadastro());
            }
            if(uf != null){
                uf.setText(listaClientes.get(position).getUf());
            }

        }
        return v;
    }
}

