package com.zoptal.chinamoonbuffet.JsonClasses;

import java.util.ArrayList;

/**
 * Created by zotal.102 on 08/12/16.
 */
public class Model_LoyaltyProgram {

    private ArrayList<LoyaltyProgramData> al_loyaltyprogram = new ArrayList<LoyaltyProgramData>();


    public ArrayList<LoyaltyProgramData> getAl_loyaltyprogram() {
        return al_loyaltyprogram;
    }

    public void setAl_loyaltyprogram(ArrayList<LoyaltyProgramData> al_loyaltyprogram) {
        this.al_loyaltyprogram= al_loyaltyprogram;
    }

}
