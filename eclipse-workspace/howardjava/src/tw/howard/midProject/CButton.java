package tw.howard.midProject;

import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JButton;

public class CButton extends JButton{
	public void start() {
		int nums = 52;
		LinkedList<Integer> player1 = new LinkedList<>();
		LinkedList<Integer> player2 = new LinkedList<>();
		LinkedList<Integer> player3 = new LinkedList<>();
		LinkedList<Integer> player4 = new LinkedList<>();

		int[] poker = new int[nums]; // 洗牌從51到1
		for (int i = 0; i < 4; i++) // poker[i] = i;
		{
			for (int j = 0; j < 13; j++) {
				poker[i * 13 + j] = j + 1;
			}
		}
		for (int k = nums - 1; k > 0; k--) {
			int rand = (int) (Math.random() * (k + 1));
			// poker[rand] 交換 poker[i]
			int temp = poker[rand];
			poker[rand] = poker[k];
			poker[k] = temp;
		}
		for (int a : poker) {
			System.out.println(a);
		}
		for (int a = 0; a < 52; a++) {
			if (a % 4 == 0) {
				player1.add(poker[a]);
			} else if (a % 4 == 1) {
				player2.add(poker[a]);
			} else if (a % 4 == 2) {
				player3.add(poker[a]);
			} else {
				player4.add(poker[a]);
			}
		}
		
		
		
	}
}
