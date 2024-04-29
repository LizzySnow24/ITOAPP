package com.example.itoapp;

import android.view.ScaleGestureDetector;
import android.widget.ImageView;

public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
    private ImageView imageView;
    private float scaleFactor = 1f;

    public ScaleListener(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        scaleFactor *= detector.getScaleFactor();

        // Restringir el escalamiento dentro de ciertos límites
        scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 10.0f));

        // Aplicar el escalamiento a la imagen
        imageView.setScaleX(scaleFactor);
        imageView.setScaleY(scaleFactor);

        return true;
    }
}

