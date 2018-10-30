package earn.aml.com.rx.observables;

import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;

public class CompletableSample {

    public void fromCallable(){
        Completable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return null;
            }
        }).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                System.out.println("<<<>>> onComplete");
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    public void create(){
        Disposable d = Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {
                emitter.onComplete();
            }
        }).subscribe(new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("<<<>>> run");
            }
        });
    }

    public void fromAction(){
        Disposable d = Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("<<<>>> run");
            }
        }).subscribe(new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("<<<>>> res");
            }
        });
    }

    public void fromRunnable(){
        Disposable d = Completable.fromRunnable(new Runnable() {
            @Override
            public void run() {
                System.out.println("<<<>>> run");
            }
        }).subscribe(new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("<<<>>> res");
            }
        });


    }
}
