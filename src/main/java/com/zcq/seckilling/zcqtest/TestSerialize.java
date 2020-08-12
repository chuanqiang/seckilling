package com.zcq.seckilling.zcqtest;

import com.zcq.seckilling.domain.User;

import java.io.*;

public class TestSerialize {
	public static void main(String[] args) throws Exception {
//		serializeFlyPig();
		 User user = deserializeFlyPig();
		 System.out.println(user.toString());

	}

	/**
	 * 序列化
	 */
	private static void serializeFlyPig() throws IOException {
		User user = new User();
		user.setPassWord("black");
		user.setName("naruto");
		user.setId(123);
		// ObjectOutputStream 对象输出流，将 flyPig 对象存储到E盘的 flyPig.txt 文件中，完成对 flyPig 对象的序列化操作
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("/Users/zhangchuanqiang/Downloads/a.txt")));
		oos.writeObject(user);
		System.out.println("User 对象序列化成功！");
		oos.close();
	}

	/**
	 * 反序列化
	 */
	private static User deserializeFlyPig() throws Exception {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("/Users/zhangchuanqiang/Downloads/a.txt")));
		User person = (User) ois.readObject();
		System.out.println("User 对象反序列化成功！");
		return person;
	}
}
