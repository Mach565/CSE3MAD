package com.example.deckadence;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.deckadence.deck.Deck;
import com.example.deckadence.deck.DeckAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DecksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DecksFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;
    private DeckAdapter adapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference deckRef = db.collection("Decks");

    public DecksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DecksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DecksFragment newInstance(String param1, String param2) {
        DecksFragment fragment = new DecksFragment();
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
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_decks, container, false);
        setUpRecyclerView();
        return view;
    }

    private void setUpRecyclerView() {
        Query query = db.collection("Decks").orderBy("Title", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Deck> options = new FirestoreRecyclerOptions.Builder<Deck>()
                .setQuery(query, Deck.class)
                .build();
        adapter = new DeckAdapter(options);
        RecyclerView recyclerView = view.findViewById(R.id.rec_view);
        // fragments don't work as context, go figure
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
        Log.d("deck","listening for changes");
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}