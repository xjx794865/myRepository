package com.zxhy.webservice.test;

public class key {

	public static void main(String[] args) {
		String s = "23fs-wq23-f-2f222";
		int k = 3;
		// ������-��ɿո�
		String ss = s.replaceAll("-", "");
		// �õ�ת��֮����ַ���.ȫ�����ɴ�д
		String news = ss.toUpperCase();

		// ����ַ����ĳ��� �� ȡ��ĳ���
		int len = news.length();
		int tep = len % k;
		System.out.println("length-----" + len);
		System.out.println(len / k);

		System.out.println(tep);

		// ���崦��֮����ַ���
		StringBuffer sb = new StringBuffer();

		// ��ȡ�ַ�������ʼλ�úͿ�ʼλ��
		int sttr = 0;
		int stto = 0;

		// ��ȡһ���ַ���
		String startstr = "";
		if (tep != 0) {
			startstr = news.substring(0, tep);
			//System.out.println(startstr);

			if (startstr != "") {
				sb.append(startstr);
				sb.append("-");
				sttr = tep;
			}

			// ѭ��ʣ����ַ���
			for (int i = 1; i <= len / k; i++) {
				stto = i * k + 2 ;
				sb.append(news.substring(sttr, stto));
				sb.append("-");
				System.out.println("sttr=============" + sttr + "-------------" + "stto===============" + stto);
				System.out.println(sb);
				sttr = stto;
			}

		} else if (tep == 0) {
			// ѭ��ʣ����ַ���
			for (int i = 1; i <= len / k; i++) {
				stto = i * k;
				sb.append(news.substring(sttr, stto));
				sb.append("-");
				System.out.println("sttr=============" + sttr + "-------------" + "stto===============" + stto);
				sttr = stto;
			}

		}

		System.out.println(sb.substring(0, sb.length() - 1));
		System.out.println(sb);
	}

}
