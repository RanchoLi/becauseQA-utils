/*Copyright 2014 Genium Testing Framework
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.*/
package com.github.becausetesting.cucumber.selenium.appium;

/**
 * A general server exception for any exception that happens while interacting
 * with a server instance.
 *
 * @author Hassan Radi
 */
public class ServerException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3614602958419336425L;

	public ServerException(String message) {
        super(message);
    }
}
