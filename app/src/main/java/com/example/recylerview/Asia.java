package com.example.recylerview;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "country_details")
public class Asia  implements Serializable {


    @PrimaryKey(autoGenerate = true)

    private int id;
    @ColumnInfo(name="name")
     private String name;
    @ColumnInfo(name="capital")
     private String capital;
    @ColumnInfo(name="flagurl")
     private String flagurl;
    @ColumnInfo(name="region")
     private String region;
    @ColumnInfo(name="subregion")
     private String subregion;
    @ColumnInfo(name="population")
    private String population;
    @ColumnInfo(name="borgers")
    private String borgers;
    @ColumnInfo(name="languages")
    private String languages;

    public Asia(String name, String capital, String flagurl, String region, String subregion, String population, String borgers, String languages) {
        this.name = name;
        this.capital = capital;
        this.flagurl = flagurl;
        this.region = region;
        this.subregion = subregion;
        this.population = population;
        this.borgers = borgers;
        this.languages = languages;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public void setFlagurl(String flagurl) {
        this.flagurl = flagurl;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public void setBorgers(String borgers) {
        this.borgers = borgers;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCapital() {
        return capital;
    }

    public String getFlagurl() {
        return flagurl;
    }

    public String getRegion() {
        return region;
    }

    public String getSubregion() {
        return subregion;
    }

    public String getPopulation() {
        return population;
    }

    public String getBorgers() {
        return borgers;
    }

    public String getLanguages() {
        return languages;
    }

}
