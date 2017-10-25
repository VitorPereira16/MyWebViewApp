package pt.itmanager.www.mywebviewapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * Created by theappguruz on 07/05/16.
 */
public class PhoneStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        DatabaseHandler db = new DatabaseHandler(context);

        try {
            //System.out.println("Receiver start aaaaa");
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                Integer status = db.verifyContactoExistByNumber(incomingNumber);
                if(status==1){
                    Contact cn = db.getContactByNumber(incomingNumber);
                    Intent i = new Intent(context.getApplicationContext(), PopUpInfo.class);
                    i.putExtra("nome", cn.getNome());
                    i.putExtra("ultimonome", cn.getUltimoNome());
                    i.putExtra("telefone", incomingNumber);
                    i.putExtras(intent);
                    //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    //Wait.oneSec();
                    context.startActivity(i);

                }else{
                    Integer status2 = db.verifyContactoAdditionalExistByNumber(incomingNumber);
                    if(status2==1){
                        ContactAdditional cn2 = db.getContactAdditionalByNumber(incomingNumber);
                        Intent i = new Intent(context.getApplicationContext(), PopUpInfo.class);
                        i.putExtra("nome", cn2.getContactName());
                        i.putExtra("ultimonome", "");
                        i.putExtra("telefone", incomingNumber);
                        i.putExtras(intent);
                        //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        //Wait.oneSec();
                        context.startActivity(i);
                    }else{
                        Toast.makeText(context,"Não encontrou o seguinte numero: "+incomingNumber, Toast.LENGTH_LONG).show();
                    }
                }


                //startActivity
                //cn.getID();
                //Toast.makeText(context,"Ringing State Number is -"+cn.getID(), Toast.LENGTH_SHORT).show();

            }
            if ((state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))){
                //Toast.makeText(context,"Nome: ", Toast.LENGTH_LONG).show();
            }
            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                //Toast.makeText(context,"Nome2: ", Toast.LENGTH_LONG).show();
                //this.fi.finish
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}


