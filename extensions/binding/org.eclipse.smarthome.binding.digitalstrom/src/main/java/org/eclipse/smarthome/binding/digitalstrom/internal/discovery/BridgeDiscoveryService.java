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
package org.eclipse.smarthome.binding.digitalstrom.internal.discovery;

import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.smarthome.binding.digitalstrom.DigitalSTROMBindingConstants;
import org.eclipse.smarthome.binding.digitalstrom.internal.lib.config.Config;
import org.eclipse.smarthome.binding.digitalstrom.internal.lib.serverConnection.DsAPI;
import org.eclipse.smarthome.binding.digitalstrom.internal.lib.serverConnection.constants.JSONApiResponseKeysEnum;
import org.eclipse.smarthome.binding.digitalstrom.internal.lib.serverConnection.impl.DsAPIImpl;
import org.eclipse.smarthome.config.discovery.AbstractDiscoveryService;
import org.eclipse.smarthome.config.discovery.DiscoveryResult;
import org.eclipse.smarthome.config.discovery.DiscoveryResultBuilder;
import org.eclipse.smarthome.core.thing.ThingUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link BridgeDiscoveryService} is responsible for discovering digitalSTROM-Server, if the server is in the
 * local network and is reachable through "dss.local." with default port number "8080". It uses the central
 * {@link AbstractDiscoveryService}.
 *
 * @author Michael Ochel - Initial contribution
 * @author Matthias Siegele - Initial contribution
 */
public class BridgeDiscoveryService extends AbstractDiscoveryService {

    private final Logger logger = LoggerFactory.getLogger(BridgeDiscoveryService.class);
    public static final String HOST_ADDRESS = "dss.local.";

    private final Runnable resultCreater = new Runnable() {

        @Override
        public void run() {
            createResult();
        }

        private void createResult() {
            ThingUID uid = getThingUID();

            if (uid != null) {
                Map<String, Object> properties = new HashMap<>(2);
                properties.put(DigitalSTROMBindingConstants.HOST, HOST_ADDRESS);
                DiscoveryResult result = DiscoveryResultBuilder.create(uid).withProperties(properties)
                        .withLabel("digitalSTROM-Server").build();
                thingDiscovered(result);
            }
        }

        private ThingUID getThingUID() {
            DsAPI digitalSTROMClient = new DsAPIImpl(HOST_ADDRESS, Config.DEFAULT_CONNECTION_TIMEOUT,
                    Config.DEFAULT_READ_TIMEOUT, true);
            String dSID = null;
            switch (digitalSTROMClient.checkConnection("123")) {
                case HttpURLConnection.HTTP_OK:
                case HttpURLConnection.HTTP_UNAUTHORIZED:
                case HttpURLConnection.HTTP_FORBIDDEN:
                    Map<String, String> dsidMap = digitalSTROMClient.getDSID(null);
                    if (dsidMap != null) {
                        dSID = dsidMap.get(JSONApiResponseKeysEnum.DSID.getKey());
                    }
                    if (StringUtils.isNotBlank(dSID)) {
                        return new ThingUID(DigitalSTROMBindingConstants.THING_TYPE_DSS_BRIDGE, dSID);
                    } else {
                        logger.error("Can't get server dSID to generate ThingUID. Please add the server manually.");
                    }
            }
            return null;
        }
    };

    /**
     * Creates a new {@link BridgeDiscoveryService}.
     */
    public BridgeDiscoveryService() {
        super(new HashSet<>(Arrays.asList(DigitalSTROMBindingConstants.THING_TYPE_DSS_BRIDGE)), 10, false);
    }

    @Override
    protected void activate(Map<String, Object> configProperties) {
        super.activate(configProperties);
    }

    @Override
    protected void modified(Map<String, Object> configProperties) {
        super.modified(configProperties);
    }

    @Override
    protected void startScan() {
        scheduler.execute(resultCreater);
    }
}
