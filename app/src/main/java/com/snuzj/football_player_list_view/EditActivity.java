package com.snuzj.football_player_list_view;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.snuzj.football_player_list_view.databinding.ActivityEditBinding;

public class EditActivity extends AppCompatActivity {

    private String playerName;
    private String playerNationality;
    private String playerDOB;

    private static final int PICK_PLAYER_IMAGE_REQUEST_CODE = 1;
    private static final int PICK_NATION_IMAGE_REQUEST_CODE = 2;
    private static final int PICK_CLUB_IMAGE_REQUEST_CODE = 3;

    private Uri playerImageUrl;
    private Uri nationImageUrl;
    private Uri clubImageUrl;

    private ActivityEditBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backButton.setOnClickListener(v -> onBackPressed());

        binding.addPlayerImg.setOnClickListener(v -> openImagePicker(PICK_PLAYER_IMAGE_REQUEST_CODE));
        binding.addNationImg.setOnClickListener(v -> openImagePicker(PICK_NATION_IMAGE_REQUEST_CODE));
        binding.addClubImg.setOnClickListener(v -> openImagePicker(PICK_CLUB_IMAGE_REQUEST_CODE));

        binding.saveButton.setOnClickListener(v -> validateData());
    }

    private void openImagePicker(int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, requestCode);
                return;
            }
        }

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, requestCode);
    }

    private void validateData() {
        playerName = binding.editTextName.getText().toString().trim();
        playerNationality = binding.editTextNationality.getText().toString();
        playerDOB = binding.editTextDOB.getText().toString();

        if (playerName.isEmpty()) {
            showToast("Hay nhap ten cau thu");
        } else if (playerNationality.isEmpty()) {
            showToast("Hay nhap quoc tich");
        } else if (playerDOB.isEmpty()) {
            showToast("Hay nhap ngay sinh");
        } else if (playerImageUrl == null) {
            showToast("Hay them anh cau thu");
        } else if (nationImageUrl == null) {
            showToast("Hay them anh quoc tich");
        } else if (clubImageUrl == null) {
            showToast("Hay them anh club");
        } else {
            enterDataToList();
        }
    }

    private void enterDataToList() {
        Intent intent = new Intent();
        intent.putExtra("playerName", playerName);
        intent.putExtra("playerNationality", playerNationality);
        intent.putExtra("playerDOB", playerDOB);
        intent.putExtra("playerImageBitmap", ((BitmapDrawable) binding.playerIv.getDrawable()).getBitmap()); // Lấy hình ảnh từ ImageView
        intent.putExtra("nationImageBitmap", ((BitmapDrawable) binding.nationIv.getDrawable()).getBitmap()); // Lấy hình ảnh từ ImageView
        intent.putExtra("clubImageBitmap", ((BitmapDrawable) binding.clubIv.getDrawable()).getBitmap()); // Lấy hình ảnh từ ImageView
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == PICK_PLAYER_IMAGE_REQUEST_CODE) {
                playerImageUrl = data.getData();
                binding.playerIv.setImageURI(playerImageUrl);
                // Cập nhật hình ảnh cho ImageView
            } else if (requestCode == PICK_NATION_IMAGE_REQUEST_CODE) {
                nationImageUrl = data.getData();
                binding.nationIv.setImageURI(nationImageUrl); // Cập nhật hình ảnh cho ImageView
            } else if (requestCode == PICK_CLUB_IMAGE_REQUEST_CODE) {
                clubImageUrl = data.getData();
                binding.clubIv.setImageURI(clubImageUrl); // Cập nhật hình ảnh cho ImageView
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PICK_PLAYER_IMAGE_REQUEST_CODE ||
                requestCode == PICK_NATION_IMAGE_REQUEST_CODE ||
                requestCode == PICK_CLUB_IMAGE_REQUEST_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openImagePicker(requestCode);
            } else {
                showToast("Quyen truy cap bi tu choi");
            }
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
