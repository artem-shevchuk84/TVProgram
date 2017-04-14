package ua.tools.escondido.tvprogram.services;


public interface AsyncTaskCallback<T> {

    void run(T result);

    void handleError();

}
