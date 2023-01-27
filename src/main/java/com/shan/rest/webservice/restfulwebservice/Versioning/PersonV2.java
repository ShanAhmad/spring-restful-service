package com.shan.rest.webservice.restfulwebservice.Versioning;

public class PersonV2 {

    public Name getName() {
        return name;
    }

    public PersonV2() {
    }

    public PersonV2(Name name) {
        this.name = name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    private Name name;
}
