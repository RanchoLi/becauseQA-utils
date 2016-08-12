package com.github.becauseQA.httpclient.bean;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.github.becauseQA.apache.commons.StringUtils;
import com.github.becauseQA.properties.PropertiesUtils;

public class RequestProxyConfig {

	private static final Logger logger=Logger.getLogger(RequestProxyConfig.class);
	 // Global
    public static final int DEFAULT_PORT = 8080;
    
    private static final String PROP_PREFIX = "proxy.options.";

    // Singleton
    
    private static RequestProxyConfig _proxy;
    
    public static RequestProxyConfig getInstance(){
        if(_proxy == null){
            _proxy = new RequestProxyConfig();
            try {
                _proxy.init();
            }
            catch(Exception ex) {
            	logger.error( "Cannot load Proxy options from properties.", ex);
            }
        }
        return _proxy;
    }
    
    private void init() {

        _proxy.setEnabled(Boolean.valueOf(PropertiesUtils.getBundleString(PROP_PREFIX + "is_enabled")));
        _proxy.setHost(PropertiesUtils.getBundleString(PROP_PREFIX + "host"));
        { // Port:
            final String portStr = PropertiesUtils.getBundleString(PROP_PREFIX + "port");
            if(StringUtils.isNotEmpty(portStr)) {
                _proxy.setPort(Integer.parseInt(portStr));
            }
        }
        _proxy.setAuthEnabled(Boolean.valueOf(PropertiesUtils.getBundleString(PROP_PREFIX + "is_auth_enabled")));
        _proxy.setUsername(PropertiesUtils.getBundleString(PROP_PREFIX + "username"));
        { // Password:
            final String pwdStr = PropertiesUtils.getBundleString(PROP_PREFIX + "password");
            if(StringUtils.isNotEmpty(pwdStr)) {
                _proxy.setPassword(pwdStr.toCharArray());
            }
        }
    }
    
    public void write() {
      //  IGlobalOptions options = ServiceLocator.getInstance(IGlobalOptions.class);
      /*  options.setProperty(PROP_PREFIX + "is_enabled", String.valueOf(_proxy.isEnabled()));
        options.setProperty(PROP_PREFIX + "host", _proxy.getHost());
        options.setProperty(PROP_PREFIX + "port", String.valueOf(_proxy.getPort()));
        options.setProperty(PROP_PREFIX + "is_auth_enabled", String.valueOf(_proxy.isAuthEnabled()));
        options.setProperty(PROP_PREFIX + "username", _proxy.getUsername());
        String pwd = _proxy.getPassword()==null? "": new String(_proxy.getPassword());
        options.setProperty(PROP_PREFIX + "password", pwd);*/
    }
    
    private RequestProxyConfig(){}
    
    // Lock to ensure consistency
    private final Lock _lck = new ReentrantLock();
    
    public void acquire(){
        _lck.lock();
    }
    
    public void release(){
        _lck.unlock();
    }
    
    // Data
    
    private boolean enabled = false;
    private String host;
    private int port = DEFAULT_PORT;
    private boolean authEnabled = false;
    private String username;
    private char[] password;

    public boolean isAuthEnabled() {
        return authEnabled;
    }

    public void setAuthEnabled(boolean authEnabled) {
        this.authEnabled = authEnabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
