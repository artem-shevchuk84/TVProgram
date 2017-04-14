package ua.tools.escondido.tvprogram.services.loader.async;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import ua.tools.escondido.tvprogram.R;
import ua.tools.escondido.tvprogram.data.ProgramEvent;
import ua.tools.escondido.tvprogram.services.AsyncTaskCallback;
import ua.tools.escondido.tvprogram.services.ChannelProgramService;

public class ProgramListDataLoader extends AsyncTask<String, Void, List<ProgramEvent>> {

    private ChannelProgramService channelProgramService;
    private AsyncTaskCallback<List<ProgramEvent>> callback;
    private ProgressDialog progressDialog;
    private Context context;

    public ProgramListDataLoader(Context context,
                                 ChannelProgramService channelProgramService,
                                 AsyncTaskCallback<List<ProgramEvent>> callback) {
        this.callback = callback;
        this.context = context;
        this.channelProgramService = channelProgramService;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getString(R.string.progressbar_loading_text));
        progressDialog.show();
    }

    @Override
    protected List<ProgramEvent> doInBackground(String... params) {
        return channelProgramService.getChannelProgram(params[0]);
    }

    @Override
    protected void onPostExecute(List<ProgramEvent> programEvents) {
        progressDialog.dismiss();
        if(programEvents == null){
            final AlertDialog alertDialog = new AlertDialog.Builder(context).
                    setMessage(context.getString(R.string.alert_error_no_internet)).
                    setCancelable(false).
                    setNegativeButton(R.string.alert_btn_close, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            dialog.cancel();
                            callback.handleError();
                        }
                    }).
                    create();
            alertDialog.setOnShowListener( new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface arg0) {
                    alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                            .setBackgroundResource(R.drawable.button_selected);
                }
            });
            alertDialog.show();
            programEvents = new ArrayList<>();
        }
        callback.run(programEvents);
    }
}
