package io.github.qishr.cascara.common.spi;

import java.util.ServiceLoader;

import io.github.qishr.cascara.common.content.ContentType;
import io.github.qishr.cascara.common.lang.annotation.Nullable;
import io.github.qishr.cascara.common.lang.processor.Parser;

public class ParserFactory {

    @Nullable
    public Parser<?, ?> create(String type) {
        // We call a generic helper that "captures" the Parser type
        return findParser(ServiceLoader.load(Parser.class), type);
    }

    private <P extends Parser<?, ?>> P findParser(ServiceLoader<P> loader, String type) {
        for (ServiceLoader.Provider<P> provider : loader.stream().toList()) {
            try {
                P parser = provider.get();
                ContentType contentType = parser.getContentType();

                if (contentType.getMimeTypes().contains(type)
                    || contentType.getSuffixes().contains(type)
                    || contentType.getName().equalsIgnoreCase(type)) {
                    return parser;
                }
            } catch (RuntimeException e) {
            } catch (Exception e) {
            }
        }
        return null;
    }
}

// public class ParserFactory {

//     @Nullable
//     public Parser<?,?> getParserFor(String type) {
//         var providers = ServiceLoader.load(Parser.class).stream().toList();
//         for (Provider<Parser> provider : providers) {
//             try {
//                 Parser<?,?> parser = provider.get();
//                 ContentType contentType = parser.getContentType();
//                 if (contentType.getMimeTypes().contains(type)
//                     || contentType.getSuffixes().contains(type)
//                     || contentType.getName().equalsIgnoreCase(type)) {

//                     //
//                     return parser;
//                 }
//             } catch (RuntimeException e) {
//             } catch (Exception e) {
//             }
//         }
//         return null;
//     }
// }
