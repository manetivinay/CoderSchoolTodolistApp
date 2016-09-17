package com.vinaymaneti.coderschooltodolistapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.vinaymaneti.coderschooltodolistapp.model.TodoListModel;

import io.realm.RealmBaseAdapter;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by vinaymaneti on 9/17/16.
 */
public class RealmModelAdapter<T extends RealmObject> extends RealmBaseAdapter<T> {

    public RealmModelAdapter(Context context, RealmResults<T> realmResults, boolean automaticUpdate) {
        super(context, realmResults);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
