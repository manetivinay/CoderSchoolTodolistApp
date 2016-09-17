package com.vinaymaneti.coderschooltodolistapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.vinaymaneti.coderschooltodolistapp.R;
import com.vinaymaneti.coderschooltodolistapp.activities.MainActivity;
import com.vinaymaneti.coderschooltodolistapp.model.TodoListModel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by vinaymaneti on 9/12/16.
 */
public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.TodoViewHolder> {

    private List<TodoListModel> mTodoListModelList;


    public TodoListAdapter(List<TodoListModel> todoListModels) {
        mTodoListModelList = todoListModels;
    }

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TodoViewHolder holder, int position) {
        holder.bindListData(position);
    }

    @Override
    public int getItemCount() {
        return mTodoListModelList.size();
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.textViewList)
        TextView mTextViewList;

        public TodoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), "Hello", Toast.LENGTH_LONG).show();
        }

        public void bindListData(int position) {
            TodoListModel todoListModel = mTodoListModelList.get(position);
            mTextViewList.setText(todoListModel.getTextValue());
        }
    }
}
