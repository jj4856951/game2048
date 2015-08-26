package com.zjw.game2048;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

public class GameView extends GridLayout {
	private float startX, startY, offsetX, offsetY;

	public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initGameView();
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initGameView();
	}

	public GameView(Context context) {
		super(context);
		initGameView();
	}

	public void initGameView() {
		setColumnCount(4);
		setBackgroundColor(0xffbbada0);

		setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startX = event.getX();
					startY = event.getY();
					break;

				case MotionEvent.ACTION_UP:
					offsetX = event.getX() - startX;
					offsetY = event.getY() - startY;

					if (Math.abs(offsetX) > Math.abs(offsetY)) {
						// 水平移动
						if (offsetX < -5) {
							swipe_left();
						} else if (offsetX > 5) {
							swipe_right();
						}

					} else {
						// 垂直移动
						if (offsetY < -5) {
							swipe_up();
						} else if (offsetY > 5) {
							swipe_down();
						}

					}

					break;
				}
				return true;// 只能返回true，如果返回false的话，moveup就不起作用了。
			}
		});
	}

	public void swipe_left() {
		boolean merge = false;

		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {

				for (int x1 = x + 1; x1 < 4; x1++) {
					if (cards[x1][y].getNum() > 0) {

						if (cards[x][y].getNum() <= 0) {
							cards[x][y].setNum(cards[x1][y].getNum());
							cards[x1][y].setNum(0);
							merge = true;
							x--;
						} else if (cards[x][y].equals(cards[x1][y])) {
							cards[x][y].setNum(cards[x][y].getNum() * 2);
							cards[x1][y].setNum(0);
							MainActivity.getMainActivity().add_score(cards[x][y].getNum());
							merge = true;
						}
						break;
					}
				}
			}
		}
		if (merge) {
			addRandom();
			checkGameover();
		}
	}

	public void swipe_right() {
		boolean merge = false;

		for (int y = 0; y < 4; y++) {
			for (int x = 3; x >= 0; x--) {

				for (int x1 = x - 1; x1 >= 0; x1--) {
					if (cards[x1][y].getNum() > 0) {

						if (cards[x][y].getNum() <= 0) {
							cards[x][y].setNum(cards[x1][y].getNum());
							cards[x1][y].setNum(0);
							merge = true;
							x++;
						} else if (cards[x][y].equals(cards[x1][y])) {
							cards[x][y].setNum(cards[x][y].getNum() * 2);
							cards[x1][y].setNum(0);
							MainActivity.getMainActivity().add_score(cards[x][y].getNum());
							merge = true;
						}
						break;
					}
				}
			}
		}
		if (merge) {
			addRandom();
			checkGameover();
		}
	}

	public void swipe_up() {
		boolean merge = false;

		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {

				for (int y1 = y + 1; y1 < 4; y1++) {
					if (cards[x][y1].getNum() > 0) {

						if (cards[x][y].getNum() <= 0) {
							cards[x][y].setNum(cards[x][y1].getNum());
							cards[x][y1].setNum(0);
							merge = true;
							y--;
						} else if (cards[x][y].equals(cards[x][y1])) {
							cards[x][y].setNum(cards[x][y].getNum() * 2);
							cards[x][y1].setNum(0);
							MainActivity.getMainActivity().add_score(cards[x][y].getNum());
							merge = true;
						}
						break;
					}
				}
			}
		}
		if (merge) {
			addRandom();
			checkGameover();
		}
	}

	public void swipe_down() {
		boolean merge = false;

		for (int x = 0; x < 4; x++) {
			for (int y = 3; y >= 0; y--) {

				for (int y1 = y - 1; y1 >= 0; y1--) {
					if (cards[x][y1].getNum() > 0) {

						if (cards[x][y].getNum() <= 0) {
							cards[x][y].setNum(cards[x][y1].getNum());
							cards[x][y1].setNum(0);
							merge = true;
							y++;
						} else if (cards[x][y].equals(cards[x][y1])) {
							cards[x][y].setNum(cards[x][y].getNum() * 2);
							cards[x][y1].setNum(0);
							MainActivity.getMainActivity().add_score(cards[x][y].getNum());
							merge = true;
						}
						break;
					}
				}
			}
		}
		if (merge) {
			addRandom();
			checkGameover();
		}
	}

	public void checkGameover() {
		boolean gameover = true;
		All: for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				if (cards[x][y].getNum() == 0 
						|| (x > 0 && cards[x][y].equals(cards[x - 1][y]))
						//下面的是要小于3，写4就越界了。
						|| (x < 3 && cards[x][y].equals(cards[x + 1][y]))
						|| (y > 0 && cards[x][y].equals(cards[x][y - 1]))
						|| (y < 3 && cards[x][y].equals(cards[x][y + 1]))) {
					gameover = false;
					break All;
				}
			}
		}
		if (gameover) {
			new AlertDialog.Builder(getContext()).setTitle("提示").setMessage("游戏结束！")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							startGame();
						}
					}).show();
		}
	}

	public void addRandom() {
		// 整一个得到所有点的list，并清空
		emptyPoints.clear();

		// 遍历行列，全部形成list
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				if (cards[x][y].getNum() <= 0) {
					emptyPoints.add(new Point(x, y));
				}
			}
		}

		Point p = emptyPoints.remove(((int) (Math.random() * emptyPoints.size())));
		cards[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4);

	}

	// 将卡片添加到布局中，先要适配手机屏幕,同时需要指定竖屏丅
	// android:screenOrientation="portrait"
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		// 设置小方块的边长
		int card_width = (Math.min(w, h) - 10) / 4;
		addCard(card_width, card_width);
		startGame();
	}

	private void startGame() {
		MainActivity.getMainActivity().clear_score();
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				cards[x][y].setNum(0);
			}
		}

		addRandom();
		addRandom();
	}

	private void addCard(int w, int h) {
		Card c;

		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				c = new Card(getContext());
				c.setNum(0);
				addView(c, w, h);

				cards[x][y] = c;
			}
		}
	}

	private Card[][] cards = new Card[4][4];
	private List<Point> emptyPoints = new ArrayList<>();
}
