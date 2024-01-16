package by.javaguru.mapper;

public interface Mapper<F, T> {
    T mapFrom(F object);
}
