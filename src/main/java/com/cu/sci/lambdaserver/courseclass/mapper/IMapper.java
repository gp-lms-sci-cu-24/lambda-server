package com.cu.sci.lambdaserver.courseclass.mapper;

public interface IMapper<A, B> {
    B mapTo(A a);

    A mapFrom(B b);
}