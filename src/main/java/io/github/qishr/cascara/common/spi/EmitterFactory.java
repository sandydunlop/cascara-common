package io.github.qishr.cascara.common.spi;

import java.util.ServiceLoader;

import io.github.qishr.cascara.common.content.ContentType;
import io.github.qishr.cascara.common.lang.annotation.Nullable;
import io.github.qishr.cascara.common.lang.processor.Emitter;

public class EmitterFactory {

    @Nullable
    public Emitter create(String type) {
        return findEmitter(ServiceLoader.load(Emitter.class), type);
    }

    private <T extends Emitter> T findEmitter(ServiceLoader<T> loader, String type) {
        for (ServiceLoader.Provider<T> provider : loader.stream().toList()) {
            try {
                T emitter = provider.get();
                ContentType contentType = emitter.getContentType();

                if (contentType.getMimeTypes().contains(type)
                    || contentType.getSuffixes().contains(type)
                    || contentType.getName().equalsIgnoreCase(type)) {
                    return emitter;
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
