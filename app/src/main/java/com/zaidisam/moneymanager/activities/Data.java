package com.zaidisam.moneymanager.activities;

public class Data {
    String item ,data , id,notes,type , month;
    int amount ;

    public Data()
    {

    }

    public Data(String type,String item, String data, String id, String notes, int amount, String month) {
        this.type = type;
        this.item = item;

        this.data = data;

        this.id = id;
        this.notes = notes;
        this.amount = amount;
        this.month = month;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type= type;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
