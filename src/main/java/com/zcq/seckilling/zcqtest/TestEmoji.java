package com.zcq.seckilling.zcqtest;

import com.github.binarywang.java.emoji.EmojiConverter;
import org.junit.Assert;

/**
 * @Description: 表情转换（解决数据库不能存储表情的问题）
 * 其他解决方式：
 *  1. 修改数据库属性为utf8mb4
 *  2. 表和字段改为utf8mb4
 *  3. 数据库连接池改为druid,并且设置编码方式为utf8mb4：   <property name="connectionInitSqls" value="set names utf8mb4;"/>
 * @Author: zcq
 * @Date: 2018/10/24 6:37 PM
 */
public class TestEmoji {
	private EmojiConverter emojiConverter = EmojiConverter.getInstance();

	@org.junit.Test
	public void testToAlias() {
		String str = "收拾收拾 😃😀awesome 😃😃string with a few 😃😉emojis!";
		String alias = this.emojiConverter.toAlias(str);
		System.out.println(str);
		System.out.println("EmojiConverterTest.testToAlias()=====>");
		System.out.println(alias);
		String result = this.emojiConverter.toUnicode(alias);
		System.out.println(result);
		/*Assert.assertEquals(
				":no_good: :ok_woman: :couple_with_heart:An :smiley::grinning:awesome :smiley::smiley:string with a few :smiley::wink:emojis!",
				alias);*/
	}

	@org.junit.Test
	public void testToHtml() {
		String str = "收拾收拾 😀😃awesome 😃😃string with a few 😉😃emojis!";
		String result = this.emojiConverter.toHtml(str);
		System.out.println(str);
		System.out.println("EmojiConverterTest.testToHtml()=====>");
		System.out.println(result);
		Assert.assertEquals(
				"&#128581; &#128582; &#128145;An &#128512;&#128515;awesome &#128515;&#128515;string with a few &#128521;&#128515;emojis!",
				result);
	}

	@org.junit.Test
	public void testToUnicode() {
		String str = "收拾收拾 :smiley::grinning:awesome :smiley::smiley:string with a few :smiley::wink:emojis!";
		String result = this.emojiConverter.toUnicode(str);
		System.out.println(str);
		System.out.println("EmojiConverterTest.testToUnicode()=====>");
		System.out.println(result);
		Assert.assertEquals("收拾收拾 😀😃awesome 😃😃string with a few 😉😃emojis!", result);
	}
}
