package com.springframework.extensionpoint.model;

import com.springframework.extensionpoint.stragety.ExceptionStrategy;
import com.springframework.extensionpoint.stragety.ResultStrategy;
import com.springframework.extensionpoint.stragety.RouterStrategy;

public class ExtensionPoint {
    ExtensionPointCode code;
    RouterStrategy routerStrategy;
    ResultStrategy resultStrategy;
    ExceptionStrategy exceptionStrategy;
}
