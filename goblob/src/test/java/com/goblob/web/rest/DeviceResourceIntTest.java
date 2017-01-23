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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.goblob.web.rest.TestUtil.sameInstant;
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

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_MANUFACTURER = "AAAAAAAAAA";
    private static final String UPDATED_MANUFACTURER = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY = "BBBBBBBBBB";

    private static final String DEFAULT_GCM_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_GCM_TOKEN = "BBBBBBBBBB";

    private static final String DEFAULT_ANDROID_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_ANDROID_VERSION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_SIGNED_IN = "AAAAAAAAAA";
    private static final String UPDATED_SIGNED_IN = "BBBBBBBBBB";

    private static final String DEFAULT_APP_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_APP_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_DEVICE_ID = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_OS = "AAAAAAAAAA";
    private static final String UPDATED_OS = "BBBBBBBBBB";

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
                .model(DEFAULT_MODEL)
                .manufacturer(DEFAULT_MANUFACTURER)
                .display(DEFAULT_DISPLAY)
                .gcmToken(DEFAULT_GCM_TOKEN)
                .androidVersion(DEFAULT_ANDROID_VERSION)
                .created(DEFAULT_CREATED)
                .signedIn(DEFAULT_SIGNED_IN)
                .appVersion(DEFAULT_APP_VERSION)
                .deviceId(DEFAULT_DEVICE_ID)
                .os(DEFAULT_OS);
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
        assertThat(testDevice.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testDevice.getManufacturer()).isEqualTo(DEFAULT_MANUFACTURER);
        assertThat(testDevice.getDisplay()).isEqualTo(DEFAULT_DISPLAY);
        assertThat(testDevice.getGcmToken()).isEqualTo(DEFAULT_GCM_TOKEN);
        assertThat(testDevice.getAndroidVersion()).isEqualTo(DEFAULT_ANDROID_VERSION);
        assertThat(testDevice.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testDevice.getSignedIn()).isEqualTo(DEFAULT_SIGNED_IN);
        assertThat(testDevice.getAppVersion()).isEqualTo(DEFAULT_APP_VERSION);
        assertThat(testDevice.getDeviceId()).isEqualTo(DEFAULT_DEVICE_ID);
        assertThat(testDevice.getOs()).isEqualTo(DEFAULT_OS);
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
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL.toString())))
            .andExpect(jsonPath("$.[*].manufacturer").value(hasItem(DEFAULT_MANUFACTURER.toString())))
            .andExpect(jsonPath("$.[*].display").value(hasItem(DEFAULT_DISPLAY.toString())))
            .andExpect(jsonPath("$.[*].gcmToken").value(hasItem(DEFAULT_GCM_TOKEN.toString())))
            .andExpect(jsonPath("$.[*].androidVersion").value(hasItem(DEFAULT_ANDROID_VERSION.toString())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))))
            .andExpect(jsonPath("$.[*].signedIn").value(hasItem(DEFAULT_SIGNED_IN.toString())))
            .andExpect(jsonPath("$.[*].appVersion").value(hasItem(DEFAULT_APP_VERSION.toString())))
            .andExpect(jsonPath("$.[*].deviceId").value(hasItem(DEFAULT_DEVICE_ID.toString())))
            .andExpect(jsonPath("$.[*].os").value(hasItem(DEFAULT_OS.toString())));
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
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL.toString()))
            .andExpect(jsonPath("$.manufacturer").value(DEFAULT_MANUFACTURER.toString()))
            .andExpect(jsonPath("$.display").value(DEFAULT_DISPLAY.toString()))
            .andExpect(jsonPath("$.gcmToken").value(DEFAULT_GCM_TOKEN.toString()))
            .andExpect(jsonPath("$.androidVersion").value(DEFAULT_ANDROID_VERSION.toString()))
            .andExpect(jsonPath("$.created").value(sameInstant(DEFAULT_CREATED)))
            .andExpect(jsonPath("$.signedIn").value(DEFAULT_SIGNED_IN.toString()))
            .andExpect(jsonPath("$.appVersion").value(DEFAULT_APP_VERSION.toString()))
            .andExpect(jsonPath("$.deviceId").value(DEFAULT_DEVICE_ID.toString()))
            .andExpect(jsonPath("$.os").value(DEFAULT_OS.toString()));
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
                .model(UPDATED_MODEL)
                .manufacturer(UPDATED_MANUFACTURER)
                .display(UPDATED_DISPLAY)
                .gcmToken(UPDATED_GCM_TOKEN)
                .androidVersion(UPDATED_ANDROID_VERSION)
                .created(UPDATED_CREATED)
                .signedIn(UPDATED_SIGNED_IN)
                .appVersion(UPDATED_APP_VERSION)
                .deviceId(UPDATED_DEVICE_ID)
                .os(UPDATED_OS);

        restDeviceMockMvc.perform(put("/api/devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDevice)))
            .andExpect(status().isOk());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeUpdate);
        Device testDevice = deviceList.get(deviceList.size() - 1);
        assertThat(testDevice.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testDevice.getManufacturer()).isEqualTo(UPDATED_MANUFACTURER);
        assertThat(testDevice.getDisplay()).isEqualTo(UPDATED_DISPLAY);
        assertThat(testDevice.getGcmToken()).isEqualTo(UPDATED_GCM_TOKEN);
        assertThat(testDevice.getAndroidVersion()).isEqualTo(UPDATED_ANDROID_VERSION);
        assertThat(testDevice.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testDevice.getSignedIn()).isEqualTo(UPDATED_SIGNED_IN);
        assertThat(testDevice.getAppVersion()).isEqualTo(UPDATED_APP_VERSION);
        assertThat(testDevice.getDeviceId()).isEqualTo(UPDATED_DEVICE_ID);
        assertThat(testDevice.getOs()).isEqualTo(UPDATED_OS);
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
