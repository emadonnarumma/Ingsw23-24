package com.ingsw.dietiDeals24.ui.home.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.ProfileController;
import com.ingsw.dietiDeals24.ui.utility.recyclerViews.externalLinks.ExternalLinksAdapter;

public class EditExternalLinksFragment extends Fragment {
    ConstraintLayout addExternalLinkButton;
    RecyclerView externalLinksRecyclerView;
    ImageView doneButton;
    ExternalLinksAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_external_links, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBackButtonEnabled(true);

        addExternalLinkButton = view.findViewById(R.id.add_external_link_button_edit_external_links);
        externalLinksRecyclerView = view.findViewById(R.id.external_links_recycler_view_edit_external_links);
        doneButton = view.findViewById(R.id.done_button_edit_external_links);

        initRecyclerView();
        //addExternalLinkButton
        doneButton.setOnClickListener(v -> goToEditProfileFragment());
    }

     private void setBackButtonEnabled(boolean enabled) {
        if (getActivity() instanceof AppCompatActivity) {
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(enabled);
            }
        }
    }

    private void initRecyclerView() {
        externalLinksRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        adapter = new ExternalLinksAdapter(ProfileController.getUser().getExternalLinks(), requireActivity());
        externalLinksRecyclerView.setAdapter(adapter);
    }

    private void goToEditProfileFragment() {
        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container_home,
                new EditProfileFragment()).commit();
    }
}
