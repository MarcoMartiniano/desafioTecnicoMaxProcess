package com.marco.desafiotecnicomaxprocess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class TabelaSQLHelper extends SQLiteOpenHelper {

    //String criacao da tabela
    private final String SQL_Clientes = "CREATE TABLE CLIENTES ( "+
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, cpf TEXT, dataCadastro TEXT, dataNascimento TEXT, uf TEXT)";

    private final String SQL_Telefone = "CREATE TABLE TELEFONES ( "+
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, telefone TEXT, id_Cliente INTEGER, FOREIGN KEY(id_Cliente) REFERENCES CLIENTES(_id))";



    public TabelaSQLHelper(Context context,String name,SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_Clientes);
        db.execSQL(SQL_Telefone);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS CLIENTES");
        db.execSQL("DROP TABLE IF EXISTS TELEFONES");
    }



    //INTERFACE DOS NOMES DAS TABELAS, COLUNAS E LISTA DE COLUNAS
    public interface dadosSQL{
        //TABELA CLIENTES
        String TABELA_CLIENTES = "CLIENTES";
        //COLUNA
        String COL_CLIENTES_ID = "_id";
        String COL_CLIENTES_NOME= "nome";
        String COL_CLIENTES_CPF = "cpf";
        String COL_CLIENTES_DATACADASTRO = "dataCadastro";
        String COL_CLIENTES_DATANASCIMENTO = "dataNascimento";
        String COL_CLIENTES_UF = "uf";

        //LISTA DAS COLUNAS CLIENTES
        String [] LISTA_COLUNAS_CLIENTES = {"_id, nome, cpf, dataCadastro, dataNascimento, uf"};


        //TABELA TELEFONES
        String TABELA_TELEFONES= "TELEFONES";
        String COL_TELEFONES_ID = "_id";
        String COL_TELEFONES_TELEFONE = "telefone";
        String COL_TELEFONES_IDCLIENTE = "id_Cliente";
        //LISTA DAS COLUNAS
        String [] LISTA_COLUNAS_TELEFONES = {"_id, telefone, id_Cliente"};

    }
}
