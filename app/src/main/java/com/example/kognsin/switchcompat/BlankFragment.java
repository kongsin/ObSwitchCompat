package com.example.kognsin.switchcompat;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {

    public String p;

    public BlankFragment() {
        // Required empty public constructor
    }

    public static BlankFragment newInstance(String pageNumber) {

        Bundle args = new Bundle();
        args.putString("P", pageNumber);
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        TextView textView = (TextView) view.findViewById(R.id.tv);
        textView.setText(p);
        view.setBackgroundColor(colorRandomed());
        return view;
    }

    private int colorRandomed(){
        int[] colors = new int[] {Color.RED, Color.GRAY, Color.GREEN, Color.BLUE, Color.YELLOW, Color.MAGENTA};
        return colors[Integer.parseInt(p.split("\\.")[1])-1];
    }

}
