package com.ingsw.dietiDeals24.ui.utility;

import static java.lang.Thread.sleep;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.ProfileController;
import com.ingsw.dietiDeals24.model.ExternalLink;
import com.ingsw.dietiDeals24.ui.home.HomeActivity;
import com.ingsw.dietiDeals24.ui.home.myAuctions.MyAuctionsFragment;
import com.ingsw.dietiDeals24.ui.home.profile.my.EditExternalLinksFragment;
import com.ingsw.dietiDeals24.ui.login.LoginActivity;
import com.saadahmedsoft.popupdialog.PopupDialog;
import com.saadahmedsoft.popupdialog.Styles;
import com.saadahmedsoft.popupdialog.listener.OnDialogButtonClickListener;

import java.util.concurrent.ExecutionException;

public class PopupGeneratorOf {

    public static PopupDialog loadingPopup(Context context) {
        PopupDialog loadingPopup = PopupDialog.getInstance(context);
        loadingPopup.setStyle(Styles.PROGRESS)
                .setProgressDialogTint(context.getColor(R.color.green))
                .setCancelable(false)
                .showDialog();
        return loadingPopup;
    }

    public static void errorPopup(Context context, String message) {
        PopupDialog.getInstance(context)
                .setStyle(Styles.FAILED)
                .setHeading("Riprova!")
                .setDescription(message)
                .setCancelable(false)
                .setDismissButtonText("Chiudi")
                .showDialog(new OnDialogButtonClickListener() {
                    @Override
                    public void onDismissClicked(Dialog dialog) {
                        super.onDismissClicked(dialog);
                    }
                });
    }

    public static void successPopup(Context context, String message) {
        PopupDialog.getInstance(context)
                .setStyle(Styles.SUCCESS)
                .setHeading("Finito!")
                .setDescription(message)
                .setCancelable(false)
                .setDismissButtonText("Chiudi")
                .showDialog(new OnDialogButtonClickListener() {
                    @Override
                    public void onDismissClicked(Dialog dialog) {
                        super.onDismissClicked(dialog);
                    }
                });
    }

