package com.vinaymaneti.coderschooltodolistapp.adapter;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vinaymaneti.coderschooltodolistapp.R;
import com.vinaymaneti.coderschooltodolistapp.model.TodoListModel;
import com.vinaymaneti.coderschooltodolistapp.realm.RealmController;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by vinaymaneti on 9/17/16.
 */
public class TodoAdapter extends RealmRecyclerViewAdapter<TodoListModel> {
    final Context mContext;
    private Realm mRealm;
    private LayoutInflater mLayoutInflater;

    public TodoAdapter(Context context) {
        this.mContext = context;
    }


    //create a new views (invoked by layout manager)
    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new CardViewHolder(view);
    }

    //re[lace the content of a view (invoked by layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        mRealm = RealmController.getRealmController().getRealm();

        //get the list
        final TodoListModel todoListModel = getItem(position);

        //cast the generic view holder to specific one
        final CardViewHolder cardViewHolder = (CardViewHolder) holder;


        //set the textview
        cardViewHolder.mTextViewList.setText(todoListModel.getTextValue());

        //remove single match from realm
        cardViewHolder.mTextViewList.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                RealmResults<TodoListModel> results = mRealm.where(TodoListModel.class).findAll();

                //get the todolist text to show it in the toast message
                TodoListModel listModel = results.get(position);
                String text = listModel.getTextValue();

                //All changes to data must happen in a transaction
                mRealm.beginTransaction();

                //remove single match
                results.get(position).deleteFromRealm();
                mRealm.commitTransaction();

                notifyDataSetChanged();

                Toast.makeText(mContext, text + "is removed from list", Toast.LENGTH_LONG).show();


                return false;
            }
        });

        //update single list item
        cardViewHolder.mTextViewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View content = mLayoutInflater.inflate(R.layout.edit_item, null);
                final TextView title = (TextView) content.findViewById(R.id.title_tv);
                final EditText message = (EditText) content.findViewById(R.id.message_tv);
                final AppCompatButton cancelBtn = (AppCompatButton) content.findViewById(R.id.cancel_btn);
                final AppCompatButton okBtn = (AppCompatButton) content.findViewById(R.id.ok_btn);

                message.setText(todoListModel.getTextValue());
                message.setSelection(message.getText().length());

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setView(content);
                final AlertDialog dialog = builder.create();
                dialog.show();
                title.setText("Edit To Do List");
                okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RealmResults<TodoListModel> results = mRealm.where(TodoListModel.class).findAll();

                        if (message.getText().toString().isEmpty() || message.getText().toString().equals("") || message.getText().toString().equals(" ") || message.getText().toString().trim().equals("")) {
                            Toast.makeText(mContext, "Entry not saved because your todo message is empty", Toast.LENGTH_LONG).show();
                        } else {
                            mRealm.beginTransaction();
                            results.get(position).setTextValue(message.getText().toString());
                            mRealm.commitTransaction();

                            notifyDataSetChanged();
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


            }
        });
    }

    @Override
    public int getItemCount() {

        if (getRealmBaseAdapter() != null) {
            return getRealmBaseAdapter().getCount();
        }
        return 0;
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.textViewList)
        TextView mTextViewList;

        public CardViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);

        }
    }
}
