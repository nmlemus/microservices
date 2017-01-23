package com.goblob.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.goblob.domain.Device;

import com.goblob.repository.DeviceRepository;
import com.goblob.web.rest.util.HeaderUtil;
import com.goblob.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Device.
 */
@RestController
@RequestMapping("/api")
public class DeviceResource {

    private final Logger log = LoggerFactory.getLogger(DeviceResource.class);
        
    @Inject
    private DeviceRepository deviceRepository;

    /**
     * POST  /devices : Create a new device.
     *
     * @param device the device to create
     * @return the ResponseEntity with status 201 (Created) and with body the new device, or with status 400 (Bad Request) if the device has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/devices")
    @Timed
    public ResponseEntity<Device> createDevice(@RequestBody Device device) throws URISyntaxException {
        log.debug("REST request to save Device : {}", device);
        if (device.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("device", "idexists", "A new device cannot already have an ID")).body(null);
        }
        Device result = deviceRepository.save(device);
        return ResponseEntity.created(new URI("/api/devices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("device", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /devices : Updates an existing device.
     *
     * @param device the device to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated device,
     * or with status 400 (Bad Request) if the device is not valid,
     * or with status 500 (Internal Server Error) if the device couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/devices")
    @Timed
    public ResponseEntity<Device> updateDevice(@RequestBody Device device) throws URISyntaxException {
        log.debug("REST request to update Device : {}", device);
        if (device.getId() == null) {
            return createDevice(device);
        }
        Device result = deviceRepository.save(device);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("device", device.getId().toString()))
            .body(result);
    }

    /**
     * GET  /devices : get all the devices.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of devices in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/devices")
    @Timed
    public ResponseEntity<List<Device>> getAllDevices(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Devices");
        Page<Device> page = deviceRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/devices");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /devices/:id : get the "id" device.
     *
     * @param id the id of the device to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the device, or with status 404 (Not Found)
     */
    @GetMapping("/devices/{id}")
    @Timed
    public ResponseEntity<Device> getDevice(@PathVariable Long id) {
        log.debug("REST request to get Device : {}", id);
        Device device = deviceRepository.findOne(id);
        return Optional.ofNullable(device)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /devices/:id : delete the "id" device.
     *
     * @param id the id of the device to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/devices/{id}")
    @Timed
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        log.debug("REST request to delete Device : {}", id);
        deviceRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("device", id.toString())).build();
    }

}
