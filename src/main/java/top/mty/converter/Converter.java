package top.mty.converter;

public interface Converter<S, T> {
  T convert(S source);
}
