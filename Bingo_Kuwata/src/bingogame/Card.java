package bingogame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Card {

	public static final int COLUMN = 5;
	public static final int ROW = 5;
	/** 数字配置のルール（1列当たりの数字の範囲）*/
	public static final int MAX_IN_COLUMN = 15;
	/** ゲーム終了となるビンゴ数 (この値で終了条件を調整可能。) 
	 *   最大値：ROW + COLUMN + (ROW == COLUMN ? 2 : 0)*/
	public static final int FINISH_LINES = ROW + COLUMN + (ROW == COLUMN ? 2 : 0);
	/** FREE扱いにする数字 (その他の０と混同させないため。0も抽選対象にする場合を考慮)*/
	public static final int FREE_NUMBER = 0;

	private final int[][] cardNumbers; //2次元配列で表現
	private final boolean[][] openedNumbers;

	/**カードの作成*/
	public Card() {
		if (COLUMN * MAX_IN_COLUMN >= 100) {
			throw new IllegalStateException("エラー：カードの数字の最大値が100を超えると表示が崩れます。");
		}
		if (FINISH_LINES <= 0 || FINISH_LINES > (ROW + COLUMN + (ROW == COLUMN ? 2 : 0))) {
			throw new IllegalStateException("終了条件(FINISH_LINES)の設定値が不正です。");
		}
		cardNumbers = new int[ROW][COLUMN];
		openedNumbers = new boolean[ROW][COLUMN];
		//カード内に同じ数字は出現せず、数字配置ルールに従って配置
		for (int j = 0; j < COLUMN; j++) {
			List<Integer> columnNumbers = new ArrayList<>();
			int top = (j * MAX_IN_COLUMN) + 1;
			for (int k = 0; k < MAX_IN_COLUMN; k++) {
				columnNumbers.add(top + k);
			}
			//カードの数字をランダム配置するように
			Collections.shuffle(columnNumbers);

			for (int i = 0; i < ROW; i++) {
				cardNumbers[i][j] = columnNumbers.get(i);
				openedNumbers[i][j] = false;
			}
		}
		//奇数の場合、中心をFREE（穴あき）に。偶数の場合中心がないため
		if (COLUMN % 2 != 0 && ROW % 2 != 0) {
			int freeRow = ROW / 2;
			int freeColumn = COLUMN / 2;
			cardNumbers[freeRow][freeColumn] = FREE_NUMBER;
			openedNumbers[freeRow][freeColumn] = true;
		}
	}

	/**カードの穴開け*/
	public void openCard(int ball) {
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COLUMN; j++) {
				if (cardNumbers[i][j] == ball) {
					openedNumbers[i][j] = true;
					return;
				}
			}
		}
	}

	/**カードの状態を表示*/
	public void printCard() {
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COLUMN; j++) {
				if (cardNumbers[i][j] == 0) {
					System.out.print("FREE");
				} else if (openedNumbers[i][j]) {
					// 穴が開いている場合は()付きで表示（元の数字を確認可）
					System.out.printf("(%02d)", cardNumbers[i][j]);
				} else {
					System.out.printf(" %02d ", cardNumbers[i][j]);
				}
			}
			System.out.println();
		}
	}

	/**リーチ・ビンゴ数の確認*/
	public int[] checkBingo() {
		int reachCount = 0;
		int bingoCount = 0;
		int count;

		//横のライン
		for (int i = 0; i < ROW; i++) {
			count = 0;
			for (int j = 0; j < COLUMN; j++) {
				if (openedNumbers[i][j])
					count++;
			}
			if (count == COLUMN) {
				bingoCount++;
			} else if (count == COLUMN - 1) {
				reachCount++;
			}
		}

		//縦のライン
		for (int j = 0; j < COLUMN; j++) {
			count = 0;
			for (int i = 0; i < ROW; i++) {
				if (openedNumbers[i][j])
					count++;
			}
			if (count == ROW) {
				bingoCount++;
			} else if (count == ROW - 1) {
				reachCount++;
			}
		}

		//斜めのライン
		if (ROW == COLUMN) {

			count = 0;
			for (int i = 0; i < ROW; i++) {
				if (openedNumbers[i][i])
					count++;
			}
			if (count == ROW) {
				bingoCount++;
			} else if (count == ROW - 1) {
				reachCount++;
			}

			count = 0;
			for (int i = 0; i < ROW; i++) {

				if (openedNumbers[i][(COLUMN - 1) - i])
					count++;
			}
			if (count == ROW) {
				bingoCount++;
			} else if (count == ROW - 1) {
				reachCount++;
			}
		}
		int[] results = { reachCount, bingoCount };
		return results;
	}

}
