package bingogame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BallBox {
	/** 抽選する数字の最大値。カードの仕様（列数×各列の数字の範囲）から算出 */
	private static final int MAX_BALL_NUMBER = Card.COLUMN * Card.MAX_IN_COLUMN;
	private List<Integer> balls = new ArrayList<>();

	public BallBox() {
		if (MAX_BALL_NUMBER < Card.COLUMN * Card.MAX_IN_COLUMN) {
			throw new IllegalStateException("エラー：設定されたボール総数(" + MAX_BALL_NUMBER
					+ ")が、カードの数字の最大値(" + (Card.COLUMN * Card.MAX_IN_COLUMN) + ")を下回っています。");
		}
		for (int i = 1; i <= MAX_BALL_NUMBER; i++) {
			balls.add(i);
		}
		//ランダムに数字が出るように
		Collections.shuffle(balls);
	}

	//ボールの取り出しと表示
	public int drawBall() {
		//同じ数字が出ないように
		return balls.remove(0);
	}
}