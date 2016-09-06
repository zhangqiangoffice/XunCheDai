package com.minsheng.app.xunchedai.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 检查字符串格式
 */
public class CheckFormatUtils {

	public static final int PHONE = 0;
	public static final int NAME = 1;
	public static final int IDCARD = 2;
	public static final int EMAIL = 3;
	public static final int POSTCODE = 4;
	public static final int ADDRESS = 5;
	public static final int MONEY = 6;

	/**
	 * 根据类型判别格式是否正确
	 */
	public static boolean check(int type, String str) {
		String reg;
		boolean flag;

		switch (type){
			case NAME:
				reg = "";
				break;
			case IDCARD:
				reg = "(^[1-9]//d{5}[1-2]//d{3}((0//d)|(1[0-2]))(([0|1|2]//d)|3[0-1])//d{3}(//d|X|x)$)|(^[1-9]//d{7}((0//d)|(1[0-2]))(([0|1|2]//d)|3[0-1])//d{3}$)";
				break;
			case PHONE:
				reg = "^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$";
				break;
			case EMAIL:
				reg = "^[//w-]+(//.[//w-]+)*@[//w-]+(//.[//w-]+)+$";
				break;
			case POSTCODE:
				reg = "^//d{6}$";
				break;
			case ADDRESS:
				reg = "";
				break;
			case MONEY:
				reg = "^([1-9][//d]{0,7}|0)(//.[//d]{1,2})?$";
				break;
			default:
				reg = "^//w+$";
				break;
		}

		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(str);

		flag = matcher.matches();

		return flag;
	}


}
