package com.cu.sci.lambdaserver.classGroup.mapper;

public interface iMapper<A, B> {
    B mapTo(A a);

    A mapFrom(B b);
}