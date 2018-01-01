package com.rest.autotests.core.util.filters;

import com.jayway.restassured.builder.ResponseBuilder;
import com.jayway.restassured.filter.Filter;
import com.jayway.restassured.filter.FilterContext;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.FilterableRequestSpecification;
import com.jayway.restassured.specification.FilterableResponseSpecification;

/**
 * Created by Andrej Skeledzija 2017
 */
public class MyFilter implements Filter {
    @Override
    public Response filter(FilterableRequestSpecification filterableRequestSpecification, FilterableResponseSpecification filterableResponseSpecification, FilterContext filterContext) {
        Response response = filterContext.next(filterableRequestSpecification, filterableResponseSpecification); // Invoke the request by delegating to the next filter in the filter chain.
        ResponseBuilder builder = new ResponseBuilder();
        builder.clone(response);
        builder.setCookies(response.detailedCookies());
        builder.setContentType(response.getContentType());
        builder.setHeaders(response.getHeaders());
        builder.setStatusCode(response.getStatusCode());
        builder.setStatusLine(response.getStatusLine());
//        byte[] bytes = new byte[response.body().asByteArray().length-3];
//        for (int i = 3; i < response.body().asByteArray().length; i++){
//            bytes[i-3] = response.body().asByteArray()[i];
//        }
//        builder.setBody(bytes);
        builder.setBody(response.body().asByteArray());
        Response newResponse = builder.build();
        return newResponse;
    }
}
