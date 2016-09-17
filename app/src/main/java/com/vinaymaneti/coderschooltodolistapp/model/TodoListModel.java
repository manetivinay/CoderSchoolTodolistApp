package com.vinaymaneti.coderschooltodolistapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by vinaymaneti on 9/12/16.
 */
public class TodoListModel extends RealmObject {

    @PrimaryKey
    private int id;
    private String textValue;

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //    @Override
//    public int describeContents() {
//        return 0;
//    }

//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(textValue);
//    }

//    private TodoListModel(Parcel parcel) {
//        textValue = parcel.readString();
//    }

//    public static final Creator<TodoListModel> CREATOR = new Creator<TodoListModel>() {
//        @Override
//        public TodoListModel createFromParcel(Parcel in) {
//            return new TodoListModel(in);
//        }
//
//        @Override
//        public TodoListModel[] newArray(int size) {
//            return new TodoListModel[size];
//        }
//    };
}
