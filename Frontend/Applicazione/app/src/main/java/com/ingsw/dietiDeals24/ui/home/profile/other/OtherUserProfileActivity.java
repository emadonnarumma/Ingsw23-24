package com.ingsw.dietiDeals24.ui.home.profile.other;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.model.User;


public class OtherUserProfileActivity extends AppCompatActivity {
    private TextView usernameTextView;
    private TextView userBioTextView;
    private ImageView iconLinkImageView;
    private TextView linkTextView;
    private TextView andNMoreLinksTextView;
    private TextView userRegionTextView;
    private ProgressBar progressBar;
    private User user;

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
        iconLinkImageView = findViewById(R.id.icon_link_other_user_profile);
        linkTextView = findViewById(R.id.link_other_user_profile);
        andNMoreLinksTextView = findViewById(R.id.and_n_more_links_other_user_profile);
        userRegionTextView = findViewById(R.id.user_region_other_user_profile);
        progressBar = findViewById(R.id.progress_bar_other_user_profile);
        setupActionBar();
        showUserData();
    }

    private void setupActionBar() {
        setSupportActionBar(findViewById(R.id.toolbar_other_user_profile));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void showUserData() {
        if (user == null)
            return;

        String username = user.getName();
        String userBio = user.getBio();
        String userRegion = " " + user.getRegion().toString() + " ";
        usernameTextView.setText(username);
        userBioTextView.setText(userBio);
        userRegionTextView.setText(userRegion);

        if (user.hasExternalLinks()) {
            String link = user.getExternalLinks().get(0).getTitle();
            String andNMoreLinks = "and " + (user.getExternalLinks().size() - 1) + " more";
            linkTextView.setText(link);
            andNMoreLinksTextView.setText(andNMoreLinks);
        } else {
            iconLinkImageView.setVisibility(View.GONE);
        }
    }
}
