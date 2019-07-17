package com.dkv.applocker.model;

import java.io.Serializable;

public class Password implements Serializable {
    private String password;

    public Password(String password) {
        this.password = password;
    }

    public void changePasswordInSQlite(String oldPassword) {
        //for safeness, save HASHCODE to DB, not the password.
        String newToken = "" + password.hashCode();

        String oldToken = getTokenFromDB();
        //If there is a token in db
        if(oldToken!=null) {
            //check if oldPassword matches the token in DB
            if(isMatchedWithTokenInDB(oldPassword)) {
                //alter the existed token in database with the new token
                alterPassword(newToken);
            } else {
                //throw exception failed to login
            }
        } else {
            //save the token as a new record
            writeTokenToDB(newToken);
        }
    }
    
    private boolean isMatchedWithTokenInDB(String inputPassword) {
        boolean result = false;
        String oldToken = getTokenFromDB();
        if(oldToken.equals(inputPassword.hashCode())) {
            result = true;
        }
        return result;
    }
    
    public boolean isMatchedWithTokenInDB() {
        return isMatchedWithTokenInDB(password);
    }

    private void writeTokenToDB(String newToken) {
        ////////////////////////////////////////////////////////////////////////////////////////////
        //Code to write token in here

        ////////////////////////////////////////////////////////////////////////////////////////////
    }

    private void alterPassword(String newToken) {
        ////////////////////////////////////////////////////////////////////////////////////////////
        //Code to change token in here

        ////////////////////////////////////////////////////////////////////////////////////////////
    }

    private String getTokenFromDB() {
        String token = null;

        ////////////////////////////////////////////////////////////////////////////////////////////
        //Get token from DB

        ////////////////////////////////////////////////////////////////////////////////////////////

        return token;
    }


}
