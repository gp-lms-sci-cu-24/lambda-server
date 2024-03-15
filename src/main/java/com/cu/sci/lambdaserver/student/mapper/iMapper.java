package com.cu.sci.lambdaserver.student.mapper;

public interface iMapper<A, B> {
    B mapTo(A a);

    A mapFrom(B b);
}
