package com.pool.model.exception;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CustomErrorResponse {
	private String errorCode;
	private String errorMsg;
	private Integer status;
	@JsonFormat(shape = Shape.STRING, pattern = "MM-dd-yyyy hh:mm:ss")
	private Date timestamp;
}
