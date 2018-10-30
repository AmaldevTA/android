package earn.aml.com.rx.observables;

import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;

public class SingleSample {

    public void just(){
        Single.just("one")
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        System.out.println("<<<>>>" + s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void fromCallable(){
        Disposable d = Single.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "Hello";
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("<<<>>>" + s);
            }
        });
    }

    public void create(){
        Disposable d = Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(SingleEmitter<String> emitter) throws Exception {
                emitter.onSuccess("Result");
            }
        }).subscribe(new BiConsumer<String, Throwable>() {
            @Override
            public void accept(String s, Throwable throwable) throws Exception {
                System.out.println("<<<>>>" + s);
            }
        });
    }
}
