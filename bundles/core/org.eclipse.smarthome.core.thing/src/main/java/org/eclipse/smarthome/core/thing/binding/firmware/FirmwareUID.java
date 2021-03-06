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
package org.eclipse.smarthome.core.thing.binding.firmware;

import java.util.Objects;

import org.eclipse.smarthome.core.thing.ThingTypeUID;

/**
 * The {@link FirmwareUID} is the unique identifier for a {@link Firmware}. It consists of the {@link ThingTypeUID} and
 * the corresponding firmware version.
 *
 * @author Thomas Höfer - Initial contribution
 */
public final class FirmwareUID {

    private ThingTypeUID thingTypeUID;
    private String firmwareVersion;

    /**
     * Default constructor. Will allow to instantiate this class by reflection.
     */
    protected FirmwareUID() {
        // does nothing at all
    }

    /**
     * Creates a new firmware UID.
     *
     * @param thingTypeUID the thing type UID (must not be null)
     * @param firmwareVersion the version of the firmware (must not be null)
     * @throws NullPointerException if given thing type UID or firmware version is null
     * @throws IllegalArgumentException if given firmware version is null or empty or consist of a colon
     */
    public FirmwareUID(ThingTypeUID thingTypeUID, String firmwareVersion) {
        Objects.requireNonNull(thingTypeUID, "Thing type UID must not be null.");
        if (firmwareVersion == null || firmwareVersion.isEmpty()) {
            throw new IllegalArgumentException("Firmware version must not be null or empty.");
        }
        if (firmwareVersion.contains(":")) {
            throw new IllegalArgumentException("Firmware version must not consist of a colon.");
        }
        this.thingTypeUID = thingTypeUID;
        this.firmwareVersion = firmwareVersion;
    }

    /**
     * Returns the thing type UID.
     *
     * @return the thing type UID
     */
    public ThingTypeUID getThingTypeUID() {
        return thingTypeUID;
    }

    /**
     * Returns the firmware version.
     *
     * @return the firmware version
     */
    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((firmwareVersion == null) ? 0 : firmwareVersion.hashCode());
        result = prime * result + ((thingTypeUID == null) ? 0 : thingTypeUID.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        FirmwareUID other = (FirmwareUID) obj;
        if (firmwareVersion == null) {
            if (other.firmwareVersion != null) {
                return false;
            }
        } else if (!firmwareVersion.equals(other.firmwareVersion)) {
            return false;
        }
        if (thingTypeUID == null) {
            if (other.thingTypeUID != null) {
                return false;
            }
        } else if (!thingTypeUID.equals(other.thingTypeUID)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FirmwareUID [thingTypeUID=" + thingTypeUID + ", firmwareVersion=" + firmwareVersion + "]";
    }

}
