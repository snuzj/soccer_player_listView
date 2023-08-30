package com.snuzj.football_player_list_view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ImageView;

import com.snuzj.football_player_list_view.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private ArrayList<FootballPlayer> playerArrayList;
    private ArrayList<FootballPlayer> originalPlayerArrayList; // Danh sách gốc
    private ItemAdapter listAdapter;
    private static final int EDIT_ACTIVITY_REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializePlayerData();
        setupListView();

        binding.searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchText = charSequence.toString().toLowerCase();
                ArrayList<FootballPlayer> filteredPlayers = new ArrayList<>();

                for (FootballPlayer player : originalPlayerArrayList) {
                    if (player.getPlayer_name().toLowerCase().contains(searchText)) {
                        filteredPlayers.add(player);
                    }
                }

                // Cập nhật danh sách hiển thị với danh sách đã lọc
                playerArrayList = filteredPlayers;
                listAdapter.updateList(playerArrayList);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        binding.editButton.setOnClickListener(v -> {
            // Start the EditActivity
            Intent intent = new Intent(MainActivity.this, EditActivity.class);
            startActivityForResult(intent, EDIT_ACTIVITY_REQUEST_CODE);
        });

        /*

        //sửa dữ liệu cầu thủ
        binding.customListview.setOnItemClickListener((parent, view, position, id) -> {
            // Lấy dữ liệu của cầu thủ tại vị trí đã chọn
            FootballPlayer selectedPlayer = playerArrayList.get(position);

            Intent intent = new Intent(MainActivity.this, EditActivity.class);

            // Đặt dữ liệu cầu thủ đã chọn vào intent
            intent.putExtra("playerImageBitmap", selectedPlayer.getPlayer_img());
            intent.putExtra("playerName", selectedPlayer.getPlayer_name());
            intent.putExtra("playerNationality", selectedPlayer.getTeam_name());
            intent.putExtra("playerDOB", selectedPlayer.getPlayer_dob());
            intent.putExtra("nationImageBitmap", selectedPlayer.getNational_img());
            intent.putExtra("clubImageBitmap", selectedPlayer.getClub_img());

            // Bắt đầu EditActivity và mong đợi kết quả
            startActivityForResult(intent, EDIT_ACTIVITY_REQUEST_CODE);
        }); */

        binding.customListview.setOnItemLongClickListener((parent, view, position, id) -> {
            // Lấy dữ liệu của cầu thủ tại vị trí đã chọn
            FootballPlayer selectedPlayer = playerArrayList.get(position);

            // Hiển thị thông báo xác nhận trước khi xóa
            showDeleteConfirmationDialog(selectedPlayer);

            return true; // Trả về true để thông báo rằng sự kiện đã được xử lý
        });


    }
    private void setupListView() {
        listAdapter = new ItemAdapter(MainActivity.this, playerArrayList);
        binding.customListview.setAdapter(listAdapter);
    }

    private void initializePlayerData() {
        int[] playerImage = {R.drawable.johan_cruyff, R.drawable.ferenc_puskas, R.drawable.roberto_baggio, R.drawable.eusebio, R.drawable.garrincha,R.drawable.lionel_messi,R.drawable.lewandowski,R.drawable.kevin_de_bruyne,R.drawable.griezmann,R.drawable.kimmich};
        String[] playerName = {"Johan Cruyff", "Ferenc Puskás", "Roberto Baggio", "Eusébio", "Garrincha","Lionel Messi","Robert Lewandowski","Kevin De Bruyne","Antoine Griezmann","Joshua Kimmich"};
        String[] teamName = {"Hà Lan", "Hungary", "Ý", "Bồ Đào Nha", "Brazil","Argentina","Ba Lan","Bỉ","Pháp","Đức"};
        String[] dob = {"25.4.1947", "1.4.1927", "18.2.1967", "25.1.1942", "28.10.1933","24.6.1987","21.8.1988","28.6.1991","21.3.1991","8.2.1995"};
        int[] nationFlag = {R.drawable.holland, R.drawable.hungary, R.drawable.italy, R.drawable.portugal, R.drawable.italy,R.drawable.argentina,R.drawable.poland,R.drawable.belgium,R.drawable.new_france,R.drawable.germany};
        int[] clubFlag = {R.drawable.itm, R.drawable.itm, R.drawable.itm, R.drawable.itm, R.drawable.itm,R.drawable.tots,R.drawable.tots,R.drawable.tots,R.drawable.tots,R.drawable.tots};

        // Khởi tạo danh sách cầu thủ ban đầu
        originalPlayerArrayList = new ArrayList<>();
        for (int i = 0; i < playerImage.length; i++) {
            Bitmap playerImageBitmap = BitmapFactory.decodeResource(getResources(), playerImage[i]);
            Bitmap nationFlagBitmap = BitmapFactory.decodeResource(getResources(), nationFlag[i]);
            Bitmap clubFlagBitmap = BitmapFactory.decodeResource(getResources(), clubFlag[i]);

            FootballPlayer player = new FootballPlayer(playerImageBitmap, playerName[i], teamName[i], dob[i], nationFlagBitmap, clubFlagBitmap);
            originalPlayerArrayList.add(player);
        }

        // Khởi tạo danh sách cầu thủ để hiển thị
        playerArrayList = new ArrayList<>(originalPlayerArrayList); // Sử dụng danh sách gốc ban đầu
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String playerName = data.getStringExtra("playerName");
            String playerNationality = data.getStringExtra("playerNationality");
            String playerDOB = data.getStringExtra("playerDOB");

            Bitmap playerImageBitmap = data.getParcelableExtra("playerImageBitmap");
            Bitmap nationImageBitmap = data.getParcelableExtra("nationImageBitmap");
            Bitmap clubImageBitmap = data.getParcelableExtra("clubImageBitmap");

            // Cố định kích thước của nationImageBitmap và clubImageBitmap
            int targetSize = getResources().getDimensionPixelSize(R.dimen.image_size); // Kích thước 48dp
            nationImageBitmap = Bitmap.createScaledBitmap(nationImageBitmap, 100, 50, true);
            clubImageBitmap = Bitmap.createScaledBitmap(clubImageBitmap, targetSize, targetSize, true);

            FootballPlayer newPlayer = new FootballPlayer(playerImageBitmap, playerName, playerNationality, playerDOB, nationImageBitmap, clubImageBitmap);
            originalPlayerArrayList.add(newPlayer);
            playerArrayList.add(newPlayer);
            listAdapter.notifyDataSetChanged();
        }
    }

    private void showDeleteConfirmationDialog(FootballPlayer player) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa cầu thủ");
        builder.setMessage("Bạn có chắc chắn muốn xóa cầu thủ " + player.getPlayer_name() + "?");
        builder.setPositiveButton("Xóa", (dialog, which) -> {
            // Xóa cầu thủ khi người dùng xác nhận
            removePlayer(player);
            dialog.dismiss();
        });
        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void removePlayer(FootballPlayer player) {
        originalPlayerArrayList.remove(player);
        playerArrayList.remove(player);
        listAdapter.notifyDataSetChanged();
    }



}
