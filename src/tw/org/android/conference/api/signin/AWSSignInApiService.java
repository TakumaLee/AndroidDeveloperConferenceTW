package tw.org.android.conference.api.signin;


/**
 * amazon web service的簽到
 * @author Kai Chung
 *
 */
public class AWSSignInApiService extends SignInApiService {
	
	public static synchronized SignInApiService getInstance() {
		if (instance == null)
			instance = new AWSSignInApiService();
		return instance;
	}

	@Override
	public boolean insertSignIn(SignIn sigin, ApiServiceDoneCallback callback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateSignIn(SignIn sigin, final ApiServiceDoneCallback callback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deletetSignIn(SignIn sigin, final ApiServiceDoneCallback callback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T, E> boolean query(final ApiServiceDoneCallback<T, E> callback) {
		// TODO Auto-generated method stub
		return false;
	}

}
