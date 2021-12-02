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
    private int redPreDir = 2;
    private int pinkPreDir = 2;
    private int bluePreDir = 2;
    private int orangePreDir = 2;
    private int redGetOut=4;
    private int pinkGetOut=3;
    private int blueGetOut=3;
    private int orangeGetOut=4;
    private int score = 0;
    private int pinkChaseDir;
    private int blueChaseDir;
    private int redChaseDir;
    private int orangeChaseDir;
    Handler redGhostAnimateHandler = new Handler();
    Runnable redGhostAnimateRunnable;
    Handler pinkGhostAnimateHandler = new Handler();
    Runnable pinkGhostAnimateRunnable;
    Handler blueGhostAnimateHandler = new Handler();
    Runnable blueGhostAnimateRunnable;
    Handler orangeGhostAnimateHandler = new Handler();
    Runnable orangeGhostAnimateRunnable;
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
    boolean usePostDeplay = true;
    final int animteInterval = 200;
    final int moveInterval = 300;
    int imageAfterPinkMove=R.drawable.none;
    int imageAfterRedMove=R.drawable.none;
    int imageAfterBlueMove=R.drawable.none;
    int imageAfterOrangeMove=R.drawable.none;
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
    public void destroyRunnable(){
        pacMoveHandler.removeCallbacks(pacMoveRunnable);
    }
    public void ghostMove(GridView cells){
        pinkGhostMove(cells);
        redGhostMove(cells);
        orangeGhostMove(cells);
        blueGhostMove(cells);
    }

    public void redGhostMove(GridView cells){
        if(redGetOut==4){
            redGetOut-=1;
            int rgPosition = map.get(Integer.toString(redGhostRow) + "," + Integer.toString(redGhostCol));
            ImageView imv = (ImageView) cells.getChildAt(rgPosition);
            imv.setImageResource(R.drawable.none);
            redGhostCol+=1;
            rgPosition = map.get(Integer.toString(redGhostRow) + "," + Integer.toString(redGhostCol));
            imv = (ImageView) cells.getChildAt(rgPosition);
            redGhostAnimationRight(imv);
        }if(redGetOut==3){
            redGetOut-=1;
            int rgPosition = map.get(Integer.toString(redGhostRow) + "," + Integer.toString(redGhostCol));
            ImageView imv = (ImageView) cells.getChildAt(rgPosition);
            imv.setImageResource(R.drawable.none);
            redGhostCol+=1;
            rgPosition = map.get(Integer.toString(redGhostRow) + "," + Integer.toString(redGhostCol));
            imv = (ImageView) cells.getChildAt(rgPosition);
            redGhostAnimationRight(imv);
        }else if(redGetOut==2){
            redGetOut-=1;
            int rgPosition = map.get(Integer.toString(redGhostRow) + "," + Integer.toString(redGhostCol));
            ImageView imv = (ImageView) cells.getChildAt(rgPosition);
            imv.setImageResource(R.drawable.none);
            redGhostRow-=1;
            rgPosition = map.get(Integer.toString(redGhostRow) + "," + Integer.toString(redGhostCol));
            imv = (ImageView) cells.getChildAt(rgPosition);
            redGhostAnimationUp(imv);
        }else if(redGetOut==1){
            redGetOut-=1;
            int rgPosition = map.get(Integer.toString(redGhostRow) + "," + Integer.toString(redGhostCol));
            ImageView imv = (ImageView) cells.getChildAt(rgPosition);
            imv.setImageResource(R.drawable.food);
            redGhostRow-=1;
            rgPosition = map.get(Integer.toString(redGhostRow) + "," + Integer.toString(redGhostCol));
            imv = (ImageView) cells.getChildAt(rgPosition);
            redGhostAnimationUp(imv);
        }else{
            int nextRedRow,nextRedCol;
            if(redChasePac()){
                if(redChaseDir==1){
                    nextRedRow = redGhostRow-1;
                    nextRedCol = redGhostCol;
                }else if(redChaseDir==2){
                    nextRedRow = redGhostRow+1;
                    nextRedCol = redGhostCol;
                }else if(redChaseDir==3){
                    nextRedRow = redGhostRow;
                    nextRedCol = redGhostCol-1;
                }else{
                    nextRedRow = redGhostRow;
                    nextRedCol = redGhostCol+1;
                }
            }else{
                if(redPreDir==1){
                    nextRedRow = redGhostRow+1;
                    nextRedCol = redGhostCol;
                }else if(redPreDir==2){
                    nextRedRow = redGhostRow-1;
                    nextRedCol = redGhostCol;
                }else if(redPreDir==3){
                    nextRedRow = redGhostRow;
                    nextRedCol = redGhostCol+1;
                }else{
                    nextRedRow = redGhostRow;
                    nextRedCol = redGhostCol-1;
                }
            }
            if(nextRedRow==11&&nextRedCol==15){
                nextRedCol=0;
            }else if(nextRedRow==11&&nextRedCol==-1){
                nextRedCol=14;
            }
            int moveToPosition = map.get(Integer.toString(nextRedRow) + "," + Integer.toString(nextRedCol));
            if(walls.contains(moveToPosition)){
                int moveTo = redRandomMove();
                if(moveTo==1){
                    redPreDir = 2;
                }else if(moveTo==2){
                    redPreDir = 1;
                }else if(moveTo==3){
                    redPreDir = 4;
                }else if(moveTo==4){
                    redPreDir = 3;
                }
            }else {
                int redPosition = map.get(Integer.toString(redGhostRow) + "," + Integer.toString(redGhostCol));
                ImageView imv = (ImageView) cells.getChildAt(redPosition);
                imv.setImageResource(imageAfterRedMove);
                if (foods.contains(Integer.toString(moveToPosition))) {
                    imageAfterRedMove = R.drawable.food;
                } else {
                    imageAfterRedMove = R.drawable.none;
                }
                redGhostRow = nextRedRow;
                redGhostCol = nextRedCol;
                imv = (ImageView) cells.getChildAt(moveToPosition);
                if (redPreDir == 1) {
                    redGhostAnimationDown(imv);
                } else if (redPreDir == 2) {
                    redGhostAnimationUp(imv);
                } else if (redPreDir == 3) {
                    redGhostAnimationRight(imv);
                } else {
                    redGhostAnimationLeft(imv);
                }
            }
        }
    }
    public void orangeGhostMove(GridView cells){
        if(orangeGetOut==4){
            orangeGetOut-=1;
            int ogPosition = map.get(Integer.toString(orangeGhostRow) + "," + Integer.toString(orangeGhostCol));
            ImageView imv = (ImageView) cells.getChildAt(ogPosition);
            imv.setImageResource(R.drawable.none);
            orangeGhostCol-=1;
            ogPosition = map.get(Integer.toString(orangeGhostRow) + "," + Integer.toString(orangeGhostCol));
            imv = (ImageView) cells.getChildAt(ogPosition);
            orangeGhostAnimationLeft(imv);
        }if(orangeGetOut==3){
            orangeGetOut-=1;
            int ogPosition = map.get(Integer.toString(orangeGhostRow) + "," + Integer.toString(orangeGhostCol));
            ImageView imv = (ImageView) cells.getChildAt(ogPosition);
            imv.setImageResource(R.drawable.none);
            orangeGhostCol-=1;
            ogPosition = map.get(Integer.toString(orangeGhostRow) + "," + Integer.toString(orangeGhostCol));
            imv = (ImageView) cells.getChildAt(ogPosition);
            orangeGhostAnimationLeft(imv);
        }else if(orangeGetOut==2){
            orangeGetOut-=1;
            int ogPosition = map.get(Integer.toString(orangeGhostRow) + "," + Integer.toString(orangeGhostCol));
            ImageView imv = (ImageView) cells.getChildAt(ogPosition);
            imv.setImageResource(R.drawable.none);
            orangeGhostRow-=1;
            ogPosition = map.get(Integer.toString(orangeGhostRow) + "," + Integer.toString(orangeGhostCol));
            imv = (ImageView) cells.getChildAt(ogPosition);
            orangeGhostAnimationUp(imv);
        }else if(orangeGetOut==1){
            orangeGetOut-=1;
            int ogPosition = map.get(Integer.toString(orangeGhostRow) + "," + Integer.toString(orangeGhostCol));
            ImageView imv = (ImageView) cells.getChildAt(ogPosition);
            imv.setImageResource(R.drawable.food);
            orangeGhostRow-=1;
            ogPosition = map.get(Integer.toString(orangeGhostRow) + "," + Integer.toString(orangeGhostCol));
            imv = (ImageView) cells.getChildAt(ogPosition);
            orangeGhostAnimationUp(imv);
        }else{
            int nextOrangeRow,nextOrangeCol;
            if(orangeChasePac()){
                if(orangeChaseDir==1){
                    nextOrangeRow = orangeGhostRow-1;
                    nextOrangeCol = orangeGhostCol;
                }else if(orangeChaseDir==2){
                    nextOrangeRow = orangeGhostRow+1;
                    nextOrangeCol = orangeGhostCol;
                }else if(orangeChaseDir==3){
                    nextOrangeRow = orangeGhostRow;
                    nextOrangeCol = orangeGhostCol-1;
                }else{
                    nextOrangeRow = orangeGhostRow;
                    nextOrangeCol = orangeGhostCol+1;
                }
            }else{
                if(orangePreDir==1){
                    nextOrangeRow = orangeGhostRow+1;
                    nextOrangeCol = orangeGhostCol;
                }else if(orangePreDir==2){
                    nextOrangeRow = orangeGhostRow-1;
                    nextOrangeCol = orangeGhostCol;
                }else if(orangePreDir==3){
                    nextOrangeRow = orangeGhostRow;
                    nextOrangeCol = orangeGhostCol+1;
                }else{
                    nextOrangeRow = orangeGhostRow;
                    nextOrangeCol = orangeGhostCol-1;
                }
            }
            if(nextOrangeRow==11&&nextOrangeCol==15){
                nextOrangeCol=0;
            }else if(nextOrangeRow==11&&nextOrangeCol==-1){
                nextOrangeCol=14;
            }
            int moveToPosition = map.get(Integer.toString(nextOrangeRow) + "," + Integer.toString(nextOrangeCol));
            if(walls.contains(moveToPosition)){
                int moveTo = orangeRandomMove();
                if(moveTo==1){
                    orangePreDir = 2;
                }else if(moveTo==2){
                    orangePreDir = 1;
                }else if(moveTo==3){
                    orangePreDir = 4;
                }else if(moveTo==4){
                    orangePreDir = 3;
                }
            }else {
                int orangePosition = map.get(Integer.toString(orangeGhostRow) + "," + Integer.toString(orangeGhostCol));
                ImageView imv = (ImageView) cells.getChildAt(orangePosition);
                imv.setImageResource(imageAfterOrangeMove);
                if (foods.contains(Integer.toString(moveToPosition))) {
                    imageAfterOrangeMove = R.drawable.food;
                } else {
                    imageAfterOrangeMove = R.drawable.none;
                }
                orangeGhostRow = nextOrangeRow;
                orangeGhostCol = nextOrangeCol;
                imv = (ImageView) cells.getChildAt(moveToPosition);
                if (orangePreDir == 1) {
                    orangeGhostAnimationDown(imv);
                } else if (orangePreDir == 2) {
                    orangeGhostAnimationUp(imv);
                } else if (orangePreDir == 3) {
                    orangeGhostAnimationRight(imv);
                } else {
                    orangeGhostAnimationLeft(imv);
                }
            }
        }
    }
    public void blueGhostMove(GridView cells){
        if(blueGetOut==3){
            blueGetOut-=1;
            int bgPosition = map.get(Integer.toString(blueGhostRow) + "," + Integer.toString(blueGhostCol));
            ImageView imv = (ImageView) cells.getChildAt(bgPosition);
            imv.setImageResource(R.drawable.none);
            blueGhostCol-=1;
            bgPosition = map.get(Integer.toString(blueGhostRow) + "," + Integer.toString(blueGhostCol));
            imv = (ImageView) cells.getChildAt(bgPosition);
            blueGhostAnimationLeft(imv);
        }else if(blueGetOut==2){
            blueGetOut-=1;
            int bgPosition = map.get(Integer.toString(blueGhostRow) + "," + Integer.toString(blueGhostCol));
            ImageView imv = (ImageView) cells.getChildAt(bgPosition);
            imv.setImageResource(R.drawable.none);
            blueGhostRow-=1;
            bgPosition = map.get(Integer.toString(blueGhostRow) + "," + Integer.toString(blueGhostCol));
            imv = (ImageView) cells.getChildAt(bgPosition);
            blueGhostAnimationUp(imv);
        }else if(blueGetOut==1){
            blueGetOut-=1;
            int bgPosition = map.get(Integer.toString(blueGhostRow) + "," + Integer.toString(blueGhostCol));
            ImageView imv = (ImageView) cells.getChildAt(bgPosition);
            imv.setImageResource(R.drawable.none);
            blueGhostRow-=1;
            bgPosition = map.get(Integer.toString(blueGhostRow) + "," + Integer.toString(blueGhostCol));
            imv = (ImageView) cells.getChildAt(bgPosition);
            blueGhostAnimationUp(imv);
        }else{
            int nextBlueRow,nextBlueCol;
            if(blueChasePac()){
                if(blueChaseDir==1){
                    nextBlueRow = blueGhostRow-1;
                    nextBlueCol = blueGhostCol;
                }else if(blueChaseDir==2){
                    nextBlueRow = blueGhostRow+1;
                    nextBlueCol = blueGhostCol;
                }else if(blueChaseDir==3){
                    nextBlueRow = blueGhostRow;
                    nextBlueCol = blueGhostCol-1;
                }else{
                    nextBlueRow = blueGhostRow;
                    nextBlueCol = blueGhostCol+1;
                }
            }else{
                if(bluePreDir==1){
                    nextBlueRow = blueGhostRow+1;
                    nextBlueCol = blueGhostCol;
                }else if(bluePreDir==2){
                    nextBlueRow = blueGhostRow-1;
                    nextBlueCol = blueGhostCol;
                }else if(bluePreDir==3){
                    nextBlueRow = blueGhostRow;
                    nextBlueCol = blueGhostCol+1;
                }else{
                    nextBlueRow = blueGhostRow;
                    nextBlueCol = blueGhostCol-1;
                }
            }
            if(nextBlueRow==11&&nextBlueCol==15){
                nextBlueCol=0;
            }else if(nextBlueRow==11&&nextBlueCol==-1){
                nextBlueCol=14;
            }
            int moveToPosition = map.get(Integer.toString(nextBlueRow) + "," + Integer.toString(nextBlueCol));
            if(walls.contains(moveToPosition)){
                int moveTo = blueRandomMove();
                if(moveTo==1){
                    bluePreDir = 2;
                }else if(moveTo==2){
                    bluePreDir = 1;
                }else if(moveTo==3){
                    bluePreDir = 4;
                }else if(moveTo==4){
                    bluePreDir = 3;
                }
            }else {
                int bluePosition = map.get(Integer.toString(blueGhostRow) + "," + Integer.toString(blueGhostCol));
                ImageView imv = (ImageView) cells.getChildAt(bluePosition);
                imv.setImageResource(imageAfterBlueMove);
                if (foods.contains(Integer.toString(moveToPosition))) {
                    imageAfterBlueMove = R.drawable.food;
                } else {
                    imageAfterBlueMove = R.drawable.none;
                }
                blueGhostRow = nextBlueRow;
                blueGhostCol = nextBlueCol;
                imv = (ImageView) cells.getChildAt(moveToPosition);
                if (bluePreDir == 1) {
                    blueGhostAnimationDown(imv);
                } else if (bluePreDir == 2) {
                    blueGhostAnimationUp(imv);
                } else if (bluePreDir == 3) {
                    blueGhostAnimationRight(imv);
                } else {
                    blueGhostAnimationLeft(imv);
                }
            }
        }
    }
    public void pinkGhostMove(GridView cells){
                if(pinkGetOut==3){
                    pinkGetOut-=1;
                    int pgPosition = map.get(Integer.toString(pinkGhostRow) + "," + Integer.toString(pinkGhostCol));
                    ImageView imv = (ImageView) cells.getChildAt(pgPosition);
                    imv.setImageResource(R.drawable.none);
                    pinkGhostCol+=1;
                    pgPosition = map.get(Integer.toString(pinkGhostRow) + "," + Integer.toString(pinkGhostCol));
                    imv = (ImageView) cells.getChildAt(pgPosition);
                    pinkGhostAnimationUp(imv);
                }else if(pinkGetOut==2){
                    pinkGetOut-=1;
                    int pgPosition = map.get(Integer.toString(pinkGhostRow) + "," + Integer.toString(pinkGhostCol));
                    ImageView imv = (ImageView) cells.getChildAt(pgPosition);
                    imv.setImageResource(R.drawable.none);
                    pinkGhostRow-=1;
                    pgPosition = map.get(Integer.toString(pinkGhostRow) + "," + Integer.toString(pinkGhostCol));
                    imv = (ImageView) cells.getChildAt(pgPosition);
                    pinkGhostAnimationUp(imv);
                }else if(pinkGetOut==1){
                    pinkGetOut-=1;
                    int pgPosition = map.get(Integer.toString(pinkGhostRow) + "," + Integer.toString(pinkGhostCol));
                    ImageView imv = (ImageView) cells.getChildAt(pgPosition);
                    imv.setImageResource(R.drawable.none);
                    pinkGhostRow-=1;
                    pgPosition = map.get(Integer.toString(pinkGhostRow) + "," + Integer.toString(pinkGhostCol));
                    imv = (ImageView) cells.getChildAt(pgPosition);
                    pinkGhostAnimationUp(imv);
                }else{
                    int nextPinkRow,nextPinkCol;
                    if(pinkChasePac()){
                        if(pinkChaseDir==1){
                            nextPinkRow = pinkGhostRow-1;
                            nextPinkCol = pinkGhostCol;
                        }else if(pinkChaseDir==2){
                            nextPinkRow = pinkGhostRow+1;
                            nextPinkCol = pinkGhostCol;
                        }else if(pinkChaseDir==3){
                            nextPinkRow = pinkGhostRow;
                            nextPinkCol = pinkGhostCol-1;
                        }else{
                            nextPinkRow = pinkGhostRow;
                            nextPinkCol = pinkGhostCol+1;
                        }
                    }else{
                        if(pinkPreDir==1){
                            nextPinkRow = pinkGhostRow+1;
                            nextPinkCol = pinkGhostCol;
                        }else if(pinkPreDir==2){
                            nextPinkRow = pinkGhostRow-1;
                            nextPinkCol = pinkGhostCol;
                        }else if(pinkPreDir==3){
                            nextPinkRow = pinkGhostRow;
                            nextPinkCol = pinkGhostCol+1;
                        }else{
                            nextPinkRow = pinkGhostRow;
                            nextPinkCol = pinkGhostCol-1;
                        }
                    }
                    if(nextPinkRow==11&&nextPinkCol==15){
                        nextPinkCol=0;
                    }else if(nextPinkRow==11&&nextPinkCol==-1){
                        nextPinkCol=14;
                    }
                    int moveToPosition = map.get(Integer.toString(nextPinkRow) + "," + Integer.toString(nextPinkCol));
                    if(walls.contains(moveToPosition)){
                        int moveTo = pinkRandomMove();
                        if(moveTo==1){
                            pinkPreDir = 2;
                        }else if(moveTo==2){
                            pinkPreDir = 1;
                        }else if(moveTo==3){
                            pinkPreDir = 4;
                        }else if(moveTo==4){
                            pinkPreDir = 3;
                        }
                    }else {
                        int pinkPosition = map.get(Integer.toString(pinkGhostRow) + "," + Integer.toString(pinkGhostCol));
                        ImageView imv = (ImageView) cells.getChildAt(pinkPosition);
                        imv.setImageResource(imageAfterPinkMove);
                        if (foods.contains(Integer.toString(moveToPosition))) {
                            imageAfterPinkMove = R.drawable.food;
                        } else {
                            imageAfterPinkMove = R.drawable.none;
                        }
                        pinkGhostRow = nextPinkRow;
                        pinkGhostCol = nextPinkCol;
                        imv = (ImageView) cells.getChildAt(moveToPosition);
                        if (pinkPreDir == 1) {
                            pinkGhostAnimationDown(imv);
                        } else if (pinkPreDir == 2) {
                            pinkGhostAnimationUp(imv);
                        } else if (pinkPreDir == 3) {
                            pinkGhostAnimationRight(imv);
                        } else {
                            pinkGhostAnimationLeft(imv);
                        }
                    }
                }
    }

    public boolean pinkChasePac(){
        int max,min;
        if(pinkGhostRow==pacmanRow){
            if(pinkGhostCol>pacmanCol){
                max=pinkGhostCol;
                min=pacmanCol;
            }else{
                max=pacmanCol;
                min=pinkGhostCol;
            }
            for(int i=min+1;i<max;i++){
                int moveToPosition = map.get(Integer.toString(pinkGhostRow) + "," + Integer.toString(i));
                if(walls.contains(moveToPosition)){
                    return false;
                }
            }
            pinkChaseDir = (pinkGhostCol>pacmanCol)?3:4;
            return true;
        }
        if(pinkGhostCol==pacmanCol){
            if(pinkGhostRow>pacmanRow){
                max=pinkGhostRow;
                min=pacmanRow;
            }else{
                max=pacmanRow;
                min=pinkGhostRow;
            }
            for(int i=min+1;i<max;i++){
                int moveToPosition = map.get(Integer.toString(i) + "," + Integer.toString(pinkGhostCol));
                if(walls.contains(moveToPosition)){
                    return false;
                }
            }
            pinkChaseDir = (pinkGhostRow>pacmanRow)?1:2;
            return true;
        }
        return false;
    }
    public boolean blueChasePac(){
        int max,min;
        if(blueGhostRow==pacmanRow){
            if(blueGhostCol>pacmanCol){
                max=blueGhostCol;
                min=pacmanCol;
            }else{
                max=pacmanCol;
                min=blueGhostCol;
            }
            for(int i=min+1;i<max;i++){
                int moveToPosition = map.get(Integer.toString(blueGhostRow) + "," + Integer.toString(i));
                if(walls.contains(moveToPosition)){
                    return false;
                }
            }
            blueChaseDir = (blueGhostCol>pacmanCol)?3:4;
            return true;
        }
        if(blueGhostCol==pacmanCol){
            if(blueGhostRow>pacmanRow){
                max=blueGhostRow;
                min=pacmanRow;
            }else{
                max=pacmanRow;
                min=blueGhostRow;
            }
            for(int i=min+1;i<max;i++){
                int moveToPosition = map.get(Integer.toString(i) + "," + Integer.toString(blueGhostCol));
                if(walls.contains(moveToPosition)){
                    return false;
                }
            }
            blueChaseDir = (blueGhostRow>pacmanRow)?1:2;
            return true;
        }
        return false;
    }
    public boolean redChasePac(){
        int max,min;
        if(redGhostRow==pacmanRow){
            if(redGhostCol>pacmanCol){
                max=redGhostCol;
                min=pacmanCol;
            }else{
                max=pacmanCol;
                min=redGhostCol;
            }
            for(int i=min+1;i<max;i++){
                int moveToPosition = map.get(Integer.toString(redGhostRow) + "," + Integer.toString(i));
                if(walls.contains(moveToPosition)){
                    return false;
                }
            }
            redChaseDir = (redGhostCol>pacmanCol)?3:4;
            return true;
        }
        if(redGhostCol==pacmanCol){
            if(redGhostRow>pacmanRow){
                max=redGhostRow;
                min=pacmanRow;
            }else{
                max=pacmanRow;
                min=redGhostRow;
            }
            for(int i=min+1;i<max;i++){
                int moveToPosition = map.get(Integer.toString(i) + "," + Integer.toString(redGhostCol));
                if(walls.contains(moveToPosition)){
                    return false;
                }
            }
            redChaseDir = (redGhostRow>pacmanRow)?1:2;
            return true;
        }
        return false;
    }
    public boolean orangeChasePac(){
        int max,min;
        if(orangeGhostRow==pacmanRow){
            if(orangeGhostCol>pacmanCol){
                max=orangeGhostCol;
                min=pacmanCol;
            }else{
                max=pacmanCol;
                min=orangeGhostCol;
            }
            for(int i=min+1;i<max;i++){
                int moveToPosition = map.get(Integer.toString(orangeGhostRow) + "," + Integer.toString(i));
                if(walls.contains(moveToPosition)){
                    return false;
                }
            }
            orangeChaseDir = (orangeGhostCol>pacmanCol)?3:4;
            return true;
        }
        if(orangeGhostCol==pacmanCol){
            if(orangeGhostRow>pacmanRow){
                max=orangeGhostRow;
                min=pacmanRow;
            }else{
                max=pacmanRow;
                min=orangeGhostRow;
            }
            for(int i=min+1;i<max;i++){
                int moveToPosition = map.get(Integer.toString(i) + "," + Integer.toString(orangeGhostCol));
                if(walls.contains(moveToPosition)){
                    return false;
                }
            }
            orangeChaseDir = (orangeGhostRow>pacmanRow)?1:2;
            return true;
        }
        return false;
    }

    public int redRandomMove(){
        // 1 is move from Top, 2 is move from Bottom, 3 is move from left, 4 is move from right
        int max,min;
        if(redPreDir==1||redPreDir==2){
            max=4;
            min=3;
        }else{
            max=2;
            min=1;
        }
        int random = new Random().nextInt((max - min) + 1) + min;
        return random;
    }
    public int blueRandomMove(){
        // 1 is move from Top, 2 is move from Bottom, 3 is move from left, 4 is move from right
        int max,min;
        if(bluePreDir==1||bluePreDir==2){
            max=4;
            min=3;
        }else{
            max=2;
            min=1;
        }
        int random = new Random().nextInt((max - min) + 1) + min;
        return random;
    }
    public int orangeRandomMove(){
        // 1 is move from Top, 2 is move from Bottom, 3 is move from left, 4 is move from right
        int max,min;
        if(orangePreDir==1||orangePreDir==2){
            max=4;
            min=3;
        }else{
            max=2;
            min=1;
        }
        int random = new Random().nextInt((max - min) + 1) + min;
        return random;
    }
    public int pinkRandomMove(){
        // 1 is move from Top, 2 is move from Bottom, 3 is move from left, 4 is move from right
        int max,min;
        if(pinkPreDir==1||pinkPreDir==2){
            max=4;
            min=3;
        }else{
            max=2;
            min=1;
        }
        int random = new Random().nextInt((max - min) + 1) + min;
        return random;
    }

    public void redGhostAnimationUp(ImageView imv){
        redGhostAnimateHandler.removeCallbacks(redGhostAnimateRunnable);
        redGhostAnimateRunnable = new Runnable(){
            public void run() {
                if(rgPicture==0){
                    imv.setImageResource(R.drawable.gru1);
                    rgPicture=1;
                }
                else if(rgPicture==1){
                    imv.setImageResource(R.drawable.gru2);
                    rgPicture=0;
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
                    rgPicture=1;
                }
                else if(rgPicture==1){
                    imv.setImageResource(R.drawable.grd2);
                    rgPicture=0;
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
                    rgPicture=1;
                }
                else if(rgPicture==1){
                    imv.setImageResource(R.drawable.grl2);
                    rgPicture=0;
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
                    rgPicture=1;
                }
                else if(rgPicture==1){
                    imv.setImageResource(R.drawable.grr2);
                    rgPicture=0;
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
                if(pgPicture==0){
                    imv.setImageResource(R.drawable.gpu1);
                    pgPicture=1;
                }
                else if(pgPicture==1){
                    imv.setImageResource(R.drawable.gpu2);
                    pgPicture=0;
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
                if(pgPicture==0){
                    imv.setImageResource(R.drawable.gpd1);
                    pgPicture=1;
                }
                else if(pgPicture==1){
                    imv.setImageResource(R.drawable.gpd2);
                    pgPicture=0;
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
                if(pgPicture==0){
                    imv.setImageResource(R.drawable.gpl1);
                    pgPicture=1;
                }
                else if(pgPicture==1){
                    imv.setImageResource(R.drawable.gpl2);
                    pgPicture=0;
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
                if(pgPicture==0){
                    imv.setImageResource(R.drawable.gpr1);
                    pgPicture=1;
                }
                else if(pgPicture==1){
                    imv.setImageResource(R.drawable.gpr2);
                    pgPicture=0;
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
                if(bgPicture==0){
                    imv.setImageResource(R.drawable.gbu1);
                    bgPicture=1;
                }
                else if(bgPicture==1){
                    imv.setImageResource(R.drawable.gbu2);
                    bgPicture=0;
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
                if(bgPicture==0){
                    imv.setImageResource(R.drawable.gbd1);
                    bgPicture=1;
                }
                else if(bgPicture==1){
                    imv.setImageResource(R.drawable.gbd2);
                    bgPicture=0;
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
                if(bgPicture==0){
                    imv.setImageResource(R.drawable.gbl1);
                    bgPicture=1;
                }
                else if(bgPicture==1){
                    imv.setImageResource(R.drawable.gbl2);
                    bgPicture=0;
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
                if(bgPicture==0){
                    imv.setImageResource(R.drawable.gbr1);
                    bgPicture=1;
                }
                else if(bgPicture==1){
                    imv.setImageResource(R.drawable.gbr2);
                    bgPicture=0;
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
                if(ogPicture==0){
                    imv.setImageResource(R.drawable.gou1);
                    ogPicture=1;
                }
                else if(ogPicture==1){
                    imv.setImageResource(R.drawable.gou2);
                    ogPicture=0;
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
                if(ogPicture==0){
                    imv.setImageResource(R.drawable.god1);
                    ogPicture=1;
                }
                else if(ogPicture==1){
                    imv.setImageResource(R.drawable.god2);
                    ogPicture=0;
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
                if(ogPicture==0){
                    imv.setImageResource(R.drawable.gol1);
                    ogPicture=1;
                }
                else if(ogPicture==1){
                    imv.setImageResource(R.drawable.gol2);
                    ogPicture=0;
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
                if(ogPicture==0){
                    imv.setImageResource(R.drawable.gor1);
                    ogPicture=1;
                }
                else if(ogPicture==1){
                    imv.setImageResource(R.drawable.gor2);
                    ogPicture=0;
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
                    if(getScore(moveToPosition)){
                        usePostDeplay = false;
                    }
                }else{
                    int pacmanPosition = map.get(Integer.toString(pacmanRow) + "," + Integer.toString(pacmanCol));
                    ImageView imv = (ImageView) cells.getChildAt(pacmanPosition);
                    pacAnimationUp(imv);
                }
                if(!meetGhost()){
                    ghostMove(cells);
                    meetGhost();
                }
                if(usePostDeplay){
                    pacMoveHandler.postDelayed(this, moveInterval);
                }
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
                        if(getScore(moveToPosition)){
                            usePostDeplay = false;
                        }
                    }else{
                        int pacmanPosition = map.get(Integer.toString(pacmanRow) + "," + Integer.toString(pacmanCol));
                        ImageView imv = (ImageView) cells.getChildAt(pacmanPosition);
                        pacAnimationRight(imv);
                    }
                }
                if(!meetGhost()){
                    ghostMove(cells);
                    meetGhost();
                }
                if(usePostDeplay){
                    pacMoveHandler.postDelayed(this, moveInterval);
                }
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
                        if(getScore(moveToPosition)){
                            usePostDeplay = false;
                        }
                    }else{
                        int pacmanPosition = map.get(Integer.toString(pacmanRow) + "," + Integer.toString(pacmanCol));
                        ImageView imv = (ImageView) cells.getChildAt(pacmanPosition);
                        pacAnimationLeft(imv);
                    }
                }
                if(!meetGhost()){
                    ghostMove(cells);
                    meetGhost();
                }
                if(usePostDeplay){
                    pacMoveHandler.postDelayed(this, moveInterval);
                }
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
                    if(getScore(moveToPosition)){
                        usePostDeplay = false;
                    }
                }else{
                    int pacmanPosition = map.get(Integer.toString(pacmanRow) + "," + Integer.toString(pacmanCol));
                    ImageView imv = (ImageView) cells.getChildAt(pacmanPosition);
                    pacAnimationDown(imv);
                }
                if(!meetGhost()){
                    ghostMove(cells);
                    meetGhost();
                }
                if(usePostDeplay){
                    pacMoveHandler.postDelayed(this, moveInterval);
                }
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
    public boolean getScore(int position){
            if(foods.contains(Integer.toString(position))){
                foods.remove(Integer.toString(position));
                score+=1;
                TextView scoreTextView = findViewById(R.id.score);
                scoreTextView.setText(Integer.toString(score));
            }
            if(win()){
                AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
                b.setTitle("You fucking won #@!#!@");
                b.setMessage("Play again?");
                b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        recreate();
                    }
                });
                AlertDialog al = b.create();
                al.show();
                return true;
            }
            return false;
    }
    public boolean win(){
        if(foods.isEmpty()){
            destroyRunnable();
            return true;
        }
        return false;
    }
    public boolean meetGhost(){
        if((pacmanRow==redGhostRow&&pacmanCol==redGhostCol)||
                (pacmanRow==pinkGhostRow&&pacmanCol==pinkGhostCol)||
                (pacmanRow==blueGhostRow&&pacmanCol==blueGhostCol)||
                (pacmanRow==orangeGhostRow&&pacmanCol==orangeGhostCol)
        ){
            usePostDeplay = false;
            AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
            b.setTitle("YOU LOSE TT_TT");
            b.setMessage("Play again?");
            b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    recreate();
                }
            });
            AlertDialog al = b.create();
            al.show();
            destroyRunnable();
            return true;
        }
        return false;
    }
}