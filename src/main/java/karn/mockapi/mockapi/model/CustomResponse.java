package karn.mockapi.mockapi.model;

public record CustomResponse<T>(String respName, T body) {

}
