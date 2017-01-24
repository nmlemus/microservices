package com.goblob.web.rest;

import com.goblob.GoblobApp;

import com.goblob.config.SecurityBeanOverrideConfiguration;

import com.goblob.domain.Device;
import com.goblob.repository.DeviceRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DeviceResource REST controller.
 *
 * @see DeviceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GoblobApp.class, SecurityBeanOverrideConfiguration.class})
public class DeviceResourceIntTest {

    private static final String DEFAULT_OS = "AAAAAAAAAA";
    private static final String UPDATED_OS = "BBBBBBBBBB";

    private static final String DEFAULT_OS_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_OS_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_DEVICE = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_MANUFACTURER = "AAAAAAAAAA";
    private static final String UPDATED_MANUFACTURER = "BBBBBBBBBB";

    private static final String DEFAULT_APP_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_APP_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_DEVICE_ID = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_GCM_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_GCM_TOKEN = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_SIGNED_IN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SIGNED_IN = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DISPLAY = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT = "BBBBBBBBBB";

    private static final String DEFAULT_SERIAL = "AAAAAAAAAA";
    private static final String UPDATED_SERIAL = "BBBBBBBBBB";

    private static final String DEFAULT_SDK_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_SDK_VERSION = "BBBBBBBBBB";

    @Inject
    private DeviceRepository deviceRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restDeviceMockMvc;

