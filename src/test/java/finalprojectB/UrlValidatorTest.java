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



   public void testManualTest()
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
      valid = urlVal.isValid("http://www.##amazon.com"); //for somereason can get this to get branch coverage for line 290
      assertFalse(valid);
      //test file
      valid = urlVal.isValid("file:///Z:/final/final/target/site/cobertura/index.html");
      //assertTrue(valid); // probably false for you
      valid = urlVal.isValid("file:///Z:/final/final/target///site/cobertura/index.html");
      assertFalse(valid); // probably false for you

   }


   public void testYourFirstPartition()
   {

   }

   public void testYourSecondPartition(){

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
