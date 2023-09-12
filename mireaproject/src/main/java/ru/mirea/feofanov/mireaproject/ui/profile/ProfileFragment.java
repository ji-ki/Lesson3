package ru.mirea.feofanov.mireaproject.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.mirea.feofanov.mireaproject.R;
import ru.mirea.feofanov.mireaproject.databinding.FragmentProfileBinding;


public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SharedPreferences sharedPref = getActivity().getSharedPreferences("profile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        binding.btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.putString("FIO", binding.getname.getText().toString());
                editor.putString("DATE", binding.getdate.getText().toString());
                editor.putString("GROUP", binding.getgroup.getText().toString());

                editor.apply();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}