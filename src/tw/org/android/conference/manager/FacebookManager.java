package tw.org.android.conference.manager;

public class FacebookManager {
	
	private static FacebookManager facebookManager;
	
	public FacebookManager() {
		
	}
	
	public static FacebookManager getFacebookManager() {
		if (facebookManager == null)
			facebookManager = new FacebookManager();
		return facebookManager;
	}

}
