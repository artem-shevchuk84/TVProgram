package ua.tools.escondido.tvprogram.services.loader.async;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
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
            AlertDialog alertDialog = new AlertDialog.Builder(context).
                    setMessage(context.getString(R.string.alert_error_no_internet)).
                    create();
            alertDialog.setCancelable(true);
            alertDialog.show();
            programEvents = new ArrayList<>();
        }
        callback.run(programEvents);
    }
}