    private Device device;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DeviceResource deviceResource = new DeviceResource();
        ReflectionTestUtils.setField(deviceResource, "deviceRepository", deviceRepository);
        this.restDeviceMockMvc = MockMvcBuilders.standaloneSetup(deviceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Device createEntity(EntityManager em) {
        Device device = new Device()
                .os(DEFAULT_OS)
                .osVersion(DEFAULT_OS_VERSION)
                .device(DEFAULT_DEVICE)
                .model(DEFAULT_MODEL)
                .manufacturer(DEFAULT_MANUFACTURER)
                .appVersion(DEFAULT_APP_VERSION)
                .deviceId(DEFAULT_DEVICE_ID)
                .gcmToken(DEFAULT_GCM_TOKEN)
                .created(DEFAULT_CREATED)
                .signedIn(DEFAULT_SIGNED_IN)
                .display(DEFAULT_DISPLAY)
                .product(DEFAULT_PRODUCT)
                .serial(DEFAULT_SERIAL)
                .sdkVersion(DEFAULT_SDK_VERSION);
        return device;
    }

    @Before
    public void initTest() {
        device = createEntity(em);
    }

    @Test
    @Transactional
    public void createDevice() throws Exception {
        int databaseSizeBeforeCreate = deviceRepository.findAll().size();

        // Create the Device

        restDeviceMockMvc.perform(post("/api/devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(device)))
            .andExpect(status().isCreated());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeCreate + 1);
        Device testDevice = deviceList.get(deviceList.size() - 1);
        assertThat(testDevice.getOs()).isEqualTo(DEFAULT_OS);
        assertThat(testDevice.getOsVersion()).isEqualTo(DEFAULT_OS_VERSION);
        assertThat(testDevice.getDevice()).isEqualTo(DEFAULT_DEVICE);
        assertThat(testDevice.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testDevice.getManufacturer()).isEqualTo(DEFAULT_MANUFACTURER);
        assertThat(testDevice.getAppVersion()).isEqualTo(DEFAULT_APP_VERSION);
        assertThat(testDevice.getDeviceId()).isEqualTo(DEFAULT_DEVICE_ID);
        assertThat(testDevice.getGcmToken()).isEqualTo(DEFAULT_GCM_TOKEN);
        assertThat(testDevice.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testDevice.getSignedIn()).isEqualTo(DEFAULT_SIGNED_IN);
        assertThat(testDevice.getDisplay()).isEqualTo(DEFAULT_DISPLAY);
        assertThat(testDevice.getProduct()).isEqualTo(DEFAULT_PRODUCT);
        assertThat(testDevice.getSerial()).isEqualTo(DEFAULT_SERIAL);
        assertThat(testDevice.getSdkVersion()).isEqualTo(DEFAULT_SDK_VERSION);
    }

    @Test
    @Transactional
    public void createDeviceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deviceRepository.findAll().size();

        // Create the Device with an existing ID
        Device existingDevice = new Device();
        existingDevice.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeviceMockMvc.perform(post("/api/devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingDevice)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDevices() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList
        restDeviceMockMvc.perform(get("/api/devices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(device.getId().intValue())))
            .andExpect(jsonPath("$.[*].os").value(hasItem(DEFAULT_OS.toString())))
            .andExpect(jsonPath("$.[*].osVersion").value(hasItem(DEFAULT_OS_VERSION.toString())))
            .andExpect(jsonPath("$.[*].device").value(hasItem(DEFAULT_DEVICE.toString())))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL.toString())))
            .andExpect(jsonPath("$.[*].manufacturer").value(hasItem(DEFAULT_MANUFACTURER.toString())))
            .andExpect(jsonPath("$.[*].appVersion").value(hasItem(DEFAULT_APP_VERSION.toString())))
            .andExpect(jsonPath("$.[*].deviceId").value(hasItem(DEFAULT_DEVICE_ID.toString())))
            .andExpect(jsonPath("$.[*].gcmToken").value(hasItem(DEFAULT_GCM_TOKEN.toString())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].signedIn").value(hasItem(DEFAULT_SIGNED_IN.toString())))
            .andExpect(jsonPath("$.[*].display").value(hasItem(DEFAULT_DISPLAY.toString())))
            .andExpect(jsonPath("$.[*].product").value(hasItem(DEFAULT_PRODUCT.toString())))
            .andExpect(jsonPath("$.[*].serial").value(hasItem(DEFAULT_SERIAL.toString())))
            .andExpect(jsonPath("$.[*].sdkVersion").value(hasItem(DEFAULT_SDK_VERSION.toString())));
    }

    @Test
    @Transactional
    public void getDevice() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get the device
        restDeviceMockMvc.perform(get("/api/devices/{id}", device.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(device.getId().intValue()))
            .andExpect(jsonPath("$.os").value(DEFAULT_OS.toString()))
            .andExpect(jsonPath("$.osVersion").value(DEFAULT_OS_VERSION.toString()))
            .andExpect(jsonPath("$.device").value(DEFAULT_DEVICE.toString()))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL.toString()))
            .andExpect(jsonPath("$.manufacturer").value(DEFAULT_MANUFACTURER.toString()))
            .andExpect(jsonPath("$.appVersion").value(DEFAULT_APP_VERSION.toString()))
            .andExpect(jsonPath("$.deviceId").value(DEFAULT_DEVICE_ID.toString()))
            .andExpect(jsonPath("$.gcmToken").value(DEFAULT_GCM_TOKEN.toString()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.signedIn").value(DEFAULT_SIGNED_IN.toString()))
            .andExpect(jsonPath("$.display").value(DEFAULT_DISPLAY.toString()))
            .andExpect(jsonPath("$.product").value(DEFAULT_PRODUCT.toString()))
            .andExpect(jsonPath("$.serial").value(DEFAULT_SERIAL.toString()))
            .andExpect(jsonPath("$.sdkVersion").value(DEFAULT_SDK_VERSION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDevice() throws Exception {
        // Get the device
        restDeviceMockMvc.perform(get("/api/devices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDevice() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);
        int databaseSizeBeforeUpdate = deviceRepository.findAll().size();

        // Update the device
        Device updatedDevice = deviceRepository.findOne(device.getId());
        updatedDevice
                .os(UPDATED_OS)
                .osVersion(UPDATED_OS_VERSION)
                .device(UPDATED_DEVICE)
                .model(UPDATED_MODEL)
                .manufacturer(UPDATED_MANUFACTURER)
                .appVersion(UPDATED_APP_VERSION)
                .deviceId(UPDATED_DEVICE_ID)
                .gcmToken(UPDATED_GCM_TOKEN)
                .created(UPDATED_CREATED)
                .signedIn(UPDATED_SIGNED_IN)
                .display(UPDATED_DISPLAY)
                .product(UPDATED_PRODUCT)
                .serial(UPDATED_SERIAL)
                .sdkVersion(UPDATED_SDK_VERSION);

        restDeviceMockMvc.perform(put("/api/devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDevice)))
            .andExpect(status().isOk());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeUpdate);
        Device testDevice = deviceList.get(deviceList.size() - 1);
        assertThat(testDevice.getOs()).isEqualTo(UPDATED_OS);
        assertThat(testDevice.getOsVersion()).isEqualTo(UPDATED_OS_VERSION);
        assertThat(testDevice.getDevice()).isEqualTo(UPDATED_DEVICE);
        assertThat(testDevice.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testDevice.getManufacturer()).isEqualTo(UPDATED_MANUFACTURER);
        assertThat(testDevice.getAppVersion()).isEqualTo(UPDATED_APP_VERSION);
        assertThat(testDevice.getDeviceId()).isEqualTo(UPDATED_DEVICE_ID);
        assertThat(testDevice.getGcmToken()).isEqualTo(UPDATED_GCM_TOKEN);
        assertThat(testDevice.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testDevice.getSignedIn()).isEqualTo(UPDATED_SIGNED_IN);
        assertThat(testDevice.getDisplay()).isEqualTo(UPDATED_DISPLAY);
        assertThat(testDevice.getProduct()).isEqualTo(UPDATED_PRODUCT);
        assertThat(testDevice.getSerial()).isEqualTo(UPDATED_SERIAL);
        assertThat(testDevice.getSdkVersion()).isEqualTo(UPDATED_SDK_VERSION);
    }

    @Test
    @Transactional
    public void updateNonExistingDevice() throws Exception {
        int databaseSizeBeforeUpdate = deviceRepository.findAll().size();

        // Create the Device

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDeviceMockMvc.perform(put("/api/devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(device)))
            .andExpect(status().isCreated());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDevice() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);
        int databaseSizeBeforeDelete = deviceRepository.findAll().size();

        // Get the device
        restDeviceMockMvc.perform(delete("/api/devices/{id}", device.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
