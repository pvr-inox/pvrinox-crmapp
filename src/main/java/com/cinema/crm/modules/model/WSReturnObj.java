package com.cinema.crm.modules.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WSReturnObj<T> {
	 private String result = "";
	    private int responseCode;
	    private String msg = "";
	    private Object output;

}
