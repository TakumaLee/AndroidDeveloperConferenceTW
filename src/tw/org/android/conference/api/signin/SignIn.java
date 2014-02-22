package tw.org.android.conference.api.signin;

import java.util.Date;

/**
 * 簽到資料
 * @author Kai Chung
 *	
是否有到
更新時間
 */
public class SignIn {
	
	public final static String ACTIVITYNAME = "activityName";
	public final static String ACTIVITYDATE = "activityDate";
	public final static String ACTIVITYPLACENAME = "activityPlaceName";
	public final static String FBID = "fbId";
	public final static String USERALIAS = "userAlias";
	public final static String ISSIGNIN = "isSignIn";
	
	String objectPK;
	/**
	 * 活動名稱
	 */
	String activityName;
	/**
	 * 活動時間
	 */
	Date activityDate;
	/**
	 * 活動地點
	 */
	String activityPlaceName;
	
	/**
	 * facebook ID;
	 * 是long id或是別名在討論
	 */
	long fbId;
	
	/**
	 * 簽到者別名
	 */
	String userAlias;
	
	/**
	 * 是否簽到
	 */
	boolean isSignIn;
	
	/**
	 * 更新時間
	 */
	long updateDate;

	/**
	 * @return the activityName
	 */
	public String getActivityName() {
		return activityName;
	}

	
	/**
	 * @return the objectPK
	 */
	public String getObjectPK() {
		return objectPK;
	}


	/**
	 * @param objectPK the objectPK to set
	 */
	public void setObjectPK(String objectPK) {
		this.objectPK = objectPK;
	}


	/**
	 * @param activityName the activityName to set
	 */
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	/**
	 * @return the activityDate
	 */
	public Date getActivityDate() {
		return activityDate;
	}

	/**
	 * @param activityDate the activityDate to set
	 */
	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}

	/**
	 * @return the activityPlaceName
	 */
	public String getActivityPlaceName() {
		return activityPlaceName;
	}

	/**
	 * @param activityPlaceName the activityPlaceName to set
	 */
	public void setActivityPlaceName(String activityPlaceName) {
		this.activityPlaceName = activityPlaceName;
	}

	/**
	 * @return the fbId
	 */
	public long getFbId() {
		return fbId;
	}

	/**
	 * @param fbId the fbId to set
	 */
	public void setFbId(long fbId) {
		this.fbId = fbId;
	}

	/**
	 * @return the userAlias
	 */
	public String getUserAlias() {
		return userAlias;
	}

	/**
	 * @param userAlias the userAlias to set
	 */
	public void setUserAlias(String userAlias) {
		this.userAlias = userAlias;
	}

	/**
	 * @return the isSignIn
	 */
	public boolean isSignIn() {
		return isSignIn;
	}

	/**
	 * @param isSignIn the isSignIn to set
	 */
	public void setSignIn(boolean isSignIn) {
		this.isSignIn = isSignIn;
	}
	
}
