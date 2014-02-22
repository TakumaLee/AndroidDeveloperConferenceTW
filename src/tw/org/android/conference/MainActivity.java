package tw.org.android.conference;

import tw.org.android.conference.api.signin.ApiServiceDoneCallback;
import tw.org.android.conference.api.signin.ParseSignInApiService;
import tw.org.android.conference.api.signin.SignInApiService;
import tw.org.android.conference.util.net.LogUtil;
import android.os.Bundle;
import android.view.Menu;

import com.parse.Parse;

public class MainActivity extends BaseActivity {

	SignInApiService service = new ParseSignInApiService();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		
		Parse.initialize(this, "ucHeW9Ypt5vq44nYAQS3udqFItycpTLrJqGhYDpQ", "Gw5OzRl94HE2sHlBIBCCACkgLIvPxbw9EJ8pvsLf");
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				service.query(new ApiServiceDoneCallback<String, Exception>() {

					@Override
					public void onDone(String res) {
						LogUtil.i("res:"+res);
					}

					@Override
					public void onFault(Exception e) {
						LogUtil.e("e:"+e.getMessage());
					}
				});
			}
		}).start();
		
		/*
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				service.insertSignIn(null, new ApiServiceDoneCallback<String, Exception>() {

					@Override
					public void onDone(String res) {
						LogUtil.i("res:"+res);
					}

					@Override
					public void onFault(Exception e) {
						LogUtil.e("e:"+e.getMessage());
					}
				});
			}
		}).start();*/
		/*
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				service.updateSignIn(null, new ApiServiceDoneCallback<String, Exception>() {

					@Override
					public void onDone(String res) {
						LogUtil.i("res:"+res);
					}

					@Override
					public void onFault(Exception e) {
						LogUtil.e("e:"+e.getMessage());
					}
				});
			}
		}).start();*/
		/*
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				service.deletetSignIn(null, new ApiServiceDoneCallback<String, Exception>() {

					@Override
					public void onDone(String res) {
						LogUtil.i("res:"+res);
					}

					@Override
					public void onFault(Exception e) {
						LogUtil.e("e:"+e.getMessage());
					}
				});
			}
		}).start();*/
		
		
		
		LogUtil.e("done..."+System.currentTimeMillis());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
