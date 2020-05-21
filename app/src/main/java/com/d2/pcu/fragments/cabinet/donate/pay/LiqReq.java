package com.d2.pcu.fragments.cabinet.donate.pay;

import androidx.annotation.StringDef;

import com.google.gson.annotations.SerializedName;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public final class LiqReq {
    @SerializedName("public_key")
    private String puKey = "i67472329007";
    @SerializedName("private_key")

    private String priKey = "f2zSFljyGiIjSmiZD8HdVOzxV1hiU9tHDnyBkJPD";
    @SerializedName("version")
    private String version = "3";
    @SerializedName("action")
    private String action = Action.DONATE;
    @SerializedName("amount")
    private String amount = "3";
    @SerializedName("currency")
    private String currency = "UAH";
    @SerializedName("description")
    private String description = "test";
    @SerializedName("order_id")
    private String order_id = "000001123";
    @SerializedName("language")
    private String lang = "uk";
    @SerializedName("result_url")
    private String resultUrl = "http://pcu.com/success";

    public String getPuKey() {
        return puKey;
    }

    public void setPuKey(String puKey) {
        this.puKey = puKey;
    }

    public String getPriKey() {
        return priKey;
    }

    public void setPriKey(String priKey) {
        this.priKey = priKey;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    @StringDef({
            Action.SUBSCRIBE, Action.DONATE, Action.PAY, Action.AUTH
    })
    @Retention(RetentionPolicy.CLASS)
    public @interface Action {
        String SUBSCRIBE="subscribe";
        String DONATE = "paydonate";
        String PAY = "pay";
        String AUTH = "auth";
    }



    /*subscribe	            Optional	String	Регулярный платеж. Возможные значения: 1
    subscribe_date_start	Optional	String	Дата первого платежа. Время необходимо указывать в следующем формате 2015-03-31 00:00:00 по UTC. Если указана прошедшая дата, то подписка будет активирована с текущей даты получения запроса
    subscribe_periodicity	Optional	String	Периодичность списания средств. Возможные значения:
    month - раз в месяц
    year - раз в год*/
}
