package com.example.Service_system.dto.client;

public class ClientModelForChangePassword {
    private String emailAddress;
    private String oldPassword;
    private String newPassword;

    public ClientModelForChangePassword(String emailAddress, String oldPassword, String newPassword) {
        this.emailAddress = emailAddress;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
