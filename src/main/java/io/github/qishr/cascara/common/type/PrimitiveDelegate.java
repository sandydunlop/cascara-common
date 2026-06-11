package io.github.qishr.cascara.common.type;

import io.github.qishr.cascara.common.lang.QuoteStyle;

public interface PrimitiveDelegate {
    QuoteStyle inferQuoteStyle(Object value);
    Object coerceLiteralValue(String text);
    String unescapeQuotedString(String text, QuoteStyle style);
}
