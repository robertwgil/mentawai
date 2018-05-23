/*
 * Copyright 2001-2004 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mentawai.mail;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.activation.DataSource;


// Revision: 192946

/**
 * This class implements a typed DataSource from:<br>
 *
 * - an InputStream<br>
 * - a byte array<br>
 * - a String<br>
 *
 * @author <a href="mailto:colin.chalmers@maxware.nl">Colin Chalmers</a>
 * @author <a href="mailto:jon@latchkey.com">Jon S. Stevens</a>
 * @author <a href="mailto:bmclaugh@algx.net">Brett McLaughlin</a>
 * @version $Id: ByteArrayDataSource.java,v 1.1 2006/03/02 17:11:41 soliveira Exp $
 */
public class ByteArrayDataSource implements DataSource {

    /** define the buffer size */
    public static final int BUFFER_SIZE = 512;

    /** Stream containg the Data */
    private ByteArrayOutputStream baos;

    /** Content-type. */
    private String type = "application/octet-stream";

    /**
     * Create a datasource from a byte array.
     *
     * @param data A byte[].
     * @param aType A String.
     * @throws IOException IOException
     */
    public ByteArrayDataSource(byte[] data, String aType) throws IOException {
        ByteArrayInputStream bis = null;

        try {
            bis = new ByteArrayInputStream(data);
            this.byteArrayDataSource(bis, aType);
        } catch (IOException ioex) {
            throw ioex;
        } finally {
            if (bis != null) {
                bis.close();
            }
        }
    }

    /**
     * Create a datasource from an input stream.
     *
     * @param aIs An InputStream.
     * @param aType A String.
     * @throws IOException IOException
     */
    public ByteArrayDataSource(InputStream aIs, String aType) throws IOException {
        this.byteArrayDataSource(aIs, aType);
    }

    /**
     * Create a datasource from a String.
     *
     * @param data A String.
     * @param aType A String.
     * @throws IOException IOException
     */
    public ByteArrayDataSource(String data, String aType) throws IOException {
        this.type = aType;

        try {
            baos = new ByteArrayOutputStream();

            // Assumption that the string contains only ASCII
            // characters!  Else just pass in a charset into this
            // constructor and use it in getBytes().
            baos.write(data.getBytes("iso-8859-1"));
            baos.flush();
            baos.close();
        } catch (UnsupportedEncodingException uex) {
            throw new IOException("The Character Encoding is not supported.");
        } finally {
            if (baos != null) {
                baos.close();
            }
        }
    }

    /**
     * Create a datasource from an input stream.
     *
     * @param aIs An InputStream.
     * @param aType A String.
     * @throws IOException IOException
     */
    private void byteArrayDataSource(InputStream aIs, String aType)
        throws IOException {
        this.type = aType;

        BufferedInputStream bis = null;
        BufferedOutputStream osWriter = null;

        try {
            int length = 0;
            byte[] buffer = new byte[ByteArrayDataSource.BUFFER_SIZE];

            bis = new BufferedInputStream(aIs);
            baos = new ByteArrayOutputStream();
            osWriter = new BufferedOutputStream(baos);

            //Write the InputData to OutputStream
            while ((length = bis.read(buffer)) != -1) {
                osWriter.write(buffer, 0, length);
            }
            osWriter.flush();
            osWriter.close();

        } catch (IOException ioex) {
            throw ioex;
        } finally {
            if (bis != null) {
                bis.close();
            }
            if (baos != null) {
                baos.close();
            }
            if (osWriter != null) {
                osWriter.close();
            }
        }
    }

    /**
     * Get the content type.
     *
     * @return A String.
     */
    public String getContentType() {
        return type == null ? "application/octet-stream" : type;
    }

    /**
     * Get the input stream.
     *
     * @return An InputStream.
     * @throws IOException IOException
     */
    public InputStream getInputStream() throws IOException {
        if (baos == null) {
            throw new IOException("no data");
        }
        return new ByteArrayInputStream(baos.toByteArray());
    }

    /**
     * Get the name.
     *
     * @return A String.
     */
    public String getName() {
        return "ByteArrayDataSource";
    }

    /**
     * Get the OutputStream to write to
     *
     * @return  An OutputStream
     */
    public OutputStream getOutputStream() {
        baos = new ByteArrayOutputStream();
        return baos;
    }
}
