package com.goblob.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
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

    @Column(name = "os")
    private String os;

    @Column(name = "os_version")
    private String osVersion;

    @Column(name = "device")
    private String device;

    @Column(name = "model")
    private String model;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "app_version")
    private String appVersion;

    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "gcm_token")
    private String gcmToken;

    @Column(name = "created")
    private LocalDate created;

    @Column(name = "signed_in")
    private LocalDate signedIn;

    @Column(name = "display")
    private String display;

    @Column(name = "product")
    private String product;

    @Column(name = "serial")
    private String serial;

    @Column(name = "sdk_version")
    private String sdkVersion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getOsVersion() {
        return osVersion;
    }

    public Device osVersion(String osVersion) {
        this.osVersion = osVersion;
        return this;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getDevice() {
        return device;
    }

    public Device device(String device) {
        this.device = device;
        return this;
    }

    public void setDevice(String device) {
        this.device = device;
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

    public LocalDate getCreated() {
        return created;
    }

    public Device created(LocalDate created) {
        this.created = created;
        return this;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public LocalDate getSignedIn() {
        return signedIn;
    }

    public Device signedIn(LocalDate signedIn) {
        this.signedIn = signedIn;
        return this;
    }

    public void setSignedIn(LocalDate signedIn) {
        this.signedIn = signedIn;
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

    public String getProduct() {
        return product;
    }

    public Device product(String product) {
        this.product = product;
        return this;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getSerial() {
        return serial;
    }

    public Device serial(String serial) {
        this.serial = serial;
        return this;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getSdkVersion() {
        return sdkVersion;
    }

    public Device sdkVersion(String sdkVersion) {
        this.sdkVersion = sdkVersion;
        return this;
    }

    public void setSdkVersion(String sdkVersion) {
        this.sdkVersion = sdkVersion;
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
            ", os='" + os + "'" +
            ", osVersion='" + osVersion + "'" +
            ", device='" + device + "'" +
            ", model='" + model + "'" +
            ", manufacturer='" + manufacturer + "'" +
            ", appVersion='" + appVersion + "'" +
            ", deviceId='" + deviceId + "'" +
            ", gcmToken='" + gcmToken + "'" +
            ", created='" + created + "'" +
            ", signedIn='" + signedIn + "'" +
            ", display='" + display + "'" +
            ", product='" + product + "'" +
            ", serial='" + serial + "'" +
            ", sdkVersion='" + sdkVersion + "'" +
            '}';
    }
}
