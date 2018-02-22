package sample;

import java.io.IOException;

public interface BackendService {
    String doSomething();

    String doSomethingWithException() throws IOException;
}
