package com.snuzj.football_player_list_view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<FootballPlayer> {

    private ArrayList<FootballPlayer> originalPlayers; // Thêm biến để lưu danh sách gốc

    public ItemAdapter(Context context, ArrayList<FootballPlayer> playerArrayList){
        super(context, R.layout.player_item, playerArrayList);
        originalPlayers = new ArrayList<>(playerArrayList); // Lưu danh sách gốc khi khởi tạo adapter
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        FootballPlayer player = getItem(position);


        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.player_item, parent, false);
        }

        ImageView playerImg = convertView.findViewById(R.id.playerImage);
        TextView playerName = convertView.findViewById(R.id.playerName);
        TextView teamName = convertView.findViewById(R.id.teamName);
        TextView dob = convertView.findViewById(R.id.dobNumber);
        ImageView nationalImg = convertView.findViewById(R.id.nationImg);
        ImageView clubImg = convertView.findViewById(R.id.clubImg);



        assert player != null;
        playerImg.setImageBitmap(player.player_img);
        playerName.setText(player.player_name);
        teamName.setText(player.team_name);
        dob.setText(player.player_dob);
        nationalImg.setImageBitmap(player.national_img);
        clubImg.setImageBitmap(player.club_img);


        return convertView;
    }

    // Phương thức để cập nhật danh sách cầu thủ trong adapter
    public void updateList(ArrayList<FootballPlayer> filteredPlayers) {
        clear(); // Xóa dữ liệu hiện tại trong adapter
        addAll(filteredPlayers); // Thêm các cầu thủ đã lọc vào adapter
        notifyDataSetChanged(); // Thông báo cho adapter rằng dữ liệu đã thay đổi
    }
}
