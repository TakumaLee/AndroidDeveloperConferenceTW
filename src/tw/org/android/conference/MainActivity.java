package tw.org.android.conference;

import tw.org.android.conference.api.signin.ApiServiceDoneCallback;
import tw.org.android.conference.api.signin.ParseSignInApiService;
import tw.org.android.conference.api.signin.SignInApiService;
import tw.org.android.conference.util.net.LogUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import com.parse.Parse;

public class MainActivity extends BaseActivity {

	SignInApiService service = new ParseSignInApiService();
	
	private MainFragment mainFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (savedInstanceState == null) {
	        // Add the fragment on initial activity setup
	        mainFragment = new MainFragment();
	        getSupportFragmentManager().beginTransaction().add(android.R.id.content, mainFragment).commit();
	    } else {
	        // Or set the fragment from restored state info
	        mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(android.R.id.content);
	    }
		
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
		// Troubleshooting for key hash - print out the key hash sent to Facebook and use that value
		// Make sure you properly organize import when uncommented
		/*
		try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "tw.org.android.conference", 
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                }
        } catch (NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
		*/
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
