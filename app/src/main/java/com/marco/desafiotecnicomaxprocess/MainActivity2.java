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
                Clientes c = (Clientes) parent.getItemAtPosition(position);
                ToastTelefone(c.id);
            }
        });

    }


    //Volta para o menu inicial
    public void ListCadastrar(View View) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //Volta para o menu inicial
    public void ListVoltar(View View) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void MetodoAtualizarLista() {
        //setando valores listadados
        listadados = MetodoSetarListaDados();
        //setando ListAdapter na ListView
        ListAdapterBD listAdaptorBD = new ListAdapterBD(this, R.layout.layout_lista_bancodedados, listadados);
        listView.setAdapter(listAdaptorBD);
    }

    private List<Clientes> MetodoSetarListaDados() {
        List<Clientes> retorno = new ArrayList<>();
        ContentResolver resolver = this.getContentResolver();
        Cursor cursor = resolver.query(BDContentProvider.Provider.CLIENTES_URI, TabelaSQLHelper.dadosSQL.LISTA_COLUNAS_CLIENTES, null, null, null);

        if (cursor != null) {
            int tamanho = cursor.getCount();

            Clientes c = new Clientes();

            if (tamanho > 0) {
                cursor.moveToFirst();
                for (int i = 0; i < tamanho; i++) {
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

    private List<String> MetodoSetarListaTelefoneCliente(int idCliente) {
        List<String> retorno = new ArrayList<>();
        ContentResolver resolver = this.getContentResolver();
        Cursor cursor = resolver.query(BDContentProvider.Provider.TELEFONES_URI, TabelaSQLHelper.dadosSQL.LISTA_COLUNAS_TELEFONES, null, null, null);
        int tamanho = cursor.getCount();

        if (tamanho > 0) {
            for (int i = 0; i < tamanho; i++) {
                cursor.moveToPosition(i);
                int idClienteBD = cursor.getInt(2);

                if (idCliente == idClienteBD) {
                    retorno.add(cursor.getString(1));
                }

            }

        }
        cursor.close();
        return retorno;
    }

    private void ToastTelefone(int id) {
        //Pegar ID do usuario que foi clicado
        ContentResolver resolver = this.getContentResolver();
        Cursor cursor = resolver.query(BDContentProvider.Provider.TELEFONES_URI, TabelaSQLHelper.dadosSQL.LISTA_COLUNAS_TELEFONES, null, null, null);
        int contador = 1;

        if (cursor != null) {
            int tamanho = cursor.getCount();
            for (int i = 0; i < tamanho; i++) {
                cursor.moveToPosition(i);
                int idClienteCursos = cursor.getInt(2);
                if (id == idClienteCursos) {
                    contador = contador + 1;
                }

            }

            cursor.close();
        }

    }
}