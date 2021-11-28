package com.client.myapplication.pacman;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    protected GridView cells;
    private int col=15,row=23;
    private int pacmanCol = 7;
    private int pacmanRow = 20;
    private int redGhostCol = 5;
    private int redGhostRow = 11;
    private int pinkGhostCol = 6;
    private int pinkGhostRow = 11;
    private int blueGhostCol = 8;
    private int blueGhostRow = 11;
    private int orangeGhostCol = 9;
    private int orangeGhostRow = 11;
    ArrayList<String> foods;
    private int score = 0;
    Handler redGhostAnimateHandler = new Handler();
    Runnable redGhostAnimateRunnable;
    Handler pinkGhostAnimateHandler = new Handler();
    Runnable pinkGhostAnimateRunnable;
    Handler blueGhostAnimateHandler = new Handler();
    Runnable blueGhostAnimateRunnable;
    Handler orangeGhostAnimateHandler = new Handler();
    Runnable orangeGhostAnimateRunnable;
    Handler redGhostMoveHandler = new Handler();
    Runnable redGhostMoveRunnable;
    Handler pinkGhostMoveHandler = new Handler();
    Runnable pinkGhostMoveRunnable;
    Handler blueGhostMoveHandler = new Handler();
    Runnable blueGhostMoveRunnable;
    Handler orangeGhostMoveHandler = new Handler();
    Runnable orangeGhostMoveRunnable;
    Handler pacAnimateHandler = new Handler();
    Runnable pacAnimateRunnable;
    Handler pacMoveHandler = new Handler();
    Runnable pacMoveRunnable;
    HashMap<String,Integer> map;
    ArrayList<Integer> walls;
    int picture = 0;
    int rgPicture = 0;
    int pgPicture = 0;
    int bgPicture = 0;
    int ogPicture = 0;
    final int animteInterval = 150;
    final int moveInterval = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cells = findViewById(R.id.GridCell);
        cells.setNumColumns(15);
        GameMap gamemap = new GameMap(this, col, row);
        foods = gamemap.getFoods();
        map = gamemap.getMap();
        walls = gamemap.getWalls();
        cells.setAdapter(gamemap);
        ghostMove(cells);
        cells.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeTop() {
                pacMoveUp(cells);
            }
            public void onSwipeRight() {
                pacMoveRight(cells);
            }
            public void onSwipeLeft() {
                pacMoveLeft(cells);
            }
            public void onSwipeBottom() {
                pacMoveDown(cells);
            }
        });
    }
    public void ghostMove(GridView cells){

    }

    public void pinkGhostMove(GridView cells){
        pinkGhostMoveHandler.removeCallbacks(pinkGhostMoveRunnable);
        pinkGhostMoveRunnable = new Runnable() {
            @Override
            public void run() {
                int random = new Random().nextInt((4 - 1) + 1) + 1;
//                if()
//                int moveToPosition = map.get(Integer.toString(pacmanRow-1) + "," + Integer.toString(pacmanCol));
                pinkGhostMoveHandler.postDelayed(this, moveInterval);
            }
        };
        pinkGhostMoveRunnable.run();
    }

    public void redGhostAnimationUp(ImageView imv){
        redGhostAnimateHandler.removeCallbacks(redGhostAnimateRunnable);
        redGhostAnimateRunnable = new Runnable(){
            public void run() {
                if(rgPicture==0){
                    imv.setImageResource(R.drawable.gru1);
                    picture=1;
                }
                else if(rgPicture==1){
                    imv.setImageResource(R.drawable.gru2);
                    picture=0;
                }
                redGhostAnimateHandler.postDelayed(this, animteInterval);
            }
        };
        redGhostAnimateRunnable.run();
    }
    public void redGhostAnimationDown(ImageView imv){
        redGhostAnimateHandler.removeCallbacks(redGhostAnimateRunnable);
        redGhostAnimateRunnable = new Runnable(){
            public void run() {
                if(rgPicture==0){
                    imv.setImageResource(R.drawable.grd1);
                    picture=1;
                }
                else if(rgPicture==1){
                    imv.setImageResource(R.drawable.grd2);
                    picture=0;
                }
                redGhostAnimateHandler.postDelayed(this, animteInterval);
            }
        };
        redGhostAnimateRunnable.run();
    }
    public void redGhostAnimationLeft(ImageView imv){
        redGhostAnimateHandler.removeCallbacks(redGhostAnimateRunnable);
        redGhostAnimateRunnable = new Runnable(){
            public void run() {
                if(rgPicture==0){
                    imv.setImageResource(R.drawable.grl1);
                    picture=1;
                }
                else if(rgPicture==1){
                    imv.setImageResource(R.drawable.grl2);
                    picture=0;
                }
                redGhostAnimateHandler.postDelayed(this, animteInterval);
            }
        };
        redGhostAnimateRunnable.run();
    }
    public void redGhostAnimationRight(ImageView imv){
        redGhostAnimateHandler.removeCallbacks(redGhostAnimateRunnable);
        redGhostAnimateRunnable = new Runnable(){
            public void run() {
                if(rgPicture==0){
                    imv.setImageResource(R.drawable.grr1);
                    picture=1;
                }
                else if(rgPicture==1){
                    imv.setImageResource(R.drawable.grr2);
                    picture=0;
                }
                redGhostAnimateHandler.postDelayed(this, animteInterval);
            }
        };
        redGhostAnimateRunnable.run();
    }

    public void pinkGhostAnimationUp(ImageView imv){
        pinkGhostAnimateHandler.removeCallbacks(pinkGhostAnimateRunnable);
        pinkGhostAnimateRunnable = new Runnable(){
            public void run() {
                if(rgPicture==0){
                    imv.setImageResource(R.drawable.gpu1);
                    picture=1;
                }
                else if(rgPicture==1){
                    imv.setImageResource(R.drawable.gpu2);
                    picture=0;
                }
                pinkGhostAnimateHandler.postDelayed(this, animteInterval);
            }
        };
        pinkGhostAnimateRunnable.run();
    }
    public void pinkGhostAnimationDown(ImageView imv){
        pinkGhostAnimateHandler.removeCallbacks(pinkGhostAnimateRunnable);
        pinkGhostAnimateRunnable = new Runnable(){
            public void run() {
                if(rgPicture==0){
                    imv.setImageResource(R.drawable.gpd1);
                    picture=1;
                }
                else if(rgPicture==1){
                    imv.setImageResource(R.drawable.gpd2);
                    picture=0;
                }
                pinkGhostAnimateHandler.postDelayed(this, animteInterval);
            }
        };
        pinkGhostAnimateRunnable.run();
    }
    public void pinkGhostAnimationLeft(ImageView imv){
        pinkGhostAnimateHandler.removeCallbacks(pinkGhostAnimateRunnable);
        pinkGhostAnimateRunnable = new Runnable(){
            public void run() {
                if(rgPicture==0){
                    imv.setImageResource(R.drawable.gpl1);
                    picture=1;
                }
                else if(rgPicture==1){
                    imv.setImageResource(R.drawable.gpl2);
                    picture=0;
                }
                pinkGhostAnimateHandler.postDelayed(this, animteInterval);
            }
        };
        pinkGhostAnimateRunnable.run();
    }
    public void pinkGhostAnimationRight(ImageView imv){
        pinkGhostAnimateHandler.removeCallbacks(pinkGhostAnimateRunnable);
        pinkGhostAnimateRunnable = new Runnable(){
            public void run() {
                if(rgPicture==0){
                    imv.setImageResource(R.drawable.gpr1);
                    picture=1;
                }
                else if(rgPicture==1){
                    imv.setImageResource(R.drawable.gpr2);
                    picture=0;
                }
                pinkGhostAnimateHandler.postDelayed(this, animteInterval);
            }
        };
        pinkGhostAnimateRunnable.run();
    }

    public void blueGhostAnimationUp(ImageView imv){
        blueGhostAnimateHandler.removeCallbacks(blueGhostAnimateRunnable);
        blueGhostAnimateRunnable = new Runnable(){
            public void run() {
                if(rgPicture==0){
                    imv.setImageResource(R.drawable.gbu1);
                    picture=1;
                }
                else if(rgPicture==1){
                    imv.setImageResource(R.drawable.gbu2);
                    picture=0;
                }
                blueGhostAnimateHandler.postDelayed(this, animteInterval);
            }
        };
        blueGhostAnimateRunnable.run();
    }
    public void blueGhostAnimationDown(ImageView imv){
        blueGhostAnimateHandler.removeCallbacks(blueGhostAnimateRunnable);
        blueGhostAnimateRunnable = new Runnable(){
            public void run() {
                if(rgPicture==0){
                    imv.setImageResource(R.drawable.gbd1);
                    picture=1;
                }
                else if(rgPicture==1){
                    imv.setImageResource(R.drawable.gbd2);
                    picture=0;
                }
                blueGhostAnimateHandler.postDelayed(this, animteInterval);
            }
        };
        blueGhostAnimateRunnable.run();
    }
    public void blueGhostAnimationLeft(ImageView imv){
        blueGhostAnimateHandler.removeCallbacks(blueGhostAnimateRunnable);
        blueGhostAnimateRunnable = new Runnable(){
            public void run() {
                if(rgPicture==0){
                    imv.setImageResource(R.drawable.gbl1);
                    picture=1;
                }
                else if(rgPicture==1){
                    imv.setImageResource(R.drawable.gbl2);
                    picture=0;
                }
                blueGhostAnimateHandler.postDelayed(this, animteInterval);
            }
        };
        blueGhostAnimateRunnable.run();
    }
    public void blueGhostAnimationRight(ImageView imv){
        blueGhostAnimateHandler.removeCallbacks(blueGhostAnimateRunnable);
        blueGhostAnimateRunnable = new Runnable(){
            public void run() {
                if(rgPicture==0){
                    imv.setImageResource(R.drawable.gbr1);
                    picture=1;
                }
                else if(rgPicture==1){
                    imv.setImageResource(R.drawable.gbr2);
                    picture=0;
                }
                blueGhostAnimateHandler.postDelayed(this, animteInterval);
            }
        };
        blueGhostAnimateRunnable.run();
    }

    public void orangeGhostAnimationUp(ImageView imv){
        orangeGhostAnimateHandler.removeCallbacks(orangeGhostAnimateRunnable);
        orangeGhostAnimateRunnable = new Runnable(){
            public void run() {
                if(rgPicture==0){
                    imv.setImageResource(R.drawable.gou1);
                    picture=1;
                }
                else if(rgPicture==1){
                    imv.setImageResource(R.drawable.gou2);
                    picture=0;
                }
                orangeGhostAnimateHandler.postDelayed(this, animteInterval);
            }
        };
        orangeGhostAnimateRunnable.run();
    }
    public void orangeGhostAnimationDown(ImageView imv){
        orangeGhostAnimateHandler.removeCallbacks(orangeGhostAnimateRunnable);
        orangeGhostAnimateRunnable = new Runnable(){
            public void run() {
                if(rgPicture==0){
                    imv.setImageResource(R.drawable.god1);
                    picture=1;
                }
                else if(rgPicture==1){
                    imv.setImageResource(R.drawable.god2);
                    picture=0;
                }
                orangeGhostAnimateHandler.postDelayed(this, animteInterval);
            }
        };
        orangeGhostAnimateRunnable.run();
    }
    public void orangeGhostAnimationLeft(ImageView imv){
        orangeGhostAnimateHandler.removeCallbacks(orangeGhostAnimateRunnable);
        orangeGhostAnimateRunnable = new Runnable(){
            public void run() {
                if(rgPicture==0){
                    imv.setImageResource(R.drawable.gol1);
                    picture=1;
                }
                else if(rgPicture==1){
                    imv.setImageResource(R.drawable.gol2);
                    picture=0;
                }
                orangeGhostAnimateHandler.postDelayed(this, animteInterval);
            }
        };
        orangeGhostAnimateRunnable.run();
    }
    public void orangeGhostAnimationRight(ImageView imv){
        orangeGhostAnimateHandler.removeCallbacks(orangeGhostAnimateRunnable);
        orangeGhostAnimateRunnable = new Runnable(){
            public void run() {
                if(rgPicture==0){
                    imv.setImageResource(R.drawable.gor1);
                    picture=1;
                }
                else if(rgPicture==1){
                    imv.setImageResource(R.drawable.gor2);
                    picture=0;
                }
                orangeGhostAnimateHandler.postDelayed(this, animteInterval);
            }
        };
        orangeGhostAnimateRunnable.run();
    }

    public void pacMoveUp(GridView cells){
        pacMoveHandler.removeCallbacks(pacMoveRunnable);
        pacMoveRunnable = new Runnable() {
            @Override
            public void run() {
                int moveToPosition = map.get(Integer.toString(pacmanRow-1) + "," + Integer.toString(pacmanCol));
                if(!walls.contains(moveToPosition)){
                    int pacmanPosition = map.get(Integer.toString(pacmanRow) + "," + Integer.toString(pacmanCol));
                    ImageView imv = (ImageView) cells.getChildAt(pacmanPosition);
                    imv.setImageResource(R.drawable.none);
                    pacmanRow-=1;
                    imv = (ImageView) cells.getChildAt(moveToPosition);
                    pacAnimationUp(imv);
                    getScore(moveToPosition);
                }else{
                    int pacmanPosition = map.get(Integer.toString(pacmanRow) + "," + Integer.toString(pacmanCol));
                    ImageView imv = (ImageView) cells.getChildAt(pacmanPosition);
                    pacAnimationUp(imv);
                }
                pacMoveHandler.postDelayed(this, moveInterval);
            }
        };
        pacMoveRunnable.run();
    }
    public void pacMoveRight(GridView cells){
        pacMoveHandler.removeCallbacks(pacMoveRunnable);
        pacMoveRunnable = new Runnable() {
            @Override
            public void run() {
                if(pacmanRow==11&&(pacmanCol+1)==15){
                    int pacmanPosition = map.get(Integer.toString(pacmanRow) + "," + Integer.toString(pacmanCol));
                    ImageView imv = (ImageView) cells.getChildAt(pacmanPosition);
                    imv.setImageResource(R.drawable.none);
                    pacmanCol=0;
                    imv = (ImageView) cells.getChildAt(map.get(Integer.toString(pacmanRow) + "," + Integer.toString(pacmanCol)));
                    pacAnimationRight(imv);
                }else{
                    int moveToPosition = map.get(Integer.toString(pacmanRow) + "," + Integer.toString(pacmanCol+1));
                    if(!walls.contains(moveToPosition)){
                        int pacmanPosition = map.get(Integer.toString(pacmanRow) + "," + Integer.toString(pacmanCol));
                        ImageView imv = (ImageView) cells.getChildAt(pacmanPosition);
                        imv.setImageResource(R.drawable.none);
                        pacmanCol+=1;
                        imv = (ImageView) cells.getChildAt(moveToPosition);
                        pacAnimationRight(imv);
                        getScore(moveToPosition);
                    }else{
                        int pacmanPosition = map.get(Integer.toString(pacmanRow) + "," + Integer.toString(pacmanCol));
                        ImageView imv = (ImageView) cells.getChildAt(pacmanPosition);
                        pacAnimationRight(imv);
                    }
                }
                pacMoveHandler.postDelayed(this, moveInterval);
            }
        };
        pacMoveRunnable.run();
    }
    public void pacMoveLeft(GridView cells){
        pacMoveHandler.removeCallbacks(pacMoveRunnable);
        pacMoveRunnable = new Runnable() {
            @Override
            public void run() {
                if(pacmanRow==11&&(pacmanCol-1)==-1){
                    int pacmanPosition = map.get(Integer.toString(pacmanRow) + "," + Integer.toString(pacmanCol));
                    ImageView imv = (ImageView) cells.getChildAt(pacmanPosition);
                    imv.setImageResource(R.drawable.none);
                    pacmanCol=14;
                    imv = (ImageView) cells.getChildAt(map.get(Integer.toString(pacmanRow) + "," + Integer.toString(pacmanCol)));
                    pacAnimationLeft(imv);
                }else{
                    int moveToPosition = map.get(Integer.toString(pacmanRow) + "," + Integer.toString(pacmanCol-1));
                    if(!walls.contains(moveToPosition)){
                        int pacmanPosition = map.get(Integer.toString(pacmanRow) + "," + Integer.toString(pacmanCol));
                        ImageView imv = (ImageView) cells.getChildAt(pacmanPosition);
                        imv.setImageResource(R.drawable.none);
                        pacmanCol-=1;
                        imv = (ImageView) cells.getChildAt(moveToPosition);
                        pacAnimationLeft(imv);
                        getScore(moveToPosition);
                    }else{
                        int pacmanPosition = map.get(Integer.toString(pacmanRow) + "," + Integer.toString(pacmanCol));
                        ImageView imv = (ImageView) cells.getChildAt(pacmanPosition);
                        pacAnimationLeft(imv);
                    }
                }
                pacMoveHandler.postDelayed(this, moveInterval);
            }
        };
        pacMoveRunnable.run();
    }
    public void pacMoveDown(GridView cells){
        pacMoveHandler.removeCallbacks(pacMoveRunnable);
        pacMoveRunnable = new Runnable() {
            @Override
            public void run() {
                int moveToPosition = map.get(Integer.toString(pacmanRow+1) + "," + Integer.toString(pacmanCol));
                if(!walls.contains(moveToPosition)){
                    int pacmanPosition = map.get(Integer.toString(pacmanRow) + "," + Integer.toString(pacmanCol));
                    ImageView imv = (ImageView) cells.getChildAt(pacmanPosition);
                    imv.setImageResource(R.drawable.none);
                    pacmanRow+=1;
                    imv = (ImageView) cells.getChildAt(moveToPosition);
                    pacAnimationDown(imv);
                    getScore(moveToPosition);
                }else{
                    int pacmanPosition = map.get(Integer.toString(pacmanRow) + "," + Integer.toString(pacmanCol));
                    ImageView imv = (ImageView) cells.getChildAt(pacmanPosition);
                    pacAnimationDown(imv);
                }
                pacMoveHandler.postDelayed(this, moveInterval);
            }
        };
        pacMoveRunnable.run();
    }

    public void pacAnimationUp(ImageView imv){
        pacAnimateHandler.removeCallbacks(pacAnimateRunnable);
        pacAnimateRunnable = new Runnable(){
            public void run() {
                if(picture==0){
                    imv.setImageResource(R.drawable.pacmanu1);
                    picture=1;
                }
                else if(picture==1){
                    imv.setImageResource(R.drawable.pacmanu2);
                    picture=2;
                }
                else{
                    imv.setImageResource(R.drawable.pacman3);
                    picture=0;
                }
                pacAnimateHandler.postDelayed(this, animteInterval);
            }
        };
        pacAnimateRunnable.run();
    }
    public void pacAnimationDown(ImageView imv){
        pacAnimateHandler.removeCallbacks(pacAnimateRunnable);
        pacAnimateRunnable = new Runnable(){
            public void run() {
                if(picture==0){
                    imv.setImageResource(R.drawable.pacmand1);
                    picture=1;
                }
                else if(picture==1){
                    imv.setImageResource(R.drawable.pacmand2);
                    picture=2;
                }
                else{
                    imv.setImageResource(R.drawable.pacman3);
                    picture=0;
                }
                pacAnimateHandler.postDelayed(this, animteInterval);
            }
        };
        pacAnimateRunnable.run();
    }
    public void pacAnimationLeft(ImageView imv){
        pacAnimateHandler.removeCallbacks(pacAnimateRunnable);
        pacAnimateRunnable = new Runnable(){
            public void run() {
                if(picture==0){
                    imv.setImageResource(R.drawable.pacmanl1);
                    picture=1;
                }
                else if(picture==1){
                    imv.setImageResource(R.drawable.pacmanl2);
                    picture=2;
                }
                else{
                    imv.setImageResource(R.drawable.pacman3);
                    picture=0;
                }
                pacAnimateHandler.postDelayed(this, animteInterval);
            }
        };
        pacAnimateRunnable.run();
    }
    public void pacAnimationRight(ImageView imv){
        pacAnimateHandler.removeCallbacks(pacAnimateRunnable);
        pacAnimateRunnable = new Runnable(){
            public void run() {
                if(picture==0){
                    imv.setImageResource(R.drawable.pacmanr1);
                    picture=1;
                }
                else if(picture==1){
                    imv.setImageResource(R.drawable.pacmanr2);
                    picture=2;
                }
                else{
                    imv.setImageResource(R.drawable.pacman3);
                    picture=0;
                }
                pacAnimateHandler.postDelayed(this, animteInterval);
            }
        };
        pacAnimateRunnable.run();
    }
    public void getScore(int position){
        if(meetGhost()){
            AlertDialog.Builder b = new AlertDialog.Builder(this);
            b.setTitle("YOU LOSE TT_TT");
            b.setMessage("Play again?");
            b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    recreate();
                }
            });
            AlertDialog al = b.create();
            al.show();
        }else{
            if(foods.contains(Integer.toString(position))){
                foods.remove(Integer.toString(position));
                score+=1;
                TextView scoreTextView = findViewById(R.id.score);
                scoreTextView.setText(Integer.toString(score));
            }
            if(win()){
                AlertDialog.Builder b = new AlertDialog.Builder(this);
                b.setTitle("You fucking won #@!#!@");
                b.setMessage("Play again?");
                b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        recreate();
                    }
                });
                AlertDialog al = b.create();
                al.show();
            }
        }
    }
    public boolean win(){
        if(foods.isEmpty())return true;
        return false;
    }
    public boolean meetGhost(){
        if((pacmanRow==redGhostRow&&pacmanCol==redGhostCol)||
                (pacmanRow==pinkGhostRow&&pacmanCol==pinkGhostCol)||
                (pacmanRow==blueGhostRow&&pacmanCol==blueGhostCol)||
                (pacmanRow==orangeGhostRow&&pacmanCol==orangeGhostCol)
        )return true;
        return false;
    }
}