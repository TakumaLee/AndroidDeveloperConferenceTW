package tw.org.android.conference.util.net;

import org.apache.http.HttpResponse;

/**
 * 
 * @author "jones"
 *
 */
public interface HttpResponseFactory<T> {

	public void process(HttpResponse response) throws Exception;
	public T getResponseResult();
}
