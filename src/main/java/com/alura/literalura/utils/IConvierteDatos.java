package com.alura.literalura.utils;

public interface IConvierteDatos {
    <T> T convertirDatos(String json, Class<T> clase);
}
