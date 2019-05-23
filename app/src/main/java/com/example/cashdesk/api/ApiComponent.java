package com.example.cashdesk.api;

public class ApiComponent {
    private static ApiImpl api;

    public static ApiImpl provideApi(){
        if (api == null){
            api = new ApiImpl(new ApiClient().getClient());
        }

        return api;
    }
}
