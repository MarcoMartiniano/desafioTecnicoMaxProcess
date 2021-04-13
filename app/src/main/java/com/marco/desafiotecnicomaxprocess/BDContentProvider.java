package com.marco.desafiotecnicomaxprocess;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.ContentUris;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;




public class BDContentProvider extends ContentProvider {

    private TabelaSQLHelper helperDB;
    private static UriMatcher uriMatcher;
    private SQLiteDatabase db;

    //CODIGO IDENTIFICACAO DAS URIs
    private static final int CODE_URI_CLIENTES = 1;
    private static final int UNICO_CODE_URI_CLIENTES = 2;

    private static final int CODE_URI_TELEFONES = 3;
    private static final int UNICO_CODE_URI_TELEFONES  = 4;

    int match;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(Provider.AUTHORITY, "clientes", CODE_URI_CLIENTES);
        uriMatcher.addURI(Provider.AUTHORITY, "clientes/#", UNICO_CODE_URI_CLIENTES);

        uriMatcher.addURI(Provider.AUTHORITY, "telefones", CODE_URI_TELEFONES );
        uriMatcher.addURI(Provider.AUTHORITY, "telefones/#", UNICO_CODE_URI_TELEFONES);

    }
    public BDContentProvider() {
    }

    public BDContentProvider(TabelaSQLHelper helperDB) {
        this.helperDB = helperDB;
    }

    //Construtor
    @Override
    public boolean onCreate() {
        helperDB = new TabelaSQLHelper(getContext(),Provider.BD_NOME, null,Provider.BD_VERSION);
        return true;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection,  String[] selectionArgs, String sortOrder) {
        match = uriMatcher.match(uri);
        db = helperDB.getReadableDatabase();


        switch (match){

            case CODE_URI_CLIENTES:
                return db.query(TabelaSQLHelper.dadosSQL.TABELA_CLIENTES,projection,selection,selectionArgs,null,null,sortOrder);

            case UNICO_CODE_URI_CLIENTES:
                selection = String.format("%s=%s", "_id",uri.getLastPathSegment());
                return db.query(TabelaSQLHelper.dadosSQL.TABELA_CLIENTES,projection,selection,selectionArgs,null,null,sortOrder);

            case CODE_URI_TELEFONES:
                return db.query(TabelaSQLHelper.dadosSQL.TABELA_TELEFONES,projection,selection,selectionArgs,null,null,sortOrder);

            case UNICO_CODE_URI_TELEFONES:
                selection = String.format("%s=%s", "_id",uri.getLastPathSegment());
                return db.query(TabelaSQLHelper.dadosSQL.TABELA_TELEFONES,projection,selection,selectionArgs,null,null,sortOrder);

            default:
                return null;
        }

    }


    @Override
    public String getType( Uri uri) {

        int match = uriMatcher.match(uri);

        switch (match){
            case CODE_URI_CLIENTES:
                return "vnd.android.cursor.dir/vnd.clientes";
            case UNICO_CODE_URI_CLIENTES:
                return "vnd.android.cursor.item/vnd.clientes";

            case CODE_URI_TELEFONES:
                return "vnd.android.cursor.dir/vnd.telefones";
            case UNICO_CODE_URI_TELEFONES:
                return "vnd.android.cursor.item/vnd.telefones";
            default:
                return null;
        }
    }


    @Override
    public Uri insert( Uri uri, ContentValues values) {

        match = uriMatcher.match(uri);
        db = helperDB.getWritableDatabase();
        long id;

        switch (match){
            case CODE_URI_CLIENTES:
                id = db.insert(TabelaSQLHelper.dadosSQL.TABELA_CLIENTES,null,values);
                getContext().getContentResolver().notifyChange(uri,null);
                uri = ContentUris.withAppendedId(uri,id);
                return uri;

            case CODE_URI_TELEFONES:
                id = db.insert(TabelaSQLHelper.dadosSQL.TABELA_TELEFONES,null,values);
                getContext().getContentResolver().notifyChange(uri,null);
                uri = ContentUris.withAppendedId(uri,id);
                return uri;

            default:
                return null;
        }

    }

    @Override
    public int delete(Uri uri, String selection,String[] selectionArgs) {
        match = uriMatcher.match(uri);
        db = helperDB.getWritableDatabase();

        switch (match){

            case CODE_URI_CLIENTES:
                return db.delete(TabelaSQLHelper.dadosSQL.TABELA_CLIENTES,selection,selectionArgs);

            case UNICO_CODE_URI_CLIENTES:
                selection = "_id" + "=?";
                selectionArgs = new String[]{uri.getLastPathSegment()};
                return db.delete(TabelaSQLHelper.dadosSQL.TABELA_CLIENTES,selection,selectionArgs);

            case CODE_URI_TELEFONES:
                return db.delete(TabelaSQLHelper.dadosSQL.TABELA_TELEFONES,selection,selectionArgs);

            case UNICO_CODE_URI_TELEFONES:
                selection = "_id" + "=?";
                selectionArgs = new String[]{uri.getLastPathSegment()};
                return db.delete(TabelaSQLHelper.dadosSQL.TABELA_TELEFONES,selection,selectionArgs);

            default:
                return 0;
        }
    }

    @Override
    public int update(Uri uri, ContentValues values,String selection, String[] selectionArgs) {
        match = uriMatcher.match(uri);
        db = helperDB.getWritableDatabase();
        switch (match){
            case CODE_URI_CLIENTES:
                return db.update(TabelaSQLHelper.dadosSQL.TABELA_CLIENTES,values,selection,selectionArgs);
            case CODE_URI_TELEFONES:
                return db.update(TabelaSQLHelper.dadosSQL.TABELA_TELEFONES,values,selection,selectionArgs);
        }
        return 0;
    }


    public static class Provider {

        public static final String AUTHORITY = "com.marco.desafiotecnicomaxprocess";
        public static final String uri = "content://" + AUTHORITY;

        public static final String uri_Clientes = uri + "/clientes";
        public static final Uri CLIENTES_URI = Uri.parse(uri_Clientes);

        public static final String uri_Telefones = uri + "/telefones";
        public static final Uri TELEFONES_URI = Uri.parse(uri_Telefones);

        public static final String BD_NOME = "BDteste4";
        public static final int BD_VERSION =  1;
    }

}
