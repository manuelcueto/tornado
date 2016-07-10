package seminario.futbol.controller;

public class Response<T> {
    private T content;

    public Response(T t) {
	this.content = t;
    }

    public T getContent() {
	return content;
    }

}
