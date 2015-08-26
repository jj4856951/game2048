package com.zjw.game2048;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Card extends FrameLayout{
	private int num = 0;
	private TextView lable;
	
	public Card(Context context) {
		super(context);
		lable = new TextView(getContext());
		lable.setTextSize(32);
		lable.setBackgroundColor(0x33ffffff);
		lable.setGravity(Gravity.CENTER);
		LayoutParams layoutParams = new LayoutParams(-1, -1);//�����������������
		layoutParams.setMargins(10, 10, 0, 0);
		addView(lable, layoutParams);
		
		setNum(0);
	}
	

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
		if (num <= 0) {
			lable.setText("");
		}else{			
			lable.setText(num+"");
		}
	}
	
	//�ж�һ�ſ�Ƭ����һ�ſ�Ƭ�Ƿ���ͬ
	public boolean equals(Card o) {
		return this.getNum() == o.getNum();
	}
	

	

}
