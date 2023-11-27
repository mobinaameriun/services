package com.example.Service_system.model.response;

public class ResponseModel {
    private String massage;

    public ResponseModel(String massage) {
        this.massage = massage;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }
}
