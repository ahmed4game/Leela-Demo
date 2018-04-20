package msense.homepages;

import android.content.Context;
import android.content.Intent;
import android.os.Process;

import java.io.PrintWriter;
import java.io.StringWriter;
/*http://trivedihardik.wordpress.com/2011/08/20/how-to-avoid-force-close-error-in-android/*/
public class ExceptionHandler implements Thread.UncaughtExceptionHandler {
	private final Context myContext;

	public ExceptionHandler(Context context) {
		myContext = context;
	}

	public void uncaughtException(Thread thread, Throwable exception) {
		StringWriter stackTrace = new StringWriter();
		exception.printStackTrace(new PrintWriter(stackTrace));
		
		System.err.println(stackTrace);// You can use LogCat too

		//Intent intent = new Intent(myContext, CrashActivity.class);
		Intent intent = new Intent("com.msense.iptvmessagehandler.crashmessdisplayer");
		//intent.putExtra(CrashActivity.STACKTRACE, stackTrace.toString());
		myContext.startActivity(intent);

		Process.killProcess(Process.myPid());
		System.exit(10);
	}
}