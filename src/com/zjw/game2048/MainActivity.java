package com.zjw.game2048;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView tv_score;
	private static MainActivity mainActivity = null;
	private static int score;
	
	public MainActivity() {
		mainActivity = this;
	}
	public void clear_score(){
		score = 0;
		show_score();
	}
	public void add_score(int num){
		score += num;
		show_score();
	}
	public void show_score(){
		tv_score.setText(score+"");
	}
	public static MainActivity getMainActivity() {
		return mainActivity;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv_score = (TextView) findViewById(R.id.tv_score);
	}

}
