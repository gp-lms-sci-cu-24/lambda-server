package com.cu.sci.lambdaserver.courseClass.mapper;

public interface iMapper<A, B> {
    B mapTo(A a);

    A mapFrom(B b);
}