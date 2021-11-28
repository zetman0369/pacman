package com.client.myapplication.pacman;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;

public class GameMap extends BaseAdapter {
    private Context mContext;
    public Integer[] mThumbIds;
    private HashMap<String, Integer> map;
    private ArrayList<Integer> wallPosition;
    private ArrayList<String> foodPosition;
    public GameMap(Context c,int col, int row){
        mThumbIds = new Integer[col*row];
        mContext = c;
        map = new HashMap<String, Integer>();
        int count=0;
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++) {
                map.put(Integer.toString(i)+","+Integer.toString(j),count++);
            }
        }
        for(int i=0;i<row*col;i++){
            mThumbIds[i]=R.drawable.food;
        }
        mThumbIds[(int)map.get(Integer.toString(2) + "," + Integer.toString(2))] = R.drawable.wall;
        mThumbIds[(int)map.get(Integer.toString(2) + "," + Integer.toString(12))] = R.drawable.wall;
        mThumbIds[(int)map.get(Integer.toString(20) + "," + Integer.toString(2))] = R.drawable.wall;
        mThumbIds[(int)map.get(Integer.toString(20) + "," + Integer.toString(12))] = R.drawable.wall;
        for(int i=0;i<col;i++) {
            int position = map.get(Integer.toString(0) + "," + Integer.toString(i));
            mThumbIds[position] = R.drawable.wall;
            position = map.get(Integer.toString(row-1) + "," + Integer.toString(i));
            mThumbIds[position] = R.drawable.wall;
            if(i<5||i>9){
                position = map.get(Integer.toString(5) + "," + Integer.toString(i));
                mThumbIds[position] = R.drawable.wall;
                position = map.get(Integer.toString(row-6) + "," + Integer.toString(i));
                mThumbIds[position] = R.drawable.wall;
            }
            if(i>3&&i<11){
                mThumbIds[(int)map.get(Integer.toString(10) + "," + Integer.toString(i))] = R.drawable.wall;
                mThumbIds[(int)map.get(Integer.toString(12) + "," + Integer.toString(i))] = R.drawable.wall;
                mThumbIds[(int)map.get(Integer.toString(8) + "," + Integer.toString(i))] = R.drawable.wall;
                mThumbIds[(int)map.get(Integer.toString(14) + "," + Integer.toString(i))] = R.drawable.wall;
            }
            if(i>5&&i<9){
                mThumbIds[(int)map.get(Integer.toString(20) + "," + Integer.toString(i))] = R.drawable.wall;
                mThumbIds[(int)map.get(Integer.toString(19) + "," + Integer.toString(i))] = R.drawable.wall;
            }
        }
        for(int i=0;i<row;i++) {
            int position = map.get(Integer.toString(i) + "," + Integer.toString(0));
            mThumbIds[position] = R.drawable.wall;
            position = map.get(Integer.toString(i) + "," + Integer.toString(col-1));
            mThumbIds[position] = R.drawable.wall;
            if(i<4||i>18){
                position = map.get(Integer.toString(i) + "," + Integer.toString(4));
                mThumbIds[position] = R.drawable.wall;
                position = map.get(Integer.toString(i) + "," + Integer.toString(10));
                mThumbIds[position] = R.drawable.wall;
            }
            if(i>1&&i<7){
                mThumbIds[(int)map.get(Integer.toString(i) + "," + Integer.toString(6))] = R.drawable.wall;
                mThumbIds[(int)map.get(Integer.toString(i) + "," + Integer.toString(8))] = R.drawable.wall;
            }
            if(i>6&&i<16){
                mThumbIds[(int)map.get(Integer.toString(i) + "," + Integer.toString(2))] = R.drawable.wall;
                mThumbIds[(int)map.get(Integer.toString(i) + "," + Integer.toString(12))] = R.drawable.wall;
            }
            if(i>14&&i<18){
                mThumbIds[(int)map.get(Integer.toString(i) + "," + Integer.toString(6))] = R.drawable.wall;
                mThumbIds[(int)map.get(Integer.toString(i) + "," + Integer.toString(8))] = R.drawable.wall;
            }
            if(i==10){
                mThumbIds[(int)map.get(Integer.toString(i) + "," + Integer.toString(10))] = R.drawable.wall;
            }
        }
        mThumbIds[(int)map.get(Integer.toString(11) + "," + Integer.toString(4))] = R.drawable.wall;
        mThumbIds[(int)map.get(Integer.toString(11) + "," + Integer.toString(10))] = R.drawable.wall;
        mThumbIds[(int)map.get(Integer.toString(11) + "," + Integer.toString(5))] = R.drawable.grr1;
        mThumbIds[(int)map.get(Integer.toString(11) + "," + Integer.toString(6))] = R.drawable.gpr1;
        mThumbIds[(int)map.get(Integer.toString(11) + "," + Integer.toString(8))] = R.drawable.gbl1;
        mThumbIds[(int)map.get(Integer.toString(11) + "," + Integer.toString(9))] = R.drawable.gol1;
        mThumbIds[(int)map.get(Integer.toString(20) + "," + Integer.toString(7))] = R.drawable.pacmand1;
        mThumbIds[(int)map.get(Integer.toString(11) + "," + Integer.toString(0))] = R.drawable.none;
        mThumbIds[(int)map.get(Integer.toString(11) + "," + Integer.toString(14))] = R.drawable.none;
        mThumbIds[(int)map.get(Integer.toString(11) + "," + Integer.toString(7))] = R.drawable.none;
        mThumbIds[(int)map.get(Integer.toString(10) + "," + Integer.toString(7))] = R.drawable.none;
        //get wall position
        wallPosition = new ArrayList<Integer>();
        foodPosition = new ArrayList<String>();
        for(int i=0;i<row*col;i++){
            if(mThumbIds[i]==R.drawable.wall){
                wallPosition.add(i);
            }else if(mThumbIds[i]==R.drawable.food){
                foodPosition.add(Integer.toString(i));
            }
        }
    }

    public ArrayList<String> getFoods(){
        return foodPosition;
    }

    public ArrayList<Integer> getWalls(){
        return wallPosition;
    }

    public HashMap<String,Integer> getMap(){
        return map;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(mThumbIds[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(70, 70));
        return imageView;
    }
}

