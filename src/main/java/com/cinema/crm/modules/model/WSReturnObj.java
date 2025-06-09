package com.cinema.crm.modules.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WSReturnObj<T> {
	 private String result = "";
	    private int responseCode;
	    private String msg = "";
	    private Object output;

}
