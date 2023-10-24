package ru.netology.springmvc.service;

public interface DtoMutator<S, D> {
    D to(S source);

    S from(D dto);
}
