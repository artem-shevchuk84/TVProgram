package ua.tools.escondido.tvprogram.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import ua.tools.escondido.tvprogram.R;
import ua.tools.escondido.tvprogram.utils.Navigate;

public class HowToActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.how_to_use);

        final TextView description = (TextView) findViewById(R.id.description);
        description.setText(getResources().getString(R.string.howto_description));

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigate.goHome(HowToActivity.this);
            }
        });
    }

}
