package domaci2.server;

public class HttpRoute {

    private String httpMethod;
    private String methodName; //java method
    private String route;
    private Class controller;

    public HttpRoute(String httpMethod, String methodName, String route, Class controller) {
        this.httpMethod = httpMethod;
        this.methodName = methodName;
        this.route = route;
        this.controller = controller;
    }

    @Override
    public String toString() {
        return   "Route: "+ "path = '" + route + '\'' +", httpMethod = '" + httpMethod + '\'' + ", controller = " + controller.getSimpleName() + ", methodName = '" + methodName + '\'' ;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getRoute() {
        return route;
    }

    public Class getController() {
        return controller;
    }
}
