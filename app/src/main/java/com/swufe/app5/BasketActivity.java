package com.swufe.app5;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class BasketActivity extends AppCompatActivity implements View.OnClickListener {
    //定义数组存放加分
    private  final  int scoreArray[]={3,2,1};
    private  int lastScore_a,lastScore_b,score_a,score_b;
    private Button btna_1,btna_2,btna_3,btnb_1,btnb_2,btnb_3;
    private ImageView pic_cancel,pic_reset;
    private TextView test_score_a,test_score_b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basket_layout);
        inintView();
    }

    private void inintView() {
        //获取按钮id
        btna_1=findViewById(R.id.btna_1);
        btna_2=findViewById(R.id.btna_2);
        btna_3=findViewById(R.id.btna_3);
        btnb_1=findViewById(R.id.btnb_1);
        btnb_2=findViewById(R.id.btnb_2);
        btnb_3=findViewById(R.id.btnb_3);


        //得分情况和清零
        pic_cancel=findViewById(R.id.pic_cancel);
        pic_reset=findViewById(R.id.pic_reset);
        test_score_a=findViewById(R.id.score_a);
        test_score_b=findViewById(R.id.score_b);

        //按钮实现监听
        btna_1.setOnClickListener(this);
        btna_2.setOnClickListener(this);
        btna_3.setOnClickListener(this);
        btnb_1.setOnClickListener(this);
        btnb_2.setOnClickListener(this);
        btnb_3.setOnClickListener(this);
        pic_reset.setOnClickListener(this);
        pic_cancel.setOnClickListener(this);
    }


    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btna_1:
                scoreAdd(0,scoreArray[0]);

                break;
            case R.id.btna_2:
                scoreAdd(0,scoreArray[1]);

                break;
            case R.id.btna_3:
                scoreAdd(0,scoreArray[2]);

                break;
            case  R.id.btnb_1:
                scoreAdd(1,scoreArray[0]);

                break;
            case  R.id.btnb_2:
                scoreAdd(1,scoreArray[1]);

                break;
            case  R.id.btnb_3:
                scoreAdd(1,scoreArray[2]);

                break;
            case  R.id.pic_reset:
                reset();
                break;
            case  R.id.pic_cancel:
                cancel();
                break;
            default:
                break;
        }
    }

    //取消上次加分操作
    private void cancel() {
        if (score_a!=0&&score_a-lastScore_a>=0){
            score_a-=lastScore_a;
        }
        if (score_b!=0&&score_b-lastScore_b>=0){
            score_b-=lastScore_b;
        }
        ShowText();
    }
    //重置，清零功能
    private void reset() {
        score_a=0;
        score_b=0;
        ShowText();
    }

    private   void  scoreAdd(int Tage,int score){
        //Tage:   0:a  1:b
        if (Tage==0||Tage==1){
            if (Tage==0){

                lastScore_b=0;
                lastScore_a=score;
                score_a+=lastScore_a;
            }else if (Tage==1){

                lastScore_a=0;
                lastScore_b=score;
                score_b+=lastScore_b;
            }
            ShowText();
        }
    }
    //进行显示
    private  void ShowText(){
        test_score_a.setText(Integer.toString(score_a));
        test_score_b.setText(Integer.toString(score_b));
    }

    private  void open(View V){
        test_score_a.setText(Integer.toString(score_a));
        test_score_b.setText(Integer.toString(score_b));
    }

}

