package org.travelsystem.travel.utils;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVOC {
    private int code;
    private String msg;
    private Object data;
}
