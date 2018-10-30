package earn.aml.com.rx.observables;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;

public class FlowableSample {


    public void just(){
        Flowable.just("one", "two", "three")
                .subscribe(new FlowableSubscriber<String>() {
                    Subscription sub;
                    
                    @Override
                    public void onSubscribe(Subscription s) {
                        sub = s;
                        s.request(1);

                    }

                    @Override
                    public void onNext(String str) {
                        System.out.println("<<<>>>  "+str);
                        sub.request(1);

                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("<<<>>>  onComplete");
                    }
                });
    }

    public void fromArray(){
        List<String> sandwichIngredients = Arrays.asList("bread", "bread", "cheese", "mayo", "turkey");
        Flowable.fromIterable(sandwichIngredients)
                .subscribe(new Subscriber<String>() {
                    Subscription sub;
                    @Override
                    public void onSubscribe(Subscription s) {
                        sub = s;
                        s.request(1);
                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println("<<<>>>  "+s);
                        sub.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("<<<>>>  onComplete");
                    }
                });
    }
}
