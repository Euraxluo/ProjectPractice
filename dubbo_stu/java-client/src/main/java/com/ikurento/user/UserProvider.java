/*
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
package com.ikurento.user;

public interface UserProvider {
//	User GetUser(String userId,User user);
	User GetUser(String userId);
//	User sayHi(String userId,String username,int age); //链接java时,需要小写
	User SayHi(String userId,String username,int age);//链接go时需要大写
}
