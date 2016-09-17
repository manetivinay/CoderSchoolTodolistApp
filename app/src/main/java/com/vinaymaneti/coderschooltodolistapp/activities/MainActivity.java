package com.vinaymaneti.coderschooltodolistapp.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vinaymaneti.coderschooltodolistapp.R;
import com.vinaymaneti.coderschooltodolistapp.adapter.RealmTodoListAdapter;
import com.vinaymaneti.coderschooltodolistapp.adapter.TodoAdapter;
import com.vinaymaneti.coderschooltodolistapp.app.Prefs;
import com.vinaymaneti.coderschooltodolistapp.model.TodoListModel;
import com.vinaymaneti.coderschooltodolistapp.realm.RealmController;

import java.util.ArrayList;

import butterknife.Bind;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private TodoAdapter mTodoAdapter;
    private LayoutInflater mLayoutInflater;
    RecyclerView mRecyclerView;
    private Realm realm;

//    TodoListAdapter todoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);

//        View myLayout = findViewById(R.id.relativeLayout); // root View id
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView); // id of a view contained in the included file
        mRecyclerView.setNestedScrollingEnabled(false);

        //get the realm instance
        realm = RealmController.with(this).getRealm();

        setUpRecycler();
        if (!Prefs.with(this).getPreLoad()) {
            prepareToDoData();
        }

        //refresh the realm instance
        RealmController.with(this).refresh();
        //get all the persisted objects
        //create the helper adapter and notify data set change
        //change will be reflected automatically
        setRealmAdapter(RealmController.with(this).getTodoLists());

        Toast.makeText(this, "Press card item for edit, long press to remove item ", Toast.LENGTH_LONG).show();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //add new item
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                mLayoutInflater = MainActivity.this.getLayoutInflater();
                View content = mLayoutInflater.inflate(R.layout.edit_item, null);
                final TextView title = (TextView) content.findViewById(R.id.title_tv);
                final EditText message = (EditText) content.findViewById(R.id.message_tv);
                final AppCompatButton cancelBtn = (AppCompatButton) content.findViewById(R.id.cancel_btn);
                final AppCompatButton okBtn = (AppCompatButton) content.findViewById(R.id.ok_btn);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(content);
                final AlertDialog dialog = builder.create();
                dialog.show();
                title.setText("New ToDo List");

                okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TodoListModel todoList = new TodoListModel();
                        todoList.setId((int) (RealmController.getRealmController().getTodoLists().size() + System.currentTimeMillis()));
                        todoList.setTextValue(message.getText().toString());

                        if (message.getText() == null || message.getText().toString().equals("") || message.getText().toString().equals(" ") || message.getText().toString().trim().equals("") ) {
                            Toast.makeText(MainActivity.this, "Entry not saved because your todo message is empty", Toast.LENGTH_LONG).show();
                        } else {
                            //persist data to real db and display in recycler view
                            realm.beginTransaction();
                            realm.copyToRealm(todoList);
                            realm.commitTransaction();

                            mTodoAdapter.notifyDataSetChanged();

                            mRecyclerView.scrollToPosition(RealmController.getRealmController().getTodoLists().size() - 1);
                            dialog.dismiss();
                        }
                    }
                });

                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

    }

    private void setRealmAdapter(RealmResults<TodoListModel> todoLists) {
        RealmTodoListAdapter realmTodoListAdapter = new RealmTodoListAdapter(this.getApplicationContext(), todoLists, true);
        mTodoAdapter.setRealmBaseAdapter(realmTodoListAdapter);
        mTodoAdapter.notifyDataSetChanged();
    }

    private void setUpRecycler() {
        // use a linear layout manager since the cards are vertically scrollable
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mTodoAdapter = new TodoAdapter(this);
        mRecyclerView.setAdapter(mTodoAdapter);
    }

    private void prepareToDoData() {
        ArrayList<TodoListModel> mTodoListModelList = new ArrayList<>();

        TodoListModel listModel = new TodoListModel();
        listModel.setId((int) (1 + System.currentTimeMillis()));
        listModel.setTextValue("Reto Meier");
        mTodoListModelList.add(listModel);

        listModel = new TodoListModel();
        listModel.setId((int) (2 + System.currentTimeMillis()));
        listModel.setTextValue("Inside Out");
        mTodoListModelList.add(listModel);

        listModel = new TodoListModel();
        listModel.setId((int) (3 + System.currentTimeMillis()));
        listModel.setTextValue("Star Wars: Episode VII - The Force Awakens");
        mTodoListModelList.add(listModel);

        listModel = new TodoListModel();
        listModel.setId((int) (4 + System.currentTimeMillis()));
        listModel.setTextValue("Shaun the Sheep");
        mTodoListModelList.add(listModel);

        listModel = new TodoListModel();
        listModel.setId((int) (5 + System.currentTimeMillis()));
        listModel.setTextValue("Reto Meier 1");
        mTodoListModelList.add(listModel);

        listModel = new TodoListModel();
        listModel.setId((int) (6 + System.currentTimeMillis()));
        listModel.setTextValue("Inside Out 1");
        mTodoListModelList.add(listModel);

        listModel = new TodoListModel();
        listModel.setId((int) (7 + System.currentTimeMillis()));
        listModel.setTextValue("Star Wars: Episode VII - The Force Awakens 1");
        mTodoListModelList.add(listModel);

        listModel = new TodoListModel();
        listModel.setId((int) (8 + System.currentTimeMillis()));
        listModel.setTextValue("Shaun the Sheep 1");
        mTodoListModelList.add(listModel);


        listModel = new TodoListModel();
        listModel.setId((int) (9 + System.currentTimeMillis()));
        listModel.setTextValue("Reto Meier 2");
        mTodoListModelList.add(listModel);

        listModel = new TodoListModel();
        listModel.setId((int) (10 + System.currentTimeMillis()));
        listModel.setTextValue("Inside Out 2");
        mTodoListModelList.add(listModel);

        listModel = new TodoListModel();
        listModel.setId((int) (11 + System.currentTimeMillis()));
        listModel.setTextValue("Star Wars: Episode VII - The Force Awakens 2");
        mTodoListModelList.add(listModel);

        listModel = new TodoListModel();
        listModel.setId((int) (12 + System.currentTimeMillis()));
        listModel.setTextValue("Shaun the Sheep 2");
        mTodoListModelList.add(listModel);


        listModel = new TodoListModel();
        listModel.setId((int) (13 + System.currentTimeMillis()));
        listModel.setTextValue("Reto Meier 3");
        mTodoListModelList.add(listModel);

        listModel = new TodoListModel();
        listModel.setId((int) (14 + System.currentTimeMillis()));
        listModel.setTextValue("Inside Out 3");
        mTodoListModelList.add(listModel);

        listModel = new TodoListModel();
        listModel.setId((int) (15 + System.currentTimeMillis()));
        listModel.setTextValue("Star Wars: Episode VII - The Force Awakens 3");
        mTodoListModelList.add(listModel);

        listModel = new TodoListModel();
        listModel.setId((int) (16 + System.currentTimeMillis()));
        listModel.setTextValue("Shaun the Sheep 3");
        mTodoListModelList.add(listModel);

        for (TodoListModel todoListModel : mTodoListModelList) {
            realm.beginTransaction();
            realm.copyToRealm(todoListModel);
            realm.commitTransaction();
        }

        Prefs.with(this).setPreLoad(true);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
