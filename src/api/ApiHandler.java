package api;

import DataBase.DaoCallback;
import DataBase.DataBaseDao;
import Network.Request.data.LoginRequest;
import Network.Request.data.NetworkRequest;
import Network.Request.data.RegisterRequest;
import Network.Response.NetworkResponse.ResponseMode;
import Network.Response.NetworkResponse.ResponseStatus;
import Network.Response.ResponseHandler;
import Network.Response.data.LogInResponse;
import Network.Response.data.RegisterResposne;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import gameserver.GameHandler;
import java.util.LinkedList;
import java.util.Queue;

public class ApiHandler {

    private static ApiHandler apiHandler = null;
    private Queue<String> requests;
    private Queue<String> responses;
    private Queue<String> ips;

    private final Thread requestThread;
    private final Thread responseThread;

    private ApiHandler() {
        this.requestThread = new Thread(() -> {
            handleRequest();
        });
        this.responseThread = new Thread(() -> {
        });
        requests = new LinkedList<>();
        responses = new LinkedList<>();
        ips = new LinkedList<>();

        start();
    }

    public static ApiHandler getInstance() {
        if (apiHandler == null) {
            apiHandler = new ApiHandler();
        }
        return apiHandler;
    }

    public void addRequest(String networkRequest) {
        requests.add(networkRequest);
    }

    public void handleRequest() {
        while (true) {
            if (!requests.isEmpty()) {
                String currentRequest = requests.poll();
                redirectRequest(currentRequest);
            }
        }
    }

    private void redirectRequest(String jsonRequest) {
        JsonArray array = new Gson().fromJson(jsonRequest, JsonArray.class);
        System.err.println("Redirect Request json : " + jsonRequest);
        NetworkRequest.RequestType requestType = NetworkRequest.RequestType.fromString(array.get(14).toString());
        ips.add(array.get(12).toString());
        System.err.println("Current Ip" + array.get(12).toString());
        System.err.println("REGISTER : " + requestType);
        switch (requestType) {

            case LOGIN: {
                System.out.println("api.ApiHandler.redirectRequest() + Json" + jsonRequest);
                LoginRequest loginRequest = new LoginRequest(jsonRequest);
                new DataBaseDao().getDataForLogin(loginRequest, new DaoCallback<String[]>() {
                    @Override
                    public void onSuccess(String[] results) {
                        String[] values = new String[15];
                        values[0] = results[0];
                        values[1] = results[1];
                        values[2] = results[2];
                        values[3] = results[3];
                        values[4] = results[4];
                        values[5] = results[5];
                        values[6] = results[6];
                        values[12] = loginRequest.getIp();
                        values[13] = ResponseStatus.SUCCESS.name();
                        values[14] = ResponseMode.LOGIN.name();

                        LogInResponse response = new LogInResponse(values);
                        System.err.println("Login response = " + response);
                        if (results != null) {
                            System.out.println(".onSuccess()+ " + response);
                            addResponse(new Gson().toJson(response.toArray()));
                        } else {
                            response.setResponseStatus(ResponseStatus.NOTFOUND);
                            response.setMode(ResponseMode.LOGIN);
                            response.setIp(loginRequest.getIp());
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        LogInResponse response = new LogInResponse(loginRequest.getIp());
                        response.setResponseStatus(ResponseStatus.FAILURE);
                        String loginJsonResponse = ResponseHandler.getLoginResponseJson(response);
                        System.out.println(".onFaluire()+ " + loginJsonResponse);
                        addResponse(loginJsonResponse);
                    }
                });
            }
            case REGISTER: {
                RegisterRequest convertedRequest = new RegisterRequest(jsonRequest);

                new DataBaseDao().registerUser(convertedRequest, new DaoCallback<LogInResponse>() {
                    @Override
                    public void onSuccess(LogInResponse results) {
                        results.setIp(convertedRequest.getIp());
                        results.setMode(ResponseMode.LOGIN);
                        results.setResponseStatus(ResponseStatus.SUCCESS);
                        String json = new Gson().toJson(results.toArray());
                        addResponse(json);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        RegisterResposne response = new RegisterResposne(convertedRequest.getIp());
                        response.setResponseStatus(ResponseStatus.FAILURE);
                        addResponse(ResponseHandler.getRegisterResponseJson(response));
                    }
                });

                break;
            }
            case AVAILABLE_PLAYERS: {
//                System.out.println(convertedRequest);
                break;
            }
        }
    }

    private void redirectResponse(String response) {
        System.err.println(" Redidect Response " + response);
        System.out.println("api.ApiHandler.redirectResponse() " + response.replace("\\\"", ""));
        String ip = new Gson().fromJson(response, JsonArray.class).get(12).toString().replace("\"", "");
        GameHandler.clients.forEach(client -> {
            System.err.println("Client ip = " + client.getIp() + "\n ip now = " + ip);
            if (ip.replace("\"", "").equals(client.getIp())) {
                client.getSender().println(response);
            }
        });
    }

    public void addResponse(String networkResponse) {
        responses.add(networkResponse);
        String json = responses.poll();
        redirectResponse(json);
    }

    public void start() {
        requestThread.start();
//        responseThread.start();
    }

    public void stop() {
        requestThread.stop();
        responseThread.stop();
    }
}
