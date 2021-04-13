package com.marco.desafiotecnicomaxprocess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private List<Clientes> listadados;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        listadados = new ArrayList<>();
        listView = (ListView)findViewById(R.id.lvCadastro);

        //Setando Lista
        MetodoAtualizarLista();

        //listener da ListView para Mostrar toast com telefone ao cliclar

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastTelefone(position);

            }
        });

    }



    //Volta para o menu inicial
    public void ListCadastrar (View View){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    //Volta para o menu inicial
    public void ListVoltar (View View){
    Intent intent = new Intent(this,MainActivity.class);
    startActivity(intent);
    }


    public void MetodoAtualizarLista(){
        //setando valores listadados
       listadados = MetodoSetarListaDados();
        //setando ListAdapter na ListView
        ListAdapterBD listAdaptorBD = new ListAdapterBD(this,R.layout.layout_lista_bancodedados,listadados);
        listView.setAdapter(listAdaptorBD);
    }

    private List<Clientes> MetodoSetarListaDados() {
        List<Clientes> retorno = new ArrayList<>();
        ContentResolver resolver = this.getContentResolver();
        Cursor cursor = resolver.query(BDContentProvider.Provider.CLIENTES_URI,TabelaSQLHelper.dadosSQL.LISTA_COLUNAS_CLIENTES,null,null,null);

        if(cursor != null){
        int tamanho = cursor.getCount();

        Clientes c = new Clientes();

        if(tamanho > 0){
            cursor.moveToFirst();
            for(int i=0;i<tamanho;i++){
                c = new Clientes();
                cursor.moveToPosition(i);
                int id = cursor.getInt(0);
                String nomeCursor = cursor.getString(1);
                String cpfCursor = cursor.getString(2);
                String dataCadastro = cursor.getString(3);
                String dataNascimento = cursor.getString(4);
                String uf = cursor.getString(5);
                c.id = id;
                c.nome = nomeCursor;
                c.cpf = cpfCursor;
                c.dataCadastro = dataCadastro;
                c.dataNascimento = dataNascimento;
                c.uf = uf;

                //Lista telefone atraves do metodo
                c.telefones = MetodoSetarListaTelefoneCliente(id);
                retorno.add(c);
            }
        }
        cursor.close();
        }

        return retorno;
    }

    private List<String> MetodoSetarListaTelefoneCliente(int idCliente){
        List<String> retorno = new ArrayList<>();
        ContentResolver resolver = this.getContentResolver();
        Cursor cursor = resolver.query(BDContentProvider.Provider.TELEFONES_URI,TabelaSQLHelper.dadosSQL.LISTA_COLUNAS_TELEFONES,null,null,null);
        int tamanho = cursor.getCount();

        if(tamanho > 0){
            for(int i=0;i<tamanho;i++){
                cursor.moveToPosition(i);
            int idClienteBD = cursor.getInt(2);

            if(idCliente == idClienteBD){
                retorno.add(cursor.getString(1));
                Toast.makeText(this, "MetodoSetarListaTelefoneCliente " +cursor.getString(1) + "ID cliente " + idCliente, Toast.LENGTH_LONG).show();
            }

            }

        }
        cursor.close();
        return retorno;
    }

    private void ToastTelefone (int posicao){
       //Pegar ID do usuario que foi clicado
        List<Clientes> listaClientes = MetodoSetarListaDados();
        ContentResolver resolver = this.getContentResolver();
        Cursor cursor = resolver.query(BDContentProvider.Provider.CLIENTES_URI,TabelaSQLHelper.dadosSQL.LISTA_COLUNAS_CLIENTES,null,null,null);
        cursor.moveToPosition(posicao);
        int idCliente = cursor.getInt(0);
        int contador = 1;


        //pegando a lista de telefone pelo idCliente atraves do metodo MetodoSetarListaTelefoneCliente
        List<String> telefones = MetodoSetarListaTelefoneCliente(idCliente);
        Toast.makeText(this, "Telefone tamanho " +telefones.size() + "ID cliente " + idCliente, Toast.LENGTH_LONG).show();

        if(telefones.size() > 0){
            for(int i=0;i < telefones.size();i++){
                Toast.makeText(this, "Telefone " + contador + " " + telefones.get(i), Toast.LENGTH_LONG).show();
                contador = contador + 1;
            }

        }
        cursor.close();
    }

}