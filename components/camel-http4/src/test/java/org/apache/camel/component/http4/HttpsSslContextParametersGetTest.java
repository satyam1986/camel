/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.http4;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.http4.handler.BasicValidationHandler;
import org.apache.camel.impl.JndiRegistry;
import org.apache.camel.util.jsse.SSLContextParameters;
import org.junit.Test;

public class HttpsSslContextParametersGetTest extends HttpsGetTest {
    
    @Override
    protected JndiRegistry createRegistry() throws Exception {
        JndiRegistry registry = super.createRegistry();
        registry.bind("sslContextParameters", new SSLContextParameters());

        return registry;
    }

    @Test
    public void httpsGet() throws Exception {
        localServer.register("/mail/", new BasicValidationHandler("GET", null, null, getExpectedContent()));

        Exchange exchange = template.request("https4://127.0.0.1:" + getPort() + "/mail/?x509HostnameVerifier=x509HostnameVerifier&sslContextParametersRef=sslContextParameters", new Processor() {
            public void process(Exchange exchange) throws Exception {
            }
        });

        assertExchange(exchange);
    }

}
