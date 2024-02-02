package com.ingsw.dietiDeals24.ui.utility.auctionHolder;

import android.content.Context;
import android.net.Uri;
import android.util.Base64;

import com.ingsw.dietiDeals24.model.Image;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ImageConverter {

    private static String convertUriToBase64String(Context context, Uri uri) throws IOException {
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;

        while ((length = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, length);
        }

        byte[] data = outputStream.toByteArray();
        return Base64.encodeToString(data, Base64.DEFAULT);
    }

    public static List<Image> convertUriListToImageList(Context context, List<Uri> uriList) throws IOException {
        List<Image> imageList = new ArrayList<>();
        for (Uri uri : uriList) {
            String base64Data = convertUriToBase64String(context, uri);
            Image image = new Image(null, base64Data, null);
            imageList.add(image);
        }
        return imageList;
    }
}