package com.example.reweave;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class IntroFragment extends Fragment {
    private static final String ARG_POS = "pos";

    public static IntroFragment newInstance(int pos) {
        IntroFragment f = new IntroFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POS, pos);
        f.setArguments(args);
        return f;
    }

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        int pos = requireArguments().getInt(ARG_POS, 0);
        int layout = (pos == 0) ? R.layout.fragment_intro1 : R.layout.fragment_intro2;
        return inflater.inflate(layout, container, false);
    }
}
