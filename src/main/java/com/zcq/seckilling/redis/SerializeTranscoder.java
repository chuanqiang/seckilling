package com.zcq.seckilling.redis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Closeable;


public abstract class SerializeTranscoder {
	protected static Log log = LogFactory.getLog("log");
	  
	  public abstract byte[] serialize(Object value);
	  
	  public abstract <T> T deserialize(byte[] in);
	  
	  public void close(Closeable closeable) {
	    if (closeable != null) {
	      try {
	        closeable.close();
	      } catch (Exception e) {
	         log.error("Unable to close " + closeable, e);
	         e.printStackTrace();
	      }
	    }
	  }
}
