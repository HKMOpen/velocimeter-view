package com.github.glomadrian.velocimeter;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import com.github.glomadrian.velocimeterlibrary.VelocimeterView;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

  //runs without a timer by reposting this handler at the end of the runnable
  Handler timerHandler = new Handler();
  Runnable timerRunnable = new Runnable() {

    @Override
    public void run() {
      velocimeter.setValue((speed * 1.6 > 100)?100:speed * 1.6f, true);
      velocimeter2.setValue(speed, true);

      timerHandler.postDelayed(this, 500);
    }
  };
  private SeekBar seek;
  private VelocimeterView velocimeter;
  private VelocimeterView velocimeter2;
  private SocketHandler handler;
  private int speed;

  void updateSpeed(int speed) {
    this.speed = speed;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    seek = (SeekBar) findViewById(R.id.seek);
    seek.setOnSeekBarChangeListener(new SeekListener());
    velocimeter = (VelocimeterView) findViewById(R.id.velocimeter);
    velocimeter2 = (VelocimeterView) findViewById(R.id.velocimeter2);
    handler = new SocketHandler("{'auth':'AuthMagic', 'item': {'action':'SUBSCRIBE', 'signal':{'path':'root.engine.speed'}}}", this);
    timerHandler.postDelayed(timerRunnable, 0);
  }

  private class SeekListener implements SeekBar.OnSeekBarChangeListener {

    @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//      velocimeter.setValue(speed, true);
//      velocimeter2.setValue(speed, true);
    }

    @Override public void onStartTrackingTouch(SeekBar seekBar) {
      //Empty
    }

    @Override public void onStopTrackingTouch(SeekBar seekBar) {
      //Empty
    }
  }
}
