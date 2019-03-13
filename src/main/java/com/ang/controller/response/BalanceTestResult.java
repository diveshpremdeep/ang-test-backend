package com.ang.controller.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class BalanceTestResult {

    private final String input;
    private final boolean valid;

}
