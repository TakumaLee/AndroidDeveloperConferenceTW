package tw.org.android.conference.api.signin;

/**
 * 
 * @author Kai Chung
 *
 * @param <T>
 * @param <E>
 */
public interface ApiServiceDoneCallback<T, E> {
	
	void onDone(T t);
	void onFault(E e);
}
