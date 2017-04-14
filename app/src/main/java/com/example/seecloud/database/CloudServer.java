package com.example.seecloud.database;

import com.example.seecloud.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by monkeysmac on 2017/4/9.
 */

public class CloudServer implements Serializable{

    private List<Attribute> attributes = new ArrayList<>();

    private String name;

    private int cloudID;

    private int imageID;

    public CloudServer(String name, int cloudID) {
        this.name = name;
        this.cloudID = cloudID;
    }

    public CloudServer(String name, int cloudID, int imageID) {
        this.name = name;
        this.cloudID = cloudID;
        this.imageID = imageID;
    }

    public void initAttr(double connet, double pacakage, double screen, double download, double responde, double cpu, double database, double apdex, double memory, int tingyun) {
        Attribute attributes[] = {
                new Attribute("connectingtime", connet),
                new Attribute("firstpackagetime", pacakage),
                new Attribute("firstscreentime", screen),
                new Attribute("totaldownloadtime", download),
                new Attribute("applicationserverresponsetime", responde),
                new Attribute("cpuusage", cpu),
                new Attribute("databasecallingtime", database),
                new Attribute("apdex", apdex),
                new Attribute("servermemorytakeup", memory),
                new Attribute("tingyuncomprehensiveranking", tingyun)
        };
        for (Attribute attr:attributes
                ) {
            this.attributes.add(attr);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCloudID() {
        return cloudID;
    }

    public void setCloudID(int cloudID) {
        this.cloudID = cloudID;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    @Override
    public String toString() {
        return cloudID + " " + name;
    }
}
