package com.zcq.seckilling.zcqtest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ZcqTest {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static String format(Date date) {
		return sdf.format(date);
	}

	public static Date parse(String strDate) throws ParseException {
		return sdf.parse(strDate);
	}

	public static Date parse1(String strDate) throws ParseException {
		synchronized (sdf) {
			return sdf.parse(strDate);
		}
	}

	private static ThreadLocal<DateFormat> threadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

	public static Date parse2(String dateStr) throws ParseException {
		return threadLocal.get().parse(dateStr);
	}

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public static String formatDate(LocalDateTime date) {
		return FORMATTER.format(date);
	}

	public static LocalDateTime parseNew(String dateNow) {
		return LocalDateTime.parse(dateNow, FORMATTER);
	}


	public static void main(String[] args) {
//		ExecutorService service = Executors.newFixedThreadPool(100);
//		for (int i = 0; i < 20; i++) {
//			service.execute(() -> {
//				for (int j = 0; j < 10; j++) {
//					System.out.println(parseNew(formatDate(LocalDateTime.now())));
//				}
//			});
//		}
//		service.shutdown();

		DateTimeFormatter[] formatters = new DateTimeFormatter[]{
				// 直接使用常量创建DateTimeFormatter格式器
				DateTimeFormatter.ISO_LOCAL_DATE,
				DateTimeFormatter.ISO_LOCAL_TIME,
				DateTimeFormatter.ISO_LOCAL_DATE_TIME,
				// 使用本地化的不同风格来创建DateTimeFormatter格式器
				DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.MEDIUM),
				DateTimeFormatter.ofLocalizedTime(FormatStyle.LONG),
				// 根据模式字符串来创建DateTimeFormatter格式器
				DateTimeFormatter.ofPattern("Gyyyy%%MMM%%dd HH:mm:ss")
		};
		LocalDateTime date = LocalDateTime.now();
		// 依次使用不同的格式器对LocalDateTime进行格式化
		for (int i = 0; i < formatters.length; i++) {
			// 下面两行代码的作用相同
			System.out.println(date.format(formatters[i]));
			System.out.println(formatters[i].format(date));
		}
	}
}
