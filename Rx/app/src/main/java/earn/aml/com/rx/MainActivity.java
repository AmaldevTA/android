package earn.aml.com.rx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import earn.aml.com.rx.observables.CompletableSample;
import earn.aml.com.rx.observables.FlowableSample;
import earn.aml.com.rx.observables.MaybeSample;
import earn.aml.com.rx.observables.SingleSample;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //new ObservableSample().create();
        //new CompletableSample().fromAction();
        //new SingleSample().create();
        //new MaybeSample().create();
        new FlowableSample().fromArray();
    }
}
