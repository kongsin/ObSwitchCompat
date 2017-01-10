package com.example.kognsin.switchcompat;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {

    public String p;
    private int img;

    public BlankFragment() {
        // Required empty public constructor
    }

    public static BlankFragment newInstance(String pageNumber, int img) {

        Bundle args = new Bundle();
        args.putString("P", pageNumber);
        args.putInt("I", img);
        BlankFragment fragment = new BlankFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public BlankFragment setPage(String p){
        this.p = p;
        return this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            setPage(getArguments().getString("P"));
            img = getArguments().getInt("I");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.img_view);
        imageView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        imageView.setImageResource(img);
        return view;
    }
}
