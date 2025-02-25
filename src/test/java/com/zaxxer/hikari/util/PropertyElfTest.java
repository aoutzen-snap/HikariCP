package com.zaxxer.hikari.util;

import org.junit.Test;
import com.zaxxer.hikari.mocks.TestObject;

import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;

public class PropertyElfTest
{
   @Test
   public void setTargetFromProperties() throws Exception
   {
      Properties properties = new Properties();
      properties.setProperty("string", "aString");
      properties.setProperty("testObject", "com.zaxxer.hikari.mocks.TestObject");
      properties.setProperty("shortRaw", "1");
      TestObject testObject = new TestObject();
      PropertyElf.setTargetFromProperties(testObject, properties);
      assertEquals("aString", testObject.getString());
      assertEquals((short) 1, testObject.getShortRaw());
      assertEquals(com.zaxxer.hikari.mocks.TestObject.class, testObject.getTestObject().getClass());
      assertNotSame(testObject, testObject.getTestObject());
   }

   @Test
   public void setTargetFromPropertiesNotAClass() throws Exception
   {
      Properties properties = new Properties();
      properties.setProperty("string", "aString");
      properties.setProperty("testObject", "it is not a class");
      TestObject testObject = new TestObject();
      try {
         PropertyElf.setTargetFromProperties(testObject, properties);
         fail("Could never come here");
      }
      catch (RuntimeException e) {
         assertEquals("argument type mismatch", e.getCause().getMessage());
      }
   }

   @Test
   public void copyPropertiesPropValueIsString() {
      Properties propertiesOrig = new Properties();
      String stringPropName = "stringProp";
      String stringPropValue = "aString";
      propertiesOrig.setProperty(stringPropName, stringPropValue);

      Properties propertiesCopy = PropertyElf.copyProperties(propertiesOrig);
      assertEquals(stringPropValue, propertiesCopy.getProperty(stringPropName));
   }

   @Test
   public void copyPropertiesPropValueIsNonStringObject() {
      Properties propertiesOrig = new Properties();
      String propName = "nonStringProp";
      Integer propValue = 10;
      propertiesOrig.put(propName, propValue);

      Properties propertiesCopy = PropertyElf.copyProperties(propertiesOrig);
      assertEquals(propValue, propertiesCopy.get(propName));
   }
}
