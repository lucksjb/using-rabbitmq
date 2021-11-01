package com.example.demo.config.jackson;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import com.fasterxml.jackson.databind.util.StdConverter;

public class LocalDateTimeToStringConverter extends StdConverter<LocalDateTime, String> {
  static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);

  @Override
  public String convert(LocalDateTime value) {
      return value.format(DATE_FORMATTER);
  }
}