package com.cu.sci.lambdaserver.courseclass.mapper;

public interface IMapperV2<A, B> {
    B mapTo(A a);

    A mapFrom(B b);

    A update(B b, A a);
}