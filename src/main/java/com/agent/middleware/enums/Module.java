package com.agent.middleware.enums;

public enum Module {
    ACCESS_CONTROL("access-control"), OPERATIONS("operations");
    public final String path;

    Module(String path){
        this.path = path;
    }
    public static Module getModuleByPath(String path){
        for(Module e : Module.values()){
            if(e.path.equals(path))
                return e;
        }
        return null;
    }
}
