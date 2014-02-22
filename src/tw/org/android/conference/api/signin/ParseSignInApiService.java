package tw.org.android.conference.api.signin;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import tw.org.android.conference.util.net.HttpClientHelper;
import tw.org.android.conference.util.net.LogUtil;

/**
 * using parse java native
 * 可用rest api統一factory接口
 * @author Kai Chung
 *
 */
public class ParseSignInApiService extends SignInApiService {
	
	final String X_Parse_Application_Id = "ucHeW9Ypt5vq44nYAQS3udqFItycpTLrJqGhYDpQ";
	final String X_Parse_REST_API_Key = "NIFyty65UsPicgYMyuqFV8GiljZq55nv7uLueSAp";
	final String apiUrl = "https://api.parse.com/1/classes/signIn";

	@Override
	public boolean query(final ApiServiceDoneCallback callback) {
		HttpClientHelper helper = new HttpClientHelper();
		Header[] headers = {
				new BasicHeader("X-Parse-Application-Id", X_Parse_Application_Id),
				new BasicHeader("X-Parse-REST-API-Key", X_Parse_REST_API_Key),
				new BasicHeader("Content-Type", "application/json")
		};
		
		try {
			String response = helper.executeHttpGet(apiUrl, headers);
			LogUtil.d("response:"+response);
			
			callback.onDone(response);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			callback.onFault(e);
		}
		return false;
	}

	@Override
	public boolean insertSignIn(SignIn sigin, final ApiServiceDoneCallback callback) {
		HttpClientHelper helper = new HttpClientHelper();
		Header[] headers = {
				new BasicHeader("X-Parse-Application-Id", X_Parse_Application_Id),
				new BasicHeader("X-Parse-REST-API-Key", X_Parse_REST_API_Key),
				new BasicHeader("Content-Type", "application/json")
		};
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		JSONObject jsonObj = new JSONObject();
		try {
			
			 sigin = new SignIn();
			 sigin.setActivityName("第五回Android Developer讀書會");
			 sigin.setActivityDate(new Date());// should not device date
			 sigin.setActivityPlaceName("Katze cafe' 優米胡椒");
			 sigin.setFbId(123456);
			 sigin.setUserAlias("kai chung");
			 sigin.setSignIn(true);
			 
			jsonObj.put(SignIn.ACTIVITYNAME, sigin.getActivityName());
			JSONObject activityDate = new JSONObject();
			activityDate.put("__type", "Date");
			String parseFormat = sf.format(sigin.getActivityDate())+"Z";
			activityDate.put("iso", parseFormat);
		
			jsonObj.put(SignIn.ACTIVITYDATE, activityDate);
			jsonObj.put(SignIn.ACTIVITYPLACENAME, sigin.getActivityName());
			jsonObj.put(SignIn.FBID, sigin.getFbId());
			jsonObj.put(SignIn.USERALIAS, sigin.getUserAlias());
			jsonObj.put(SignIn.ISSIGNIN, sigin.isSignIn());
			String str = jsonObj.toString();
			
			LogUtil.d("json:"+str);
			StringEntity entity = new StringEntity(str, HTTP.UTF_8);
			entity.setContentType("application/json");
			String response =  helper.executeHttpPost(apiUrl, headers, entity);
			LogUtil.d("response:"+response);
			
			callback.onDone(response);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			callback.onFault(e);
		}
		
		return false;
	}

	@Override
	public boolean updateSignIn(SignIn sigin, final ApiServiceDoneCallback callback) {
		HttpClientHelper helper = new HttpClientHelper();
		Header[] headers = {
				new BasicHeader("X-Parse-Application-Id", X_Parse_Application_Id),
				new BasicHeader("X-Parse-REST-API-Key", X_Parse_REST_API_Key)
		};
		try {
			sigin = new SignIn();
			sigin.setObjectPK("FoUAhqztqC");
			sigin.setUserAlias("kai chung...");
			 
			JSONObject jsonObj = new JSONObject();
			jsonObj.put(SignIn.USERALIAS, sigin.getUserAlias());
			StringEntity entity = new StringEntity(jsonObj.toString(), HTTP.UTF_8);
			entity.setContentType("application/json");
			
			String response = helper.executeHttpPut(String.format("%s/%s", apiUrl, sigin.getObjectPK()), headers, entity);
			LogUtil.d("response:"+response);
			
			callback.onDone(response);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			callback.onFault(e);
		}
		
		return false;
	}

	@Override
	public boolean deletetSignIn(SignIn sigin, final ApiServiceDoneCallback callback) {
		HttpClientHelper helper = new HttpClientHelper();
		Header[] headers = {
				new BasicHeader("X-Parse-Application-Id", X_Parse_Application_Id),
				new BasicHeader("X-Parse-REST-API-Key", X_Parse_REST_API_Key)
		};
		try {
			sigin = new SignIn();
			sigin.setObjectPK("FoUAhqztqC");
			
			String url = String.format("%s/%s", apiUrl, sigin.getObjectPK());
			String response = helper.executeHttpDelete(url, headers);
			LogUtil.d("response:"+response);
			
			callback.onDone(response);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			callback.onFault(e);
		}
			
		return false;
	}

}
