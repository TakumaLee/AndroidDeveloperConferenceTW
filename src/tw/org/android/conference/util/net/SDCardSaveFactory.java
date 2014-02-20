package tw.org.android.conference.util.net;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

/**
 * 由http inputstream讀資料寫至SDCard
 * 
 * @author jones
 * 	#issue 
 *	http://webcache.googleusercontent.com/search?q=cache:xIjj8aFFYNAJ:stackoverflow.com/questions/5940487/bitmapfactory-decodestreaminputstream-is-returns-null-for-non-null-inputstream+&cd=1&hl=zh-TW&ct=clnk&gl=tw
 *	http://code.google.com/p/android/issues/detail?id=6066
 */
public class SDCardSaveFactory implements HttpResponseFactory {
	private File file;
	
	public SDCardSaveFactory(File file) {
		assert (file == null) : "save file can not be null";
			
		this.file = file;
	}

	public SDCardSaveFactory(String path) {
		// ex:file = new File("/sdcard/test.xml");
		file = new File(path);
	}

	public void process(HttpResponse response) throws Exception {
		// BufferedHttpEntity bufHttpEntity = new
		// BufferedHttpEntity(response.getEntity());
		// InputStream is = bufHttpEntity.getContent();
		HttpEntity entity = response.getEntity();
		InputStream inputStream = null;
		FileOutputStream fileOutputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		try {
			if ((response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
					&& (entity != null)) {
				inputStream = entity.getContent();
				fileOutputStream = new FileOutputStream(file);
//				fileOutputStream = FileUtils.openOutputStream(file, true);
				bufferedOutputStream = new BufferedOutputStream(
						fileOutputStream);
				byte[] buffer = new byte[512];
				int len = 0;
				while ((len = inputStream.read(buffer)) != -1) {
					// fileOutputStream.write(buffer, 0, len);
					bufferedOutputStream.write(buffer, 0, len);
				}
			}
		} finally {
			if (bufferedOutputStream != null) {
				bufferedOutputStream.close();
			}
			if (fileOutputStream != null) {
				fileOutputStream.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
			entity.consumeContent();
		}
	}

	public Object getResponseResult() {
		return null;
	}
}