    public static void successAuctionCreationPopup(HomeActivity homeActivity) {
        PopupDialog.getInstance(homeActivity)
                .setStyle(Styles.SUCCESS)
                .setHeading("Finito!")
                .setDescription("Asta creata con successo!")
                .setCancelable(false)
                .setDismissButtonText("Chiudi")
                .showDialog(new OnDialogButtonClickListener() {
                    @Override
                    public void onDismissClicked(Dialog dialog) {
                        super.onDismissClicked(dialog);
                        homeActivity.getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container_home, new MyAuctionsFragment())
                                .commit();

                        homeActivity.getNavigationBarView().setSelectedItemId(R.id.navigation_my_auctions);
                    }
                });
    }

    public static void bidSendedSuccessfullyPopup(Context context) {
        PopupDialog.getInstance(context)
                .setStyle(Styles.SUCCESS)
                .setHeading("Offerta inviata!")
                .setDescription("La tua offerta è stata inviata con successo.")
                .setPopupDialogIcon(R.drawable.ic_check_circle)
                .setPopupDialogIconTint(R.color.green)
                .setCancelable(false)
                .setDismissButtonText("OK")
                .showDialog(new OnDialogButtonClickListener() {
                    @Override
                    public void onDismissClicked(Dialog dialog) {
                        super.onDismissClicked(dialog);
                        OnNavigateToHomeActivityFragmentListener.navigateTo("MyBidsFragment", context);
                    }
                });
    }

    public static void areYouSureToLogoutPopup(Context context) {
        PopupDialog.getInstance(context)
                .setStyle(Styles.STANDARD)
                .setHeading("Disconnettersi?")
                .setDescription("Sei sicuro di volerti disconnettere? Dovrai effettuare nuovamente l'accesso.")
                .setPopupDialogIcon(R.drawable.ic_logout)
                .setPopupDialogIconTint(R.color.red)
                .setCancelable(false)
                .setPositiveButtonText("Conferma")
                .setNegativeButtonText("Annulla")
                .showDialog(new OnDialogButtonClickListener() {
                    @Override
                    public void onPositiveClicked(Dialog dialog) {
                        super.onPositiveClicked(dialog);
                        logout(context);
                    }

                    @Override
                    public void onNegativeClicked(Dialog dialog) {
                        super.onNegativeClicked(dialog);
                    }
                });
    }

    private static void logout(Context context) {
        ProfileController.logout();
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void areYouSureToDeleteLinkPopup(Fragment fragment, ExternalLink externalLink) {
        PopupDialog.getInstance(fragment.getContext())
                .setStyle(Styles.STANDARD)
                .setHeading("Cancellare il link?")
                .setDescription("Sei sicuro di voler cancellare il link? Non potrai recuperarlo.")
                .setPopupDialogIcon(R.drawable.ic_delete_forever)
                .setPopupDialogIconTint(R.color.red)
                .setCancelable(false)
                .setPositiveButtonText("Conferma")
                .setNegativeButtonText("Annulla")
                .showDialog(new OnDialogButtonClickListener() {
                    @Override
                    public void onPositiveClicked(Dialog dialog) {
                        super.onPositiveClicked(dialog);
                        deleteLink(fragment, externalLink);
                    }

                    @Override
                    public void onNegativeClicked(Dialog dialog) {
                        super.onNegativeClicked(dialog);
                    }
                });
    }

    private static void deleteLink(Fragment fragment, ExternalLink externalLink) {
        PopupDialog loading = PopupGeneratorOf.loadingPopup(fragment.getContext());
        new Thread(() -> {
            try {
                ProfileController.deleteLink(externalLink).get();
                sleep(500);
                fragment.requireActivity().runOnUiThread(() -> ToastManager.showToast(fragment.getContext(), fragment.getString(R.string.link_deleted)));
                fragment.getParentFragmentManager().beginTransaction().replace(R.id.fragment_container_home,
                        new EditExternalLinksFragment()).commit();
            } catch (ExecutionException e) {
                fragment.requireActivity().runOnUiThread(() -> PopupGeneratorOf.errorPopup(fragment.getContext(), e.getCause().getMessage()));
            } catch (InterruptedException e) {
                fragment.requireActivity().runOnUiThread(() -> PopupGeneratorOf.errorPopup(fragment.getContext(), "Operazione interrotta, riprovare"));
            } finally {
                fragment.requireActivity().runOnUiThread(loading::dismissDialog);
            }
        }).start();
    }


    public static void areYouSureToDeleteAuctionPopup(Context context) {
        PopupDialog.getInstance(context)
                .setStyle(Styles.STANDARD)
                .setHeading("Cancellare l'asta?")
                .setDescription("Sei sicuro di voler cancellare l'asta? Questa operazione non è reversibile.")
                .setPopupDialogIcon(R.drawable.ic_delete_forever)
                .setPopupDialogIconTint(R.color.red)
                .setCancelable(false)
                .setPositiveButtonText("Conferma")
                .setNegativeButtonText("Annulla")
                .showDialog(new OnDialogButtonClickListener() {
                    @Override
                    public void onPositiveClicked(Dialog dialog) {
                        super.onPositiveClicked(dialog);
                        //TODO: delete auction
                    }

                    @Override
                    public void onNegativeClicked(Dialog dialog) {
                        super.onNegativeClicked(dialog);
                    }
                });
    }


    public static void areYouSureToDeleteBidPopup(Context context) {
        PopupDialog.getInstance(context)
                .setStyle(Styles.STANDARD)
                .setHeading("Cancellare l'offerta?")
                .setDescription("Sei sicuro di voler cancellare l'offerta? Non potrai più recuperarla.")
                .setPopupDialogIcon(R.drawable.ic_delete_forever)
                .setPopupDialogIconTint(R.color.red)
                .setCancelable(false)
                .setPositiveButtonText("Conferma")
                .setNegativeButtonText("Annulla")
                .showDialog(new OnDialogButtonClickListener() {
                    @Override
                    public void onPositiveClicked(Dialog dialog) {
                        super.onPositiveClicked(dialog);
                        //TODO: delete bid
                    }

                    @Override
                    public void onNegativeClicked(Dialog dialog) {
                        super.onNegativeClicked(dialog);
                    }
                });
    }


    public static void areYouSureToAcceptBidPopup(Context context) {
        PopupDialog.getInstance(context)
                .setStyle(Styles.STANDARD)
                .setHeading("Accettare l'offerta?")
                .setDescription("Sei sicuro di voler accettare l'offerta? L'asta finirà e non potrai più ricevere altre offerte.")
                .setPopupDialogIcon(R.drawable.ic_check_circle)
                .setPopupDialogIconTint(R.color.green)
                .setCancelable(false)
                .setPositiveButtonText("Conferma")
                .setNegativeButtonText("Annulla")
                .showDialog(new OnDialogButtonClickListener() {
                    @Override
                    public void onPositiveClicked(Dialog dialog) {
                        super.onPositiveClicked(dialog);
                        //TODO: accept bid
                    }

                    @Override
                    public void onNegativeClicked(Dialog dialog) {
                        super.onNegativeClicked(dialog);
                    }
                });
    }
}
