package utils;

import play.data.format.Formatters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AnnotationDateFormatter extends Formatters.AnnotationFormatter<DateFormat, Date> {
  private final static String PATTERN = "dd-MM-yyyy";

  @Override
  public Date parse(DateFormat annotation, String text, Locale locale)
      throws ParseException {
    if(text == null || text.trim().isEmpty()) {
      return null;
    }
    SimpleDateFormat sdf = new SimpleDateFormat(annotation.value(),
        locale);
    sdf.setLenient(true);
    return sdf.parse(text);
  }

  @Override
  public String print(DateFormat annotation, Date date, Locale locale) {
    if(date==null) {
      return "";
    }

    return new SimpleDateFormat(annotation.value(), locale)
        .format(date);
  }
}
