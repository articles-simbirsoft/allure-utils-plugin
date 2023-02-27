package models.categoriestrend;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Data {
    @JsonProperty("Product defects")
    public int productDefects;

    @JsonProperty("Test defects")
    public int testDefects;

}
