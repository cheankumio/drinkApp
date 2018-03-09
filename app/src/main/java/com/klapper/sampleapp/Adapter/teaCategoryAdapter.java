package com.klapper.sampleapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.klapper.sampleapp.Animation.CustomAnimation;
import com.klapper.sampleapp.MainActivity;
import com.klapper.sampleapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c1103304 on 2018/3/8.
 */

public class teaCategoryAdapter extends RecyclerView.Adapter<teaCategoryAdapter.MyViewHolder> {
    Context mcontext;
    List<String> TeaCategoryList;
    int mViewType = 0;
    View.OnClickListener onClickListener;
    public teaCategoryAdapter(Context context, String teaCategoryStr){
        this.TeaCategoryList = new ArrayList<>();
        this.mcontext = context;
        for(String ct:teaCategoryStr.split(",")){
            this.TeaCategoryList.add(ct);
        }

    }

    @Override
    public teaCategoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==0){
            return new MyViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.teas,parent,false));
        }else{
            return new MyViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.teas,parent,false));
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        Button teaButton;
        public MyViewHolder(View view) {
            super(view);
            teaButton = view.findViewById(R.id.teacategory);
            teaButton.setHeight((int)(MainActivity.screenWidth/4f));
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if(position==TeaCategoryList.size()){
            holder.teaButton.setText("新增茶類");
            holder.teaButton.setOnClickListener(onClickListener);

        }else {
            holder.teaButton.setText(TeaCategoryList.get(position));
            holder.teaButton.setTag(TeaCategoryList.get(position));
            holder.teaButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomAnimation.singleClick(v);
                    Log.d("MYLOG","點到了 "+v.getTag());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return TeaCategoryList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==TeaCategoryList.size()){
            mViewType = 1;
            return 1;
        }else{
            mViewType = 0;
            return 0;
        }
    }

    public void setIncreaseClickListener(View.OnClickListener clickListener){
        onClickListener = clickListener;
    }

    public List<String> getTeaCategoryList(){
        return TeaCategoryList;
    }


}
