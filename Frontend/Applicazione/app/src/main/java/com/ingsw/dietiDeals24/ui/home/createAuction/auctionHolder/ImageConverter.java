package com.ingsw.dietiDeals24.ui.home.createAuction.auctionHolder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import com.ingsw.dietiDeals24.model.Image;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ImageConverter {

    private static String convertUriToBase64String(Context context, Uri uri) throws IOException {
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        Bitmap compressedBitmap = compressBitmap(bitmap, 1);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        compressedBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        byte[] data = outputStream.toByteArray();
        return Base64.encodeToString(data, Base64.DEFAULT);
    }

    private static Bitmap compressBitmap(Bitmap original, int quality) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        original.compress(Bitmap.CompressFormat.JPEG, quality, out);
        return BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
    }

    public static List<Image> convertUriListToImageList(Context context, List<Uri> uriList) throws IOException {
        List<Image> imageList = new ArrayList<>();
        for (Uri uri : uriList) {
            String base64Data = convertUriToBase64String(context, uri);
            Image image = new Image(null, base64Data);
            imageList.add(image);
        }
        return imageList;
    }
}