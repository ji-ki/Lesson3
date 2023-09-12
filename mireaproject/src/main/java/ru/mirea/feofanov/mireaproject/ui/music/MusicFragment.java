package ru.mirea.feofanov.mireaproject.ui.music;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.mirea.feofanov.mireaproject.R;
import ru.mirea.feofanov.mireaproject.databinding.ActivityMainBinding;
import ru.mirea.feofanov.mireaproject.databinding.FragmentBrouserBinding;
import ru.mirea.feofanov.mireaproject.databinding.FragmentMusicBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MusicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MusicFragment extends Fragment {
    FragmentMusicBinding binding;
    private	int	PermissionCode	=	200;
    private boolean play = false;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MusicFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MusicFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MusicFragment newInstance(String param1, String param2) {
        MusicFragment fragment = new MusicFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMusicBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (play == false) {
                    play = true;
                    binding.imageView.setImageResource(R.drawable.tag_team_we_are_the_champions);
                    binding.song.setText("We are the champions");
                    binding.btnPlay.setImageResource(com.google.android.material.R.drawable.btn_radio_off_mtrl);
                    Intent serviceIntent = new Intent(getActivity(), PlayService.class);
                    ContextCompat.startForegroundService(getActivity(), serviceIntent);
                }
                else {
                    binding.imageView.setImageResource(R.color.md_theme_light_onSecondaryContainer);
                    binding.song.setText("Song");
                    getActivity().stopService(new Intent(getActivity(), PlayService.class));
                    binding.btnPlay.setImageResource(com.google.android.material.R.drawable.btn_radio_on_mtrl);
                    play = false;
                }
            }
        });

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        binding.btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return root;
    }
}