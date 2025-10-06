package org.example.mongoemptymaptest;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.lang.Nullable;

import java.util.Collections;
import java.util.Map;

@ReadingConverter
public class EmptyListToMapReadingConverter implements Converter<Collections.EmptyList, Map> {
    @Nullable
    @Override
    public Map convert(Collections.EmptyList source) {
        return Collections.;
    }
}
