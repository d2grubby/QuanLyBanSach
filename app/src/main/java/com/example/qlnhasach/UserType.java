package com.example.qlnhasach;

public class UserType {
    public String userType;
    public int idUser;

    public UserType(String userType, int idUser) {
        this.idUser = idUser;
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
