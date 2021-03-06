/**
 * Copyright (c) 2014,2018 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.smarthome.config.core;

import java.net.URI;
import java.util.Collection;
import java.util.Locale;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

/**
 * The {@link ConfigDescriptionProvider} can be implemented and registered as an <i>OSGi</i>
 * service to provide {@link ConfigDescription}s. The {@link ConfigDescriptionRegistry} tracks
 * each {@link ConfigDescriptionProvider} and registers a {@link ConfigDescriptionsChangeListener} on each provider.
 *
 * @author Dennis Nobel - Initial contribution
 * @author Michael Grammling - Initial contribution
 */
@NonNullByDefault
public interface ConfigDescriptionProvider {

    /**
     * Provides a collection of {@link ConfigDescription}s.
     *
     * @param locale locale
     * @return the configuration descriptions provided by this provider (not
     *         null, could be empty)
     */
    Collection<ConfigDescription> getConfigDescriptions(Locale locale);

    /**
     * Provides a {@link ConfigDescription} for the given URI.
     *
     * @param uri uri of the config description
     * @param locale locale
     * @return config description or null if no config description could be found
     */
    @Nullable
    ConfigDescription getConfigDescription(URI uri, Locale locale);

}
