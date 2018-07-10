package com.zcq.seckilling.redis;

import java.io.*;

public class JObjectTranscoder<T> extends SerializeTranscoder{

	/**
	* @Description: 对象序列化
	* @Method: serialize
	* @Author: zcq
	* @Date: 2018/7/2 下午2:18
	*/
	@Override
	public byte[] serialize(Object value) {
		if (value == null) {
			throw new NullPointerException("Can't serialize null");
		}
		byte[] result;
		ByteArrayOutputStream bos = null;
		ObjectOutputStream os = null;
		try {
			bos = new ByteArrayOutputStream();
			os = new ObjectOutputStream(bos);
			os.writeObject(value);
			result = bos.toByteArray();
		} catch (IOException e) {
			throw new IllegalArgumentException("Non-serializable object", e);
		} finally {
			close(os);
			close(bos);
		}
		return result;
	}

	/**
	* @Description: 对象反序列化
	* @Method: deserialize
	* @Author: zcq
	* @Date: 2018/7/2 下午2:18
	*/
	@SuppressWarnings("unchecked")
	@Override
	public T deserialize(byte[] in) {
		T result = null;
		ByteArrayInputStream bis = null;
		ObjectInputStream is = null;
		try {
			if (in != null) {
				bis = new ByteArrayInputStream(in);
				is = new ObjectInputStream(bis);
				result = (T) is.readObject();
			}
		} catch (IOException e) {
			log.error( String.format("Caught IOException decoding %d bytes of data", in == null ? 0 : in.length), e);
		} catch (ClassNotFoundException e) {
			log.error( String.format("Caught CNFE decoding %d bytes of data", in == null ? 0 : in.length), e);
		} finally {
			close(is);
			close(bis);
		}
		return result;
	}
}
