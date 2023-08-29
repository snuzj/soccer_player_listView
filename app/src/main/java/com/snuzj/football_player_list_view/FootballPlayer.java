package com.snuzj.football_player_list_view;

import android.graphics.Bitmap;

public class FootballPlayer {
    public FootballPlayer(Bitmap player_img, String player_name, String team_name, String player_dob, Bitmap national_img, Bitmap club_img) {
        this.player_img = player_img;
        this.player_name = player_name;
        this.team_name = team_name;
        this.player_dob = player_dob;
        this.national_img = national_img;
        this.club_img = club_img;
    }

    public Bitmap getPlayer_img() {
        return player_img;
    }

    public void setPlayer_img(Bitmap player_img) {
        this.player_img = player_img;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getPlayer_dob() {
        return player_dob;
    }

    public void setPlayer_dob(String player_dob) {
        this.player_dob = player_dob;
    }

    public Bitmap getNational_img() {
        return national_img;
    }

    public void setNational_img(Bitmap national_img) {
        this.national_img = national_img;
    }

    public Bitmap getClub_img() {
        return club_img;
    }

    public void setClub_img(Bitmap club_img) {
        this.club_img = club_img;
    }

    Bitmap player_img;
    String player_name;
    String team_name;
    String player_dob;
    Bitmap national_img;
    Bitmap club_img;


}
