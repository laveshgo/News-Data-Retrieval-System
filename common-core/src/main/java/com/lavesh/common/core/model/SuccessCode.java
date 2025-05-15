package com.lavesh.common.core.model;

import com.lavesh.common.core.enums.SuccessCodeEnum;
import com.lavesh.common.core.util.AppUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SuccessCode {
    private SuccessCodeEnum successCodeEnum;

    private String message;

    @Override
    public String toString() {
        return AppUtil.toJson(this);
    }
}