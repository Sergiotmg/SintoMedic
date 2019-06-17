package com.example.sintomedic.rest;

import com.example.sintomedic.model.Enfermedad;
import com.example.sintomedic.model.Role;
import com.example.sintomedic.model.Sintoma;
import com.example.sintomedic.model.Tratamiento;
import com.example.sintomedic.model.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SintoMedicAPI {

    // ############### USUARIOS ###########################

    /** Devuelve el usuario logueado.*/
    @GET("usuario")
    Call<Usuario> getUsuario(@Header("Authorization") String auth);

    /**  * Devuelve un usuario por DNI/NIE.  */
    @GET("usuarios")
    Call<Usuario> getUsuarioByNif(@Query("nif") String dniNie);

    /** Devuelve un usuario por id.  */
    @GET("usuarios/{id}")
    Call<Usuario> getUsuarioById(@Path("id") Long id);

    /*** Crea un usuario.*/
    @POST("usuarios")
    Call<Usuario> createUsuario(@Body Usuario usuario);


    // ######################## DOCTORES ########################

    /** Devuelve los doctores del usuario logueado.*/
    @GET("usuario/doctores")
    Call<List<Usuario>> getDoctores();


    // ######### ENFERMEDADES #############

    /*** Devuelve las enfermedades de un paciente.   */
    @GET("usuarios/{userId}/enfermedades")
    Call<List<Enfermedad>> getEnfermedades(@Path("userId") Long userId);

    /** Registra una enfermedad de un paciente.   **/
    @POST("usuarios/{userId}/enfermedades")
    Call<Enfermedad> createEnfermedad(@Path("userId") Long userId, @Body Enfermedad enfermedad);

    /** Devuelve la enfermedad de un paciente.  */
    @GET("usuarios/{userId}/enfermedades/{id}")
    Call<Enfermedad> getEnfermedad(@Path("userId") Long userId, @Path("id") Long id);

    /** Actualiza enfermedad la enfermedad de un paciente.  */
    @PUT("usuarios/{userId}/enfermedades/{id}")
    Call<Enfermedad> updateEnfermedad(
            @Path("userId") long userId,
            @Path("id") long id,
            @Body Enfermedad enfermedad
    );

    /** Elimina la enfermedad de un paciente.*/
    @DELETE("usuarios/{userId}/enfermedades/{id}")
    Call<Void> deleteEnfermedad(@Path("userId") Long userId, @Path("id") Long id);


    // ##################### PACIENTES ########################

    /**  * Devuelve los pacientes del usuario logueado. **/
    @GET("usuario/pacientes")
    Call<List<Usuario>> getPacientes();

    /** Añade un paciente a la lista de pacientes del usuario logueado.   */
    @POST("usuario/pacientes/{id}")
    Call<Void> addPaciente(@Path("id") Long id);

    /** Elimina un paciente de la lista de pacientes del usuario logueado.  */
    @DELETE("usuario/pacientes/{id}")
    Call<Void> deletePaciente(@Path("id") Long id);


    // ######################### ROLES ##################

    /*********DEVUELVE ROLES USUARIO************/
    @GET("roles")
    Call<List<Role>> getRoles();


    //######### SINTOMAS #############

    /** * Devuelve los síntomas de un paciente. *     */
    @GET("usuarios/{userId}/sintomas")
    Call<List<Sintoma>> getSintomas(@Path("userId") Long userId);

    /** Registra un síntoma de un paciente. */
    @POST("usuarios/{userId}/sintomas")
    Call<Sintoma> createSintoma(@Path("userId") Long userId, @Body Sintoma sintoma);

    /*** Devuelve el síntoma de un paciente. */
    @GET("usuarios/{userId}/sintomas/{id}")
    Call<Sintoma> getSintoma(@Path("userId") Long userId, @Path("id") Long id);


    // ######### TRATAMIENTOS #############

    /*** Devuelve el tratamiento de un paciente.*/
    @GET("usuarios/{userId}/tratamiento")
    Call<Tratamiento> getTratamiento(@Path("userId") Long userId);

    /** * Actualiza el tratamiento de un paciente.*/
    @PUT("usuarios/{userId}/tratamiento")
    Call<Tratamiento> updateTratamiento(@Path("userId") Long userId, @Body Tratamiento tratamiento);

}
