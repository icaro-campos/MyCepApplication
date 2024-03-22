package br.itcampos.mycepapplication;

import static br.itcampos.mycepapplication.utils.CepValidator.isValid;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import br.itcampos.mycepapplication.model.ViaCepResponse;
import br.itcampos.mycepapplication.model.data.entity.CepEntity;
import br.itcampos.mycepapplication.viewmodel.CepViewModel;

public class MainActivity extends AppCompatActivity {

    private EditText cepEditText;
    private TextView resultTextView;
    private Button searchButton;
    private CepViewModel viewModel;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cepEditText = findViewById(R.id.edit_text_cep);
        resultTextView = findViewById(R.id.text_view_result);
        searchButton = findViewById(R.id.button_search);
        progressBar = findViewById(R.id.progress_bar);

        viewModel = new ViewModelProvider(this).get(CepViewModel.class);

        searchButton.setOnClickListener(view -> {
            String cep = cepEditText.getText().toString();
            if (isValid(cep)) {
                searchCep(cep);
            } else {
                Toast.makeText(MainActivity.this, R.string.invalid_cep, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchCep(String cep) {
        progressBar.setVisibility(View.VISIBLE);
        searchButton.setEnabled(false);
        viewModel.getCepDetailsFromLocal(cep).observe(this, cepEntity -> {
            if (cepEntity != null) {
                displayCepDetails(cepEntity);
                progressBar.setVisibility(View.GONE);
                searchButton.setEnabled(true);
                Toast.makeText(this, R.string.cep_success_locally, Toast.LENGTH_SHORT).show();
            } else {
                viewModel.getCepDetailsFromRemote(cep).observe(this, viaCep -> {
                    progressBar.setVisibility(View.GONE);
                    searchButton.setEnabled(false);
                    if (viaCep != null) {
                        if (viaCep.getSource() != null) {
                            viewModel.insertCepDetails(viaCep);
                            switch (viaCep.getSource()) {
                                case "ViaCep":
                                    Toast.makeText(this, R.string.cep_details_viacep, Toast.LENGTH_SHORT).show();
                                    break;
                                case "ApiCep":
                                    Toast.makeText(this, R.string.cep_details_apicep, Toast.LENGTH_SHORT).show();
                                    break;
                                case "AwesomeApi":
                                    Toast.makeText(this, R.string.cep_details_awesomeapi, Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                        displayCepDetails(viaCep);
                    } else {
                        Toast.makeText(this, R.string.details_cep_error, Toast.LENGTH_SHORT).show();
                    }
                    searchButton.setEnabled(true);
                });
            }
        });
    }

    private void displayCepDetails(ViaCepResponse viaCep) {
        String result = "CEP: " + viaCep.getCep() + "\n" +
                "Logradouro: " + viaCep.getAddress() + "\n" +
                "Complemento: " + viaCep.getComplement() + "\n" +
                "Bairro: " + viaCep.getNeighborhood() + "\n" +
                "Cidade: " + viaCep.getCity() + "\n" +
                "Estado: " + viaCep.getState();
        resultTextView.setText(result);
    }

    private void displayCepDetails(CepEntity cepEntity) {
        String result = "CEP: " + cepEntity.getCep() + "\n" +
                "Logradouro: " + cepEntity.getAddress() + "\n" +
                "Complemento: " + cepEntity.getComplement() + "\n" +
                "Bairro: " + cepEntity.getNeighborhood() + "\n" +
                "Cidade: " + cepEntity.getCity() + "\n" +
                "Estado: " + cepEntity.getState();
        resultTextView.setText(result);
    }
}
