package com.vinaymaneti.coderschooltodolistapp.realm;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.vinaymaneti.coderschooltodolistapp.model.TodoListModel;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by vinaymaneti on 9/17/16.
 */
public class RealmController {

    private static RealmController realmController;
    private final Realm mRealm;

    public RealmController(Application application) {
        mRealm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {
        if (realmController == null)
            realmController = new RealmController(fragment.getActivity().getApplication());
        return realmController;
    }

    public static RealmController with(Activity activity) {
        if (realmController == null)
            realmController = new RealmController(activity.getApplication());
        return realmController;
    }

    public static RealmController with(Application application) {
        if (realmController == null)
            realmController = new RealmController(application);
        return realmController;
    }

    public static RealmController getRealmController() {
        return realmController;
    }

    public Realm getRealm() {
        return mRealm;
    }

    //refresh realm instance
    public void refresh() {
        mRealm.setAutoRefresh(true);
    }

    //clear all the objects from TodoListModel.class
    public void clearAll() {
        mRealm.beginTransaction();
        mRealm.delete(TodoListModel.class);
        mRealm.commitTransaction();
    }

    //find all the objects in TodoListModel.class
    public RealmResults<TodoListModel> getTodoLists() {
        return mRealm.where(TodoListModel.class).findAll();
    }

    //query a single item with the given id
    public TodoListModel getTodoList(String id) {
        return mRealm.where(TodoListModel.class).equalTo("id", id).findFirst();
    }

    //check if the TodoList is empty
    public boolean hasTodoList() {
        return !mRealm.where(TodoListModel.class).findAll().isEmpty();
    }

}
