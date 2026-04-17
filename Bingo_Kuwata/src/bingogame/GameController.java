package bingogame;

import java.util.Scanner;

public class GameController {

	/**ゲームの開始*/
	public void start() {
		Scanner scanner = new Scanner(System.in);
		String input = "";

		do {
			//毎ゲーム、新しいカードと数字（ボール）を生成
			Card card = new Card();
			BallBox box = new BallBox();
			int turn = 1;

			System.out.println("--- ビンゴゲーム開始 ---");

			while (true) {
				//下記2行を無効にすると終了まで自動で抽選を繰り返す
				System.out.println("Enterで抽選");
				scanner.nextLine();

				int ball = box.drawBall();

				//出た数字（ボール）と現在の抽選回数を表示
				System.out.println("ball[" + turn + "]:" + ball);

				card.openCard(ball);
				card.printCard();

				turn++;

				/**[0]:リーチ数、[1]:ビンゴ数 */
				int[] results = card.checkBingo();
				int reachCount = results[0];
				int bingoCount = results[1];

				//一度数字（ボール）を出すたびに現在のリーチ数、ビンゴ数を表示
				System.out.println("");
				System.out.println("REACH: " + reachCount);
				System.out.println("BINGO: " + bingoCount);
				System.out.println("--------------------\n");

				//同時に複数ラインがビンゴになった場合も考慮するため
				if (bingoCount >= Card.FINISH_LINES) {
					break;
				}
			}
			System.out.println("--- ビンゴゲーム終了 ---");
			while (true) {
				System.out.print("もう一度ゲームをする ");
				System.out.print("はい (y) / いいえ(n) > ");
				input = scanner.nextLine().toLowerCase();

				if (input.equals("y") || input.equals("n")) {
					break;
				}
				System.out.println("無効な入力です。y か n を入力してください。");
			}

		} while (input.equals("y"));

		scanner.close();
	}
}
