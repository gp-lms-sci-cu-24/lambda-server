package com.cu.sci.lambdaserver.StudentPackage.Mapper;

public interface Mapper<A, B> {
    B mapTo(A a);

    A mapFrom(B b);
}
