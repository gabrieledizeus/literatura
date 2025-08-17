package com.alura.literatura.services;

public interface IConverteDados {
    <T> T  obterDados(String json, Class<T> classe);
}
