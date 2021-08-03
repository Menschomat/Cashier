package de.menschomat.cashier.rest.model;

public class ChangePWModel {
    private String oldPW;
    private String newPW;

    public String getOldPW() {
        return oldPW;
    }

    public String getNewPW() {
        return newPW;
    }

    public void setNewPW(String newPW) {
        this.newPW = newPW;
    }

    public void setOldPW(String oldPW) {
        this.oldPW = oldPW;
    }
}
