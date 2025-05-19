package com.wowmania.dto;

public class UserDto {
    public String username;
    public String email;
    public String password;
    private boolean buyer;
    private boolean seller;

    private String inGameName;
    private String allegiance;

    public String getInGameName() {
        return inGameName;
    }
    public void setInGameName(String inGameName) {
        this.inGameName = inGameName;
    }

    public String getAllegiance() {
        return allegiance;
    }
    public void setAllegiance(String allegiance) {
        this.allegiance = allegiance;
    }


    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isBuyer() {
        return buyer;
    }

    public boolean isSeller() {
        return seller;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBuyer(boolean buyer) {
        this.buyer = buyer;
    }

    public void setSeller(boolean seller) {
        this.seller = seller;
    }
}
