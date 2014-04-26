package tw.org.android.conference.util.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.SyncBasicHttpContext;

/**
 * 
 * @author jones
 *
 */
public class HttpClientHelper {
	private final DefaultHttpClient httpClient;
    private final HttpContext httpContext;
	private int mConnectionTimeout = 7000;
	private int mSocketTimeout = -1;
	
	private String savePath = null;
	
	public static enum HttpResponseFactoryMode {
		StringBufferMode(0),
		SDCardSaveMode(1),
		JsonXmlMode(2);
		
		private int modeId;
		
		HttpResponseFactoryMode(int modeId) {
			this.modeId = modeId;
		}
		
		public int geModeId() {
			return modeId;
		}
	}
	
	public HttpClientHelper () {
		HttpParams httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is established.
		if (mConnectionTimeout != -1)
			HttpConnectionParams.setConnectionTimeout(httpParameters, mConnectionTimeout);
    	// Set the default socket timeout (SO_TIMEOUT) 
    	// in milliseconds which is the timeout for waiting for data.
		if (mSocketTimeout != -1)
			HttpConnectionParams.setSoTimeout(httpParameters, mSocketTimeout);
		
		httpContext = new SyncBasicHttpContext(new BasicHttpContext());
        httpClient = new DefaultHttpClient(httpParameters);
	}
	
	protected <T> HttpResponseFactory<T> switchHttpResponseFactoryMode(HttpResponseFactoryMode mode) {
		HttpResponseFactory<T> httpResponseFactory = null;
		switch (mode) {
		case StringBufferMode:
			httpResponseFactory = new StringBufferFactory();
			break;
		case SDCardSaveMode:
			assert (savePath != null) : "need to setSDCardSaveFactorySavePath";
			
			httpResponseFactory = new SDCardSaveFactory(savePath);
			break;
		default:
//			httpResponseFactory =  new JsonØßXmlFactory();
			httpResponseFactory = new StringBufferFactory();
			break;
		}
		
		return httpResponseFactory;
	}
	
	public void setSDCardSaveFactorySavePath(String savePath) {
		this.savePath = savePath;
	}
	
	public <T> T executeHttpGet(String url) throws Exception {
		
		return executeHttpGet(url, null, HttpResponseFactoryMode.StringBufferMode);
	}
	
	public <T> T executeHttpGet(String url, Header[] headers) throws Exception {
		
		return executeHttpGet(url, headers, HttpResponseFactoryMode.StringBufferMode);
	}
	
	public <T> T executeHttpGet(String url, Header[] headers, HttpResponseFactoryMode httpResponseFactoryMode) throws Exception {
		HttpResponseFactory<T> httpResponseFactory = switchHttpResponseFactoryMode(httpResponseFactoryMode);
		
		return executeHttpGet(url, headers, httpResponseFactory);
	}
	
	public <T> T executeHttpGet(String url, Header[] headers, HttpResponseFactory<T> httpResponseFactory) throws Exception {
		HttpGet request = new HttpGet(url);
		
		return sendRequest(request, headers, httpResponseFactory);
	}
	
	public <T> T executeHttpDelete(String url, Header[] headers) throws Exception {
		
		return executeHttpDelete(url, headers, HttpResponseFactoryMode.StringBufferMode);
	}
	
	public <T> T executeHttpDelete(String url, Header[] headers, HttpResponseFactoryMode httpResponseFactoryMode) throws Exception {
		HttpResponseFactory<T> httpResponseFactory = switchHttpResponseFactoryMode(httpResponseFactoryMode);
		
		return executeHttpDelete(url, headers, httpResponseFactory);
	}
	
	public <T> T executeHttpDelete(String url, Header[] headers, HttpResponseFactory<T> httpResponseFactory) throws Exception {
		HttpDelete request = new HttpDelete(url);
		
		return sendRequest(request, headers, httpResponseFactory);
	}
	
	public <T> T executeHttpPost(String url, Header[] headers, HttpEntity entity) throws Exception {
		
		return executeHttpPost(url, headers, entity, HttpResponseFactoryMode.StringBufferMode);
	}
	
	public <T> T executeHttpPost(String url, Header[] headers, HttpEntity entity, HttpResponseFactoryMode httpResponseFactoryMode) throws Exception {
		HttpResponseFactory<T> httpResponseFactory = switchHttpResponseFactoryMode(httpResponseFactoryMode);
		
		return executeHttpPost(url, headers, entity, httpResponseFactory);
	}
	
