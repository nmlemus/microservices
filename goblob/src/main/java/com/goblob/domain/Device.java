package com.goblob.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Device.
 */
@Entity
@Table(name = "device")
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "model")
    private String model;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "display")
    private String display;

    @Column(name = "gcm_token")
    private String gcmToken;

    @Column(name = "android_version")
    private String androidVersion;

    @Column(name = "created")
    private ZonedDateTime created;

    @Column(name = "signed_in")
    private String signedIn;

    @Column(name = "app_version")
    private String appVersion;

    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "os")
    private String os;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public Device model(String model) {
        this.model = model;
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public Device manufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDisplay() {
        return display;
    }

    public Device display(String display) {
        this.display = display;
        return this;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getGcmToken() {
        return gcmToken;
    }

    public Device gcmToken(String gcmToken) {
        this.gcmToken = gcmToken;
        return this;
    }

    public void setGcmToken(String gcmToken) {
        this.gcmToken = gcmToken;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public Device androidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
        return this;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public Device created(ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public String getSignedIn() {
        return signedIn;
    }

    public Device signedIn(String signedIn) {
        this.signedIn = signedIn;
        return this;
    }

    public void setSignedIn(String signedIn) {
        this.signedIn = signedIn;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public Device appVersion(String appVersion) {
        this.appVersion = appVersion;
        return this;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public Device deviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getOs() {
        return os;
    }

    public Device os(String os) {
        this.os = os;
        return this;
    }

    public void setOs(String os) {
        this.os = os;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Device device = (Device) o;
        if (device.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, device.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Device{" +
            "id=" + id +
            ", model='" + model + "'" +
            ", manufacturer='" + manufacturer + "'" +
            ", display='" + display + "'" +
            ", gcmToken='" + gcmToken + "'" +
            ", androidVersion='" + androidVersion + "'" +
            ", created='" + created + "'" +
            ", signedIn='" + signedIn + "'" +
            ", appVersion='" + appVersion + "'" +
            ", deviceId='" + deviceId + "'" +
            ", os='" + os + "'" +
            '}';
    }
}
