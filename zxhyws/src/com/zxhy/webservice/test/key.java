package com.zxhy.webservice.test;

public class key {

	public static void main(String[] args) {
		String s = "23fs-wq23-f-2f222";
		int k = 3;
		// 将所有-变成空格
		String ss = s.replaceAll("-", "");
		// 得到转换之后的字符串.全部换成大写
		String news = ss.toUpperCase();

		// 获得字符串的长度 与 取余的长度
		int len = news.length();
		int tep = len % k;
		System.out.println("length-----" + len);
		System.out.println(len / k);

		System.out.println(tep);

		// 定义处理之后的字符串
		StringBuffer sb = new StringBuffer();

		// 截取字符串的起始位置和开始位置
		int sttr = 0;
		int stto = 0;

		// 获取一个字符串
		String startstr = "";
		if (tep != 0) {
			startstr = news.substring(0, tep);
			//System.out.println(startstr);

			if (startstr != "") {
				sb.append(startstr);
				sb.append("-");
				sttr = tep;
			}

			// 循环剩余的字符串
			for (int i = 1; i <= len / k; i++) {
				stto = i * k + 2 ;
				sb.append(news.substring(sttr, stto));
				sb.append("-");
				System.out.println("sttr=============" + sttr + "-------------" + "stto===============" + stto);
				System.out.println(sb);
				sttr = stto;
			}

		} else if (tep == 0) {
			// 循环剩余的字符串
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