	public <T> T executeHttpPost(String url, Header[] headers, List<NameValuePair> nvps, HttpResponseFactory<T> httpResponseFactory) throws Exception {
		
		return executeHttpPost(url, headers, new UrlEncodedFormEntity(nvps, HTTP.UTF_8), httpResponseFactory);
	}
	
	public <T> T executeHttpPost(String url, Header[] headers, HttpEntity entity, HttpResponseFactory<T> httpResponseFactory) throws Exception {
		HttpPost request = new HttpPost(url);
		if (entity != null)
			request.setEntity(entity);
		
		return sendRequest(request, headers, httpResponseFactory);
	}
	
	public <T> T executeHttpPut(String url, Header[] headers, HttpEntity entity) throws Exception {
		
		return executeHttpPut(url, headers, entity, HttpResponseFactoryMode.StringBufferMode);
	}
	
	public <T> T executeHttpPut(String url, Header[] headers, HttpEntity entity, HttpResponseFactoryMode httpResponseFactoryMode) throws Exception {
		HttpResponseFactory<T> httpResponseFactory = switchHttpResponseFactoryMode(httpResponseFactoryMode);
		
		return executeHttpPut(url, headers, entity, httpResponseFactory);
	}
	
	public <T> T executeHttpPut(String url, Header[] headers, HttpEntity entity, HttpResponseFactory<T> httpResponseFactory) throws Exception {
		HttpPut request = new HttpPut(url);
		if (entity != null)
			request.setEntity(entity);
		
		return sendRequest(request, headers, httpResponseFactory);
	}
	
	<T> T sendRequest(HttpUriRequest request, Header[] headers, HttpResponseFactory<T> httpResponseFactory) throws Exception {
		assert (httpResponseFactory != null) : "Illegal parameter";

		if (headers != null)
			request.setHeaders(headers);
		
        try {
            HttpResponse response = httpClient.execute(request);
            if(httpResponseFactory != null) {
            	httpResponseFactory.process(response);
            	T result = httpResponseFactory.getResponseResult();
            	
            	return result;
            } else {
            	return (T) response;
            }
        } catch (ConnectTimeoutException e ){
        	request.abort();
        	e.printStackTrace();
            throw e;
        } catch (SocketTimeoutException e){
        	request.abort();
        	e.printStackTrace();
            throw e;
        } catch (IOException e) {
        	request.abort();
        	e.printStackTrace();
            throw e;
        } catch (IllegalStateException e) {
        	request.abort();
        	e.printStackTrace();
            throw e;
        } catch (Exception e) {
        	request.abort();
        	e.printStackTrace();
            throw e;
        }
	}
	
	public class TestFactory implements HttpResponseFactory {
		private StringBuffer sb = null;

		public void process(HttpResponse response) throws Exception {
			sb = new StringBuffer();
			HttpEntity entity = response.getEntity();
			InputStream inputStream = null;
			try {
	            if ((response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) && (entity != null)) {
					inputStream = entity.getContent();
					byte[] buffer = new byte[512];
		            int len = 0;
		            while((len=inputStream.read(buffer)) != -1) {
		            	sb.append(new String(buffer, 0, len));
		            }
	            }
			} finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                entity.consumeContent();
			}
		}

		public Object getResponseResult() {
			return sb.toString();
		}
	}

	public int getConnectionTimeout() {
		return mConnectionTimeout;
	}

	/**
	 * Set the timeout in milliseconds until a connection is established.
	 * timeout setting will disabled if set to zero 
	 * @param mConnectionTimeout
	 */
	public void setConnectionTimeout(int mConnectionTimeout) {
		this.mConnectionTimeout = mConnectionTimeout;
	}

	public int getSocketTimeout() {
		return mSocketTimeout;
	}

	/**
	 * Set the default socket timeout (SO_TIMEOUT) 
     * in milliseconds which is the timeout for waiting for data.
     * timeout setting will disabled if set to zero 
	 * @param mSocketTimeout
	 */
	public void setSocketTimeout(int mSocketTimeout) {
		this.mSocketTimeout = mSocketTimeout;
	}
	
}
