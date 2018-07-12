package com.example.cleblond2016.geopostcards2.activities;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.example.cleblond2016.geopostcards2.BO.Media;
import com.example.cleblond2016.geopostcards2.BO.PostCard;
import com.example.cleblond2016.geopostcards2.BO.User;
import com.example.cleblond2016.geopostcards2.R;
import com.example.cleblond2016.geopostcards2.services.MediaService;
import com.example.cleblond2016.geopostcards2.services.PostCardService;
import com.example.cleblond2016.geopostcards2.services.UserService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreatePostCardActivity extends AppCompatActivity {

    public static final String EXTRA_LATITUDE = "lat";
    public static final String EXTRA_LONGITUDE = "long";
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int REQUEST_RECORD_SOUND = 2;
    public static final int REQUEST_VIDEO_CAPTURE = 3;
    private static final String TAG = "CreatePostCardActivity" ;

    private Integer idUser;

    private EditText edtLatitude;
    private EditText edtLongitude;
    private EditText edtTitle;
    private EditText edtMessage;
    private EditText edtDescriptionImage;
    private EditText edtDescriptionSon;
    private EditText edtDescriptionVideo;
    private Button btnImage;
    private Button btnSon;
    private Button btnVideo;
    private EditText edtPathImage;
    private EditText edtPathSon;
    private EditText edtPathVideo;
    private ImageView ivImage;

    private double actualLatitude;
    private double actualLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post_card);

        edtLatitude = findViewById(R.id.create_post_card_latitude);
        edtLongitude = findViewById(R.id.create_post_card_longitude);
        edtTitle = findViewById(R.id.create_post_card_title);
        edtMessage = findViewById(R.id.create_post_card_message);
        edtDescriptionImage = findViewById(R.id.create_post_card_description_image);
        edtDescriptionSon = findViewById(R.id.create_post_card_description_son);
        edtDescriptionVideo = findViewById(R.id.create_post_card_description_video);
        ivImage = findViewById(R.id.create_post_card_thumbnail_image);
        edtPathImage = findViewById(R.id.create_post_card_image_hidden);
        edtPathSon = findViewById(R.id.create_post_card_son_hidden);
        edtPathVideo = findViewById(R.id.create_post_card_video_hidden);

        Intent intentRecu = getIntent();
        if(intentRecu != null) {
            double defaultValue = 0.00;
            actualLatitude = intentRecu.getDoubleExtra(EXTRA_LATITUDE, defaultValue);
            actualLongitude = intentRecu.getDoubleExtra(EXTRA_LONGITUDE, defaultValue);
            idUser = intentRecu.getIntExtra(MainActivity.EXTRA_ID_USER, MainActivity.DEFAULT_ID_USER);
            Log.i(TAG, "actualLatitude :" + actualLatitude);
            Log.i(TAG, "actualLongitude :" + actualLongitude);
            Log.i(TAG, "idUser :" + idUser);
        }

        addListenerOnButton();
    }

    /**
     * Récupère la photo prise par l'appareil photo
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ivImage.setImageBitmap(imageBitmap);
            String path = saveImageToInternalStorage(imageBitmap);
            edtPathImage.setText(path);
        }
        if (requestCode == REQUEST_RECORD_SOUND && resultCode == RESULT_OK) {
            Uri sonUri = data.getData();
            edtPathSon.setText(sonUri.toString());
        }
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = data.getData();
            edtPathVideo.setText(videoUri.toString());
        }
    }

    /**
     * Affiche la barre de menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    /**
     * Ajoute des actions sur les boutons de la barre de menu
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save_save:
                savePostCard();
                finish();
                break;
        }
        return true;
    }

    /**
     * Prérempli les informations reçu des activités précédentes (ici Lat et Long de MainActivity)
     */
    @Override
    protected void onResume() {
        super.onResume();
        if(actualLatitude != 0 && actualLongitude != 0) {
            edtLatitude.setText(Double.toString(actualLatitude));
            edtLongitude.setText(Double.toString(actualLongitude));
        }
    }

    /**
     * Ajoute des actions sur les boutons image, son et video
     */
    public void addListenerOnButton() {

        btnImage = (Button) findViewById(R.id.create_post_card_btn_image);
        btnSon = (Button) findViewById(R.id.create_post_card_btn_son);
        btnVideo = (Button) findViewById(R.id.create_post_card_btn_video);

        btnImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        btnSon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent takePictureIntent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_RECORD_SOUND);
                }
            }
        });

        btnVideo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_VIDEO_CAPTURE);
                }
            }
        });
    }

    /**
     * Sauvegarde une carte postale et ses medias en base de données
     */
    private void savePostCard() {
        PostCard postCard = getPostCardIfValid();
        if(postCard != null) {
            boolean res = PostCardService.getInstance().insertPostCard(this, postCard);
            if(res){
                Log.e("CreatePostCardActivity", "Insertion PostCard OK");
            } else {
                Log.e("CreatePostCardActivity", "Insertion PostCard KO");
            }
        } else {
            Log.e("CreatePostCardActivity", "La carte postale n'est pas valide");
        }
        List<Media> listMedias = getListMediasIfValid(postCard);
        if(listMedias != null) {
            boolean res = MediaService.getInstance().insertAllMedias(this, listMedias);
            if(res){
                Log.e("CreatePostCardActivity", "Insertion Medias OK");
            } else {
                Log.e("CreatePostCardActivity", "Insertion Medias KO");
            }
        }

    }

    /**
     * Créer une carte postale
     * @return
     */
    private PostCard getPostCardIfValid() {
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
        String titre = edtTitle.getText().toString();
        String message = edtMessage.getText().toString();
        Log.e("latitude", "Latitude : "+edtLatitude.getText().toString());
        Double latitude = Double.valueOf(edtLatitude.getText().toString());
        Double longitude = Double.valueOf(edtLongitude.getText().toString());
        PostCard postCard = null;
        User connectedUser = null;
        if(idUser != null) {
            connectedUser = UserService.getInstance().getUserById(this, idUser);
        }
        if(latitude != null && longitude != null && titre != null && connectedUser != null) {
            if(message == null) {
                postCard = new PostCard(id, latitude, longitude, titre, null, connectedUser);
            } else {
                postCard = new PostCard(id, latitude, longitude, titre, message, connectedUser);
            }
        }
        return postCard;
    }

    /**
     * Créer une liste de tous les médias d'une carte postale
     * @param postCard
     * @return
     */
    private List<Media> getListMediasIfValid(PostCard postCard) {
        List<Media> listMedias = new ArrayList<>();
        String urlImage = edtPathImage.getText().toString();
        String urlSon = edtPathSon.getText().toString();
        String urlVideo = edtPathVideo.getText().toString();
        String descriptionImage = edtDescriptionImage.getText().toString();
        String descriptionSon = edtDescriptionSon.getText().toString();
        String descriptionVideo = edtDescriptionVideo.getText().toString();

        if(urlImage != null) {
            if(descriptionImage != null) {
                listMedias.add(new Media(postCard.getId(), "Image", urlImage, descriptionImage));
            } else {
                listMedias.add(new Media(postCard.getId(), "Image", urlImage, null));
            }
        }
        if(urlSon != null) {
            if(descriptionSon != null) {
                listMedias.add(new Media(postCard.getId(), "Son", urlSon, descriptionSon));
            } else {
                listMedias.add(new Media(postCard.getId(), "Son", urlSon, null));
            }
        }
        if(urlVideo != null) {
            if(descriptionVideo != null) {
                listMedias.add(new Media(postCard.getId(), "Video", urlVideo, descriptionVideo));
            } else {
                listMedias.add(new Media(postCard.getId(), "Video", urlVideo, null));
            }
        }
        return listMedias;
    }

    /**
     * Sauvegarde une image bitmap dans le stockage interne et retourne le chemin
     * @param bitmapImage
     * @return
     */
    private String saveImageToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        File mypath=new File(directory,timestamp + ".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }
}
