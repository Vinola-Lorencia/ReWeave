package com.example.reweave.ui.News;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.reweave.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NewsFragment extends Fragment {

    private NewsViewModel mViewModel;
    TextView txttgl;

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_news, container, false);

        txttgl = root.findViewById(R.id.texttgl);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE,dd MMMM yyyy", Locale.ENGLISH);
        String currentDate = sdf.format(new Date());

        txttgl.setText(currentDate);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
    }
}
