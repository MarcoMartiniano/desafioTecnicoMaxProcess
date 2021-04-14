package com.marco.desafiotecnicomaxprocess;


import androidx.appcompat.app.AppCompatActivity;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.text.InputType;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText editTextNome;
    EditText editTextCpf;
    EditText editTextDataNascimento;
    Spinner spinnerUF;
    LinearLayout linearLayoutTelefones;
    List<LinearLayout> listLinearLayout;

    int numeroTelefone;

    final String[] estadosUF = new String[]{"AC","AL","AM","AP","BA","CE","DF","ES","GO","MA","MG","MS","MT","PA","PB","PE","PI","PR","RJ","RN","RO","RR","RS","SC","SE","SP","TO"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Controle quantidade telefone
        numeroTelefone = 1;
        //Lista de LinearLayout de telefones
        listLinearLayout = new ArrayList<>();

        editTextNome = (EditText) findViewById(R.id.editTextNome);
        editTextCpf = (EditText)findViewById(R.id.editTexCPF);
        editTextDataNascimento = (EditText)findViewById(R.id.editTextDataNascimento);
        spinnerUF = (Spinner)findViewById(R.id.spinnerUF);
        linearLayoutTelefones = (LinearLayout)findViewById(R.id.LinearLayoutTelefones);


        //ArrayAdapter Valores Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
          this,
          android.R.layout.simple_spinner_item,
          estadosUF
        );
        spinnerUF.setAdapter(adapter);


    }





    //Método Botao lista cadastrados -> Abrir a ActivityListaCadastro




    //Metodo Limpar Campos
    public void Limpar (View view){
        editTextNome.setText("");
        editTextCpf.setText("");
        editTextDataNascimento.setText("");
        spinnerUF.setSelection(0);

        //Diminuir os Campos de telefone para apenas 1
        numeroTelefone = 1;
        ApagarViewsTelefone();
    }

    //Metodo Criar View Telefone
    public void CriarViewTelefone(View view){

        // LinearLayout Horinzonal
        LinearLayout linearLayoutHorizontal = new LinearLayout(this);
        linearLayoutHorizontal.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        // Text View telefone
        TextView textViewTelefone = new TextView(this);
        textViewTelefone.setText("Telefone " + numeroTelefone);
        textViewTelefone.setTextSize(18);
        textViewTelefone.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayoutHorizontal.addView(textViewTelefone);


        // LinearLayout Horinzonal
        LinearLayout linearLayoutEspaco = new LinearLayout(this);
        linearLayoutEspaco.setLayoutParams(new LinearLayout.LayoutParams(80,
                LinearLayout.LayoutParams.MATCH_PARENT));
        linearLayoutHorizontal.addView(linearLayoutEspaco);

        //EditText telefone
        EditText editTextTelefone = new EditText(this);
        editTextTelefone.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
        editTextTelefone.setInputType(InputType.TYPE_CLASS_PHONE);
        editTextTelefone.setEms(10);
        linearLayoutHorizontal.addView(editTextTelefone);

        //adicionando o LinearLayout na lista
        listLinearLayout.add(linearLayoutHorizontal);
        //Adicionando a View na View Parent
        linearLayoutTelefones.addView(linearLayoutHorizontal);
        //controle numero de telefones
        numeroTelefone = numeroTelefone + 1;
    }


    //Apagar as Views da Lista telefone
    private void ApagarViewsTelefone (){
        linearLayoutTelefones.removeAllViews();
        listLinearLayout.clear();
    }


    //Pegar valores e inserir numa classe Clientes
    private Clientes CadastroCliente () throws ParseException {
        Clientes c = new Clientes();
        //Validando valores que estao vazios
        String nome = editTextNome.getText().toString();
        String cpf = editTextCpf.getText().toString();
        String dataNascimento = editTextDataNascimento.getText().toString();
        String uf = spinnerUF.getSelectedItem().toString();
        List<String> listaTelefones = MetodoTelefonesLista(listLinearLayout);
        c.nome = nome;
        c.cpf = cpf;
        c.dataNascimento = dataNascimento;
        c.uf = uf;
        c.telefones = listaTelefones;

        //Add data cadastro
        if(nome.equals("")){
            Toast.makeText(this, "Preencha o nome por favor", Toast.LENGTH_LONG).show();
            return null;
        }else {
            switch (uf) {

                //CPF Obrigatorio
                case "SP":
                    if (!isCPF(cpf)) {
                        Toast.makeText(this, "CPF Invalido", Toast.LENGTH_LONG).show();
                        return null;
                    } else if (cpf.equals("")) {
                        Toast.makeText(this, "UF: SP - CPF Obrigatório", Toast.LENGTH_LONG).show();
                        return null;
                    } else {
                    }

                    break;

                //validar MG > 18 anos
                case "MG":

                    if (isCPF(cpf) || cpf.equals("")) {
                        if (isMaiorde18(dataNascimento)) {

                        } else {
                            Toast.makeText(this, "UF: MG - Obrigatório maior de 18 anos", Toast.LENGTH_LONG).show();
                            return null;
                        }


                    }
                    break;

                default:
                    if (isCPF(cpf) || cpf.equals("")) {


                    } else {
                        Toast.makeText(this, "CPF INVÁLIDO CORRIJA OU DEIXE EM BRANCO", Toast.LENGTH_LONG).show();
                        return null;
                    }
                    break;
            }

        }


        return c;
    }

    public void Cadastrar (View view) throws ParseException {
        Clientes cliente = CadastroCliente();

        if(cliente != null){
            ContentResolver resolver = this.getContentResolver();
            ContentValues novoRegistro = new ContentValues();

            //Salvando o Cliente
            novoRegistro.put(TabelaSQLHelper.dadosSQL.COL_CLIENTES_NOME,cliente.nome);
            novoRegistro.put(TabelaSQLHelper.dadosSQL.COL_CLIENTES_CPF,cliente.cpf);
            novoRegistro.put(TabelaSQLHelper.dadosSQL.COL_CLIENTES_DATANASCIMENTO,cliente.dataNascimento);
            novoRegistro.put(TabelaSQLHelper.dadosSQL.COL_CLIENTES_DATACADASTRO,DataHoje());
            novoRegistro.put(TabelaSQLHelper.dadosSQL.COL_CLIENTES_UF,cliente.uf);
            resolver.insert(BDContentProvider.Provider.CLIENTES_URI,novoRegistro);


            //Salvando os Telefones
            int tamanhoLista = cliente.telefones.size();
            if(tamanhoLista > 0){
                for(int i=0; i<tamanhoLista;i++){
                ContentValues novoRegistroTelefone = new ContentValues();
                ContentResolver resolverTelefone  = this.getContentResolver();
                novoRegistroTelefone.put(TabelaSQLHelper.dadosSQL.COL_TELEFONES_TELEFONE,cliente.telefones.get(i));
                novoRegistroTelefone.put(TabelaSQLHelper.dadosSQL.COL_TELEFONES_IDCLIENTE, IDUltimoCliente());
                resolverTelefone.insert(BDContentProvider.Provider.TELEFONES_URI,novoRegistroTelefone);
                }
            }

            Toast.makeText(this,"Adicionado com sucesso!",Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(this,"Não Adicionado confira - Campos Inválidos",Toast.LENGTH_LONG).show();
        }
    }



    //Boolean para saber se é maior de 18 anos utilizando Joda-Time
    private Boolean isMaiorde18(String dataAniversario) throws ParseException {
        String dat1 = dataAniversario;
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = simpleDateFormat1.parse(dat1);
        Date currentTime = Calendar.getInstance().getTime();
        long startDate = date1.getTime();
        long endDate = currentTime.getTime();
        Period period = new Period(startDate,endDate, PeriodType.yearMonthDay());
        int years = period.getYears();
        if(years >= 18){
            return true;
        }else{
            return false;
        }

    }


    //Verificação CPF válido
    public static boolean isCPF(String CPF) {
        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (CPF.equals("00000000000") ||
                CPF.equals("11111111111") ||
                CPF.equals("22222222222") || CPF.equals("33333333333") ||
                CPF.equals("44444444444") || CPF.equals("55555555555") ||
                CPF.equals("66666666666") || CPF.equals("77777777777") ||
                CPF.equals("88888888888") || CPF.equals("99999999999") ||
                (CPF.length() != 11))
            return(false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere '0' no inteiro 0
                // (48 eh a posicao de '0' na tabela ASCII)
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48); // converte no respectivo caractere numerico

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char)(r + 48);

            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                return(true);
            else return(false);
        } catch (InputMismatchException erro) {
            return(false);
        }
    }

    //Pegando todos Telefones e adicionando em uma lista que será usada para ADD no Banco de Dados
    private List<String> MetodoTelefonesLista(List<LinearLayout> listLinearLayout){
        List<String> retorno = new ArrayList<>();
        for(int i=0; i < listLinearLayout.size();i++){
            EditText edt = (EditText)listLinearLayout.get(i).getChildAt(2);
            String telefone = edt.getText().toString();
            retorno.add(telefone);
        }
        return retorno;
    }

    public void MainActivity2 (View View){
        Intent intent = new Intent(this,MainActivity2.class);
        startActivity(intent);
    }

    private int IDUltimoCliente(){
        ContentResolver resolver = this.getContentResolver();
        Cursor cursor = resolver.query(BDContentProvider.Provider.CLIENTES_URI,TabelaSQLHelper.dadosSQL.LISTA_COLUNAS_CLIENTES,null,null,null);

        if(cursor != null){
            cursor.moveToLast();
            return cursor.getInt(0);
        }else{
            return 0;
        }

    }

    private String DataHoje(){
        Date data = new Date();
        SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");
        String dataFormatada = formataData.format(data);
        return dataFormatada;
    }
}