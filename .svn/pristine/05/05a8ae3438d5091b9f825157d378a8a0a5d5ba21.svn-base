package com.minsheng.app.xunchedai.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 检查字符串格式
 */
public class LoanUtils {

	/**
	 * 根据商业险金额和起保日期算出可贷款金额
	 * 当前日期往后推5天，按保单剩余天数估算保单价值，未到起保日期的扣除3%的手续费
	 */
	public static String count(String sum, String date) {
		String money;
		long now_5_millis, insurance_millis;
		SimpleDateFormat format;
		Date date_insurance;
		double loan_amount, insurance_amount;

		insurance_amount = Double.parseDouble(sum);
		java.util.Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 5);
		now_5_millis = c.getTimeInMillis();
		format = new SimpleDateFormat("yyyy-MM-dd");
		format.setLenient(false);
		try {
			date_insurance = format.parse(date);
			insurance_millis = date_insurance.getTime();
		} catch (ParseException e) {
			return "";
		}

		if (insurance_millis < now_5_millis) {
			long day_pass =  (now_5_millis - insurance_millis) / (1000 * 60 * 60 * 24);
			loan_amount = insurance_amount * ((365.0 - day_pass) / 365.0);
		} else {
			loan_amount = insurance_amount *  0.97;
		}

		money = String.format("%.2f", loan_amount);
		return money;
	}
}
