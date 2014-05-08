package com.mixiaoxiao.translate;

/**
 * Copyright (c) blackbear, Inc All Rights Reserved.
 */

import java.io.InputStream;
import java.net.URLEncoder;
import java.text.MessageFormat;

import org.apache.commons.io.IOUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * TranslateUtil
 * 
 * <pre>翻譯工具
 * PS: 透過google translate
 * </pre>
 * 
 * @author catty
 * @version 1.0, Created on 2011/9/2
 */
public class TranslateUtil {

	protected static final String URL_TEMPLATE = "http://translate.google.com/?langpair={0}&text={1}";
	protected static final String ID_RESULTBOX = "result_box";
	protected static final String ENCODING = "UTF-8";

	protected static final String AUTO = "auto"; // google自動判斷來源語系
	public static final String TAIWAN = "zh-TW"; // 繁中
	public static final String CHINA = "zh-CN"; // 簡中
	public static final String ENGLISH = "en"; // 英
	public static final String JAPAN = "ja"; // 日

	/**
	 * <pre>Google翻譯
	 * PS: 交由google自動判斷來源語系
	 * </pre>
	 * 
	 * @param text
	 * @param target_lang 目標語系
	 * @return
	 * @throws Exception
	 */
	public static String translate(final String text, final String target_lang) throws Exception {
		return translate(text, AUTO, target_lang);
	}

	/**
	 * <pre>Google翻譯</pre>
	 * 
	 * @param text
	 * @param src_lang 來源語系
	 * @param target_lang 目標語系
	 * @return
	 * @throws Exception
	 */
	public static String translate(final String text, final String src_lang, final String target_lang)
			throws Exception {
		InputStream is = null;
		Document doc = null;
		Element ele = null;
		try {
			// create URL string
			String url = MessageFormat.format(URL_TEMPLATE,
					URLEncoder.encode(src_lang + "|" + target_lang, ENCODING),
					URLEncoder.encode(text, ENCODING));

			// connect & download html
			is = HttpClientUtil.downloadAsStream(url);

			// parse html by Jsoup
			doc = Jsoup.parse(is, ENCODING, "");
			ele = doc.getElementById(ID_RESULTBOX);
			String result = ele.text();
			return result;

		} finally {
			IOUtils.closeQuietly(is);
			is = null;
			doc = null;
			ele = null;
		}
	}

	/**
	 * <pre>Google翻譯: 簡中-->繁中</pre>
	 * 
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public static String cn2tw(final String text) throws Exception {
		return translate(text, CHINA, TAIWAN);
	}

	/**
	 * <pre>Google翻譯: 繁中-->簡中</pre>
	 * 
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public static String tw2cn(final String text) throws Exception {
		return translate(text, TAIWAN, CHINA);
	}

	/**
	 * <pre>Google翻譯: 英文-->繁中</pre>
	 * 
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public static String en2tw(final String text) throws Exception {
		return translate(text, ENGLISH, TAIWAN);
	}
	/**
	 * <pre>Google翻譯: 英文-->简体中文</pre>
	 * 
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public static String en2cn(final String text) throws Exception  {
		return translate(text, ENGLISH, CHINA);
	}

	/**
	 * <pre>Google翻譯: 繁中-->英文</pre>
	 * 
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public static String tw2en(final String text) throws Exception {
		return translate(text, TAIWAN, ENGLISH);
	}

	/**
	 * <pre>Google翻譯: 日文-->繁中</pre>
	 * 
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public static String jp2tw(final String text) throws Exception {
		return translate(text, JAPAN, TAIWAN);
	}

	/**
	 * <pre>Google翻譯: 繁中-->日</pre>
	 * 
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public static String tw2jp(final String text) throws Exception {
		return translate(text, TAIWAN, JAPAN);
	}

}