package com.ang.util;

import lombok.Data;

/**
 * A simple class to hold a pair of objects.
 */
@Data
public class Pair<A, B> {

    private final A left;
    private final B right;

}
