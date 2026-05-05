package io.github.qishr.cascara.common.service;

import java.lang.module.Configuration;
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleDescriptor.Provides;
import java.lang.module.ModuleFinder;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.stream.Collectors;

import io.github.qishr.cascara.common.diagnostic.NullReporter;
import io.github.qishr.cascara.common.diagnostic.Reporter;
import io.github.qishr.cascara.common.util.ModulePath;

public class CascaraServiceLayer {
    private static CascaraServiceLayer bootLayer;

    private Reporter reporter = new NullReporter();

    private String name;
    private ModulePath modulePath;
    private ModuleLayer moduleLayer;
    private CascaraServiceLayer parent;

    private List<Path> jarPaths = new ArrayList<>();
    private List<CascaraServiceLayer> allLayers = new ArrayList<>();
    private Map<String,CascaraServiceLayer> namedLayers = new HashMap<>();
    private Map<String,CascaraServiceMetadata> services = new HashMap<>();
    private Map<Class<?>, Set<CascaraServiceMetadata>> metadataForServiceType = new HashMap<>();

    private CascaraServiceLayer() { }

    public void setReporter(Reporter repoter) { this.reporter = repoter; }

    public String getName() { return name; }

    public CascaraServiceLayer getParent() { return parent; }

    public Collection<CascaraServiceLayer> getAllLayers() { return allLayers; }

    public CascaraServiceLayer getLayer(String name) { return namedLayers.get(name); }

    public boolean containsLayer(String name) { return namedLayers.containsKey(name); }

    public boolean containsService(String name) { return services.containsKey(name); }

    public Collection<CascaraServiceMetadata> getAllServices() { return services.values(); }

    public Path getModulePath(String name) { return modulePath.getPathForModule(name); }

    public static CascaraServiceLayer getBootLayer() {
        if (bootLayer == null) {
            bootLayer = new CascaraServiceLayer();
            // bootLayer.registerAll();
        }
        return bootLayer;
    }

