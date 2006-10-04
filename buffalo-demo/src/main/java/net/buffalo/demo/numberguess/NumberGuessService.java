/*
 * Copyright 2002-2004 the original author or authors.
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

package net.buffalo.demo.numberguess;

import java.util.Map;

import net.buffalo.request.RequestContext;

/**
 * 
 * @author michael
 */
public class NumberGuessService {
	
	private NumberGuessBean getBean() {
		Map session = RequestContext.getContext().getSession();
		
		NumberGuessBean bean = (NumberGuessBean)(session.get("guessbean"));
		if (bean == null) {
		    newRound();
		}
		bean = (NumberGuessBean)(session.get("guessbean"));
		
		return bean;
	}
	
	public void newRound() {
		
		Map session2 = RequestContext.getContext().getSession();
		
		NumberGuessBean bean = new NumberGuessBean();
		
		session2.put("guessbean", bean);
	}
	
	public NumberGuessBean guess(int guess) {
		NumberGuessBean bean = getBean(); 
		bean.setGuess(guess);
		
		return bean;
	}
}
