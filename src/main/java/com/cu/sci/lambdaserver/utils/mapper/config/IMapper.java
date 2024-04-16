package com.cu.sci.lambdaserver.utils.mapper.config;

public interface IMapper<A, B> {
    B mapTo(A a);

    A mapFrom(B b);

    A update(B b, A a);
}
