package com.example.sintomedicCasa.rest;

import com.example.sintomedicCasa.controllers.Controller;
import com.example.sintomedicCasa.model.Usuario;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthenticationInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Usuario usuario = Controller.getUsuarioLogueado(null);

        if (request.header("Authorization") == null && usuario != null) {
            request = request.newBuilder()
                    .addHeader("Authorization", Credentials.basic(usuario.getDniNie(), usuario.getContrasenia()))
                    .build();
        }

        return chain.proceed(request);
    }

}
