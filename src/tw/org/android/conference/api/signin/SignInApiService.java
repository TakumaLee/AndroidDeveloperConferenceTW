package tw.org.android.conference.api.signin;

import com.parse.ParseException;

/**
 * 定義簽到服務
 * @author Kai Chung
 *
 */
public abstract class SignInApiService {

	protected volatile static SignInApiService instance;
	
	public static synchronized SignInApiService getInstance() {
		return instance;
	}
	
	protected SignInApiService() {}
	
	public abstract <T, E> boolean query(final ApiServiceDoneCallback<T, E> callback);
	
	/**
	 * 新增簽到資料
	 * @param sigin
	 * @return
	 */
	public abstract <T, E> boolean insertSignIn(SignIn sigin, final ApiServiceDoneCallback<T, E> callback);

	/**
	 * 修改簽到資料
	 * @param sigin
	 * @return
	 */
	public abstract <T, E> boolean updateSignIn(SignIn sigin, final ApiServiceDoneCallback<T, E> callback);
	
	/**
	 * 刪除簽到資料
	 * @param sigin
	 * @return
	 */
	public abstract <T, E> boolean deletetSignIn(SignIn sigin, final ApiServiceDoneCallback<T, E> callback);

}
