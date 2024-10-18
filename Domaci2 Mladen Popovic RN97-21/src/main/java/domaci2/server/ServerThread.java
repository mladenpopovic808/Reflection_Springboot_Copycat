

package domaci2.server;
import domaci2.framework.dependencyInjection.DIEngine;
import domaci2.framework.request.Header;
import domaci2.framework.request.Helper;
import domaci2.framework.request.Request;
import domaci2.framework.request.enums.Method;
import domaci2.framework.request.exceptions.RequestNotValidException;
import domaci2.framework.response.JsonResponse;
import domaci2.framework.response.Response;
import domaci2.server.DiscoveryMechanism;
import domaci2.server.HttpRoute;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ServerThread implements Runnable{

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private final DiscoveryMechanism discoveryMechanism;
    private final DIEngine diEngine;

    public ServerThread(Socket socket){
        this.socket = socket;
        discoveryMechanism = DiscoveryMechanism.getInstance();
        diEngine = DIEngine.getInstance();

        try {
            in = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()));

            out = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(
                                    socket.getOutputStream())), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        try {

            Request request = this.generateRequest();
            if(request == null) {
                in.close();
                out.close();
                socket.close();
                return;
            }

                Object responseObject=null;
            //ovo
            for(HttpRoute httpRoute : this.discoveryMechanism.getControllerHttpRoutes()){//prodje kroz mapu ruta koje postoje i pronadje onu koju smo pogodili
                if(httpRoute.getHttpMethod().equals(request.getMethod().toString()) && httpRoute.getRoute().equals(request.getLocation())) {

                    String controllerClassName = httpRoute.getController().getName();//iz rute dobije ime kontrolera i inicijalizuje sve u njemu
                    diEngine.initDependencies(controllerClassName);

                    Class cl=Class.forName(controllerClassName);
                    Object o =diEngine.returnClassInstance(cl);

                    java.lang.reflect.Method[] methods =cl.getDeclaredMethods();
                    for(int i=0;i<methods.length;i++){
                        if(methods[i].toString().equalsIgnoreCase(httpRoute.getMethodName())){
                            responseObject =methods[i].invoke(o,null); //pokrecemo metodu
                        }
                    }
                    break;
                }
            }

            if(responseObject instanceof HashMap){
                HashMap<String,String>responseMap=(HashMap<String, String>)responseObject;
                Response response = new JsonResponse(responseMap);
                out.println(response.render());
            }

            // Response example
//            Map<String, Object> responseMap = new HashMap<>();
//            responseMap.put("route_location", request.getLocation());
//            responseMap.put("route_method", request.getMethod().toString());
//            responseMap.put("parameters", request.getParameters());
//            Response response = new JsonResponse(responseMap);
//            out.println(response.render());

            in.close();
            out.close();
            socket.close();

        } catch (IOException | RequestNotValidException | ClassNotFoundException | InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private Request generateRequest() throws IOException, RequestNotValidException {
        String command = in.readLine();
        if(command == null) {
            return null;
        }

        String[] actionRow = command.split(" "); //GET / HTTP/1.1

        Method method = Method.valueOf(actionRow[0]);
        String[] splittedRoute = actionRow[1].split("\\?"); //ovo
        String route = splittedRoute[0];
//        String route = actionRow[1];
        Header header = new Header();
        HashMap<String, String> parameters = Helper.getParametersFromRoute(actionRow[1]);

        do {
            command = in.readLine();
            String[] headerRow = command.split(": ");
            if(headerRow.length == 2) {
                header.add(headerRow[0], headerRow[1]);
            }
        } while(!command.trim().equals(""));

        if(method.equals(Method.POST)) {
            int contentLength = Integer.parseInt(header.get("Content-Length"));
            char[] buff = new char[contentLength];
            in.read(buff, 0, contentLength);
            String parametersString = new String(buff);

            //Citamo iz bodija i uspisujemo u mapu parametara
            if (buff.length > 0) jsonParser(parametersString, parameters);
            else parameters = new HashMap<>();
        }

        Request request = new Request(method, route, header, parameters);

        return request;
    }

    private void jsonParser(String jsonString, HashMap<String, String> parameters){
        String []str;
        str = jsonString.split("\n");

        for (int i = 1; i < str.length-1; i++) {
            String []paramsCut = (str[i].replaceAll("\"","")).split(":");
            parameters.put( paramsCut[0],  paramsCut[1]);
        }
//        for (Map.Entry<String,String> entry : parameters.entrySet())
//            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
    }
}
