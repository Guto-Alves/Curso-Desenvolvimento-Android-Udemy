package com.gutotech.whatsapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gutotech.whatsapp.R;

public class ConversasFragment extends Fragment {
    private RecyclerView recyclerConversas;

    public ConversasFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conversas, container, false);

        //recyclerConversas = view.findViewById(R.id.)
        /*RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerConversas.setLayoutManager(layoutManager);
        recyclerConversas.setHasFixedSize(true);
        //recyclerConversas.setAdapter();
*/
        return view;
    }
}
