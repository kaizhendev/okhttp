/*
 * Copyright (C) 2014 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.squareup.okhttp;

import java.io.IOException;

/**
 * Protocols that OkHttp implements for <a
 * href="http://tools.ietf.org/html/draft-agl-tls-nextprotoneg-04">NPN</a> and
 * <a href="http://tools.ietf.org/html/draft-ietf-tls-applayerprotoneg">ALPN</a>.
 *
 * <h3>Protocol vs Scheme</h3>
 * Despite its name, {@link java.net.URL#getProtocol()} returns the
 * {@link java.net.URI#getScheme() scheme} (http, https, etc.) of the URL, not
 * the protocol (http/1.1, spdy/3.1, etc.). OkHttp uses the word <i>protocol</i>
 * to identify how HTTP messages are framed.
 */
public enum Protocol {
  HTTP_2("h2-10"),
  SPDY_3("spdy/3.1"),
  HTTP_1_1("http/1.1");

  private final String protocol;

  Protocol(String protocol) {
    this.protocol = protocol;
  }

  /**
   * Returns the protocol identified by {@code protocol}.
   * @throws IOException if {@code protocol} is unknown.
   */
  public static Protocol get(String protocol) throws IOException {
    // Unroll the loop over values() to save an allocation.
    if (protocol.equals(HTTP_1_1.protocol)) return HTTP_1_1;
    if (protocol.equals(HTTP_2.protocol)) return HTTP_2;
    if (protocol.equals(SPDY_3.protocol)) return SPDY_3;
    throw new IOException("Unexpected protocol: " + protocol);
  }

  /**
   * Returns the string used to identify this protocol for ALPN and NPN, like
   * "http/1.1", "spdy/3.1" or "h2-10".
   */
  @Override public String toString() {
    return protocol;
  }
}
