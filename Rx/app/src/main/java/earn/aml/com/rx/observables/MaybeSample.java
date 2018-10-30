package earn.aml.com.rx.observables;

import java.util.concurrent.Callable;

import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class MaybeSample {

    public void just(){
        Maybe.just("One")
                .subscribe(new MaybeObserver<String>() {
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

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void fromCallable(){
        Disposable d = Maybe.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "Res";
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("<<<>>>" + s);
            }
        });
    }

    public void fromAction(){
        Disposable d = Maybe.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("<<<>>>  run");
            }
        }).subscribe();
    }


    public void create(){
        Disposable d = Maybe.create(new MaybeOnSubscribe<String>() {
            @Override
            public void subscribe(MaybeEmitter<String> emitter) throws Exception {
                emitter.onSuccess("Hello");
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("<<<>>>" + s);
            }
        });
    }
}
