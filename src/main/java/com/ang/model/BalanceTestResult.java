package com.ang.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
public class BalanceTestResult {

    private final String input;

    @JsonProperty("isBalanced")
    private final boolean balanced;

}
