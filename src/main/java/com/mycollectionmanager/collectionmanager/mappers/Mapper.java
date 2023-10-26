package com.mycollectionmanager.collectionmanager.mappers;

public interface Mapper<A, B> {

    B mapTo(A a);
    A mapFrom(B b);
}
