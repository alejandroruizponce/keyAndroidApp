package com.example.alejandroruizponce1.chekinkey;

import org.json.JSONArray;

class UserProfile {
    private static UserProfile ourInstance = new UserProfile();
    public String pk = "";
    public String token = "";
    public String codePhone = "";
    public String phone = "";
    public String name = "";
    public String surname = "";
    public String sex = "";
    public String birthDate = "";
    public String documentType = "";
    public String codeDocument = "";
    public String expirationDate = "";
    public String documentScanned = "";
    public String documentID = "";
    public String selfieID = "";
    public String selfieScanned = "";
    public String signaturePicture = "";
    public Boolean identityVerified = false;
    public JSONArray myBookings = null;

    static UserProfile getInstance() {
        if (ourInstance == null)
        {
            ourInstance = new UserProfile();
        }
        return ourInstance;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getPk() { return this.pk; }



}