    @SuppressWarnings("unchecked")
    public <T> T getServiceInstance(Class<T> serviceType) {
        try {
            Object object = serviceType.getDeclaredConstructor().newInstance();
            return (T)object;
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public <T> CascaraServiceMetadata getProviderMetadata(String providerName) {
        CascaraServiceMetadata meta = services.get(providerName);
        return meta;
    }

    public <T> Collection<CascaraServiceMetadata> getProvidersMetadata(Class<T> serviceType) {
        Collection<CascaraServiceMetadata> requested = new ArrayList<>();
        if (metadataForServiceType.get(serviceType) != null) {
            requested.addAll(metadataForServiceType.get(serviceType));
        }

        if (parent != null) {
            Collection<CascaraServiceMetadata> toAdd = parent.getProvidersMetadata(serviceType);
            if (toAdd != null) {
                requested.addAll(toAdd);
            }
        }

        return requested;
    }

    // TODO: Methods to find modules by metadata

    // Requirement: renderer factories need to be able to get a renderer by its content type

    // ServiceMetadata<serviceClass> getServices(Class<?> serviceType)
    // ServiceMetadata can be extended/implemented for specific services types.
    // ServiceMetadata can provide content type for filtering providers for render factories.

    public CascaraServiceLayer create() {
        return create(null);
    }

    public CascaraServiceLayer create(String id) {
        CascaraServiceLayer layer = new CascaraServiceLayer();
        layer.parent = this;
        allLayers.add(layer);
        if (id != null) {
            layer.name = id;
            namedLayers.put(id, layer);
        }
        return layer;
    }

    public void registerModule(Module module) {
        ModuleDescriptor desc = module.getDescriptor();
        for (Provides provides : desc.provides()) {
            for (String provide : provides.providers()) {
                try {
                    Class<?> type = module.getClassLoader().loadClass(provide);
                    registerClass(type);
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public void registerClass(Class<?> type) {
        try {
            Object object = type.getDeclaredConstructor().newInstance();
            registerProvider(object, null);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void registerJar(Path jarPath) {
        jarPaths.add(jarPath);
        String paths = String.join(":", getJarStrings());
        modulePath = new ModulePath(paths);

        // 1. Create a finder for modules in this layer
        ModuleFinder finder = ModuleFinder.of(getJarPaths());

        // 2. Resolve the module(s) found against the current boot layer
        Set<String> roots = finder.findAll().stream()
                .map(m -> m.descriptor().name())
                .collect(Collectors.toSet());

        ModuleLayer parent = ModuleLayer.boot();
        Configuration cf = parent.configuration().resolve(finder, ModuleFinder.of(), roots);

        // 3. (re-)create the layer.
        moduleLayer = parent.defineModulesWithManyLoaders(cf, ClassLoader.getSystemClassLoader());

        enumerateModules();
    }

    /// Use SPI to find the service implementations inside this layer
    private <P> void enumerateModules() {
        services.clear();
        metadataForServiceType.clear();
        var loader = ServiceLoader.load(moduleLayer, CascaraService.class);
        loader.forEach(provider -> {
            Path jarPath = modulePath.getPathForModule(provider.getClass().getModule().getName());
            try {
                registerProvider(provider, jarPath);
            } catch (Exception e) {
                registrationError("Failed to query module: " + jarPath.getFileName() + ".", null, e);
            } catch(AbstractMethodError e) {
                registrationError("Incompatible module: " + jarPath.getFileName() + ".", jarPath, e);
            } catch (NoClassDefFoundError e) {
                registrationError("Incompatible module: " + jarPath.getFileName() + ".", jarPath, e);
            } catch (ServiceConfigurationError e) {
                registrationError("Incompatible module: " + jarPath.getFileName() + ".", jarPath, e);
            }
        });
    }

    private void registerProvider(Object provider, Path jarPath) {
        try {
            Class<?> type = provider.getClass();
            List<Class<?>> interfaceHierarchy = new ArrayList<>();
            if (collectCascaraModuleInterfaces(type, interfaceHierarchy)) {

                String className = type.getName();
                CascaraServiceMetadata meta = new CascaraServiceMetadata();

                meta.setType(provider.getClass());
                meta.setInstance(provider);
                services.put(className, meta);

                for (Class<?> interfaceType : interfaceHierarchy) {

                    Set<CascaraServiceMetadata> metas = metadataForServiceType.get(interfaceType);
                    if (metas == null) {
                        metas = new HashSet<>();
                        metadataForServiceType.put(interfaceType, metas);
                    }
                    metas.add(meta);
                }
            }
        } catch(AbstractMethodError e) {
            registrationError("Incompatible module: " + provider.getClass().getName() + ".", jarPath, e);
        } catch (NoClassDefFoundError e) {
            registrationError("Incompatible module: " + provider.getClass().getName() + ".", jarPath, e);
        } catch (ServiceConfigurationError e) {
            registrationError("Incompatible module: " + provider.getClass().getName() + ".", jarPath, e);
        }
    }

    private boolean collectCascaraModuleInterfaces(Class<?> type, List<Class<?>> collected) {
        if (type.equals(CascaraService.class)) {
            return true;
        }
        List<Class<?>> interfaces = List.of(type.getInterfaces());
        boolean found = false;
        for (Class<?> interfaceType : interfaces) {
            if (collectCascaraModuleInterfaces(interfaceType, collected)) {
                collected.add(interfaceType);
                found = true;
            }
        }
        return found;
    }

    private void registrationError(String message, Path path, Throwable t) {
        String logMessage = message;
        if (path != null) {
            logMessage = logMessage + " " + path.getFileName();
        }
        reporter.error(logMessage, t);
    }

    private Path[] getJarPaths() {
        return jarPaths.toArray(new Path[]{});
    }

    private String[] getJarStrings() {
        String[] strings = new String[jarPaths.size()];
        for (int i = 0; i < jarPaths.size(); i++) {
            strings[i] = jarPaths.get(i).toString();
        }
        return strings;
    }
}
