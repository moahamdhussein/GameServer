package api;

import DataBase.DaoCallback;
import DataBase.DataBaseDao;
import Network.Request.RequestHandler;
import Network.Request.data.NetworkRequest;
import Network.Response.NetworkResponse;
import Network.Response.NetworkResponse.ResponseMode;
import Network.Response.NetworkResponse.ResponseStatus;
import Network.Response.ResponseHandler;
import Network.Response.data.LogInResponse;
import Network.Response.data.RegisterResposne;
import gameserver.GameHandler;
import java.util.LinkedList;
import java.util.Queue;

public class ApiHandler {

    private static ApiHandler apiHandler = null;

    private Queue<String> requests;
    private Queue<String> responses;

    private final Thread requestThread;
    private final Thread responseThread;

    private ApiHandler() {
        this.requestThread = new Thread(() -> {
            handleRequest();
        });
        this.responseThread = new Thread(() -> {
            handleResponse();
        });
        requests = new LinkedList<>();
        responses = new LinkedList<>();

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

    public void handleResponse() {
        System.out.println("api.ApiHandler.handleResponse()" + responses.size());
        while (true) {
            if (!responses.isEmpty()) {
                System.out.println("Response size in while : = " + responses.size());
                String json = responses.poll();
                NetworkResponse nr = ResponseHandler.getRegisterResponseObj(json);
                redirectResponse(nr);
            }
        }
    }

    private void redirectResponse(NetworkResponse response) {
        String ip = response.getIp();
        if (response.getMode() == ResponseMode.REGISTER) {
            GameHandler.clients.forEach(client -> {
                if (ip.equals(client.getIp())) {
                    client.getSender().println(ResponseHandler.getRegisterResponseJson((RegisterResposne) response));
                }
            });

        } else if (response.getMode() == ResponseMode.LOGIN) {
            GameHandler.clients.forEach(client -> {
                if (ip.equals(client.getIp())) {
                    client.getSender().println(ResponseHandler.getLoginResponseJson((LogInResponse) response));
                }
            });
        }
    }

    private void redirectRequest(String jsonRequest) {
        NetworkRequest convertedRequest = RequestHandler.getRequestFromJsom(jsonRequest);

        switch (convertedRequest.getRequestType()) {
            case LOGIN: {
                new DataBaseDao().getDataForLogin(RequestHandler.getLogInRequestObj(jsonRequest), new DaoCallback<LogInResponse>() {
                    @Override
                    public void onSuccess(LogInResponse results) {
                        LogInResponse response = results;
                        response.setResponseStatus(ResponseStatus.SUCCESS);
                        response.setMode(ResponseMode.LOGIN);
                        response.setIp(convertedRequest.getIp());
                        String loginJsonResponse = ResponseHandler.getLoginResponseJson(response);
                        System.out.println(".onSuccess()+ " + loginJsonResponse);
                        addResponse(loginJsonResponse);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        LogInResponse response = new LogInResponse(convertedRequest.getIp());
                        response.setResponseStatus(ResponseStatus.FAILURE);
                        String loginJsonResponse = ResponseHandler.getLoginResponseJson(response);
                        System.out.println(".onFaluire()+ " + loginJsonResponse);
                        addResponse(loginJsonResponse);
                    }
                });
                break;
            }
            case REGISTER: {

                new DataBaseDao().registerUser(RequestHandler.getRegisterRequestObj(jsonRequest), new DaoCallback<LogInResponse>() {
                    @Override
                    public void onSuccess(LogInResponse results) {
                        RegisterResposne response = new RegisterResposne(convertedRequest.getIp());
                        response.setResponseStatus(ResponseStatus.SUCCESS);
                        response.setUserName(results.getUserName());
                        response.setEmail(results.getEmail());
                        response.setStauts(results.getStatus());
                        response.setDraws(results.getDraws());
                        response.setWins(results.getWins());
                        response.setLosses(results.getLoses());
                        response.setIp(convertedRequest.getIp());
                        String json = ResponseHandler.getRegisterResponseJson(response);
                        addResponse(json);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        RegisterResposne response = new RegisterResposne(convertedRequest.getIp());
                        response = new RegisterResposne(convertedRequest.getIp());
                        response.setResponseStatus(ResponseStatus.FAILURE);
                        addResponse(ResponseHandler.getRegisterResponseJson(response));
                    }
                });

                break;
            }
            case AVAILABLE_PLAYERS: {
                System.out.println(convertedRequest);
                break;
            }
        }
    }

    public void addResponse(String networkResponse) {
        responses.add(networkResponse);
        String json = responses.poll();
        System.out.println("api.ApiHandler.addResponse()" + networkResponse);
        NetworkResponse nr = ResponseHandler.handleResponse(json);
        System.out.println(nr);
        switch (nr.getMode()) {
            case LOGIN: {
                nr = ResponseHandler.getLoginResponseObj(json);
                break;
            }
            case REGISTER: {
                nr = ResponseHandler.getRegisterResponseObj(json);
                break;
            }
        }
        redirectResponse(nr);
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
