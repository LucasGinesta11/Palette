package com.example.palette;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.transition.ChangeImageTransform;
import android.transition.Fade;
import android.transition.Slide;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;

public class ImagePalette extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configurar transiciones animadas
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Slide());
        getWindow().setSharedElementEnterTransition(new ChangeImageTransform());

        // Configurar el contenido de la actividad
        setContentView(R.layout.activity_image_palette);

        // Obtener la imagen seleccionada del Intent
        int selectedImage = getIntent().getIntExtra("image_resource", 0);

        // Configurar la imagen en el ImageView
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageResource(selectedImage);

        // Generar colores con Palette y aplicarlos
        applyPaletteColors(selectedImage);
    }

    /**
     * Genera colores a partir de la imagen y los aplica a las vistas.
     *
     * @param imageResource Recurso de la imagen seleccionada.
     */
    private void applyPaletteColors(int imageResource) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageResource);

        // Genera la paleta a partir del bitmap
        Palette.from(bitmap).generate(palette -> {
            if (palette != null) {
                // Aplicar los colores generados a las vistas correspondientes
                applyColorToView(R.id.imgToolbar, palette.getVibrantColor(0xFF000000));
                applyColorToView(R.id.toolbar, palette.getDarkVibrantColor(0xFF000000));
                applyColorToView(R.id.lightVibrant, palette.getLightVibrantColor(0xFF000000));
                applyColorToView(R.id.muted, palette.getMutedColor(0xFF000000));
                applyColorToView(R.id.darkMuted, palette.getDarkMutedColor(0xFF000000));
                applyColorToView(R.id.lightMuted, palette.getLightMutedColor(0xFF000000));
            }
        });
    }

    /**
     * Aplica un color de fondo a una vista específica.
     *
     * @param viewId ID de la vista.
     * @param color  Color a aplicar.
     */
    private void applyColorToView(int viewId, int color) {
        TextView textView = findViewById(viewId);
        if (textView != null) {
            textView.setBackgroundColor(color);
        }
    }
}
