package com.enstrapp.fieldtekpro.Initialload;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Token_SER {

    @SerializedName("d")
    @Expose
    private D d;

    public D getD() {
        return d;
    }

    public void setD(D d) {
        this.d = d;
    }


    public class D {
        @SerializedName("EntitySets")
        @Expose
        private List<String> entitySets = null;

        public List<String> getEntitySets() {
            return entitySets;
        }

        public void setEntitySets(List<String> entitySets) {
            this.entitySets = entitySets;
        }
    }


}