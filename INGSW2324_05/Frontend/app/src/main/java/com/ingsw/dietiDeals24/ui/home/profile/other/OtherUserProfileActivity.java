package com.ingsw.dietiDeals24.ui.home.profile.other;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.SearchAuctionsController;
import com.ingsw.dietiDeals24.model.DownwardAuction;
import com.ingsw.dietiDeals24.model.ReverseAuction;
import com.ingsw.dietiDeals24.model.SilentAuction;
import com.ingsw.dietiDeals24.model.User;
import com.ingsw.dietiDeals24.model.enumeration.Role;
import com.ingsw.dietiDeals24.ui.CheckConnectionActivity;
import com.ingsw.dietiDeals24.ui.recyclerViews.externalLinks.ExternalLinksAdapter;
import com.ingsw.dietiDeals24.ui.recyclerViews.searchAuctions.SearchAuctionAdapter;
import com.ingsw.dietiDeals24.utility.ToastManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class OtherUserProfileActivity extends CheckConnectionActivity {
    private TextView usernameTextView;
    private TextView userBioTextView;
    private ImageView linkIconImageView;
    private TextView linkTitleTextView;
    private TextView linkUrlTextView;
    private TextView andNMoreLinksTextView;
    private TextView userRegionTextView;
    private ProgressBar progressBar;
    private TextView roleTextView;
    private User user;
    private RecyclerView recyclerView;

    private BottomSheetDialog bottomSheetDialog;

    public OtherUserProfileActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_profile);
        user = (User) getIntent().getSerializableExtra("otherUser");
    }

    @Override
    protected void onResume() {
        super.onResume();
        usernameTextView = findViewById(R.id.username_text_other_user_profile);
        userBioTextView = findViewById(R.id.user_bio_other_user_profile);
        roleTextView = findViewById(R.id.role_text_other_user_profile);
        linkIconImageView = findViewById(R.id.link_icon_other_user_profile);
        linkTitleTextView = findViewById(R.id.link_title_other_user_profile);
        linkUrlTextView = findViewById(R.id.link_url_other_user_profile);
        andNMoreLinksTextView = findViewById(R.id.and_n_more_links_other_user_profile);
        userRegionTextView = findViewById(R.id.user_region_other_user_profile);
        progressBar = findViewById(R.id.progress_bar_other_user_profile);
        recyclerView = findViewById(R.id.recycler_view_other_user_profile);
        setupActionBar();
        showUserData();
        updateAuctions();

        setupBottomSheetDialog();
        andNMoreLinksTextView.setOnClickListener(v -> bottomSheetDialog.show());
    }

    private void updateAuctions() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        new Thread(() -> {
            try {
                List<SilentAuction> silentAuctions = SearchAuctionsController.getInProgressOtherUserSilentAuctions(user.getEmail()).get();
                List<DownwardAuction> downwardAuctions = SearchAuctionsController.getInProgressOtherUserDownwardAuctions(user.getEmail()).get();
                List<ReverseAuction> reverseAuctions = SearchAuctionsController.getInProgressOtherUserReverseAuctions(user.getEmail()).get();

                runOnUiThread(() -> {
                    recyclerView.setAdapter(new SearchAuctionAdapter(silentAuctions, downwardAuctions, reverseAuctions));
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                });
            } catch (ExecutionException e) {
                runOnUiThread(() -> {
                    recyclerView.setAdapter(new SearchAuctionAdapter(new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
                    ToastManager.showToast(this, e.getCause().getMessage());
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                });
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }


    private void setupActionBar() {
        setSupportActionBar(findViewById(R.id.toolbar_other_user_profile));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void showUserData() {
        if (user == null)
            return;

        roleTextView.setText(Role.toItalianString(user.getRole()));

        String username = user.getName();
        String userBio = user.getBio();
        String userRegion = " " + user.getRegion().toString() + " ";
        usernameTextView.setText(username);
        userBioTextView.setText(userBio);
        userRegionTextView.setText(userRegion);

        if (user.hasExternalLinks()) {
            String linkTitle = user.getExternalLinks().get(0).getTitle();

            int numberOfLinks = user.getExternalLinks().size();
            if (numberOfLinks == 1) {
                linkTitle = linkTitle + ": ";
                andNMoreLinksTextView.setVisibility(View.GONE);
                String linkUrl = user.getExternalLinks().get(0).getUrl();
                linkUrlTextView.setText(linkUrl);
            } else {
                linkUrlTextView.setVisibility(View.GONE);
                String andNMoreLinks = " e altri " + (numberOfLinks - 1);
                if(numberOfLinks == 2)
                    andNMoreLinks = " e un altro";
                andNMoreLinksTextView.setText(andNMoreLinks);
            }

            linkTitleTextView.setText(linkTitle);
        } else {
            linkIconImageView.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupBottomSheetDialog() {
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_layout);

        RecyclerView recyclerView = bottomSheetDialog.findViewById(R.id.recycler_view_bottom_sheet);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ExternalLinksAdapter adapter = new ExternalLinksAdapter(user.getExternalLinks());
        recyclerView.setAdapter(adapter);
    }
}