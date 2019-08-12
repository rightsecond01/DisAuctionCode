package com.teacherPuzzle;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class PuzzleGame extends JFrame implements ActionListener {

	JPanel global = new JPanel();
	JPanel center = new JPanel();
	JPanel south = new JPanel();
	JMenuBar mb = new JMenuBar(); // �޴��ٻ���
	JMenu m1 = new JMenu("����");
	JMenuItem m11 = new JMenuItem("���ӽ���");
	JMenuItem m12 = new JMenuItem("������");
	JMenu m3 = new JMenu("����");
	JMenuItem m31 = new JMenuItem("�����̵������̶�?");
	JMenuItem m32 = new JMenuItem("�����̵�");

	// center panel button
	JButton b1 = new JButton("1");
	JButton b2 = new JButton("2");
	JButton b3 = new JButton("3");
	JButton b4 = new JButton("4");
	JButton b5 = new JButton("5");
	JButton b6 = new JButton("6");
	JButton b7 = new JButton("7");
	JButton b8 = new JButton("8");
	JButton blank = new JButton("");

	// south panel button
	JButton start = new JButton("START!");
	JButton hint = new JButton("HINT");
	JButton exit = new JButton("EXIT");
	JButton tmp = new JButton("");

	// ����
	JPanel p3 = new JPanel(new GridLayout(6, 1));
	JDialog d = new JDialog(this, "�����̵� �����̶�?", true);
	JLabel d_la0 = new JLabel(" ");
	JLabel d_la = new JLabel("        ���̵��� ���� ���� ������ �����Ͽ� 1���� ���ʴ�� �迭�ϴ� ������,");
	JLabel d_la1 = new JLabel("       ������ �׸����� ������ ������ �׸��� ����� �׸������� �����ϴ�.");
	JLabel d_la2 = new JLabel("       �������� ��� Lucky 7, 8-Square, 15-Square, 14-15��尡 �ִµ�,");
	JLabel d_la3 = new JLabel("       ���� �������� 8-Square��带 �����Ͽ����ϴ�.");
	JLabel d_la4 = new JLabel(" ");

	// �����̵�
	JPanel p4 = new JPanel(new GridLayout(6, 1));
	JDialog d1 = new JDialog(this, "�����̵�...", true);
	JLabel d1_mk0 = new JLabel(" ");
	JLabel d1_mk = new JLabel("      �����̵����� Ver 1.0");
	JLabel d1_mk4 = new JLabel(" ");

	// ����
	JPanel p5 = new JPanel(new GridLayout(7, 1)); // �����̵�...�� ���̾�α��гλ���
	JDialog d2 = new JDialog(this, "�����̵�����", true);
	JLabel d2_mk0 = new JLabel("     ����������������"); // ���̾�α� ��
	JLabel d2_mk1 = new JLabel(""); // ���̾�α� ��
	JLabel d2_mk2 = new JLabel("              ��  ��  ��  ��  ��."); // ���̾�α� ��
	JLabel d2_mk3 = new JLabel(""); // ���̾�α� ��
	JLabel d2_mk4 = new JLabel("     ����������������"); // ���̾�α� ��
	JLabel d2_mk5 = new JLabel(""); // ���̾�α� ��
	JButton d2_bu = new JButton(" Ȯ  �� "); // ���̾�α� ��
	JDialog d_help = new JDialog(this, "Help", true); // ���̾�α� ����

	// �ٵ��α׹� ��ư�� ���ڼӼ� ����
	Font f = new Font("SansSerif", Font.BOLD, 30);
	Font g = new Font("SansSerif", Font.BOLD, 12);

	PuzzleGame() {
		super("�����̵� ����");

		// �����ӿ� �۷ι� �г��� add
		getContentPane().add(global);

		global.setLayout(new BorderLayout());
		global.add("Center", center);
		global.add("South", south);

		setJMenuBar(mb);
		mb.add(m1);
		m1.add(m11);
		m1.addSeparator();
		m1.add(m12);
		mb.add(m3);
		m3.add(m31);
		m3.addSeparator();
		m3.add(m32);

		center.setLayout(new GridLayout(3, 3));
		b2.addActionListener(this);
		tmp.addActionListener(this);
		center.add(b1);
		center.add(b2);
		center.add(b3);
		center.add(b4);
		center.add(b5);
		center.add(b6);
		center.add(b7);
		center.add(b8);
		center.add(blank);
		south.setLayout(new GridLayout(1, 4, 2, 0));
		south.add(start);
		south.add(hint);
		south.add(exit);

		// ��ư�� �׼�
		m11.addActionListener(this);
		m12.addActionListener(this);
		m31.addActionListener(this);
		m32.addActionListener(this);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		b6.addActionListener(this);
		b7.addActionListener(this);
		b8.addActionListener(this);
		blank.addActionListener(this);
		d2_bu.addActionListener(this);
		start.addActionListener(this);
		hint.addActionListener(this);
		exit.addActionListener(this);

		// ��Ʋ�� ���ڼӼ�
		b1.setFont(f);
		b2.setFont(f);
		b3.setFont(f);
		b4.setFont(f);
		b5.setFont(f);
		b6.setFont(f);
		b7.setFont(f);
		b8.setFont(f);
		blank.setFont(f);
		d2_mk0.setFont(g);
		d2_mk1.setFont(g);
		d2_mk2.setFont(g);
		d2_mk3.setFont(g);
		d2_mk4.setFont(g);
		d2_mk5.setFont(g);
		d1_mk0.setFont(g);
		d1_mk.setFont(g);
		d1_mk4.setFont(g);
		d_la0.setFont(g);
		d_la.setFont(g);
		d_la1.setFont(g);
		d_la2.setFont(g);
		d_la3.setFont(g);
		d_la4.setFont(g);

		// �������� �Ӽ�
		setBounds(300, 100, 400, 400);
		setVisible(true);
		setResizable(false); // ����ڰ� ���Ƿ� ������ ����� �ٲ��� ���ϰ���.

	}

	// �׼� �̺�Ʈ ȣ��
	public void actionPerformed(ActionEvent event) {

		if (event.getSource() == exit || event.getSource() == m12) {
			System.exit(0);
		} else if (event.getSource() == m31) {
			da_popup();
		} else if (event.getSource() == m32) {
			da_popup1();
		} else if (event.getSource() == b1) {
			buclick1();
		} else if (event.getSource() == b2) {
			buclick2();
		} else if (event.getSource() == b3) {
			buclick3();
		} else if (event.getSource() == b4) {
			buclick4();
		} else if (event.getSource() == b5) {
			buclick5();
		} else if (event.getSource() == b6) {
			buclick6();
		} else if (event.getSource() == b7) {
			buclick7();
		} else if (event.getSource() == b8) {
			buclick8();
		} else if (event.getSource() == blank) {
			buclick9();
		} else if (event.getSource() == d2_bu) {
			random();
			d2.dispose();

		} else if (event.getSource() == hint) {
			g_Help();
		} else if (event.getSource() == start || event.getSource() == m11) {
			random();
		}

	}

	// ������ ���ڹ�ư�� ����ִ°����� �̵��ϱ�
	public void buclick1() {
		if (b2.getText().equals("")) {
			tmp.setText(b2.getText());
			b2.setText(b1.getText());
			b1.setText(tmp.getText());
		}

		else if (b4.getText().equals("")) {
			tmp.setText(b4.getText());
			b4.setText(b1.getText());
			b1.setText(tmp.getText());
		}

	}

	public void buclick2() {
		if (b1.getText().equals("")) {
			tmp.setText(b1.getText());
			b1.setText(b2.getText());
			b2.setText(tmp.getText());
		}

		else if (b3.getText().equals("")) {
			tmp.setText(b3.getText());
			b3.setText(b2.getText());
			b2.setText(tmp.getText());
		}

		else if (b5.getText().equals("")) {
			tmp.setText(b5.getText());
			b5.setText(b2.getText());
			b2.setText(tmp.getText());
		}
	}

	public void buclick3() {

		if (b2.getText().equals("")) {
			tmp.setText(b2.getText());
			b2.setText(b3.getText());
			b3.setText(tmp.getText());
		}

		else if (b6.getText().equals("")) {
			tmp.setText(b6.getText());
			b6.setText(b3.getText());
			b3.setText(tmp.getText());
		}
	}

	public void buclick4() {
		if (b1.getText().equals("")) {
			tmp.setText(b1.getText());
			b1.setText(b4.getText());
			b4.setText(tmp.getText());
		}

		else if (b5.getText().equals("")) {
			tmp.setText(b5.getText());
			b5.setText(b4.getText());
			b4.setText(tmp.getText());
		}

		else if (b7.getText().equals("")) {
			tmp.setText(b7.getText());
			b7.setText(b4.getText());
			b4.setText(tmp.getText());
		}
	}

	public void buclick5() {
		if (b2.getText().equals("")) {
			tmp.setText(b2.getText());
			b2.setText(b5.getText());
			b5.setText(tmp.getText());
		}

		else if (b4.getText().equals("")) {
			tmp.setText(b4.getText());
			b4.setText(b5.getText());
			b5.setText(tmp.getText());
		}

		else if (b6.getText().equals("")) {
			tmp.setText(b6.getText());
			b6.setText(b5.getText());
			b5.setText(tmp.getText());
		}

		else if (b8.getText().equals("")) {
			tmp.setText(b8.getText());
			b8.setText(b5.getText());
			b5.setText(tmp.getText());
		}
	}

	public void buclick6() {
		if (b3.getText().equals("")) {
			tmp.setText(b3.getText());
			b3.setText(b6.getText());
			b6.setText(tmp.getText());
		}

		else if (b5.getText().equals("")) {
			tmp.setText(b5.getText());
			b5.setText(b6.getText());
			b6.setText(tmp.getText());
		}

		else if (blank.getText().equals("")) {
			tmp.setText(blank.getText());
			blank.setText(b6.getText());
			b6.setText(tmp.getText());
		}
	}

	public void buclick7() {
		if (b4.getText().equals("")) {
			tmp.setText(b4.getText());
			b4.setText(b7.getText());
			b7.setText(tmp.getText());
		}

		else if (b8.getText().equals("")) {
			tmp.setText(b8.getText());
			b8.setText(b7.getText());
			b7.setText(tmp.getText());
		}
	}

	public void buclick8() {
		if (b7.getText().equals("")) {
			tmp.setText(b7.getText());
			b7.setText(b8.getText());
			b8.setText(tmp.getText());
		} else if (b5.getText().equals("")) {
			tmp.setText(b5.getText());
			b5.setText(b8.getText());
			b8.setText(tmp.getText());
		} else if (blank.getText().equals("")) {
			tmp.setText(blank.getText());
			blank.setText(b8.getText());
			b8.setText(tmp.getText());
		}
	}

	public void buclick9() {
		if (b6.getText().equals("")) {
			tmp.setText(b6.getText());
			b6.setText(blank.getText());
			blank.setText(tmp.getText());
		}

		else if (b8.getText().equals("")) {
			tmp.setText(b8.getText());
			b8.setText(blank.getText());
			blank.setText(tmp.getText());
		}
		if (b1.getText().equals("1") && b2.getText().equals("2") && b3.getText().equals("3") && b4.getText().equals("4")
				&& b5.getText().equals("5") && b6.getText().equals("6") && b7.getText().equals("7")
				&& b8.getText().equals("8")) {
			jung_popup();
		}
	}

	// ������ ���ָ� �˾�â ����
	public void jung_popup() {
		d2.getContentPane().add(p5);
		p5.add(d2_mk0);
		p5.add(d2_mk1);
		p5.add(d2_mk2);
		p5.add(d2_mk3);
		p5.add(d2_mk4);
		p5.add(d2_mk5);
		p5.add(d2_bu);
		d2.setBounds(400, 250, 200, 150);
		d2.setResizable(false);
		d2.show();

	}

	// HINT��ư�� Ŭ���ϸ� ������ ���̾�α�
	public void g_Help() {
		int in_row = 3;
		int in_col = 3;
		int cnt = 1;

		JPanel p_help = new JPanel(new GridLayout(in_row, in_col)); // �����̵�...�� ���̾�α��гλ���
		JButton btn_help[][] = new JButton[in_row][in_col];
		d_help.getContentPane().add(p_help);

		for (int i = 0; i < in_row; i++) {
			for (int j = 0; j < in_col; j++) {
				p_help.add(btn_help[i][j] = new JButton("" + cnt));
				if (cnt == (in_row * in_col) - 1) {
					break;
				}
				cnt++;
			}
		}
		// ���̾�α��� �Ӽ�
		d_help.setBounds(400, 250, 200, 250);
		d_help.setResizable(false);
		d_help.show();

	}

	// ���� �˾�
	public void da_popup() {
		d.getContentPane().add(p3);
		p3.add(d_la0);
		p3.add(d_la);
		p3.add(d_la1);
		p3.add(d_la2);
		p3.add(d_la3);
		p3.add(d_la4);
		d.setBounds(400, 250, 440, 200);
		d.setResizable(false);
		d.show();

	}

	// �����̵� �˾�
	public void da_popup1() {
		d1.getContentPane().add(p4);
		p4.add(d1_mk0);
		p4.add(d1_mk);
		p4.add(d1_mk4);

		d1.setBounds(400, 250, 280, 150);
		d1.setResizable(false);
		d1.show();
	}

	// ���� �Լ� �߻�
	public void random() {
		String back_at = "12345678";
		String btn_label[] = new String[9];
		int k1;
		int cnt = 8;

		Random ra = new Random();

		for (int a = 0; a < 8; a++) { // ��ư�� ���� �󺧰� �ֱ�
			k1 = ra.nextInt(cnt); // ���������� ���� ������ ����
			char c_at = back_at.charAt(k1); // ������ ���ڿ��� �������� ��ġ�ϴ� ���� ������ ����
			btn_label[a] = "" + c_at;

			// ���������� �޾ƿ� ���ڸ� �� �������� �̾Ƽ� ����
			back_at = back_at.substring(0, k1) + back_at.substring(k1 + 1, cnt);

			cnt--;
		}
		b1.setText(btn_label[0]);
		b2.setText(btn_label[1]);
		b3.setText(btn_label[2]);
		b4.setText(btn_label[3]);
		b5.setText(btn_label[4]);
		b6.setText(btn_label[5]);
		b7.setText(btn_label[6]);
		b8.setText(btn_label[7]);
		blank.setText("");
	}

	public static void main(String[] args) {
		PuzzleGame pg = new PuzzleGame();
//		System.out.print("test");

	}

}
