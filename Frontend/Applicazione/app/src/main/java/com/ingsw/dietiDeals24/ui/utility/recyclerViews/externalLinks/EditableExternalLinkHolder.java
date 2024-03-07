package com.ingsw.dietiDeals24.ui.utility.recyclerViews.externalLinks;

import static java.lang.Thread.sleep;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.ProfileController;
import com.ingsw.dietiDeals24.model.ExternalLink;
import com.ingsw.dietiDeals24.ui.home.profile.my.EditExternalLinkFragment;
import com.ingsw.dietiDeals24.ui.home.profile.my.EditExternalLinksFragment;
import com.ingsw.dietiDeals24.ui.utility.PopupGeneratorOf;
import com.ingsw.dietiDeals24.ui.utility.ToastManager;

import java.util.concurrent.ExecutionException;

public class EditableExternalLinkHolder extends RecyclerView.ViewHolder {
    private TextView titleTextView;
    private TextView urlTextView;
    private ImageButton editButton;
    private ImageButton deleteButton;

    public EditableExternalLinkHolder(@NonNull View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.title_of_editable_external_link);
        urlTextView = itemView.findViewById(R.id.url_of_editable_external_link);
        editButton = itemView.findViewById(R.id.edit_external_link);
        deleteButton = itemView.findViewById(R.id.delete_external_link);
    }

    public void bind(Fragment fragment, ExternalLink externalLink) {
        titleTextView.setText(externalLink.getTitle());
        urlTextView.setText(externalLink.getUrl());
        editButton.setOnClickListener(v -> editLink(fragment, externalLink));
        deleteButton.setOnClickListener(v -> PopupGeneratorOf.areYouSureToDeleteLinkPopup(fragment, externalLink));
    }

    private void editLink(Fragment fragment, ExternalLink externalLink) {
        ProfileController.setSelectedLink(externalLink);
        fragment.getParentFragmentManager().beginTransaction().replace(R.id.fragment_container_home,
                new EditExternalLinkFragment()).commit();
    }
}
