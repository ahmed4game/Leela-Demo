package msense.homepages;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.mspl.com.hotelithaca.R;

/**
 * Created by Desktop on 1/31/2018.
 */

public class MyCustomDialog {

    private AlertDialog.Builder builder;
    ProgressDialog dialog;
    private Context mContext;


    public MyCustomDialog(Context c, String title, String message, String posBName, String negBName,
                          DialogInterface.OnClickListener posListener, DialogInterface.OnClickListener negListener) {
        builder = new AlertDialog.Builder(c, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(posBName,posListener);
        builder.show();
    }

    public MyCustomDialog(Context c, String title, String message, String posBName,
                          DialogInterface.OnClickListener posListener) {
        Log.d("MyCustomDialog", "MyCustomDialog: ");
        AlertDialog.Builder builder = new AlertDialog.Builder(c, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(posBName,posListener);
        builder.show();
    }

    public void showAlertDialog(){
        dialog.setTitle("Message");
        dialog.setMessage("Please wait ...");
        dialog.show();
    }

    public ProgressDialog getDialog(){return dialog;}
    public AlertDialog.Builder getBuilder(){return builder;}

    public MyCustomDialog(Context c) {
        builder = new AlertDialog.Builder(c);
        dialog=new ProgressDialog(c);
    }
}
