package lab.acme.noviembre15;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import lab.acme.noviembre15.common.Common;


public class AppIconAct extends Activity {
    Context mContext;
    ImageView ivImagePich;
    Button btnAppIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_icon_act);
        mContext = AppIconAct.this;

        init();

    }

    private void init() {
        ivImagePich = (ImageView) findViewById(R.id.ivAppIcon);


        btnAppIcon = (Button) findViewById(R.id.btnAppIcon);
        btnAppIcon.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ivImagePich.setImageDrawable(Common.getAppIcon(mContext));
            }
        });

    }
}