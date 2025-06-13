package com.alana.applistacursos.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.alana.applistacursos.R;
import com.alana.applistacursos.controller.CursoController;
import com.alana.applistacursos.controller.PessoaController;
import com.alana.applistacursos.model.Curso;
import com.alana.applistacursos.model.Pessoa;

public class MainActivity extends AppCompatActivity {
    private EditText editNome, editSobrenome, editTelefone;
    private Button btnSalvar, btnLimpar, btnFinalizar;
    private PessoaController pessoaController;
    private CursoController cursoController;
    private Spinner spinner;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.lista_cursos, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCourse = parent.getItemAtPosition(position).toString();
                Toast.makeText(MainActivity.this, "Selecionado: " + selectedCourse, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        pessoaController = new PessoaController(this);
        cursoController = new CursoController(this);
        editNome = findViewById(R.id.editTextText);
        editSobrenome = findViewById(R.id.editTextText2);
        editTelefone = findViewById(R.id.editTextText4);
        btnSalvar = findViewById(R.id.salvar);
        btnLimpar = findViewById(R.id.limpar);
        btnFinalizar = findViewById(R.id.finalizar);
        btnSalvar.setOnClickListener(v -> {
            Pessoa pessoa = new Pessoa(editNome.getText().toString(), editSobrenome.getText().toString(), editTelefone.getText().toString());
            String cursoSelecionado = spinner.getSelectedItem().toString();
            Curso curso = new Curso(cursoSelecionado);
            pessoaController.salvarPessoa(pessoa);
            cursoController.salvarCurso(curso);
            Toast.makeText(this, pessoa.toString(), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, curso.toString(), Toast.LENGTH_SHORT).show();
        });
        btnLimpar.setOnClickListener(v -> {
            editNome.setText("");
            editSobrenome.setText("");
            editTelefone.setText("");
            spinner.setSelection(0);
            cursoController.apagarCurso();
            pessoaController.apagarPessoa();
            Toast.makeText(this, "Campos e dados limpos", Toast.LENGTH_SHORT).show();
        });
        btnFinalizar.setOnClickListener(v -> {
            Toast.makeText(this, "Volte sempre!", Toast.LENGTH_SHORT).show();
            finish();
        });
        carregarDadosSalvos();
    }

    private void carregarDadosSalvos() {
        Curso curso = cursoController.carregarCurso();
        if (curso != null) {
            String nomeCurso = curso.getNomeCurso();
            ArrayAdapter adapter = (ArrayAdapter) spinner.getAdapter();
            int posicao = adapter.getPosition(nomeCurso);
            if (posicao >= 0) {
                spinner.setSelection(posicao);
            }
        }
        Pessoa pessoa = pessoaController.carregarPessoa();
        editNome.setText(pessoa.getNome());
        editSobrenome.setText(pessoa.getSobrenome());
        editTelefone.setText(pessoa.getTelefone());
    }
}