/*
 * DocumentProvider.java January 2010
 *
 * Copyright (C) 2010, Niall Gallagher <niallg@users.sf.net>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 * implied. See the License for the specific language governing 
 * permissions and limitations under the License.
 */

package com.aliyun.odps.simpleframework.xml.stream;

import java.io.InputStream;
import java.io.Reader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * The <code>DocumentProvider</code> object is used to provide event
 * reader implementations for DOM. Wrapping the mechanics of the
 * DOM framework within a <code>Provider</code> ensures that it can
 * be plugged in without any dependencies. This allows other parsers
 * to be swapped in should there be such a requirement.
 * 
 * @author Niall Gallagher
 * 
 * @see DocumentProvider
 */
class DocumentProvider implements Provider {
   
   /**
    * This is the factory that is used to create DOM parsers.
    */
   private final DocumentBuilderFactory factory;
   
   /**
    * Constructor for the <code>DocumentProvider</code> object. This
    * is used to instantiate a parser factory that will be used to
    * create parsers when requested. Instantiating the factory up
    * front also checks that the framework is fully supported.
    */
   public DocumentProvider() {
      this.factory = DocumentBuilderFactory.newInstance();
      this.factory.setNamespaceAware(true);
      try {
         this.factory.setFeature(
             "http://apache.org/xml/features/disallow-doctype-decl", true);
         this.factory.setFeature(
             "http://xml.org/sax/features/external-parameter-entities", false);
         this.factory.setFeature(
             "http://xml.org/sax/features/external-general-entities", false);
      } catch (ParserConfigurationException e) {
         throw new RuntimeException(e);
      }
   }
   
   /**
    * This provides an <code>EventReader</code> that will read from
    * the specified input stream. When reading from an input stream
    * the character encoding should be taken from the XML prolog or
    * it should default to the UTF-8 character encoding.
    * 
    * @param source this is the stream to read the document with
    * 
    * @return this is used to return the event reader implementation
    */
   public EventReader provide(InputStream source) throws Exception {
      return provide(new InputSource(source));
   }
   
   /**
    * This provides an <code>EventReader</code> that will read from
    * the specified reader. When reading from a reader the character
    * encoding should be the same as the source XML document.
    * 
    * @param source this is the reader to read the document with
    * 
    * @return this is used to return the event reader implementation
    */
   public EventReader provide(Reader source) throws Exception {
      return provide(new InputSource(source));
   }   
   
   /**
    * This provides an <code>EventReader</code> that will read from
    * the specified source. When reading from a source the character
    * encoding should be the same as the source XML document.
    * 
    * @param source this is the source to read the document with
    * 
    * @return this is used to return the event reader implementation
    */
   private EventReader provide(InputSource source) throws Exception {
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document document = builder.parse(source);

      return new DocumentReader(document);   
   }
}
