package app.task.ui.controller.fragments.numbers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import app.task.R;

public class NumbersFragment extends Fragment {

    private int[] numbers = {1,2,4,6,8,12,13,16,20,22,28,34,38,45,56,58,60,61,62,70,71,72,74,75,80,85,87,90,99,100};

    private EditText search;
    private Button searchButton;
    private TextView result;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_numbers, container, false);

        initUI(view);

        return view;
    }

    private void initUI(View view) {
        search = view.findViewById(R.id.search);
        searchButton = view.findViewById(R.id.search_button);
        result = view.findViewById(R.id.result);

        setOnClickSearchButton();
    }

    private void setOnClickSearchButton() {
        searchButton.setOnClickListener(view -> findEnteredNumber());
    }

    private void findEnteredNumber() {
        try {
            int enteredNumber = Integer.parseInt(search.getText().toString().trim());
            for (int i = 0; i < numbers.length; i++) {
                if (numbers[i] == enteredNumber) {
                    result.setText(getString(R.string.find_number_result_text, enteredNumber, (i + 1)));
                    return;
                }
            }
            result.setText(R.string.number_not_found);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), R.string.enter_valid_number, Toast.LENGTH_LONG).show();
        }
    }
}