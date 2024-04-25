package com.example.common.wrapper;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 脚本安全工具，这里做初步的参数过滤处理和安全处理
 * 
 * @author luoxiaodong
 * @since Nov 26, 2022 上午6:23:43
 */
public class ScriptTools {

	/**
	 * 判断是否为合法字符(a-zA-Z0-9-_)
	 *
	 * @param text
	 * @return
	 */
	public static boolean isRightfulString(String text) {

		// 初步判断字符间距
		boolean b = match(text, "^[A-Za-z0-9_-]+$");
		Assert.isTrue(b , "参数中含有非法的字符:" + text) ; 

		// 进一步判断特殊字符
		 boolean b2 = containsSqlInjection(text) ;
		 Assert.isTrue(b2 , "参数中含有非法的字符:" + text) ;

		return true;
	}

	/**
	 * 是否含有sql注入，返回true表示含有
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean containsSqlInjection(Object obj) {
		Pattern pattern = Pattern.compile(
				"\\b(exec|insert|select|drop|grant|alter|delete|update|count|chr|truncate|char|declare|or)\\b|(\\*|;|\\+|'|%)");
		Matcher matcher = pattern.matcher(obj.toString());
		return matcher.find();
	}

	/**
	 * 正则表达式匹配
	 *
	 * @param text 待匹配的文本
	 * @param reg  正则表达式
	 * @return
	 */
	private static boolean match(String text, String reg) {
		if (StringUtils.isBlank(text) || StringUtils.isBlank(reg)) {
			return false;
		}
		return Pattern.compile(reg).matcher(text).matches();
	}

}
