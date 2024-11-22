package com.example.todolog.dto.feeddto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class DateRangeRequestDto {

    // 시작 날짜
    private LocalDate startDate;

    // 종료 날짜
    private LocalDate endDate;

}
