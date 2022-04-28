package com.microservices.license.controller;

import com.microservices.license.model.License;
import com.microservices.license.service.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping(value = "v1/organization/{organizationId}/license")
public class LicenseController {
    @Autowired
    private LicenseService licenseService;

    @RequestMapping(value = "/{licenseId}", method = RequestMethod.GET)
    public ResponseEntity <License> getLicense(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("licenseId") String licenseId){
        License license = licenseService
                .getLicense(licenseId, organizationId);
        return ResponseEntity.ok(license);
    }

    @RequestMapping(value = "/{licenseId}", method = RequestMethod.PUT)
    public ResponseEntity <String> updateLicense(
            @PathVariable("organizationId") String organizationId,
            @RequestBody License request){
        return ResponseEntity.ok(licenseService.updateLicense(request,organizationId));
    }

    @PostMapping
    public ResponseEntity<String> createLicense(@PathVariable("organizationId") String organizationId, @RequestBody License request,
                                                @RequestHeader(value = "Accept-Language",required = false) Locale locale) {
        return ResponseEntity.ok(licenseService.createLicense(request, organizationId, locale));
    }

    @RequestMapping(value = "/{licenseId}", method = RequestMethod.DELETE)
    public ResponseEntity <String> deleteLicense(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("licenseId") String licenseId){
        return ResponseEntity.ok(licenseService.deleteLicense(licenseId,organizationId));
    }
}
