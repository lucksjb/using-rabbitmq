package com.example.demo.config.jackson;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.util.StdConverter;

public class LocalDateToStringConverter extends StdConverter<LocalDate, String> {
  static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_DATE;

  @Override
  public String convert(LocalDate value) {
      return value.format(DATE_FORMATTER);
  }
}