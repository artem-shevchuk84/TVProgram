package ua.tools.escondido.tvprogram.services.loader.async;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

import ua.tools.escondido.tvprogram.R;
import ua.tools.escondido.tvprogram.data.ProgramInfo;
import ua.tools.escondido.tvprogram.services.AsyncTaskCallback;
import ua.tools.escondido.tvprogram.services.ChannelProgramService;
import ua.tools.escondido.tvprogram.services.impl.ChannelProgramServiceImpl;
import ua.tools.escondido.tvprogram.services.parser.ChannelContentParser;

public class ProgramInfoDataLoader extends AsyncTask<String, Void ,ProgramInfo> {
    private AsyncTaskCallback<ProgramInfo> callback;
    private ProgressDialog progressDialog;
    private Context context;

    public ProgramInfoDataLoader(Context context, AsyncTaskCallback<ProgramInfo> callback) {
        this.callback = callback;
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getString(R.string.progressbar_loading_text));
        progressDialog.show();
    }

    @Override
    protected ProgramInfo doInBackground(String... params) {
        ChannelProgramService channelProgramService = new ChannelProgramServiceImpl<>(context, new ChannelContentParser());
        return channelProgramService.getProgramInfo(params[0]);
    }

    @Override
    protected void onPostExecute(ProgramInfo programInfo) {
        progressDialog.dismiss();
        if (programInfo == null) {
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
            programInfo = new ProgramInfo();
        }
        callback.run(programInfo);
    }
}
