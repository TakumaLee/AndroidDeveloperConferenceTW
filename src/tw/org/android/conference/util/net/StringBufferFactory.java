package tw.org.android.conference.util.net;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

/**
 * 由http inputstream讀資料寫至StringBuffer
 * @author jones
 *
 */
public class StringBufferFactory<T> implements HttpResponseFactory<String> {
	private StringBuffer sb = null;

	public void process(HttpResponse response) throws Exception {
		sb = new StringBuffer();
		HttpEntity entity = response.getEntity();
        if (entity != null) {
        	InputStream inputStream = null;
        	InputStreamReader inputStreamReader = null;
			try {
				inputStream = entity.getContent();
				// InputStream的read有時中文會出現亂碼
				inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
				char[] buffer = new char[512];
	            int len = 0;
	            while((len=inputStreamReader.read(buffer)) != -1) {
	            	sb.append(new String(buffer, 0, len));
	            }
			} finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (inputStreamReader != null) {
                	inputStreamReader.close();
                }
                entity.consumeContent();
			}
        }
	}

	public String getResponseResult() {
		return sb.toString();
	}
}
