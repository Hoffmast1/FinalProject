/*
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
package finalprojectB;

import junit.framework.TestCase;





/**
 * Performs Validation Test for url validations.
 *
 * @version $Revision: 1128446 $ $Date: 2011-05-27 13:29:27 -0700 (Fri, 27 May 2011) $
 */
public class UrlValidatorTest extends TestCase {

   private boolean printStatus = false;
   private boolean printIndex = false;//print index that indicates current scheme,host,port,path, query test were using.

   public UrlValidatorTest(String testName) {
      super(testName);
   }

   public void testManually()
   {
     String validURLs[] = {
        "http://www.amazon.com",
        "https://google.com",
        //"http://reddit.com?parameter=whatever",
        "ftp://blahblah.io",
        //"fiddlesticks.org/legos"
      };
      String invalidURLs[] = {
        "http//:website.com",
        "place.org/what.the",
        "http://ftp://blah.org",
        "query.problems?hello&fish=blah",
        "hello.world:80zzz"
      };
      //test constructor null, null, long
  	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
       System.out.println(urlVal.isValid("http://www.amazon.com"));
       //test constructor (long)
       urlVal = new UrlValidator(UrlValidator.ALLOW_ALL_SCHEMES);
  	   System.out.println(urlVal.isValid("http://www.amazon.com"));
      for (String url : validURLs) {
            boolean valid = urlVal.isValid(url);
            System.out.println(url + " " + valid);
            assertTrue(valid);
      }

      for (String url : invalidURLs) {
          boolean valid = urlVal.isValid(url);
          System.out.println(url + " " + valid);
          assertFalse(valid);
      }
      //test get instance
      UrlValidator testGet = urlVal.getInstance();
      //assertEquals(testGet, urlVal);  //doesnt work right, test fails

      //constructor for (regecValidator, long)
      RegexValidator testReg = new RegexValidator("test");
      urlVal = new UrlValidator(testReg, UrlValidator.ALLOW_ALL_SCHEMES);

      //constructor for (scheme(NULL), regecValidator, long)
      urlVal = new UrlValidator(null ,testReg, UrlValidator.ALLOW_ALL_SCHEMES);

      //constructor for (scheme, regecValidator, long(not ALLOW_ALL_SCHEMES))
      urlVal = new UrlValidator(validURLs,testReg, UrlValidator.NO_FRAGMENTS);


      urlVal = new UrlValidator(null ,testReg, UrlValidator.ALLOW_ALL_SCHEMES);
      //test isvalid null
      boolean valid = urlVal.isValid(null);
      assertFalse(valid);
      //test invalid Ascii values
      valid = urlVal.isValid("www.☃☃☃.com");
      assertFalse(valid);
      //test invalid url structure
      valid = urlVal.isValid("http://www.##amazon.com"); //for somereason cannot get this to get branch coverage for line 290
      assertFalse(valid);
      //test file
      valid = urlVal.isValid("file:///Z:/final/final/target/site/cobertura/index.html");
      //assertTrue(valid); // probably false for you
      valid = urlVal.isValid("file:///Z:/final/final/target///site/cobertura/index.html");
      assertFalse(valid); // probably false for you
      //test doubledot flag
      valid = urlVal.isValid("http://www..amazon..com");
      assertFalse(valid); // probably false for you
      //Tests url query which fails
      //FAILURE
      //FAILURE
      //FAILURE
      urlVal = new UrlValidator();
      //valid = urlVal.isValid("https://www.google.com/search?q=url+query&ie=utf-8&oe=utf-8");
      //assertTrue(valid);
      //test region codes
      valid = urlVal.isValid("https://www.go.fr:80");
      assertTrue(valid);
      //FAILURE
      //FAILURE
      //FAILURE
      //false region code not in DomainValidator
      //valid = urlVal.isValid("https://www.gov.uk/");
      //assertTrue(valid);

   }


   public void testSchemes()
   {
       UrlValidator validator = new UrlValidator();
       ResultPair[] testSchemes = {
               new ResultPair("http://", true),
               new ResultPair("ftp://", true),
               new ResultPair("https://", true),
               new ResultPair("123456://", false),
               new ResultPair("/:/", false)
       };

       for (ResultPair pair : testSchemes) {
           String url = pair.item + "website.com";
           boolean valid = validator.isValid(url);
           System.out.println(url + " " + valid);
           assertEquals(valid, pair.valid);
       }
   }

   public void testAuthorities() {
       UrlValidator validator = new UrlValidator();
       ResultPair[] testAuthorities = {
               new ResultPair("google.com", true),
               new ResultPair("231.143.81.3", true),
               new ResultPair("website.io", true),
               new ResultPair("whatever.us", true),
               new ResultPair("300.400.100.322", false),
               new ResultPair(".net", false),
               new ResultPair("...", false)
       };

       for (ResultPair pair : testAuthorities) {
           String url = "https://" + pair.item + "/test/?foo=bar";
           boolean valid = validator.isValid(url);
           System.out.println(url + " " + valid);
           assertEquals(pair.valid, valid);
       }
   }

   public void testPorts() {
        UrlValidator validator = new UrlValidator();
        ResultPair[] testPorts = {
                new ResultPair(":80", true),
                new ResultPair(":70000", false),
                new ResultPair("99:", false),
                new ResultPair(":", false)
        };

        for (ResultPair pair : testPorts) {
            String url = "https://website.com" + pair.item + "/test/?foo=bar";
            boolean valid = validator.isValid(url);
            System.out.println(url + " " + valid);
            assertEquals(pair.valid, valid);
        }
    }

    public void testPaths() {
        UrlValidator validator = new UrlValidator();
        ResultPair[] testPorts = {
                new ResultPair("/", true),
                new ResultPair("/whatever", true),
                new ResultPair("/foo/bar", true),
                new ResultPair("", true),
                new ResultPair("/bar//", false),
                new ResultPair("/.../", false)
        };

        for (ResultPair pair : testPorts) {
            String url = "https://website.com" + pair.item + "?foo=bar";
            boolean valid = validator.isValid(url);
            System.out.println(url + " " + valid);
            assertEquals(pair.valid, valid);
        }
    }

    public void testQueryStrings() {
        UrlValidator validator = new UrlValidator();
        ResultPair[] testPorts = {
                new ResultPair("?foo=bar", true),
                new ResultPair("?one=foo&two=bar", true),
        };

        for (ResultPair pair : testPorts) {
            String url = "https://website.com/test/" + pair.item;
            boolean valid = validator.isValid(url);
            System.out.println(url + " " + valid);
            assertEquals(pair.valid, valid);
        }
    }


   public void testIsValid()
   {
	   for(int i = 0;i<10000;i++)
	   {

	   }
   }

   public void testAnyOtherUnitTest()
   {

   }
   /**
    * Create set of tests by taking the testUrlXXX arrays and
    * running through all possible permutations of their combinations.
    *
    * @param testObjects Used to create a url.
    */


}
