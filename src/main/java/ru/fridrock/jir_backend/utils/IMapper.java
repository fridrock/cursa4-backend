package ru.fridrock.jir_backend.utils;

public interface IMapper<D, E> {
  D mapToDto(E e);
  E mapToEntity(D e);
}
