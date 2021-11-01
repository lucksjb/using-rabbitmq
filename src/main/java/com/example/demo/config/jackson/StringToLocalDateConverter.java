package com.example.demo.config.jackson;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.util.StdConverter;

public class StringToLocalDateConverter extends StdConverter<String, LocalDate> {

  @Override
  public LocalDate convert(String value) {
      return LocalDate.parse(value, LocalDateToStringConverter.DATE_FORMATTER);
  }
}