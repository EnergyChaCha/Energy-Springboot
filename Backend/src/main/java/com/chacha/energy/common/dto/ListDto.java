package com.chacha.energy.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ListDto<T> {

    private List<T> list;
}

