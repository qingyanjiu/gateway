package com.example.gateway;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class PreFilter extends ZuulFilter {
	
	@Autowired
	private LoadBalancerClient loadBalancerClient;

  @Override
  public String filterType() {
    return "pre";
  }

  @Override
  public int filterOrder() {
    return 1;
  }

  @Override
  public boolean shouldFilter() {
    return true;
  }

  @Override
  public Object run() {
    RequestContext ctx = RequestContext.getCurrentContext();
    HttpServletRequest request = ctx.getRequest();
    ServiceInstance serviceInstance = loadBalancerClient.choose("ee-service");
    System.out.println("****************************************************************");
    System.out.println("Request Method : " + request.getMethod() + " Request URL : " + serviceInstance.getScheme()+serviceInstance.getHost()+":"+serviceInstance.getPort());
    System.out.println("****************************************************************");
    return null;
  }
}
