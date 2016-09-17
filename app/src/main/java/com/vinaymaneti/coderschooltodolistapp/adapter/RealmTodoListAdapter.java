package com.vinaymaneti.coderschooltodolistapp.adapter;

import android.content.Context;

import com.vinaymaneti.coderschooltodolistapp.model.TodoListModel;

import io.realm.RealmResults;

/**
 * Created by vinaymaneti on 9/17/16.
 */
public class RealmTodoListAdapter extends RealmModelAdapter<TodoListModel> {

    public RealmTodoListAdapter(Context context, RealmResults<TodoListModel> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }
}
