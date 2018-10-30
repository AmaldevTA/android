package earn.aml.com.rx.observables;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class ObservableSample {

    public void just(){
        Observable.just("one", "two", "three")
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        return s.length() <= 3;
                    }
                })
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        return s.toUpperCase();
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println("<<<>>>" + s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void fromArray(){

        List<String> sandwichIngredients = Arrays.asList("bread", "bread", "cheese", "mayo", "turkey");
        Disposable d = Observable.fromIterable(sandwichIngredients)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println("<<<>>>" + s);
                    }
                });
    }


    public void fromCallable(){
        Disposable d = Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "Hello";
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("<<<>>>" + s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println("<<<>>> error " + throwable.toString());
            }
        });
    }


    public void create(){
        Disposable d = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("Hello");
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("<<<>>>" + s);
            }
        });
    }
}
